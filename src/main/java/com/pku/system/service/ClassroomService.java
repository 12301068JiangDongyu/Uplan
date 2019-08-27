package com.pku.system.service;

import com.pku.system.model.Classroom;

import java.util.List;

public interface ClassroomService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Classroom selectById(int id);

    /**
     * 根据教室号查询
     * @param classroomNum
     * @return
     */
    public Classroom selectByName(String classroomNum);

    /**
     * 根据教室号,教学楼号查询
     * @param classroomNum
     * @param bid
     * @return
     */
    public Classroom selectByNameAndBid(String classroomNum,int bid);

    /**
     * 根据教学楼id获得对应教室信息
     * @param bid
     * @return
     */
    public List<Classroom> selectByBuildingId(int bid);

    /**
     * 获得所有教室
     * @return
     */
    public List<Classroom> getAllClassroom();

    /**
     * 增加教室
     * @param classroom
     */
    public void addClassroom(Classroom classroom);

    /**
     * 更新教室
     * @param classroom
     */
    public void updateClassroom(Classroom classroom);

    /**
     * 删除教室
     * @param id
     */
    public void deleteClassroom(int id);
}
