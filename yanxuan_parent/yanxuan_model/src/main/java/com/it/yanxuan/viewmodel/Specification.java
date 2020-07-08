package com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.model.GoodsSpecOption;

import java.util.List;

/**
 * 商品规格的包装类，封装规格的信息
 * @author aaaa
 */
public class Specification extends GoodsSpec {
    private List<GoodsSpecOption> optionList;

    public List<GoodsSpecOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<GoodsSpecOption> optionList) {
        this.optionList = optionList;
    }
}
