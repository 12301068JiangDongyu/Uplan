package com.pku.system.service.impl;

import com.pku.system.dao.BuildingDao;
import com.pku.system.model.Building;
import com.pku.system.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    BuildingDao buildingDao;

    @Override
    public Building selectById(int id) {
        return buildingDao.selectById(id);
    }

    @Override
    public Building selectByName(String buildingNum) {
        return buildingDao.selectByName(buildingNum);
    }

    @Override
    public List<Building> getAllBuilding() {
        return buildingDao.getAllBuilding();
    }

    @Override
    public void addBuilding(Building building) {
        buildingDao.addBuilding(building);

    }

    @Override
    public void updateBuilding(Building building) {
        buildingDao.updateBuilding(building);

    }

    @Override
    public void deleteBuilding(int id) {
        buildingDao.deleteBuilding(id);

    }
}
