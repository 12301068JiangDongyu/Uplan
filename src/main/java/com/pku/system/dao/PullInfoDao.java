package com.pku.system.dao;

import com.pku.system.model.PullInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PullInfoDao {
    @Select("select * from pullInfo where id = #{id}")
    public PullInfo selectById(int id);

    @Insert("insert into pullInfo (id,buildingNum,classroomNum) values (#{id},#{buildingNum},#{classroomNum})")
    public void addPullInfo(PullInfo pullInfo);

    @Update("update pullInfo set buildingNum=#{buildingNum},classroomNum=#{classroomNum} where id=#{id}")
    public void updatePullInfo(PullInfo pullInfo);
}
