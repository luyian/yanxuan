package com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.model.GoodsCategoryBrandSpec;

import java.util.List;
import java.util.Map;


/**
 * 封装类目的相关信息
 * @author aaaa
 */
public class Category extends GoodsCategory {
    /**
     * 关联的品牌、规格信息
     */
    private GoodsCategoryBrandSpec relation;
    /**
     * 类目关联的规格和规格项信息
     */
    private List<Map> specList;

    public List<Map> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Map> specList) {
        this.specList = specList;
    }

    public GoodsCategoryBrandSpec getRelation() {
        return relation;
    }

    public void setRelation(GoodsCategoryBrandSpec relation) {
        this.relation = relation;
    }
}
