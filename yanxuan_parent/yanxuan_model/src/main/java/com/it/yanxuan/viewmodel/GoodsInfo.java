package com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.GoodsSku;
import com.it.yanxuan.model.GoodsSpu;

import java.util.List;

/**
 * 封装商品信息
 * @author aaaa
 */
public class GoodsInfo extends GoodsSpu {
    private List<GoodsSku> skuList;

    public List<GoodsSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<GoodsSku> skuList) {
        this.skuList = skuList;
    }
}
