package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.Banner;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerService {

    /**
     *通过状态，筛选符合要求的Banner图，并且返回BannerList
     * @param status 状态
     * @return Banner List
     *
     */
    List<Banner> selectByStatus(Integer status);
}