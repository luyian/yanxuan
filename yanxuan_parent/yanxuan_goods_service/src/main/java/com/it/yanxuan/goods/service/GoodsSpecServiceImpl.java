package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsSpecService;
import com.it.yanxuan.mapper.GoodsSpecMapper;
import com.it.yanxuan.mapper.GoodsSpecOptionMapper;
import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.model.GoodsSpecExample;
import com.it.yanxuan.model.GoodsSpecOption;
import com.it.yanxuan.model.GoodsSpecOptionExample;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.viewmodel.Specification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品规格业务实现
 * @author aaaa
 */
@Service
@Transactional
public class GoodsSpecServiceImpl implements IGoodsSpecService {
    @Autowired
    private GoodsSpecMapper goodsSpecMapper;
    @Autowired
    private GoodsSpecOptionMapper goodsSpecOptionMapper;

    @Override
    public PageResult<GoodsSpec> pageQuery(Integer pageNum, Integer pageSize, GoodsSpec goodsSpec) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //构建查询条件
        GoodsSpecExample goodsSpecExample = new GoodsSpecExample();
        if (goodsSpec != null) {
            if (goodsSpec.getName() != null && !"".equals(goodsSpec.getName())) {
                goodsSpecExample.createCriteria().andNameLike("%" + goodsSpec.getName() + "%");
            }
        }
        //调用持久层方法，查询
        Page<GoodsSpec> page = (Page<GoodsSpec>) goodsSpecMapper.selectByExample(goodsSpecExample);
        //创建分页结果对象，保存数据
        PageResult<GoodsSpec> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setResult(page.getResult());
        return pageResult;
    }

    /**
     * 保存商品规格信息，用到两张表规格、选项（一对多）
     * @param specification
     * @return
     */
    @Override
    public int save(Specification specification) {
        //先保存规格信息
        specification.setStatus("0");
        int insert = goodsSpecMapper.insert(specification);

        //保存规格选项
        for (GoodsSpecOption goodsSpecOption : specification.getOptionList()) {
            goodsSpecOption.setSpecId(specification.getId());
            goodsSpecOption.setStatus("0");
            goodsSpecOptionMapper.insert(goodsSpecOption);
        }
        return insert;
    }

    /**
     * 根据id删除规格信息
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        //先根据id获取到规格信息
        GoodsSpec goodsSpec = goodsSpecMapper.selectByPrimaryKey(id);
        //设置状态为删除
        goodsSpec.setStatus("1");
        //更新规格
        int result = goodsSpecMapper.updateByPrimaryKeySelective(goodsSpec);
        //根据规格id更新，规格项状态
        GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
        goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(goodsSpec.getId());

        //创建更新对象
        GoodsSpecOption goodsSpecOption = new GoodsSpecOption();
        goodsSpecOption.setStatus("1");
        goodsSpecOptionMapper.updateByExampleSelective(goodsSpecOption, goodsSpecOptionExample);

        return result;
    }

    /**
     * 根据主键查询规格信息，包括规格条目
     * @param id
     * @return
     */
    @Override
    public Specification getById(Long id) {
        //创建返回结果
        Specification specification = new Specification();

        GoodsSpec goodsSpec = goodsSpecMapper.selectByPrimaryKey(id);
        //根据specId再查询规格选项
        GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
        goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(goodsSpec.getId());
        List<GoodsSpecOption> optionList = goodsSpecOptionMapper.selectByExample(goodsSpecOptionExample);

        //构建返回结果
        BeanUtils.copyProperties(goodsSpec, specification);
        specification.setOptionList(optionList);

        return specification;
    }

    /**
     * 更新规格信息
     * @param specification
     * @return
     */
    @Override
    public int update(Specification specification) {
        //先设置状态为可用
        specification.setStatus("0");
        int result = goodsSpecMapper.updateByPrimaryKey(specification);
        //更新选项，需要先删除原有选项
        GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
        goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(specification.getId());
        goodsSpecOptionMapper.deleteByExample(goodsSpecOptionExample);

        //遍历，更新
        for (GoodsSpecOption goodsSpecOption : specification.getOptionList()) {
            goodsSpecOption.setStatus("0");
            goodsSpecOption.setSpecId(specification.getId());
            goodsSpecOptionMapper.insert(goodsSpecOption);
        }
        return result;
    }
}
