package com.pku.system.service.impl;

import com.pku.system.dao.PullInfoDao;
import com.pku.system.model.PullInfo;
import com.pku.system.service.PullInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PullInfoServiceImpl implements PullInfoService {
    @Autowired
    PullInfoDao pullInfoDao;

    @Override
    public PullInfo selectById(int id) {
        return pullInfoDao.selectById(id);
    }

    @Override
    public void addPullInfo(PullInfo pullInfo) {
        pullInfoDao.addPullInfo(pullInfo);

    }

    @Override
    public void updatePullInfo(PullInfo pullInfo) {
        pullInfoDao.updatePullInfo(pullInfo);

    }
}
