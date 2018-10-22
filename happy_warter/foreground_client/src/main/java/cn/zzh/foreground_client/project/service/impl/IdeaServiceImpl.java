package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.IdeaMapper;
import cn.zzh.foreground_client.project.entity.Idea;
import cn.zzh.foreground_client.project.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午3:38 2018/10/13
 * @Modified By:
 */
@Service
public class IdeaServiceImpl implements IdeaService {
    @Autowired
    IdeaMapper ideaMapper;
    @Override
    public int insertSelective(Idea idea) {
        return ideaMapper.insertSelective(idea);
    }
}
