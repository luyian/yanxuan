package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsCategoryService;
import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 类目管理控制器
 * @author aaaa
 */
@RestController
@RequestMapping("/category")
public class GoodsCategoryController {
    @Reference
    private IGoodsCategoryService goodsCategoryService;

    /**
     * 分页查询类目信息
     * @param pageNum
     * @param pageSize
     * @param goodsCategory
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<GoodsCategory>> query(Integer pageNum, Integer pageSize, GoodsCategory goodsCategory) {
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //调用远程服务，完成查询
        PageResult<GoodsCategory> pageResult = goodsCategoryService.query(pageNum, pageSize, goodsCategory);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    /**
     * 保存类目信息
     * @param category
     * @return
     */
    @PostMapping
    public ResponseEntity save(@RequestBody Category category) {
        int result = goodsCategoryService.save(category);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
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
    public ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        Category category = goodsCategoryService.getById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * 更新类目信息
     * @param category
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody Category category) {
        int result = goodsCategoryService.update(category);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
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
    public ResponseEntity delete(@PathVariable("id") Long id) {
        int result = goodsCategoryService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
