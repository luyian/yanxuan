package com.it.yanxuan.ad.api;

import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.result.PageResult;

/**
 *广告详情业务接口
 * @author aaaa
 */
public interface IAdInfoService {
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param adInfo
     * @return
     */
    PageResult<AdInfo> pageQuery(Integer pageNum, Integer pageSize, AdInfo adInfo);

    /**
     * 新增数据
     * @param adInfo
     * @return
     */
    int save(AdInfo adInfo);

    /**
     * 更新
     * @param adInfo
     * @return
     */
    int update(AdInfo adInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    AdInfo queryById(Long id);
}
