package cn.zzh.foreground_client.project.dao;

import cn.zzh.foreground_client.project.entity.Idea;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface IdeaMapper {

    int  insertSelective(Idea idea);
}