package com.pku.system.service;

import com.pku.system.model.ProjectorType;

import java.util.List;

public interface ProjectorTypeService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ProjectorType selectById(int id);

    /**
     * 根据名字查询
     * @param projectorTypeName
     * @return
     */
    public ProjectorType selectByName(String projectorTypeName);

    /**
     * 获得所有投影仪类型
     * @return
     */
    public List<ProjectorType> getAllProjectorType();

    /**
     * 增加投影仪信息
     * @param projectorType
     */
    public void addProjectorType(ProjectorType projectorType);

    /**
     * 更新投影仪信息
     * @param projectorType
     */
    public void updateProjectorType(ProjectorType projectorType);

    /**
     * 删除投影仪信息
     * @param id
     */
    public void deleteProjectorType(int id);
}
