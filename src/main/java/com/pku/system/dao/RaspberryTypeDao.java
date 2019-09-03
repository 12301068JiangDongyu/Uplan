package com.pku.system.dao;

import com.pku.system.model.RaspberryType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RaspberryTypeDao {
    @Select("select * from raspberryType where raspberryTypeId = #{raspberryTypeId}")
    public RaspberryType selectById(int id);

    @Select("select * from raspberryType where raspberryTypeName = #{raspberryTypeName}")
    public RaspberryType selectByName(String raspberryType);

    @Select("select * from raspberryType")
    public List<RaspberryType> getAllRaspberryType();

    @Insert("insert into raspberryType (raspberryTypeId,raspberryTypeName) values (#{raspberryTypeId},#{raspberryTypeName})")
    public void addRaspberryType(RaspberryType raspberryType);

    @Update("update raspberryType set raspberryTypeName=#{raspberryTypeName} where raspberryTypeId=#{raspberryTypeId}")
    public void updateRaspberryType(RaspberryType raspberryType);

    @Delete("delete from raspberryType where raspberryTypeId=#{raspberryTypeId}")
    public void deleteRaspberryType(int id);
}
