package com.example.psis.entiy;


import com.example.psis.proxy.GoodsSkuProxy;
import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.AttachmentType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Erupt( name = "货品规格",
        dataProxy = GoodsSkuProxy.class)
@Table(name = "psis_goods_sku")
@Entity
public class GoodsSku extends HyperModel {

    @ManyToOne
    private GoodsManage goodsManage;

    @EruptField(
            views = @View(title = "货物图片"),
            edit = @Edit(title = "货物图片", type = EditType.ATTACHMENT, notNull = true,
                    attachmentType = @AttachmentType(type = AttachmentType.Type.IMAGE, maxLimit = 3))
    )
    private String good_sku_pic;

    @EruptField(
            views = @View(title = "货品名称"),
            edit = @Edit(title = "货品名称",show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private String product_name;

    @EruptField(
            views = @View(title = "规格名称"),
            edit = @Edit(title = "规格名称", notNull = true, search = @Search(vague = true))
    )
    private String name;


    @EruptField(
            views = @View(title = "价格"),
            edit = @Edit(title = "价格", notNull = true)
    )
    private BigDecimal skuPrice;

    @EruptField(
            views = @View(title = "库存"),
            edit = @Edit(title = "库存", notNull = true, search = @Search(vague = true))
    )
    private Integer inventory;

    @EruptField(
            views = @View(title = "销量"),
            edit = @Edit(title = "销量", show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private Integer sales;

    @Lob
    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;
}
