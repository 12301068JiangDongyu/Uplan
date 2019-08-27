package com.pku.system.service.impl;

import com.pku.system.dao.ComputerTypeDao;
import com.pku.system.model.ComputerType;
import com.pku.system.service.ComputerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputerTypeServiceImpl implements ComputerTypeService {
    @Autowired
    ComputerTypeDao computerTypeDao;

    @Override
    public ComputerType selectById(int id) {
        return computerTypeDao.selectById(id);
    }

    @Override
    public ComputerType selectByName(String computerTypeName) {
        return computerTypeDao.selectByName(computerTypeName);
    }

    @Override
    public List<ComputerType> getAllComputerType() {
        return computerTypeDao.getAllComputerType();
    }

    @Override
    public void addComputerType(ComputerType computerType) {
        computerTypeDao.addComputerType(computerType);

    }

    @Override
    public void updateComputerType(ComputerType computerType) {
        computerTypeDao.updateComputerType(computerType);

    }

    @Override
    public void deleteComputerType(int id) {
        computerTypeDao.deleteComputerType(id);

    }

    @Override
    public ComputerType selectByAll(String computerTypeName, String memorySize, String diskSize, String operatingSystem) {
        return computerTypeDao.selectByAll(computerTypeName, memorySize, diskSize, operatingSystem);
    }
}
