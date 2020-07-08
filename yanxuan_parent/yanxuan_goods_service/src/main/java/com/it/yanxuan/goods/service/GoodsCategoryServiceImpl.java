package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsCategoryService;
import com.it.yanxuan.mapper.GoodsCategoryBrandSpecMapper;
import com.it.yanxuan.mapper.GoodsCategoryMapper;
import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.model.GoodsCategoryBrandSpec;
import com.it.yanxuan.model.GoodsCategoryBrandSpecExample;
import com.it.yanxuan.model.GoodsCategoryExample;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类目管理业务实现
 * @author aaaa
 */
@Service
@Transactional
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private GoodsCategoryBrandSpecMapper goodsCategoryBrandSpecMapper;

    @Override
    public PageResult<GoodsCategory> query(Integer pageNum, Integer pageSize, GoodsCategory goodsCategory) {
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();

        //参数处理
        if (goodsCategory != null) {
            if (!"".equals(goodsCategory.getName()) && goodsCategory.getName() != null) {
                goodsCategoryExample.or().andNameLike("%" + goodsCategory.getName() + "%");
            } else {
                //当没有查询条件时，才会使用parenId查询
                if (goodsCategory.getParentId() != null) {
                    goodsCategoryExample.or().andParentIdEqualTo(goodsCategory.getParentId());
                }
            }
        }

        //查询
        Page<GoodsCategory> page = (Page<GoodsCategory>) goodsCategoryMapper.selectByExample(goodsCategoryExample);

        //封装返回结果
        PageResult<GoodsCategory> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setResult(page.getResult());

        return pageResult;
    }

    /**
     * 保存类目信息
     * @param category
     * @return
     */
    @Override
    public int save(Category category) {
        //保存类目
        category.setStatus("0");
        //设置排序序号
        GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();
        goodsCategoryExample.or().andParentIdEqualTo(category.getParentId());
        int count = (int)goodsCategoryMapper.countByExample(goodsCategoryExample);
        category.setSortNo(count + 1);
        //保存
        int insert = goodsCategoryMapper.insert(category);

        //保存关联的品牌和规格
        GoodsCategoryBrandSpec relation = category.getRelation();
        relation.setCategoryId(category.getId());
        goodsCategoryBrandSpecMapper.insert(relation);

        return insert;
    }

    /**
     * 根据主键获取类目信息，关联品、规格
     * @param id
     * @return
     */
    @Override
    public Category getById(Long id) {
        Category category = new Category();

        //查询类目表
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(goodsCategory, category);

        //查询关联表
        GoodsCategoryBrandSpecExample goodsCategoryBrandSpecExample = new GoodsCategoryBrandSpecExample();
        goodsCategoryBrandSpecExample.or().andCategoryIdEqualTo(category.getId());
        List<GoodsCategoryBrandSpec> goodsCategoryBrandSpecs =
                goodsCategoryBrandSpecMapper.selectByExample(goodsCategoryBrandSpecExample);
        if (goodsCategoryBrandSpecs.size() > 0) {
            category.setRelation(goodsCategoryBrandSpecs.get(0));
        } else {
            GoodsCategoryBrandSpec relation = new GoodsCategoryBrandSpec();
            relation.setBrandIds("[]");
            relation.setSpecIds("[]");
            category.setRelation(relation);
        }

        return category;
    }

    /**
     * 更新类目信息
     * @param category
     * @return
     */
    @Override
    public int update(Category category) {
        //修改状态为可用
        category.setStatus("0");
        int update = goodsCategoryMapper.updateByPrimaryKey(category);
        //先删除
        GoodsCategoryBrandSpecExample goodsCategoryBrandSpecExample = new GoodsCategoryBrandSpecExample();
        goodsCategoryBrandSpecExample.or().andCategoryIdEqualTo(category.getId());
        goodsCategoryBrandSpecMapper.deleteByExample(goodsCategoryBrandSpecExample);
        //新添加
        category.getRelation().setCategoryId(category.getId());
        goodsCategoryBrandSpecMapper.insert(category.getRelation());

        return update;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        //根据id获取
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(id);
        //设置状态为停用
        goodsCategory.setStatus("1");
        //更新状态
        int delete = goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);

        return delete;
    }
}
