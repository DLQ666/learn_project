package com.learn.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.cms.entity.Ad;
import com.learn.cms.entity.vo.AdVo;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author dlq
 * @since 2020-07-03
 */
public interface AdService extends IService<Ad> {

    /**
     * 分页查询门户广告
     * @param page 页数
     * @param limit 每页记录数
     * @return 分页对象
     */
    IPage<AdVo> selectPage(Long page, Long limit);

    /**
     * 删除门户广告图片
     * @param id 广告id
     */
    boolean removeAdImageById(String id);

    List<Ad> selectByAdTypeId(String adTypeId);
}
