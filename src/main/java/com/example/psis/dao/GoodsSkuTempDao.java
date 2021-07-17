package com.example.psis.dao;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "psis_sales_order_sku_temp")
public class GoodsSkuTempDao {

    private Long id;
    private Integer num;
    private String name;
    private String productName;
}
