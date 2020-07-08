package com.it.yanxuan.seller.api;

import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.SellerInfo;

/**
 * 商家入驻业务接口
 * @author aaaa
 */
public interface ISellerInfoService {

    /**
     * 保存商家入驻的信息
     * @param sellerInfo
     * @return
     */
    public int save(SellerInfo sellerInfo);

    /**
     * 分页查询商家信息
     * @param pageNum
     * @param pageSize
     * @param sellerShop
     * @return
     */
    PageResult<SellerShop> pageQuery(Integer pageNum, Integer pageSize, SellerShop sellerShop);

    /**
     * 根据主键更新状态
     * @param sellerShop
     * @return
     */
    int update(SellerShop sellerShop);
}
