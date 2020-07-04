package com.learn.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.cms.entity.Ad;
import com.learn.cms.entity.vo.AdVo;
import com.learn.cms.feign.OssService;
import com.learn.cms.mapper.AdMapper;
import com.learn.cms.service.AdService;
import com.learn.utils.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author dlq
 * @since 2020-07-03
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

    @Autowired
    private OssService ossService;

    @Override
    public IPage<AdVo> selectPage(Long page, Long limit) {
        QueryWrapper<AdVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id","a.sort");

        Page<AdVo> pageParam = new Page<>(page,limit);

        List<AdVo> records = baseMapper.selectPageByQueryWrapper(pageParam,queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }

    @Override
    public boolean removeAdImageById(String id) {
        Ad ad = baseMapper.selectById(id);
        if(ad != null) {
            String imagesUrl = ad.getImageUrl();
            if(!StringUtils.isEmpty(imagesUrl)){
                //删除图片
                ResponseResult responseResult = ossService.removeFile(imagesUrl);
                return responseResult.getSuccess();
            }
        }
        return false;
    }

    /**
     * 1、查询Redis缓存中是否存在需要的数据 hasKey
     * 2、如果缓存中不存在则从数据库中取出数据、并将数据存入到Redis缓存 set
     * 3、如果缓存存在则从缓存中读取数据 get
     * @param adTypeId
     * @return
     */
    @Cacheable(value = "index",key = "'selectByAdTypeId'")
    @Override
    public List<Ad> selectByAdTypeId(String adTypeId) {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort","id");
        queryWrapper.eq("type_id",adTypeId);
        return baseMapper.selectList(queryWrapper);
    }
}
