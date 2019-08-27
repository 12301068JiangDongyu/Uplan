package com.pku.system.service.impl;

import com.pku.system.dao.CameraDao;
import com.pku.system.model.Camera;
import com.pku.system.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraServiceImpl implements CameraService {
    @Autowired
    CameraDao cameraDao;

    @Override
    public Camera selectById(int id) {
        return cameraDao.selectById(id);
    }

    @Override
    public List<Camera> selectByDeviceId(int did) {
        return cameraDao.selectByDeviceId(did);
    }

    @Override
    public void addCamera(Camera camera) {
        cameraDao.addCamera(camera);

    }

    @Override
    public void updateCamera(Camera camera) {
        cameraDao.updateCamera(camera);

    }

    @Override
    public void deleteCamera(int id) {
        cameraDao.deleteCamera(id);

    }
}
