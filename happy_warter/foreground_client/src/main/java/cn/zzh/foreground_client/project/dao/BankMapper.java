package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.Bank;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface BankMapper {
    /**：
     * 删除银行卡，进行解绑；
     * @param id id
     * @return int
     */
    @Delete({
        "delete from bank",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);


    /**:
     * 通过用户Id查询关联的所有银行卡
     * @param userId userID
     * @return List
     */
    @Select({
            "select * from bank where user_id=#{userId}"
    })
    List<Bank> selectByUserId(Long userId);

    @Select({
            "select count(*) from bank where card=#{card}"
    })
    int selectAccountByCard(String card);


    /**:
     * 新增银行卡
     * @param bank bank
     * @return int
     */
    int insertSelective(Bank bank);

}