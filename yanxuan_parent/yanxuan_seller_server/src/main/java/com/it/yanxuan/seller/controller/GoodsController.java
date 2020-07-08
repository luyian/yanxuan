package com.it.yanxuan.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsInfoService;
import com.it.yanxuan.viewmodel.GoodsInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品管理控制器
 * @author aaaa
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsInfoService goodsInfoService;

    /**
     * 保存商品信息
     * @param goodsInfo
     * @return
     */
    @PostMapping
    public ResponseEntity save(@RequestBody GoodsInfo goodsInfo) {
        //获取当前登陆人的名称
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        goodsInfo.setCreatePerson(username);
        //调用远程服务完成商品信息的保存
        int result = goodsInfoService.save(goodsInfo);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
