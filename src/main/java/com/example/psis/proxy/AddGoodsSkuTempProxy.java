package com.example.psis.proxy;

import com.example.psis.Mapper.GoodsSkuMapper;
import com.example.psis.dao.GoodsSkuDao;
import com.example.psis.entiy.GoodsSkuTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;

import java.util.Collection;
import java.util.Map;

@Service
public class AddGoodsSkuTempProxy implements DataProxy<GoodsSkuTemp> {

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Override
    public void beforeAdd(GoodsSkuTemp goodsSkuTemp) {
//        GoodsSkuDao goodsSkuDao = goodsSkuMapper.selectById(goodsSkuTemp.getSkus().getId());
//        goodsSkuTemp.setProduct_name(goodsSkuDao.getProductName());
//        goodsSkuTemp.setName(goodsSkuDao.getName());


    }



    @Override
    public void afterAdd(GoodsSkuTemp goodsSkuTemp) {
//        GoodsSkuDao goodsSkuDao = goodsSkuMapper.selectById(goodsSkuTemp.getSkus().getId());
//        goodsSkuTemp.setProduct_name(goodsSkuDao.getProductName());
//        goodsSkuTemp.setName(goodsSkuDao.getName());

    }

    @Override
    public void afterFetch(Collection<Map<String, Object>> list) {
        DataProxy.super.afterFetch(list);
    }

    @Override
    public String beforeFetch(Class<?> eruptClass) {
        return DataProxy.super.beforeFetch(eruptClass);
    }
}
