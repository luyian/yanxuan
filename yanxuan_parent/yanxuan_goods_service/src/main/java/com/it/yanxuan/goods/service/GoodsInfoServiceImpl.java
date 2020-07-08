package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.it.yanxuan.goods.api.IGoodsInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品管理业务实现
 * @author aaaa
 */
@Service
@Transactional
public class GoodsInfoServiceImpl implements IGoodsInfoService {
}
