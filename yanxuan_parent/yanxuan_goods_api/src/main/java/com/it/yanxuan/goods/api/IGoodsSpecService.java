package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Specification;

/**
 *商品规格业务接口
 * @author aaaa
 */
public interface IGoodsSpecService {
    /**
     * 分页查询商品规格
     * @param pageNum
     * @param pageSize
     * @param goodsSpec
     * @return
     */
    PageResult<GoodsSpec> pageQuery(Integer pageNum, Integer pageSize, GoodsSpec goodsSpec);

    /**
     * 保存信息
     * @param specification
     * @return
     */
    int save(Specification specification);

    /**
     * 根据id删除规格信息
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据主键进行查询
     * @param id
     * @return
     */
    Specification getById(Long id);

    /**
     * 更新规格信息
     * @param specification
     * @return
     */
    int update(Specification specification);
}
