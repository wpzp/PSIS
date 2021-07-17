package com.example.psis.entiy;


import com.example.psis.proxy.AddSalesOrderProxy;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Erupt(
        name = "开单管理",
        dataProxy = AddSalesOrderProxy.class
)
@Table(name = "psis_sales_order")
@Entity
public class SalesOrder extends HyperModel  {

    @EruptField(
            views = @View(title = "订单号"),
            edit = @Edit(title = "订单号", show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "客户名称"),
            edit = @Edit(title = "客户名称", search = @Search(vague = true))
    )
    private String customer_name;

    @EruptField(
            views = @View(title = "联系人"),
            edit = @Edit(title = "联系人")
    )
    private String customer_mobil;

    @EruptField(
            views = @View(title = "联系方式"),
            edit = @Edit(title = "联系方式")
    )
    private String customer_address;

    @EruptField(
            views = @View(title = "开单时间"),
            edit = @Edit(title = "开单时间", search = @Search(vague = true), show = false, readonly = @Readonly)
    )
    private Date createTime;

    @EruptField(
            views = @View(title = "订单总价"),
            edit = @Edit(title = "订单总价",show = false, readonly = @Readonly)
    )
    private BigDecimal totalPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //一对多，且开启级联
    @JoinColumn(name = "sales_order_id")
    @OrderBy
    @EruptField(
            edit = @Edit(title = "选择规格详情" ,type = EditType.TAB_TABLE_ADD)
    )
    private List<GoodsSkuTemp> skus;

}
