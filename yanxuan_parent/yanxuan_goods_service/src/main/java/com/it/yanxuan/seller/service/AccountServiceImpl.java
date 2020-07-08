package com.it.yanxuan.seller.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.it.yanxuan.mapper.AccountMapper;
import com.it.yanxuan.mapper.SellerShopMapper;
import com.it.yanxuan.model.Account;
import com.it.yanxuan.model.AccountExample;
import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.SellerShopExample;
import com.it.yanxuan.seller.api.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业认证登录
 * @author aaaa
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SellerShopMapper sellerShopMapper;

    /**
     * 根据用户名查询企业用户信息
     * @param username
     * @return
     */
    @Override
    public Account queryByUserName(String username) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andLoginNameEqualTo(username);
        //查询
        List<Account> accounts = accountMapper.selectByExample(accountExample);

        //找出关联店铺的account
        for (Account account : accounts) {
            //根据account的id查询sellerShop的信息
            SellerShopExample sellerShopExample = new SellerShopExample();
            sellerShopExample.createCriteria().andAccountIdEqualTo(account.getId());
            List<SellerShop> sellerShops = sellerShopMapper.selectByExample(sellerShopExample);
            //如果店铺是审批通过状态，返回账户信息
            if (sellerShops.size() > 0) {
                if (sellerShops.get(0).getStatus().equals("1")) {
                    return account;
                }
            }
        }
        return null;
    }
}
