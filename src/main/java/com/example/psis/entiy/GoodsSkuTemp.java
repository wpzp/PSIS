package com.example.psis.entiy;


import com.example.psis.proxy.AddGoodsSkuTempProxy;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Erupt(
        name = "选择sku 中间状态",
        dataProxy = AddGoodsSkuTempProxy.class
)
@Table(name = "psis_sales_order_sku_temp")
@Entity
public class GoodsSkuTemp extends HyperModel {

    @ManyToOne
    @EruptField(
            edit = @Edit(title = "选择规格", type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(id = "id", label = "name")
            )
    )
    private GoodsSku skus;

    @EruptField(
            views = @View(title = "货品名称"),
            edit = @Edit(title = "货品名称",show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private String product_name;

    @EruptField(
            views = @View(title = "规格名称"),
            edit = @Edit(title = "规格名称", show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "商品单价"),
            edit = @Edit(title = "商品单价", show = false, readonly = @Readonly)
    )
    private BigDecimal price;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", notNull = true)
    )
    private Integer num;


}
