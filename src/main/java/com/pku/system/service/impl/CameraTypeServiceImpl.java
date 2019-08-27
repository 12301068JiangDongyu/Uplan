package com.pku.system.service.impl;

import com.pku.system.dao.CameraTypeDao;
import com.pku.system.model.CameraType;
import com.pku.system.service.CameraTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraTypeServiceImpl implements CameraTypeService {
    @Autowired
    CameraTypeDao cameraTypeDao;

    @Override
    public CameraType selectById(int id) {
        return cameraTypeDao.selectById(id);
    }

    @Override
    public CameraType selectByName(String cameraTypeName) {
        return cameraTypeDao.selectByName(cameraTypeName);
    }

    @Override
    public List<CameraType> getAllCameraType() {
        return cameraTypeDao.getAllCameraType();
    }

    @Override
    public void addCameraType(CameraType cameraType) {
        cameraTypeDao.addCameraType(cameraType);

    }

    @Override
    public void updateCameraType(CameraType cameraType) {
        cameraTypeDao.updateCameraType(cameraType);

    }

    @Override
    public void deleteCameraType(int id) {
        cameraTypeDao.deleteCameraType(id);

    }
}
