package com.pku.system.service;

import com.pku.system.model.OfficialCarApply;
import com.pku.system.model.QueryAvailcarList;
import com.pku.system.model.carListInfoByUserID;

import java.util.Date;
import java.util.List;

/**
 * (OfficialCarApply)表服务接口
 * * @author makejava
 * @since 2019-09-03 15:00:10
 */
public interface OfficialCarApplyService {

    /**
     * 通过ID查询单条用车申请数据记录
     * @param id 主键
     * @return 实例对象
     */
    public OfficialCarApply queryById(int id);

    /**
     * 通过用户ID查询信息（通过user_id进行全表查询）
     * @param user_id
     * @return 实例对象实例数据
     */
    public List<OfficialCarApply> queryByUserId(int user_id);


    /**
     * 通过公车ID查询信息（通过car_id进行全表查询）
     * @param car_id
     * @return 实例对象实例数据
     */
    public List<OfficialCarApply> queryByCarId(Integer car_id);

    /**
     * 新增申请用车信息操作，提交一条新的记录
     * @param officialCarApply 实例对象
     * @return 插入数据
     */
    void addOfficialCarApply(OfficialCarApply officialCarApply);

    /**
     * 更新申请用车信息操作，提交一条新的记录
     * @param officialCarApply 实例对象
     * @return 更新消息
     */
    void updateOfficialCarApplyById(OfficialCarApply officialCarApply);

    /**
     * 通过user_id字段信息，执行删除申请用车信息操作
     * @param user_id
     * @return 删除用车信息
     */
    void deleteOfficialCarApply(int user_id);


    /**
     * 定时任务的更新操作
     */
    void updateOfficialCarApplyStatusSchedule(int status);

    /**
     * 获取当前时间的可用车辆的列表
     */
    public List<QueryAvailcarList> queryAvailabilityCarList(Date stratTime);


    /**
     * 获得所有申请条目。
     * @return
     */
    public List<OfficialCarApply> getAllOfficialCarApply();

    /**
     * 根据申请单号查询申请条目。
     * @param id
     * @return
     */
    public OfficialCarApply selectOfficialCarApplyById(int id);

    /**
     * 审核更新，将申请条目的状态置为通过或者未通过。
     * @param officialCarApply
     */

    public void updateOfficialCarApply(OfficialCarApply officialCarApply);
    /**
     * 通过状态值来查询对应的所有表单信息。
     * @param status
     * @return
     */
    public List<OfficialCarApply> selectOfficialCarApplyByStatus(int status);

    // 获取所有用户的用车申请单信息
    public List<carListInfoByUserID> queryCarListInfoByUserId();

}