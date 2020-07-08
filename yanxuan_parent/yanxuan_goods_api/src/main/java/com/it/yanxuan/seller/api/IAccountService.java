package com.it.yanxuan.seller.api;

import com.it.yanxuan.model.Account;

/**
 * 用户登录认证
 * @author aaaa
 */
public interface IAccountService {

    /**
     * 根据用户名称，查询企业用户信息
     * @param username
     * @return
     */
    Account queryByUserName(String username);
}
