package com.it.yanxuan.ad.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.ad.api.IAdTypeService;
import com.it.yanxuan.mapper.AdTypeMapper;
import com.it.yanxuan.model.AdType;
import com.it.yanxuan.model.AdTypeExample;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 广告类型业务实现
 * @author aaaa
 */
@Service
@Transactional
public class AdTypeServiceImpl implements IAdTypeService {

    @Autowired
    private AdTypeMapper adTypeMapper;

    /**
     * 更新
     * @param adType
     * @return
     */
    @Override
    public int update(AdType adType) {
        return adTypeMapper.updateByPrimaryKey(adType);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param adType
     * @return
     */
    @Override
    public PageResult<AdType> pageQuery(Integer pageNum, Integer pageSize, AdType adType) {
        //构建查询条件
        AdTypeExample adTypeExample = new AdTypeExample();
        if (adType != null) {
            if (adType.getName() != null && !"".equals(adType.getName())) {
                adTypeExample.createCriteria().andNameLike("%" + adType.getName() + "%");
            }
        }
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //查询
        Page<AdType> page = (Page<AdType>) adTypeMapper.selectByExample(adTypeExample);
        //构建返回结果
        PageResult<AdType> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setResult(page.getResult());

        return pageResult;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        return adTypeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加一条记录
     * @param adType
     * @return
     */
    @Override
    public int insert(AdType adType) {
        return adTypeMapper.insert(adType);
    }
}
