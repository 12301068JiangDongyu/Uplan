package com.pku.system.service.impl;

import com.pku.system.dao.OfficialCarApplyDao;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.OfficialCarApplyService;
import com.pku.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class OfficialCarApplyServiceImpl implements OfficialCarApplyService {
    @Autowired(required = false)
    OfficialCarApplyDao officialCarApplyDao;

    @Autowired
    UserService userService;

    @Override
    public List<OfficialCarApply> getAllOfficialCarApply() {
        List<OfficialCarApply> applyList = officialCarApplyDao.getAllOfficialCarApply();
//      格式转换
        formateResult(applyList);
        return applyList;
    }

    private void formateResult(List<OfficialCarApply> result){
        for(OfficialCarApply officialCarApply : result) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            switch (officialCarApply.getStatus()) {
                case 0:
                    officialCarApply.setStatusName("未审核");
                    break;
                case 1:
                    officialCarApply.setStatusName("通过");
                    break;
                case 2:
                    officialCarApply.setStatusName("不通过");
                    break;
                case 3:
                    officialCarApply.setStatusName("撤销");
                    break;
            }
            officialCarApply.setReason(officialCarApply.getReason()==null||officialCarApply.getReason().equals("")? "无" : officialCarApply.getReason());
            if (officialCarApply.getStart_time() != null) {
                officialCarApply.setStartTime(dateFormat.format(officialCarApply.getStart_time()));
            } else
                officialCarApply.setStartTime("");
        }
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
        List<OfficialCarApply> res = officialCarApplyDao.selectOfficialCarApplyByStatus(status);
        formateResult(res);
        return res;
    }
}
