package com.example.psis.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "psis_goods_manage")
public class GoodsManageDao {
    private Long id;
    private String goodName;
    private String goodPic;
    private Integer goodState;
    private String remark;
    private Long goodsTypeId;
    private Integer inventory;
    private Integer sales;
}
