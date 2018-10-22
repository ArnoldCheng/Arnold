package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.DealMapper;
import cn.zzh.foreground_client.project.entity.Deal;
import cn.zzh.foreground_client.project.service.DealService;
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
public class DealServiceImpl implements DealService {
    @Autowired
    DealMapper dealMapper;
    @Override
    public Deal selectById(Long id) {
        return dealMapper.selectById(id);
    }

    @Override
    public List<Deal> selectByUserId(Long userId) {
        return dealMapper.selectByUserId(userId);
    }

    @Override
    public int insertSelective(Deal deal) {
        return dealMapper.insertSelective(deal);
    }
}
