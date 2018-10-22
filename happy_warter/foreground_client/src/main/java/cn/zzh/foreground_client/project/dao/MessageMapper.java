package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MessageMapper {
    /**:
     * 返回该用户的所有信息
     * @param userId 用户Id
     * @return 信息List
     */
    @Select({
            "select * from message where user_id =#{userId} "
    })
    @ResultMap("ResultMapWithBLOBs")
    List<Message> selectByUserIdList(Long userId);


    /**：
     * 统计未读消息条数
     * @param userId userID
     * @return int
     */
    @Select({
            "select count(*) from message where user_id=#{userId} and is_read=0 "
    })
    int selectCountByUIdRead(Long userId);


    /**：
     * 消息插入
     * @param message message
     * @return int
     */
    int insertSelective(Message message);
}