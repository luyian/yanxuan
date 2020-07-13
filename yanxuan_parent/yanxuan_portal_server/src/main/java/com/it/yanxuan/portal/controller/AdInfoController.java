package com.it.yanxuan.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.ad.api.IAdInfoService;
import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广告查询控制器
 * @author aaaa
 */
@RestController
@RequestMapping("/adInfo")
public class AdInfoController {

    @Reference
    private IAdInfoService adInfoService;

    /**
     * 根据条件查询所有广告信息
     * @param adInfo
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<AdInfo>> query(AdInfo adInfo) {
        //初始化参数
        Integer pageNum = 1;
        Integer pageSize = Integer.MAX_VALUE;

        //调用远程服务，查询
        PageResult<AdInfo> pageResult = adInfoService.pageQuery(pageNum, pageSize, adInfo);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }
}
