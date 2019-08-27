package com.pku.system.service;

import com.pku.system.model.SinglechipType;

import java.util.List;

public interface SinglechipTypeService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public SinglechipType selectById(int id);

    /**
     * 根据名字查询
     * @param singlechipTypeName
     * @return
     */
    public SinglechipType selectByName(String singlechipTypeName);

    /**
     * 获得所有单片机类型
     * @return
     */
    public List<SinglechipType> getAllSinglechipType();

    /**
     * 增加单片机信息
     * @param singlechipType
     */
    public void addSinglechipType(SinglechipType singlechipType);

    /**
     * 更新单片机信息
     * @param singlechipType
     */
    public void updateSinglechipType(SinglechipType singlechipType);

    /**
     * 删除单片机信息
     * @param id
     */
    public void deleteSinglechipType(int id);
}
