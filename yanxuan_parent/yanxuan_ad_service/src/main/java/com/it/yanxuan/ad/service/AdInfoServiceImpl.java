package com.it.yanxuan.ad.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.ad.api.IAdInfoService;
import com.it.yanxuan.mapper.AdInfoMapper;
import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.model.AdInfoExample;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 广告详情业务实现
 * @author aaaa
 */
@Service
@Transactional
public class AdInfoServiceImpl implements IAdInfoService {

    @Autowired
    private AdInfoMapper adInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param adInfo
     * @return
     */
    @Override
    public PageResult<AdInfo> pageQuery(Integer pageNum, Integer pageSize, AdInfo adInfo) {

        //从redis缓存中获取数据
        if (adInfo != null && adInfo.getTypeId() != null) {
            PageResult pageResult = (PageResult) redisTemplate.boundHashOps("adInfo").get(adInfo.getTypeId().toString());
            if (pageResult != null) {
                System.out.println("从redis中获得数据");
                return pageResult;
            }
        }

        //构建查询条件
        AdInfoExample adInfoExample = new AdInfoExample();
        AdInfoExample.Criteria criteria = adInfoExample.createCriteria();
        if (adInfo != null) {
            if (adInfo.getName() != null && !"".equals(adInfo.getName())) {
                //name模糊查询
                criteria.andNameEqualTo(adInfo.getName());
            }
            //广告类型
            if (adInfo.getTypeId() != null) {
                criteria.andTypeIdEqualTo(adInfo.getTypeId());
            }
            //状态查询
            if (adInfo.getStatus() != null && !"".equals(adInfo.getStatus())) {
                criteria.andStatusEqualTo(adInfo.getStatus());
            }
        }
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        Page<AdInfo> adInfoPage = (Page<AdInfo>) adInfoMapper.selectByExampleWithBLOBs(adInfoExample);
        //构建返回结果
        PageResult<AdInfo> pageResult = new PageResult<>();
        pageResult.setTotal(adInfoPage.getTotal());
        pageResult.setResult(adInfoPage.getResult());

        //使用redis缓存
        if (adInfo != null && adInfo.getTypeId() != null) {
            redisTemplate.boundHashOps("adInfo").put(adInfo.getTypeId().toString(), pageResult);
        }

        return pageResult;
    }

    /**
     * 新增一条记录
     * @param adInfo
     * @return
     */
    @Override
    public int save(AdInfo adInfo) {
        return adInfoMapper.insert(adInfo);
    }

    /**
     * 修改
     * @param adInfo
     * @return
     */
    @Override
    public int update(AdInfo adInfo) {
        return adInfoMapper.updateByPrimaryKeyWithBLOBs(adInfo);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        AdInfo adInfo = adInfoMapper.selectByPrimaryKey(id);
        //删除redis缓存
        redisTemplate.boundHashOps("adInfo").delete(adInfo.getTypeId().toString());
        adInfo.setStatus("1");
        adInfo.setId(id);
        return adInfoMapper.updateByPrimaryKeySelective(adInfo);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @Override
    public AdInfo queryById(Long id) {
        return adInfoMapper.selectByPrimaryKey(id);
    }
}
