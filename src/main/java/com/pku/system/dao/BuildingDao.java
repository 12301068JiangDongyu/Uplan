package com.pku.system.dao;

import com.pku.system.model.Building;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BuildingDao {
    @Select("select * from building where id = #{id}")
    public Building selectById(int id);

    @Select("select * from building where buildingNum = #{buildingNum}")
    public Building selectByName(String buildingNum);

    @Select("select * from building")
    public List<Building> getAllBuilding();

    @Insert("insert into building (id,buildingNum) values (#{id},#{buildingNum})")
    public void addBuilding(Building building);

    @Update("update building set buildingNum=#{buildingNum} where id=#{id}")
    public void updateBuilding(Building building);

    @Delete("delete from building where id=#{id}")
    public void deleteBuilding(int id);

}
