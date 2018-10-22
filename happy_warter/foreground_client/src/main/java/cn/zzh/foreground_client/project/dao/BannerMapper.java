package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.Banner;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerMapper {

    /**
     *通过状态，筛选符合要求的Banner图，并且返回BannerList
     * @param status 状态
     * @return Banner List
     *
     */
    @Select({
        "select",
        "id, serial_id, title, thumnail, interval_time, status, created_by, updated_by, ",
        "created_at, updated_at",
        "from banner",
        "where status = #{status,jdbcType=TINYINT}"
    })

    @ResultMap("BaseResultMap")
    List<Banner> selectByStatus(Integer status);
}