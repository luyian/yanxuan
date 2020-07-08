package com.it.yanxuan.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsCategoryService;
import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类目查询控制器
 * @author aaaa
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Reference
    private IGoodsCategoryService goodsCategoryService;

    /**
     * 查询商品类目
     * @param pageNum
     * @param pageSize
     * @param goodsCategory
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<GoodsCategory>> queryCategory(Integer pageNum, Integer pageSize, GoodsCategory goodsCategory) {
        //处理参数
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }
        PageResult<GoodsCategory> pageResult = goodsCategoryService.query(pageNum, pageSize, goodsCategory);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
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
}
