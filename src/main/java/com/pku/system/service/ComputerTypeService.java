package com.pku.system.service;

import com.pku.system.model.ComputerType;

import java.util.List;

public interface ComputerTypeService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ComputerType selectById(int id);

    /**
     * 根据名字查询
     * @param computerTypeName
     * @return
     */
    public ComputerType selectByName(String computerTypeName);

    /**
     * 获得所有电脑类型
     * @return
     */
    public List<ComputerType> getAllComputerType();

    /**
     * 增加电脑信息
     * @param computerType
     */
    public void addComputerType(ComputerType computerType);

    /**
     * 更新电脑信息
     * @param computerType
     */
    public void updateComputerType(ComputerType computerType);

    /**
     * 删除电脑信息
     * @param id
     */
    public void deleteComputerType(int id);

    /**
     * 条件查询
     * @param computerTypeName
     * @param memorySize
     * @param diskSize
     * @param operatingSystem
     * @return
     */
    public ComputerType selectByAll(String computerTypeName,String memorySize,String diskSize,String operatingSystem);
}
