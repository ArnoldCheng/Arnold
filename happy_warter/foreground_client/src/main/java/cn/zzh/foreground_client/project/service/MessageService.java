package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.Message;

import java.util.List;

public interface MessageService {
    /**:
     * 返回该用户的所有信息
     * @param userId 用户Id
     * @return 信息List
     */

    List<Message> selectByUserIdList(Long userId);


    /**：
     * 统计未读消息条数
     * @param userId userID
     * @return int
     */

    int selectCountByUIdRead(Long userId);


    /**：
     * 消息插入
     * @param message message
     * @return int
     */
    int insertSelective(Message message);
}