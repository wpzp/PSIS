package com.example.psis.proxy;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.psis.Mapper.GoodsSkuMapper;
import com.example.psis.dao.GoodsSkuDao;
import com.example.psis.entiy.GoodsManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsManageProxy implements DataProxy<GoodsManage> {

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Override
    public void beforeAdd(GoodsManage goodsManage) {
        System.out.println(JSONObject.toJSONString(goodsManage));
        DataProxy.super.beforeAdd(goodsManage);
    }

    /**
     * 在显示商品之前查询显示销量和库存
     * @param list
     */
    @Override
    public void afterFetch(Collection<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            QueryWrapper<GoodsSkuDao> queryWrapper = new QueryWrapper<GoodsSkuDao>();
            List<GoodsSkuDao> dbList = goodsSkuMapper.selectList(queryWrapper.eq("goods_manage_id", map.get("id")));
            System.out.println(JSONObject.toJSONString(dbList));
            Integer inventory = dbList.stream().collect(Collectors.summingInt(GoodsSkuDao::getInventory));
            Integer sales = dbList.stream().filter(s -> !Objects.isNull(s.getSales())).collect(Collectors.summingInt(GoodsSkuDao::getSales));
            map.put("sales",sales);
            map.put("inventory",inventory);
        }
    }
}
