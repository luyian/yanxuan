package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsBrandService;
import com.it.yanxuan.model.GoodsBrand;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品品牌处理器
 * @author aaaa
 */
@Controller
@RequestMapping("/brand")
@ResponseBody
public class GoodsBrandController {

    @Reference
    private IGoodsBrandService goodsBrandService;

    /**
     * 根据主键获取品牌信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<GoodsBrand> queryById(@PathVariable("id") Long id) {
        //调用远程服务根据id查询
        GoodsBrand goodsBrand = goodsBrandService.queryById(id);
        return new ResponseEntity<>(goodsBrand, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageResult<GoodsBrand>> query(Integer pageNum, Integer pageSize, GoodsBrand goodsBrand) {
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //调用远程服务分页查询
        PageResult<GoodsBrand> pageResult = goodsBrandService.pageQuery(pageNum, pageSize, goodsBrand);
        return new ResponseEntity<PageResult<GoodsBrand>>(pageResult, HttpStatus.OK);
    }

    /**
     * 新增
     * @param goodsBrand
     */
    @PostMapping
    public ResponseEntity save(@RequestBody GoodsBrand goodsBrand) {
        //调用远程服务器，获取插入结果
        int result = goodsBrandService.save(goodsBrand);
        if (result > 0) {
            //如果成功
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 更新数据
     * @param goodsBrand
     */
    @PutMapping
    public ResponseEntity update(@RequestBody GoodsBrand goodsBrand) {
        //调用远程服务器，获取结果
        int result = goodsBrandService.update(goodsBrand);
        if (result > 0) {
            //如果成功
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        int result = goodsBrandService.deleteById(id);
        if (result > 0) {
            //如果成功
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
