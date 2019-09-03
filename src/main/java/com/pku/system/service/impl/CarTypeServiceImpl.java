package com.pku.system.service.impl;

import com.pku.system.dao.CarTypeDao;
import com.pku.system.model.Car;
import com.pku.system.model.CarType;
import com.pku.system.service.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarTypeServiceImpl implements CarTypeService {

    @Autowired
    CarTypeDao carTypeDao;

    @Override
    public CarType selectById(int id) {
        return carTypeDao.selectById(id);
    }

    @Override
    public CarType selectByBrand(String brand) {
        return carTypeDao.selectByBrand(brand);
    }

    @Override
    public List<CarType> getAllCarType() {
        return carTypeDao.getAllCarType();
    }

    @Override
    public void addCarType(CarType carType) {
        carTypeDao.addCarType(carType);

    }

    @Override
    public void updateCarType(CarType car) {
        carTypeDao.updateCarType(car);

    }

    @Override
    public void deleteCarType(int id) {
        carTypeDao.deleteCarType(id);

    }


}
