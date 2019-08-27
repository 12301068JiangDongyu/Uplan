package com.pku.system.service;

import com.pku.system.model.DeviceInfo;

import java.util.List;

public interface DeviceInfoService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public DeviceInfo selectById(int id);

    /**
     * 根据教学楼，教室号查询
     * @param buildingNum
     * @param classroomNum
     * @return
     */
    public DeviceInfo selectByBuildingClassroom(String buildingNum, String classroomNum);

    /**
     * 根据树莓派设备码查询数据
     * @param raspberryCode
     * @return
     */
    public DeviceInfo selectByRaspberryCode(String raspberryCode);

    /**
     * 获得列表
     * @return
     */
    public List<DeviceInfo> getAllDeviceInfo();

    /**
     * 获取除了某个id之外的所有的记录
     * * @param id
     * @return
     */
    public List<DeviceInfo> selectAllExceptId(int id);

    /**
     * 增加
     * @param deviceInfo
     */
    public void addDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 修改
     * @param deviceInfo
     */
    public void updateDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 更新设备状态
     * @param deviceInfo
     */
    public void updateDeviceInfoStatus(DeviceInfo deviceInfo);

    /**
     * 删除
     * @param id
     */
    public void deleteDeviceInfo(int id);

    /**
     * 获得所有设备的状态
     * @return
     */
    public List<DeviceInfo> getAllDeviceInfoStatus();

    /**
     * 获得所有树莓派的状态，用于视频管理
     * @return
     */
    public List<DeviceInfo> getRaspberryStreamStatus();

    /**
     * 获得所有正在推流的教室信息
     * @param raspberryStreamStatus
     * @return
     */
    public List<DeviceInfo> getPushClassroomList(int raspberryStreamStatus);

    /**
     * 获得所有正在推流的教学楼信息
     * @param raspberryStreamStatus
     * @return
     */
    public List<DeviceInfo> getAllPushBuildingList(int raspberryStreamStatus);

    /**
     * 获得所有正在推流的教学楼设备下的教室信息
     * @param buildingNum
     * @return
     */
    public List<DeviceInfo> getAllPushClassroomList(String buildingNum,int raspberryStreamStatus);
}
