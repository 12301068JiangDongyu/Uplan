package com.pku.system.service.impl;

import com.pku.system.dao.ProjectorTypeDao;
import com.pku.system.model.ProjectorType;
import com.pku.system.service.ProjectorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectorTypeServiceImpl implements ProjectorTypeService {
    @Autowired
    ProjectorTypeDao projectorTypeDao;

    @Override
    public ProjectorType selectById(int id) {
        return projectorTypeDao.selectById(id);
    }

    @Override
    public ProjectorType selectByName(String projectorTypeName) {
        return projectorTypeDao.selectByName(projectorTypeName);
    }

    @Override
    public List<ProjectorType> getAllProjectorType() {
        return projectorTypeDao.getAllProjectorType();
    }

    @Override
    public void addProjectorType(ProjectorType projectorType) {
        projectorTypeDao.addProjectorType(projectorType);

    }

    @Override
    public void updateProjectorType(ProjectorType projectorType) {
        projectorTypeDao.updateProjectorType(projectorType);

    }

    @Override
    public void deleteProjectorType(int id) {
        projectorTypeDao.deleteProjectorType(id);

    }
}
