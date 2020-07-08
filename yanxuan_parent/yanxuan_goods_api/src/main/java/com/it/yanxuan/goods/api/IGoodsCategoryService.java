package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Category;

/**
 * 类目管理业务接口
 * @author aaaa
 */
public interface IGoodsCategoryService {
    /**
     * 分页查询类目信息
     * @param pageNum
     * @param pageSize
     * @param goodsCategory
     * @return
     */
    PageResult<GoodsCategory> query(Integer pageNum, Integer pageSize, GoodsCategory goodsCategory);

    /**
     * 保存类目信息
     * @param category
     * @return
     */
    int save(Category category);

    /**
     * 根据id获取主键信息
     * @param id
     * @return
     */
    Category getById(Long id);

    /**
     * 更新数据
     * @param category
     * @return
     */
    int update(Category category);

    /**
     * 根据id删除类目
     * @param id
     * @return
     */
    int delete(Long id);
}
