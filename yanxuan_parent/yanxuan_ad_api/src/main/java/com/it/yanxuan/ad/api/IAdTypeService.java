package com.it.yanxuan.ad.api;

import com.it.yanxuan.model.AdType;
import com.it.yanxuan.result.PageResult;

/**
 * 广告类型业务接口
 * @author aaaa
 */
public interface IAdTypeService {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param adType
     * @return
     */
    PageResult<AdType> pageQuery(Integer pageNum, Integer pageSize, AdType adType);

    /**
     * 插入记录
     * @param adType
     * @return
     */
    int insert(AdType adType);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 更新数据
     * @param adType
     * @return
     */
    int update(AdType adType);
}
