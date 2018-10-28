package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.UserMapper;
import cn.zzh.foreground_client.project.entity.User;
import cn.zzh.foreground_client.project.service.Tools;
import cn.zzh.foreground_client.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午3:37 2018/10/13
 * @Modified By:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    Tools tools;

    @Override
    public boolean selectExitPhoneNumber(String phoneNumber) {

        return userMapper.selectExitPhoneNumber(phoneNumber)!=0;
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectSelective(User record) {
        return userMapper.selectSelective(record);
    }

    @Override
    public int selectExitByIdPwd(Long id, String pwd) {
        return userMapper.selectExitByIdPwd(id,pwd);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public String selectPhoneByPwd(String password) {
        return userMapper.selectPhoneByPwd(password);
    }

    @Override
    public int updateByPhoneNumberSelective(User record) {
        return userMapper.updateByPhoneNumberSelective(record);
    }


}
