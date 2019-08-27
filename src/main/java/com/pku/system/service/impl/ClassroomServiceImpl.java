package com.pku.system.service.impl;

import com.pku.system.dao.ClassroomDao;
import com.pku.system.model.Classroom;
import com.pku.system.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    ClassroomDao classroomDao;

    @Override
    public Classroom selectById(int id) {
        return classroomDao.selectById(id);
    }

    @Override
    public Classroom selectByName(String classroomNum) {
        return classroomDao.selectByName(classroomNum);
    }

    @Override
    public Classroom selectByNameAndBid(String classroomNum, int bid) {
        return classroomDao.selectByNameAndBid(classroomNum,bid);
    }

    @Override
    public List<Classroom> selectByBuildingId(int bid) {
        return classroomDao.selectByBuildingId(bid);
    }

    @Override
    public List<Classroom> getAllClassroom() {
        return classroomDao.getAllClassroom();
    }

    @Override
    public void addClassroom(Classroom classroom) {
        classroomDao.addClassroom(classroom);

    }

    @Override
    public void updateClassroom(Classroom classroom) {
        classroomDao.updateClassroom(classroom);

    }

    @Override
    public void deleteClassroom(int id) {
        classroomDao.deleteClassroom(id);

    }
}
