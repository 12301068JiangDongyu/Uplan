package com.pku.system.service.impl;

import com.pku.system.dao.DeviceInfoDao;
import com.pku.system.model.DeviceInfo;
import com.pku.system.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Autowired
    DeviceInfoDao deviceInfoDao;

    @Override
    public DeviceInfo selectById(int id) {
        return deviceInfoDao.selectById(id);
    }

    @Override
    public DeviceInfo selectByBuildingClassroom(String buildingNum, String classroomNum) {
        return deviceInfoDao.selectByBuildingClassroom(buildingNum, classroomNum);
    }

    @Override
    public DeviceInfo selectByRaspberryCode(String raspberryCode) {
        return deviceInfoDao.selectByRaspberryCode(raspberryCode);
    }

    @Override
    public List<DeviceInfo> getAllDeviceInfo() {
        return deviceInfoDao.getAllDeviceInfo();
    }

    @Override
    public List<DeviceInfo> selectAllExceptId(int id) {
        return deviceInfoDao.selectAllExceptId(id);
    }

    @Override
    public void addDeviceInfo(DeviceInfo deviceInfo) {
        deviceInfoDao.addDeviceInfo(deviceInfo);

    }

    @Override
    public void updateDeviceInfo(DeviceInfo deviceInfo) {
        deviceInfoDao.updateDeviceInfo(deviceInfo);

    }

    @Override
    public void updateDeviceInfoStatus(DeviceInfo deviceInfo) {
        deviceInfoDao.updateDeviceInfoStatus(deviceInfo);

    }

    @Override
    public void deleteDeviceInfo(int id) {
        deviceInfoDao.deleteDeviceInfo(id);

    }

    @Override
    public List<DeviceInfo> getAllDeviceInfoStatus() {
        return deviceInfoDao.getAllDeviceInfoStatus();
    }

    @Override
    public List<DeviceInfo> getRaspberryStreamStatus() {
        return deviceInfoDao.getRaspberryStreamStatus();
    }

    @Override
    public List<DeviceInfo> getPushClassroomList(int raspberryStreamStatus) {
        return deviceInfoDao.getPushClassroomList(raspberryStreamStatus);
    }

    @Override
    public List<DeviceInfo> getAllPushBuildingList(int raspberryStreamStatus) {
        return deviceInfoDao.getAllPushBuildingList(raspberryStreamStatus);
    }

    @Override
    public List<DeviceInfo> getAllPushClassroomList(String buildingNum, int raspberryStreamStatus) {
        return deviceInfoDao.getAllPushClassroomList(buildingNum, raspberryStreamStatus);
    }
}
