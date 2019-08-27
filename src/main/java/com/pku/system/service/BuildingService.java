package com.pku.system.service;

import com.pku.system.model.Building;

import java.util.List;

public interface BuildingService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Building selectById(int id);

    /**
     * 根据教学楼号查询
     * @param buildingNum
     * @return
     */
    public Building selectByName(String buildingNum);

    /**
     * 获得所有的教学楼
     * @return
     */
    public List<Building> getAllBuilding();

    /**
     * 增加教学楼
     * @param building
     */
    public void addBuilding(Building building);

    /**
     * 更新教学楼
     * @param building
     */
    public void updateBuilding(Building building);

    /**
     * 删除教学楼
     * @param id
     */
    public void deleteBuilding(int id);
}
