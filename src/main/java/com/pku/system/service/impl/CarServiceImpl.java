package com.pku.system.service.impl;

import com.pku.system.dao.BuildingDao;
import com.pku.system.dao.CarDao;
import com.pku.system.model.Building;
import com.pku.system.model.Car;
import com.pku.system.service.BuildingService;
import com.pku.system.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarDao carDao;

    @Override
    public Car selectById(int id) {
        return carDao.selectById(id);
    }

    @Override
    public Car selectLicensePlateNum(String plateNum) {
        return carDao.selectByLicense_Plate_Num(plateNum);
    }

    @Override
    public List<Car> getAllCar() {
        return carDao.getAllCar();
    }

    @Override
    public void addCar(Car car) {
        carDao.addCar(car);
    }

    @Override
    public void updateCar(Car car) {
        carDao.updateCar(car);

    }

    @Override
    public void deleteCar(int id) {
        carDao.deleteCar(id);

    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Car selectCarById(int id) {
        return carDao.selectById(id);
    }

    /**
     *
     * @param id
     * @param status
     */
    @Override
    public void updateCarStatusById(int id, int status) {
        carDao.updateCarById(id, status);
    }
}
