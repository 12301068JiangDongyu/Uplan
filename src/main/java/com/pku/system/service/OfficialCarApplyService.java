package com.pku.system.service;

import com.pku.system.dto.StatisticDto;
import com.pku.system.dto.StatisticTimeDto;
import com.pku.system.model.OfficialCarApply;

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
    void updateOfficialCarApply(OfficialCarApply officialCarApply);

    /**
     * 通过user_id字段信息，执行删除申请用车信息操作
     * @param user_id
     * @return 删除用车信息
     */
    void deleteOfficialCarApply(int user_id);

    /**
     * 获得车型使用情况
     * @return
     */
    List<StatisticDto> getAllBrandCount();

    /**
     * 员工预约班车次数统计
     * @return
     */
    List<StatisticDto> getAllUserCount();

    /**
     * 根据年月统计申请审核通过车的次数
     * @return
     */
    List<StatisticTimeDto> getAllTimeCount();

}