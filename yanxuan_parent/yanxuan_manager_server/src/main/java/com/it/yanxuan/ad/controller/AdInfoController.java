package com.it.yanxuan.ad.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.ad.api.IAdInfoService;
import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 广告详情控制
 * @author aaaa
 */
@RestController
@RequestMapping("/adInfo")
public class AdInfoController {

    @Reference
    private IAdInfoService adInfoService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param adInfo
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<AdInfo>> pageQuery(Integer pageNum, Integer pageSize, AdInfo adInfo) {
        //初始化参数
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //调用远程服务，查询
        PageResult<AdInfo> pageResult = adInfoService.pageQuery(pageNum, pageSize, adInfo);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    /**
     * 新增数据
     * @param adInfo
     * @return
     */
    @PostMapping
    public ResponseEntity save(@RequestBody AdInfo adInfo) {
        int result = adInfoService.save(adInfo);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改信息
     * @param adInfo
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody AdInfo adInfo) {
        int result = adInfoService.update(adInfo);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键删除记录
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        int result = adInfoService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdInfo> queryById(@PathVariable Long id) {
        AdInfo adInfo = adInfoService.queryById(id);
        return new ResponseEntity<>(adInfo, HttpStatus.OK);
    }

}
