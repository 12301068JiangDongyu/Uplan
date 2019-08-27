package com.pku.system.service.impl;

import com.pku.system.dao.SinglechipTypeDao;
import com.pku.system.model.SinglechipType;
import com.pku.system.service.SinglechipTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SinglechipTypeServiceImpl implements SinglechipTypeService {
    @Autowired
    SinglechipTypeDao singlechipTypeDao;

    @Override
    public SinglechipType selectById(int id) {
        return singlechipTypeDao.selectById(id);
    }

    @Override
    public SinglechipType selectByName(String singlechipTypeName) {
        return singlechipTypeDao.selectByName(singlechipTypeName);
    }

    @Override
    public List<SinglechipType> getAllSinglechipType() {
        return singlechipTypeDao.getAllSinglechipType();
    }

    @Override
    public void addSinglechipType(SinglechipType singlechipType) {
        singlechipTypeDao.addSinglechipType(singlechipType);

    }

    @Override
    public void updateSinglechipType(SinglechipType singlechipType) {
        singlechipTypeDao.updateSinglechipType(singlechipType);

    }

    @Override
    public void deleteSinglechipType(int id) {
        singlechipTypeDao.deleteSinglechipType(id);

    }
}
