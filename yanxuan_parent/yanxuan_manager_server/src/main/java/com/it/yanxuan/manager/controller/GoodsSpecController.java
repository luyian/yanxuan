package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsSpecService;
import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 商品规格处理器
 * @author aaaa
 */
@RestController
@RequestMapping("/spec")
public class GoodsSpecController {
    @Reference
    private IGoodsSpecService goodsSpecService;

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Specification> getById(@PathVariable("id") Long id) {
        Specification specification = goodsSpecService.getById(id);
        return new ResponseEntity<>(specification, HttpStatus.OK);
    }

    /**
     * 分页查询商品规格
     * @param pageNum
     * @param pageSize
     * @param goodsSpec
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<GoodsSpec>> pageQuery(Integer pageNum, Integer pageSize, GoodsSpec goodsSpec) {
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //调用远程服务，获取分页查询结果
        PageResult<GoodsSpec> pageResult = goodsSpecService.pageQuery(pageNum, pageSize, goodsSpec);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    /**
     * 保存商品规格
     * @param spec
     * @return
     */
    @PostMapping
    public ResponseEntity save(@RequestBody Specification spec) {
        //调用远程服务，保存数据
        int result = goodsSpecService.save(spec);
        if (result > 0) {
            //保存成功
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据id删除规格信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        //调用远程服务删除数据
        int result = goodsSpecService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 更新规格信息
     * @param specification
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Specification specification) {
        int result = goodsSpecService.update(specification);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
