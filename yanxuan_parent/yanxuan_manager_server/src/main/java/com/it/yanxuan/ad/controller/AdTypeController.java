package com.it.yanxuan.ad.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.ad.api.IAdTypeService;
import com.it.yanxuan.model.AdType;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 广告管理控制器
 * @author aaaa
 */
@RestController
@RequestMapping("/adType")
public class AdTypeController {
    @Reference
    private IAdTypeService adTypeService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param adType
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<AdType>> query(Integer pageNum, Integer pageSize, AdType adType) {
        //参数初始化
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //调用远程服务，查询
        PageResult<AdType> pageResult = adTypeService.pageQuery(pageNum, pageSize,adType);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    /**
     * 保存广告类型信息
     * @param adType
     * @return
     */
    @PostMapping
    public ResponseEntity save(@RequestBody AdType adType) {
        int result = adTypeService.insert(adType);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        int result = adTypeService.deleteById(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 更新类型信息
     * @param adType
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody AdType adType) {
        int result = adTypeService.update(adType);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
