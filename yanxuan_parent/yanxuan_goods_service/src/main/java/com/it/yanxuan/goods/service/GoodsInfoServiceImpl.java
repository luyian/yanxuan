package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.it.yanxuan.goods.api.IGoodsInfoService;
import com.it.yanxuan.mapper.*;
import com.it.yanxuan.model.*;
import com.it.yanxuan.viewmodel.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理业务实现
 * @author aaaa
 */
@Service
@Transactional
public class GoodsInfoServiceImpl implements IGoodsInfoService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SellerShopMapper sellerShopMapper;
    @Autowired
    private GoodsSpuMapper goodsSpuMapper;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    /**
     * 保存商品信息
     * @param goodsInfo
     * @return
     */
    @Override
    public int save(GoodsInfo goodsInfo) {
        //根据登陆人的用户名，获取得到店铺的名称
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andLoginNameEqualTo(goodsInfo.getCreatePerson());
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        Account account = null;
        //判断，如果不为空
        if (accountList != null && accountList.size() > 0) {
            account = accountList.get(0);
        }

        //查询店铺的信息
        SellerShopExample sellerShopExample = new SellerShopExample();
        SellerShop sellerShop = null;
        if (account != null) {
            sellerShopExample.createCriteria().andAccountIdEqualTo(account.getId());
        }
        List<SellerShop> sellerShopList = sellerShopMapper.selectByExample(sellerShopExample);
        if (sellerShopList != null && sellerShopList.size() > 0) {
            sellerShop = sellerShopList.get(0);
        }

        //设置店铺信息
        if (sellerShop != null) {
            goodsInfo.setSellerId(sellerShop.getId());
            goodsInfo.setSellerName(sellerShop.getName());
        }

        //保存商品SPU信息
        //设置商品状态为新增
        goodsInfo.setStatus("0");
        int insert = goodsSpuMapper.insert(goodsInfo);

        //查询品牌名称
        GoodsBrand goodsBrand = goodsBrandMapper.selectByPrimaryKey(goodsInfo.getBrandId());
        //查询类目的信息
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goodsInfo.getCategory3Id());
        //保存SKU信息
        List<GoodsSku> skuList = goodsInfo.getSkuList();
        for (GoodsSku sku : skuList) {
            //设置goodsId
            sku.setGoodsId(goodsInfo.getId());
            //设置label
            sku.setLabel(goodsInfo.getLabel());
            //设置品牌的名称
            sku.setBrandName(goodsBrand.getName());
            //设置类目名称
            sku.setCategoryName(goodsCategory.getStructName() + ">" + goodsCategory.getName());
            //设置商铺的信息
            assert sellerShop != null;
            sku.setSellerId(sellerShop.getId());
            sku.setSellerName(sellerShop.getName());
            sku.setCreatePerson(sellerShop.getLinkmanName());
            //设置状态新增
            sku.setOnSale("0");
            sku.setStatus("0");
            goodsSkuMapper.insert(sku);
        }
        return insert;
    }
}
