package com.pku.system.service.impl;

import com.pku.system.dao.RaspberryTypeDao;
import com.pku.system.model.RaspberryType;
import com.pku.system.service.RaspberryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaspberryTypeServiceImpl implements RaspberryTypeService {
    @Autowired
    RaspberryTypeDao raspberryTypeDao;

    @Override
    public RaspberryType selectById(int id) {
        return raspberryTypeDao.selectById(id);
    }

    @Override
    public RaspberryType selectByName(String raspberryTypeName) {
        return raspberryTypeDao.selectByName(raspberryTypeName);
    }

    @Override
    public List<RaspberryType> getAllRaspberryType() {
        return raspberryTypeDao.getAllRaspberryType();
    }

    @Override
    public void addRaspberryType(RaspberryType raspberryType) {
        raspberryTypeDao.addRaspberryType(raspberryType);

    }

    @Override
    public void updateRaspberryType(RaspberryType raspberryType) {
        raspberryTypeDao.updateRaspberryType(raspberryType);

    }

    @Override
    public void deleteRaspberryType(int id) {
        raspberryTypeDao.deleteRaspberryType(id);

    }
}
