package com.pku.system.service;

import com.pku.system.model.RaspberryType;

import java.util.List;

public interface RaspberryTypeService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public RaspberryType selectById(int id);

    /**
     * 根据名字查询
     * @param raspberryTypeName
     * @return
     */
    public RaspberryType selectByName(String raspberryTypeName);

    /**
     * 获得所有树莓派类型
     * @return
     */
    public List<RaspberryType> getAllRaspberryType();

    /**
     * 增加树莓派信息
     * @param raspberryType
     */
    public void addRaspberryType(RaspberryType raspberryType);

    /**
     * 更新树莓派信息
     * @param raspberryType
     */
    public void updateRaspberryType(RaspberryType raspberryType);

    /**
     * 删除树莓派信息
     * @param id
     */
    public void deleteRaspberryType(int id);
}
