package com.pku.system.dao;

import com.pku.system.model.DeviceInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceInfoDao {

    @Select("select * from device_info where id = #{id}")
    public DeviceInfo selectById(int id);

    @Select("select * from device_info where buildingNum = #{0} and classroomNum = #{1}")
    public DeviceInfo selectByBuildingClassroom(String buildingNum, String classroomNum);

    @Select("select * from device_info where raspberryCode = #{raspberryCode}")
    public DeviceInfo selectByRaspberryCode(String raspberryCode);

    @Select("SELECT " +
            "di.*," +
            "s.singlechipTypeName," +
            "r.raspberryTypeName," +
            "co.computerTypeName," +
            "co.memorySize," +
            "co.diskSize," +
            "co.operatingSystem," +
            "p.projectorTypeName " +
            "FROM device_info di " +
            "INNER JOIN singlechipType s ON di.singlechipTypeId=s.singlechipTypeId " +
            "INNER JOIN raspberryType r ON di.raspberryTypeId=r.raspberryTypeId " +
            "INNER JOIN computerType co ON di.computerTypeId=co.computerTypeId " +
            "INNER JOIN projectorType p ON di.projectorTypeId=p.projectorTypeId")
    public List<DeviceInfo> getAllDeviceInfo();

    @Select("select * from device_info where id != #{id}")
    public List<DeviceInfo> selectAllExceptId(int id);

    @Insert("insert into device_info (id,buildingNum,classroomNum,singlechipTypeId,raspberryTypeId,computerTypeId,projectorTypeId) values (#{id},#{buildingNum},#{classroomNum},#{singlechipTypeId},#{raspberryTypeId},#{computerTypeId},#{projectorTypeId})")
    public void addDeviceInfo(DeviceInfo deviceInfo);

    @Update("update device_info set singlechipTypeId=#{singlechipTypeId},raspberryTypeId=#{raspberryTypeId},computerTypeId=#{computerTypeId},projectorTypeId=#{projectorTypeId} where id=#{id}")
    public void updateDeviceInfo(DeviceInfo deviceInfo);

    @Update("update device_info set singlechipStatus=#{singlechipStatus},raspberryStatus=#{raspberryStatus},raspberryStreamStatus=#{raspberryStreamStatus},cameraStatus=#{cameraStatus},computerStatus=#{computerStatus},projectorStatus=#{projectorStatus} where id=#{id}")
    public void updateDeviceInfoStatus(DeviceInfo deviceInfo);

    @Delete("delete from device_info where id=#{id}")
    public void deleteDeviceInfo(int id);

    @Select("select * from device_info")
    public List<DeviceInfo> getAllDeviceInfoStatus();

    @Select("select id,buildingNum,classroomNum,raspberryStatus,raspberryStreamStatus,raspberryCode from device_info")
    public List<DeviceInfo> getRaspberryStreamStatus();

    @Select("select id,buildingNum,classroomNum,raspberryStatus,raspberryStreamStatus,raspberryCode from device_info where raspberryStreamStatus=#{raspberryStreamStatus}")
    public List<DeviceInfo> getPushClassroomList(int raspberryStreamStatus);

    @Select("select DISTINCT buildingNum from device_info where raspberryStreamStatus=#{raspberryStreamStatus}")
    public List<DeviceInfo> getAllPushBuildingList(int raspberryStreamStatus);

    @Select("select classroomNum from device_info where buildingNum=#{0} and raspberryStreamStatus=#{1}")
    public List<DeviceInfo> getAllPushClassroomList(String buildingNum,int raspberryStreamStatus);
}
