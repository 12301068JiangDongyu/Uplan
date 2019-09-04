package com.pku.system.dao;

import com.pku.system.model.ComputerType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ComputerTypeDao {

    @Select("select * from computerType where computerTypeId = #{computerTypeId}")
    public ComputerType selectById(int id);

    @Select("select * from computerType where computerTypeName = #{computerTypeName}")
    public ComputerType selectByName(String computerTypeName);

    @Select("select * from computerType where computerTypeName = #{0} and memorySize = #{1} and diskSize = #{2} and operatingSystem = #{3}")
    public ComputerType selectByAll(String computerTypeName,String memorySize,String diskSize,String operatingSystem);

    @Select("select * from computerType")
    public List<ComputerType> getAllComputerType();

    @Insert("insert into computerType (computerTypeId,computerTypeName,memorySize,diskSize,operatingSystem) values (#{computerTypeId},#{computerTypeName},#{memorySize},#{diskSize},#{operatingSystem})")
    public void addComputerType(ComputerType computerType);

    @Update("update computerType set computerTypeName = #{computerTypeName},memorySize = #{memorySize},diskSize = #{diskSize},operatingSystem = #{operatingSystem} where computerTypeId = #{computerTypeId}")
    public void updateComputerType(ComputerType computerType);

    @Delete("delete from computerType where computerTypeId = #{computerTypeId}")
    public void deleteComputerType(int id);
}
