package com.example.psis.proxy;

import com.example.psis.Mapper.GoodsManageMapper;
import com.example.psis.Mapper.GoodsSkuMapper;
import com.example.psis.dao.GoodsManageDao;
import com.example.psis.dao.GoodsSkuDao;
import com.example.psis.entiy.GoodsSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;

@Service
public class GoodsSkuProxy implements DataProxy<GoodsSku> {

    @Autowired
    private GoodsManageMapper goodsManageMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Override
    public void beforeAdd(GoodsSku goodsSku) {
        DataProxy.super.beforeAdd(goodsSku);
    }

    @Override
    public void afterAdd(GoodsSku goodsSku) {
        //region 规格添加后 追加商品名称
        GoodsManageDao goodsManageDao = goodsManageMapper.selectById(goodsSku.getGoodsManage().getId());
        GoodsSkuDao goodsSkuDao = new GoodsSkuDao();
        goodsSkuDao.setId(goodsSku.getId());
        goodsSkuDao.setProductName(goodsManageDao.getGoodName());
        goodsSkuMapper.updateById(goodsSkuDao);
        //endregion
    }
}
