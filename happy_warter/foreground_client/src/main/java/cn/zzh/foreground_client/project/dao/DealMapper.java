package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.Deal;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface DealMapper {

    /**:
     * 根据主键查找交易记录
     * @param id id
     * @return Deal
     */
    @Select({
            "select ",
            "id,serial_id,user_id,product_id,investment,profit,dealing_type",
            "creatde_at,updated_at",
            " from deal where id=#{id}"
    })
    @ResultMap("MapDealAndProduct")
    Deal selectById(Long id);


    /**:
     * 根据用户Id查找交易记录
     * @param userId userId
     * @return List <Deal>
     */
    @Select({
            "select ",
            "id,serial_id,user_id,product_id,investment,profit,dealing_type",
            "creatde_at,updated_at",
            " from deal where id=#{id}"
    })
    @ResultMap("MapDealAndProduct")
    List<Deal> selectByUserId(Long userId);

    /**:
     * 添加交易记录
     * @param deal deal
     * @return int
     */
    int insertSelective(Deal deal);
}