package com.pku.system.service.impl;

import com.pku.system.dao.OfficialCarApplyDao;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.OfficialCarApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class OfficialCarApplyServiceImpl implements OfficialCarApplyService {
    @Autowired(required = false)
    OfficialCarApplyDao officialCarApplyDao;

    @Override
    public List<OfficialCarApply> getAllOfficialCarApply() {
        return officialCarApplyDao.getAllOfficialCarApply();
    }

    @Override
    public OfficialCarApply selectOfficialCarApplyById(int id) {
        return officialCarApplyDao.selectById(id);
    }

    @Override
//    public void updateOfficialCarApply(OfficialCarApply officialCarApply) {
//        officialCarApplyDao.updateOfficialCarApply(officialCarApply);
//    }
    public void updateOfficialCarApply(OfficialCarApply officialCarApply){
        officialCarApplyDao.updateOfficialCarApply(officialCarApply.getStatus(),
                officialCarApply.getUpdate_time(),
                officialCarApply.getId());
    }

    @Override
    public List<OfficialCarApply> selectOfficialCarApplyByStatus(int status) {
        return officialCarApplyDao.selectOfficialCarApplyByStatus(status);
    }
}
