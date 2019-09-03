package com.pku.system.service;


import com.pku.system.model.Car;
import com.pku.system.model.CarType;

import java.util.List;

public interface CarTypeService {
    /**
     * 根据id查询公务车类型
     * @param id
     * @return
     */
    public CarType selectById(int id);

    /**
     * 根据使用者查询公务车
     * @param brand
     * @return
     */
    public CarType selectByBrand(String brand);

    /**
     * 获得所有的公务车类型
     * @return
     */
    public List<CarType> getAllCarType();

    /**
     * 增加公务车类型
     * @param carType
     */
    public void addCarType(CarType carType);

    /**
     * 更新公务车类型
     * @param carType
     */
    public void updateCarType(CarType carType);

    /**
     * 删除公务车类型
     * @param id
     */
    public void deleteCarType(int id);
}
