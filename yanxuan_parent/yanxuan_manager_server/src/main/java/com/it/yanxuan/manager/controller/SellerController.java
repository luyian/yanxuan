package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.seller.api.ISellerInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 商家管理处理器
 * @author aaaa
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private ISellerInfoService sellerInfoService;

    /**
     * 分页查询商家信息
     * @param pageNum
     * @param pageSize
     * @param sellerShop
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult> pageQuery(Integer pageNum, Integer pageSize, SellerShop sellerShop) {
        //分页参数的处理
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }

        //调用远程服务，查询商家信息
        PageResult<SellerShop> pageResult = sellerInfoService.pageQuery(pageNum, pageSize, sellerShop);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    /**
     * 更新状态
     * @param sellerShop
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody SellerShop sellerShop) {
        //调用远程服务，更新商家的状态
        int result = sellerInfoService.update(sellerShop);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity((HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
