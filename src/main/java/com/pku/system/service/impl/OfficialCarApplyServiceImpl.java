package com.pku.system.service.impl;

import com.pku.system.dao.OfficialCarApplyDao;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.OfficialCarApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (OfficialCarApply)表服务实现类
 *
 * @author makejava
 * @since 2019-09-03 15:00:56
 */
@Service
public class OfficialCarApplyServiceImpl implements OfficialCarApplyService {

    @Autowired
    OfficialCarApplyDao officialCarApplyDao;


    @Override
    public OfficialCarApply queryById(int id) {
        return officialCarApplyDao.queryById(id);
    }

    @Override
    public List<OfficialCarApply> queryByUserId(int user_id) {
        return officialCarApplyDao.queryByUserId(user_id);
    }

    @Override
    public List<OfficialCarApply> queryByCarId(Integer car_id) {
        return officialCarApplyDao.queryByCarId(car_id);
    }

    @Override
    public void addOfficialCarApply(OfficialCarApply officialCarApply) {
        officialCarApplyDao.addOfficialCarApply(officialCarApply);
    }

    @Override
    public void updateOfficialCarApply(OfficialCarApply officialCarApply) {
        officialCarApplyDao.updateOfficialCarApply(officialCarApply);
    }

    @Override
    public void deleteOfficialCarApply(int user_id) {
        officialCarApplyDao.deleteOfficialCarApply(user_id);
    }

    @Override
    public void updateOfficialCarApplyStatusSchedule(int status){
        officialCarApplyDao.updateOfficialCarApplyStatusSchedule(status);
    }
}