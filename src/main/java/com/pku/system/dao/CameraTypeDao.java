package com.pku.system.dao;

import com.pku.system.model.CameraType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CameraTypeDao {
    @Select("select * from cameraType where cameraTypeId = #{cameraTypeId}")
    public CameraType selectById(int id);

    @Select("select * from cameraType where cameraTypeName = #{cameraTypeName}")
    public CameraType selectByName(String cameraTypeName);

    @Select("select * from cameraType")
    public List<CameraType> getAllCameraType();

    @Insert("insert into cameraType (cameraTypeId,cameraTypeName) values (#{cameraTypeId},#{cameraTypeName})")
    public void addCameraType(CameraType cameraType);

    @Update("update cameraType set cameraTypeName=#{cameraTypeName} where cameraTypeId=#{cameraTypeId}")
    public void updateCameraType(CameraType cameraType);

    @Delete("delete from cameraType where cameraTypeId=#{cameraTypeId}")
    public void deleteCameraType(int id);

}
