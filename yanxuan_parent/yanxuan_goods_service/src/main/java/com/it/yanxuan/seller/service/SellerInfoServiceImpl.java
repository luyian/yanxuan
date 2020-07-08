package com.it.yanxuan.seller.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.mapper.AccountMapper;
import com.it.yanxuan.mapper.SellerShopMapper;
import com.it.yanxuan.model.Account;
import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.SellerShopExample;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.seller.api.ISellerInfoService;
import com.it.yanxuan.viewmodel.SellerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商家入驻业务处理层
 * @author aaaa
 */
@Service
@Transactional
public class SellerInfoServiceImpl implements ISellerInfoService {

    @Autowired
    private SellerShopMapper sellerShopMapper;
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 保存商家入驻信息
     *  先保存account，再保存sellerShop
     * @param sellerInfo
     * @return
     */
    @Override
    public int save(SellerInfo sellerInfo) {
        //创建account对象
        Account account = new Account();
        account.setLoginName(sellerInfo.getLoginName());
        //对密码进行加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String bcPassword = passwordEncoder.encode(sellerInfo.getPassword());
        account.setPassword(bcPassword);

        account.setPhone(sellerInfo.getLinkmanPhone());
        account.setEmail(sellerInfo.getLinkmanEmail());
        account.setStatus("0");
        //保存account信息
        accountMapper.insert(account);

        //保存sellerShop
        sellerInfo.setAccountId(account.getId());
        //设置状态为“待审核”
        sellerInfo.setStatus("0");
        int result = sellerShopMapper.insert(sellerInfo);
        return result;
    }

    /**
     * 分页查询商家信息
     * @param pageNum
     * @param pageSize
     * @param sellerShop
     * @return
     */
    @Override
    public PageResult<SellerShop> pageQuery(Integer pageNum, Integer pageSize, SellerShop sellerShop) {
        //开启分页插件
        PageHelper.startPage(pageNum, pageSize);
        //准备分页数据
        PageResult<SellerShop> pageResult = new PageResult<>();
        SellerShopExample sellerShopExample = new SellerShopExample();
        SellerShopExample.Criteria criteria = sellerShopExample.createCriteria();
        if (sellerShop != null) {
            if (sellerShop.getName() != null && !"".equals(sellerShop.getName())) {
                criteria.andNameLike("%" + sellerShop.getName() + "%");
            }
            if (sellerShop.getStatus() != null && !"".equals(sellerShop.getStatus())) {
                criteria.andStatusEqualTo(sellerShop.getStatus());
            }
        }
        Page<SellerShop> page =
                (Page<SellerShop>) sellerShopMapper.selectByExample(sellerShopExample);
        pageResult.setResult(page.getResult());
        pageResult.setTotal(page.getTotal());

        return pageResult;
    }

    /**
     * 根据主键更新状态
     * @param sellerShop
     * @return
     */
    @Override
    public int update(SellerShop sellerShop) {
        int update = sellerShopMapper.updateByPrimaryKeySelective(sellerShop);
        return update;
    }
}
