package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.User;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select({
            "select count(*) from user where phone_number=#{phoneNumber}"
    })
    int selectExitPhoneNumber(String phoneNumber);

    @Select({
            "select phone_number from user",
            "where password = #{password}"
    })
    String selectPhoneByPwd (String password);

    @Select({
            "select",
            "id, serial_id, phone_number, id_card, real_name, is_locked, is_noob,",
            " balance, profit, created_at, updated_at",
            "from user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("UserNormalInfo")
    User selectByPrimaryKey(Long id);

    @Select({
            "select count(*) from user ",
            "where id = #{id,jdbcType=BIGINT} and password=#{password,jdbcType=VARCHAR} "
    })
    int selectExitByIdPwd(Long id,String pwd);

    @Select({
            "select real_name from user where id=#{id,jdbcType=BIGINT}"
    })
    @ResultMap("UserNormalInfo")
    User selectForCompact(Long id);

    User selectSelective(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPhoneNumberSelective(User record);

}