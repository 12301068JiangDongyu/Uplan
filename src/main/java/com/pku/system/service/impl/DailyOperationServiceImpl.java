package com.pku.system.service.impl;

import com.pku.system.dao.DailyOperationDao;
import com.pku.system.model.DailyOperation;
import com.pku.system.service.DailyOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyOperationServiceImpl implements DailyOperationService {
    @Autowired
    DailyOperationDao dailyOperationDao;

    @Override
    public DailyOperation selectById(int id) {
        return dailyOperationDao.selectById(id) ;
    }

    @Override
    public DailyOperation selectRemark(String remark) {
        return dailyOperationDao.selectByRemark(remark);
    }

    @Override
    public List<DailyOperation> getAllDailyOperation(){
        return dailyOperationDao.getAllDailyOperation();
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
}
