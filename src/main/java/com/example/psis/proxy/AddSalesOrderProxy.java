package com.example.psis.proxy;

import com.example.psis.Mapper.GoodsSkuMapper;
import com.example.psis.Mapper.GoodsSkuTempMapper;
import com.example.psis.dao.GoodsSkuDao;
import com.example.psis.dao.GoodsSkuTempDao;
import com.example.psis.entiy.GoodsSkuTemp;
import com.example.psis.entiy.SalesOrder;
import com.example.psis.utils.GetCodeUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.core.exception.ExceptionAnsi;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AddSalesOrderProxy implements DataProxy<SalesOrder> {

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private GoodsSkuTempMapper goodsSkuTempMapper;


    @Override
    public void beforeAdd(SalesOrder salesOrder) {
        //region 添加订单之之前获取订单号
        String code = GetCodeUtils.getOrderCode();
        salesOrder.setCode(code);
        //endregion
        List<GoodsSkuTemp> skus = salesOrder.getSkus();
        BigDecimal totalPrice = new BigDecimal(0);
        for (GoodsSkuTemp goodsSkuTemp : skus) {
            GoodsSkuDao goodsSkuDao = goodsSkuMapper.selectById(goodsSkuTemp.getSkus().getId());

            //region 将商品信息赋值
            goodsSkuTemp.setProduct_name(goodsSkuDao.getProductName());
            goodsSkuTemp.setName(goodsSkuDao.getName());
            goodsSkuTemp.setPrice(goodsSkuDao.getSkuPrice());
            //endregion

            //region 对订单商品价格进行累加
            //累加订单价格
            BigDecimal price = goodsSkuDao.getSkuPrice().multiply(new BigDecimal(goodsSkuTemp.getNum()));
            totalPrice = totalPrice.add(price);
            //endregion

            //region 扣减库存
            if (Objects.isNull(goodsSkuDao.getId())){
                throw  new EruptWebApiRuntimeException(String.format("请先设置%s %s 的库存",goodsSkuDao.getProductName(),goodsSkuDao.getName()));
            }
            if (goodsSkuDao.getInventory() - goodsSkuTemp.getNum() < 0){
                throw  new EruptWebApiRuntimeException(String.format("货品%s %s 的库存不足,请先核对",goodsSkuDao.getProductName(),goodsSkuDao.getName()));
            }
            goodsSkuDao.setInventory(goodsSkuDao.getInventory() - goodsSkuTemp.getNum());
            goodsSkuDao.setSales(Objects.isNull(goodsSkuDao.getSales()) ? goodsSkuTemp.getNum() : goodsSkuDao.getSales() + goodsSkuTemp.getNum());
            goodsSkuMapper.updateById(goodsSkuDao);
            //endregion

        }
        salesOrder.setTotalPrice(totalPrice);

    }

    @Override
    public void beforeUpdate(SalesOrder salesOrder) {
        //region 将商品信息赋值
        List<GoodsSkuTemp> skus = salesOrder.getSkus();

        BigDecimal totalPrice = new BigDecimal(0);
        for (GoodsSkuTemp goodsSkuTemp : skus) {
            GoodsSkuDao goodsSkuDao = goodsSkuMapper.selectById(goodsSkuTemp.getSkus().getId());
            goodsSkuTemp.setProduct_name(goodsSkuDao.getProductName());
            goodsSkuTemp.setName(goodsSkuDao.getName());
            goodsSkuTemp.setPrice(goodsSkuDao.getSkuPrice());

            //region 扣减库存
            if (Objects.isNull(goodsSkuDao.getInventory())){
                throw  new EruptWebApiRuntimeException(String.format("请先设置%s %s 的库存",goodsSkuDao.getProductName(),goodsSkuDao.getName()));
            }
            if (goodsSkuDao.getInventory() - goodsSkuTemp.getNum() < 0){
                throw  new EruptWebApiRuntimeException(String.format("货品%s %s 的库存不足,请先核对",goodsSkuDao.getProductName(),goodsSkuDao.getName()));
            }

            //查询原订单中商品数量
            GoodsSkuTempDao dbGoodsSkuTemp = goodsSkuTempMapper.selectById(goodsSkuTemp.getId());
            //计算相差的商品数量
            Integer num =  dbGoodsSkuTemp.getNum() - goodsSkuTemp.getNum();
            //库存 = 原有库存+差异数量
            goodsSkuDao.setInventory(goodsSkuDao.getInventory() + num);
            if (goodsSkuDao.getInventory() < 0){
                throw  new EruptWebApiRuntimeException(String.format("货品%s %s 的库存不足,请先核对",goodsSkuDao.getProductName(),goodsSkuDao.getName()));
            }
            //销量 = 原有销量 - 差异数量
            goodsSkuDao.setSales(goodsSkuDao.getSales() - num);

            goodsSkuMapper.updateById(goodsSkuDao);
            //endregion

            //region 重新计算订单总价
            //累加订单价格
            BigDecimal price = goodsSkuDao.getSkuPrice().multiply(new BigDecimal(goodsSkuTemp.getNum()));
            totalPrice = totalPrice.add(price);
            //endregion
        }
        salesOrder.setTotalPrice(totalPrice);

    }

    @Override
    public String beforeFetch(Class<?> eruptClass) {
        return DataProxy.super.beforeFetch(eruptClass);
    }

    @Override
    public void afterFetch(Collection<Map<String, Object>> list) {
        DataProxy.super.afterFetch(list);
    }
}
