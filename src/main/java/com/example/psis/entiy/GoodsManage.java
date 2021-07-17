package com.example.psis.entiy;

import com.example.psis.proxy.GoodsManageProxy;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Drill;
import xyz.erupt.annotation.sub_erupt.Link;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.AttachmentType;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.*;


/**
 * 左树右表商品管理
 */
@Getter
@Setter
@Erupt(
        name = "货品管理",
        linkTree = @LinkTree(field = "goodsType", dependNode = true),//关联分类树
        drills = {
                @Drill(
                        title = "规格",
                        link = @Link(linkErupt = GoodsSku.class,  //关联表
                                joinColumn = "goodsManage.id"))  //关联表达式
        },
        dataProxy = GoodsManageProxy.class
)
@Table(name = "psis_goods_manage")
@Entity
public class GoodsManage extends HyperModel {

    //关联树字段
    @ManyToOne
    private GoodsType goodsType;


    @EruptField(
            views = @View(title = "货物图片"),
            edit = @Edit(title = "货物图片", type = EditType.ATTACHMENT, notNull = true,
                    attachmentType = @AttachmentType(type = AttachmentType.Type.IMAGE, maxLimit = 3))
    )
    private String good_pic;

    @Transient //由于该字段不需要持久化，所以使用该注解修饰
    @EruptField(
            edit = @Edit(title = "", type = EditType.DIVIDE)
    )
    private String divide1;

    @EruptField(
            views = @View(title = "货物名称"),
            edit = @Edit(title = "货物名称", notNull = true, search = @Search(vague = true))
    )
    private String good_name;

    @EruptField(
            views = @View(title = "库存"),
            edit = @Edit(title = "库存", show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private Integer inventory;

    @EruptField(
            views = @View(title = "销量"),
            edit = @Edit(title = "销量", show = false, readonly = @Readonly, search = @Search(vague = true))
    )
    private Integer sales;

    @Transient //由于该字段不需要持久化，所以使用该注解修饰
    @EruptField(
            edit = @Edit(title = "", type = EditType.DIVIDE)
    )
    private String divide3;

    @Lob
    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

}
