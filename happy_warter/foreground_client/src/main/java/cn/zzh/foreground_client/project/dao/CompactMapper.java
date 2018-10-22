package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.Compact;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompactMapper {

    /**:
     * 查找该产品的最新5条投资记录
     * @param productId productId
     * @return List
     */
   @Select({
           "select * from compact where product_id=#{productId} order by id desc limit 5 "
   })
    @ResultMap("JoinResultMap")
    List<Compact> selectListByProductId(Long productId);


    /**：
     * 根据id查找相应的合同
     * @param id id
     * @return Compact
     */
   @Select({
           "select * from compact where id=#{id}"
   })
   @ResultMap("JoinResultMap")
    Compact selectById(Long id);

   @Select({
           "select * from compact where user_id=#{user_id}"
   })
   @ResultMap("JoinResultMap")
    List<Compact> selectListByUserId(Long userId);

}