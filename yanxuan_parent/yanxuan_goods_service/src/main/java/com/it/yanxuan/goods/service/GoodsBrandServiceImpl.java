package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsBrandService;
import com.it.yanxuan.mapper.GoodsBrandMapper;
import com.it.yanxuan.model.GoodsBrand;
import com.it.yanxuan.model.GoodsBrandExample;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品品牌服务提供者
 * @author aaaa
 */
@Service
@Transactional
public class GoodsBrandServiceImpl implements IGoodsBrandService {

    /**
     * 注入Dao
     */
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;

    @Override
    public List<GoodsBrand> queryAll() {
        return goodsBrandMapper.selectByExample(null);
    }

    @Override
    public PageResult<GoodsBrand> pageQuery(Integer pageNum, Integer pageSize, GoodsBrand goodsBrand) {
        PageHelper.startPage(pageNum, pageSize);
        GoodsBrandExample goodsBrandExample = new GoodsBrandExample();

        if (goodsBrand != null) {
            if (goodsBrand.getName() != null && !"".equals(goodsBrand.getName())) {
                goodsBrandExample.createCriteria().andNameLike("%" + goodsBrand.getName() + "%");
            }
        }

        Page<GoodsBrand> brandPage = (Page<GoodsBrand>) goodsBrandMapper.selectByExample(goodsBrandExample);
        long total = brandPage.getTotal();
        List<GoodsBrand> result = brandPage.getResult();
        //封装结果
        PageResult<GoodsBrand> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setResult(result);
        return pageResult;
    }

    @Override
    public int save(GoodsBrand goodsBrand) {
        goodsBrand.setIsDelete("0");
        return goodsBrandMapper.insert(goodsBrand);
    }

    /**
     * 更新数据
     * @param goodsBrand
     * @return
     */
    @Override
    public int update(GoodsBrand goodsBrand) {
        return goodsBrandMapper.updateByPrimaryKey(goodsBrand);
    }

    /**
     * 删除品牌信息
     * @param id
     * @return
     */
    @Override
    public int deleteById(Long id) {
        //根据id查询出goodsbrand
        GoodsBrand goodsBrand = goodsBrandMapper.selectByPrimaryKey(id);
        goodsBrand.setIsDelete("1");
        //更新数据
        int delResult = goodsBrandMapper.updateByPrimaryKey(goodsBrand);
        return delResult;
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @Override
    public GoodsBrand queryById(Long id) {
        return goodsBrandMapper.selectByPrimaryKey(id);
    }
}
