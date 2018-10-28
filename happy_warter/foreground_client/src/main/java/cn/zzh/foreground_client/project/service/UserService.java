package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.User;


public interface UserService {

    /**:
     * 检查该电话号码是否被注册，是返回true，否返回false
     * @param phoneNumber phoneNumber
     * @return boolean
     */
    boolean selectExitPhoneNumber(String phoneNumber);

    User selectByPrimaryKey(Long id);

    User selectSelective(User record);

    int selectExitByIdPwd(Long id,String pwd);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    String selectPhoneByPwd (String password);

    int updateByPhoneNumberSelective(User record);


}