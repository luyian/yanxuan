package com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.SellerShop;

/**
 * 封装商家入驻信息
 * @author aaaa
 */
public class SellerInfo extends SellerShop {

    private String loginName;
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
