package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.Bank;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface BankService {
    /**：
     * 删除银行卡，进行解绑；
     * @param id id
     * @return int
     */

    int deleteByPrimaryKey(Long id);


    /**:
     * 通过用户Id查询关联的所有银行卡
     * @param userId userID
     * @return List
     */

    List<Bank> selectByUserId(Long userId);


    int selectAccountByCard(String card);


    /**:
     * 新增银行卡
     * @param bank bank
     * @return int
     */
    int insertSelective(Bank bank);


    long selectUserIdByBankId(long bankId);

}