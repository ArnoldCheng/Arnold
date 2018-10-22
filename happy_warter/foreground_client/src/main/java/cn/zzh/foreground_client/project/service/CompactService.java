package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.Compact;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompactService {

    /**:
     * 查找该产品的最新5条投资记录
     * @param productId productId
     * @return List
     */

    List<Compact> selectListByProductId(Long productId);


    /**：
     * 根据id查找相应的合同
     * @param id id
     * @return Compact
     */

    Compact selectById(Long id);

    List<Compact> selectListByUserId(Long userId);

}