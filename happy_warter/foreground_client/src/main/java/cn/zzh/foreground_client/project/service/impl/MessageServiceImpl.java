package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.MessageMapper;
import cn.zzh.foreground_client.project.entity.Message;
import cn.zzh.foreground_client.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午3:37 2018/10/13
 * @Modified By:
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public List<Message> selectByUserIdList(Long userId) {
        return messageMapper.selectByUserIdList(userId);
    }

    @Override
    public int selectCountByUIdRead(Long userId) {
        return messageMapper.selectCountByUIdRead(userId);
    }

    @Override
    public int insertSelective(Message message) {
        return messageMapper.insertSelective(message);
    }
}
