package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.BankMapper;
import cn.zzh.foreground_client.project.entity.Bank;
import cn.zzh.foreground_client.project.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午3:38 2018/10/13
 * @Modified By:
 */
@Service
public class BankServiceImpl implements BankService {
    @Autowired
    BankMapper bankMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return bankMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Bank> selectByUserId(Long userId) {
        return bankMapper.selectByUserId(userId);
    }

    @Override
    public int selectAccountByCard(String card) {
        return bankMapper.selectAccountByCard(card);
    }

    @Override
    public int insertSelective(Bank bank) {
        return bankMapper.insertSelective(bank);
    }
}
