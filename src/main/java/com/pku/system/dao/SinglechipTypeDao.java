package com.pku.system.dao;

import com.pku.system.model.SinglechipType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SinglechipTypeDao {
    @Select("select * from singlechipType where singlechipTypeId = #{singlechipTypeId}")
    public SinglechipType selectById(int id);

    @Select("select * from singlechipType where singlechipTypeName = #{singlechipTypeName}")
    public SinglechipType selectByName(String singlechipType);

    @Select("select * from singlechipType")
    public List<SinglechipType> getAllSinglechipType();

    @Insert("insert into singlechipType (singlechipTypeId,singlechipTypeName) values (#{singlechipTypeId},#{singlechipTypeName})")
    public void addSinglechipType(SinglechipType singlechipType);

    @Update("update singlechipType set singlechipTypeName=#{singlechipTypeName} where singlechipTypeId=#{singlechipTypeId}")
    public void updateSinglechipType(SinglechipType singlechipType);

    @Delete("delete from singlechipType where singlechipTypeId=#{singlechipTypeId}")
    public void deleteSinglechipType(int id);
}
