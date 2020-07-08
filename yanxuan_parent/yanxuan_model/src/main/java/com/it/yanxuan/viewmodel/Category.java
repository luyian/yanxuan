package com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.model.GoodsCategoryBrandSpec;


/**
 * 封装类目的相关信息
 * @author aaaa
 */
public class Category extends GoodsCategory {
    /**
     * 关联的品牌、规格信息
     */
    private GoodsCategoryBrandSpec relation;

    public GoodsCategoryBrandSpec getRelation() {
        return relation;
    }

    public void setRelation(GoodsCategoryBrandSpec relation) {
        this.relation = relation;
    }
}
