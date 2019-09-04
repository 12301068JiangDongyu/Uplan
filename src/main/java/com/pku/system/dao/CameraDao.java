package com.pku.system.dao;

import com.pku.system.model.Camera;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CameraDao {
    @Select("select * from camera where cameraId = #{cameraId}")
    public Camera selectById(int cameraId);

    @Select("select * from camera where did = #{did}")
    public List<Camera> selectByDeviceId(int did);

    @Insert("insert into camera (cameraTypeId,cameraStatus,cameraAngle,did) values (#{cameraTypeId},#{cameraStatus},#{cameraAngle},#{did})")
    public void addCamera(Camera camera);

    @Update("update camera set cameraTypeId=#{cameraTypeId},cameraStatus=#{cameraStatus},cameraAngle=#{cameraAngle} where cameraId=#{cameraId}")
    public void updateCamera(Camera camera);

    @Delete("delete from camera where cameraId=#{cameraId}")
    public void deleteCamera(int cameraId);
}
