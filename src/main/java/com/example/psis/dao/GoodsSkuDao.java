package com.example.psis.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "psis_goods_sku")
public class GoodsSkuDao {
    private Long id;
    private String productName;
    private String name;
    private BigDecimal skuPrice;
    private Integer inventory;
    private Integer sales;
    private Long goodsManageId;
}
