package com.pku.system.service;

import com.pku.system.model.Car;

import java.util.List;

public interface CarService {
    /**
     * 根据id查询公务车
     * @param id
     * @return
     */
    public Car selectById(int id);

    /**
     * 根据使用者查询公务车
     * @param plateNum
     * @return
     */
    public Car selectLicensePlateNum(String plateNum);

    /**
     * 获得所有的公务车
     * @return
     */
    public List<Car> getAllCar();

    /**
     * 增加公务车
     * @param car
     */
    public void addCar(Car car);

    /**
     * 更新公务车
     * @param car
     */
    public void updateCar(Car car);

    /**
     * 删除公务车
     * @param id
     */
    public void deleteCar(int id);


    /**
     * 空闲车辆查询
     * @param status
     */
    public List<Car> selectCarByStatus(int status);
}
