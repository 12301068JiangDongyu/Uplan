package com.pku.system.dao;

import com.pku.system.model.ProjectorType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectorTypeDao {
    @Select("select * from projectorType where projectorTypeId = #{projectorTypeId}")
    public ProjectorType selectById(int id);

    @Select("select * from projectorType where projectorTypeName = #{projectorTypeName}")
    public ProjectorType selectByName(String projectorType);

    @Select("select * from projectorType")
    public List<ProjectorType> getAllProjectorType();

    @Insert("insert into projectorType (projectorTypeId,projectorTypeName) values (#{projectorTypeId},#{projectorTypeName})")
    public void addProjectorType(ProjectorType projectorType);

    @Update("update projectorType set projectorTypeName=#{projectorTypeName} where projectorTypeId=#{projectorTypeId}")
    public void updateProjectorType(ProjectorType projectorType);

    @Delete("delete from projectorType where projectorTypeId=#{projectorTypeId}")
    public void deleteProjectorType(int id);
}
