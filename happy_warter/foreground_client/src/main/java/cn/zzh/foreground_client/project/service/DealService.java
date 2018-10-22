package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.Deal;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface DealService {

    /**:
     * 根据主键查找交易记录
     * @param id id
     * @return Deal
     */

    Deal selectById(Long id);


    /**:
     * 根据用户Id查找交易记录
     * @param userId userId
     * @return List <Deal>
     */

    List<Deal> selectByUserId(Long userId);

    /**:
     * 添加交易记录
     * @param deal deal
     * @return int
     */
    int insertSelective(Deal deal);
}