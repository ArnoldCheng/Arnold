package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.CompactMapper;
import cn.zzh.foreground_client.project.entity.Compact;
import cn.zzh.foreground_client.project.service.CompactService;
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
public class CompactServiceImpl implements CompactService {
    @Autowired
    CompactMapper compactMapper;
    @Override
    public List<Compact> selectListByProductId(Long productId) {
        return compactMapper.selectListByProductId(productId);
    }

    @Override
    public Compact selectById(Long id) {
        return compactMapper.selectById(id);
    }

    @Override
    public List<Compact> selectListByUserId(Long userId) {
        return compactMapper.selectListByUserId(userId) ;
    }
}
