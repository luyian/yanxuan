package com.it.yanxuan.goods.api;

import com.it.yanxuan.viewmodel.GoodsInfo;

/**
 * 商品管理业务接口
 * @author aaaa
 */
public interface IGoodsInfoService {
    /**
     * 保存商品信息
     * @param goodsInfo
     * @return
     */
    int save(GoodsInfo goodsInfo);
}
