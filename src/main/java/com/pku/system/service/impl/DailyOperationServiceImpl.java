package com.pku.system.service.impl;

import com.pku.system.dao.DailyOperationDao;
import com.pku.system.model.DailyOperation;
import com.pku.system.service.CarService;
import com.pku.system.service.DailyOperationService;
import com.pku.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyOperationServiceImpl implements DailyOperationService {
    @Autowired
    DailyOperationDao dailyOperationDao;

    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @Override
    public DailyOperation selectById(int id) {
        return dailyOperationDao.selectById(id) ;
    }

    @Override
    public DailyOperation selectRemark(String remark) {
        return dailyOperationDao.selectByRemark(remark);
    }

    @Override
    public List<DailyOperation> getAllDailyOperation(int type){
        return dailyOperationDao.getAllDailyOperation(type);
    }

    @Override
    public void addDailyOperation(DailyOperation dailyOperation) {
            dailyOperationDao.addDailyOperation(dailyOperation);
    }

    @Override
    public void updateDailyOperation(DailyOperation dailyOperation) {
            dailyOperationDao.updateDailyOperation(dailyOperation);
    }

    @Override
    public void deleteDailyOperation(int id) {
            dailyOperationDao.deleteDailyOperation(id);
    }

    @Override
    public List<DailyOperation> dealDailyOperation(List<DailyOperation> list){
        List<DailyOperation> newList = new ArrayList<>();

        for(DailyOperation dailyOperation : list){
            if(userService.selectById(dailyOperation.getCreator()) != null && carService.selectById(dailyOperation.getCar_id()) != null){
                dailyOperation.setReal_name(userService.selectById(dailyOperation.getCreator()).getReal_name());
                dailyOperation.setLicense_num(carService.selectById(dailyOperation.getCar_id()).getLicense_plate_num());
                newList.add(dailyOperation);
            }

        }
        return newList;

    }
}
