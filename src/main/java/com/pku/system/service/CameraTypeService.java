package com.pku.system.service;

import com.pku.system.model.CameraType;

import java.util.List;

public interface CameraTypeService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public CameraType selectById(int id);

    /**
     * 根据名字查询
     * @param cameraTypeName
     * @return
     */
    public CameraType selectByName(String cameraTypeName);

    /**
     * 获得所有摄像头类型
     * @return
     */
    public List<CameraType> getAllCameraType();

    /**
     * 增加摄像头信息
     * @param cameraType
     */
    public void addCameraType(CameraType cameraType);

    /**
     * 更新摄像头信息
     * @param cameraType
     */
    public void updateCameraType(CameraType cameraType);

    /**
     * 删除摄像头信息
     * @param id
     */
    public void deleteCameraType(int id);
}
