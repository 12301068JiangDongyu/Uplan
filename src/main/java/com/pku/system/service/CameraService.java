package com.pku.system.service;

import com.pku.system.model.Camera;

import java.util.List;

public interface CameraService {
    /**
     * 根据id查询摄像头
     * @param id
     * @return
     */
    public Camera selectById(int id);

    /**
     * 关联查询一个教室下的摄像头
     * @param did
     * @return
     */
    public List<Camera> selectByDeviceId(int did);

    /**
     * 增加摄像头
     * @param camera
     */
    public void addCamera(Camera camera);

    /**
     * 修改摄像头
     * @param camera
     */
    public void updateCamera(Camera camera);

    /**
     * 删除摄像头
     * @param id
     */
    public void deleteCamera(int id);
}
