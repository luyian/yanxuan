package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsBrand;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.result.Result;

import java.util.List;

/**
 * 商品列表的服务接口
 * @author aaaa
 */
public interface IGoodsBrandService {
    /**
     * 查询返回所有商品品牌信息
     * @return
     */
    public List<GoodsBrand> queryAll();

    /**
     * 根据分页参数进行发分页数据查询
     * @param pageNum
     * @param pageSize
     * @param searchName
     * @return
     */
    public PageResult<GoodsBrand> pageQuery(Integer pageNum, Integer pageSize, GoodsBrand goodsBrand);

    /**
     * 插入品牌数据
     * @param goodsBrand
     * @return
     */
    public int save(GoodsBrand goodsBrand);

    /**
     * 修改品牌数据
     * @param goodsBrand
     * @return
     */
    public int update(GoodsBrand goodsBrand);

    /**
     * 根据id删除品牌信息，将isDelete置为1
     * @param id
     * @return
     */
    public int deleteById(Long id);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    GoodsBrand queryById(Long id);
}
