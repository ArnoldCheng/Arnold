package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.BannerMapper;
import cn.zzh.foreground_client.project.entity.Banner;
import cn.zzh.foreground_client.project.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午3:38 2018/10/13
 * @Modified By:
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Override
    public List<Banner> selectByStatus(Integer status) {
        return bannerMapper.selectByStatus(status);
    }
}
