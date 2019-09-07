package com.pku.system.dao;

import com.pku.system.dto.StatisticDto;
import com.pku.system.dto.StatisticTimeDto;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.model.QueryAvailcarList;
import com.pku.system.model.carListInfoByUserID;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (OfficialCarApply)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-03 15:09:05
 */
@Mapper
@Component
public interface OfficialCarApplyDao {

    /**
     * 通过ID查询单条用车申请数据记录
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select * from official_car_apply where id = #{id}")
    public OfficialCarApply queryById(int id);


    /**
     * 通过用户ID查询信息（通过user_id进行全表查询）
     *
     * @param user_id
     * @return 实例对象实例数据
     */
    @Select("select * from official_car_apply where user_id = #{user_id}")
    public List<OfficialCarApply> queryByUserId(int user_id);


    /**
     * 通过公车ID查询信息（通过car_id进行全表查询）
     *
     * @param car_id
     * @return 实例对象实例数据
     */
    @Select("select * from official_car_apply where car_id = #{car_id}")
    public List<OfficialCarApply> queryByCarId(Integer car_id);


    /**
     * 新增申请用车信息操作，提交一条新的记录
     *
     * @param officialCarApply 实例对象
     * @return 插入数据
     */
    @Insert("insert into official_car_apply (id,car_id,brand,user_id,destination,start_time,end_time,reason,travel_distance,oil_used,status,remark,create_time,update_time) values (#{id},#{car_id},#{brand},#{user_id},#{destination},#{start_time},#{end_time},#{reason},#{travel_distance},#{oil_used},1,#{remark},#{create_time},#{update_time})")
    void addOfficialCarApply(OfficialCarApply officialCarApply);


    /**
     * 更新申请用车信息操作，提交一条新的记录
     *
     * @param officialCarApply 实例对象
     * @return 更新消息
     */
    @Update("update official_car_apply set status=#{status} where id = #{id}")
    void updateOfficialCarApplyById(OfficialCarApply officialCarApply);


    /**
     * 通过user_id字段信息，执行删除申请用车信息操作
     *
     * @param user_id
     * @return 删除用车信息
     */
    @Delete("delete from official_car_apply where user_id = #{user_id}")
    void deleteOfficialCarApply(int user_id);


    @Update("update official_car_apply set status = #{status} where status = 0")
    void updateOfficialCarApplyStatusSchedule(int status);

    // 获取当前时间可用车辆清单列表
    @Select("SELECT car.`id`,car_type.`brand`,car.`license_plate_num` FROM car,car_type WHERE car.`id` NOT IN(\n" +
            "SELECT car_id FROM official_car_apply AS oca WHERE DATEDIFF(#{startTime},start_time) = 0 AND oca.`status` = 1\n" +
            ") AND car.`car_type_id` = car_type.id AND car.`status` = 1;")
    public List<QueryAvailcarList> queryAvailabilityCarList(Date startTime);

    // 获取所有用户的用车记录
    @Select("SELECT car.`id`,official_car_apply.`id` AS applyId,official_car_apply.`brand`,car_type.`seat_num`,official_car_apply.`start_time` FROM car,official_car_apply,car_type WHERE car.`car_type_id` = car_type.`id` AND car.id = official_car_apply.car_id AND official_car_apply.status = 1")
    public List<carListInfoByUserID> queryCarListInfoByUserId();


    //-------------------------------------------------------张晔----------------------------------------------
    @Select("select ca.*, tu.username from `official_car_apply` ca, tb_user tu where ca.user_id = tu.id")
    public List<OfficialCarApply> getAllOfficialCarApply();

    @Select("select * from official_car_apply where id = #{id}")
    public OfficialCarApply selectById(int id);
    //这里还要加时间戳
    @Update("update official_car_apply set status = #{status}, update_time=#{update_time} where id = #{id}")
//    public void updateOfficialCarApply(OfficialCarApply officialCarApply);
    public void updateOfficialCarApply(@Param("status") int status,
                                       @Param("update_time") Date update_time,
                                       @Param("id") int id);

    @Select("select ca.*, tu.username from `official_car_apply` ca, tb_user tu where ca.user_id = tu.id and ca.status = #{status}")
    public List<OfficialCarApply> selectOfficialCarApplyByStatus(int status);

    /**
     * 车型使用情况
     * @return
     */
    @Select("select brand as keyName,count(brand) as keyValue from official_car_apply where status = 1 group by brand order by count(brand) DESC")
    List<StatisticDto> getAllBrandCount();

    /**
     * 员工预约班车次数统计
     * @return
     */
    @Select("select u.real_name as keyName,count(o.user_id) as keyValue from official_car_apply o INNER JOIN tb_user u on o.user_id = u.id where o.status = 1 group by o.user_id order by count(o.user_id) DESC LIMIT 10")
    List<StatisticDto> getAllUserCount();


    /**
     * 根据年月统计申请审核通过车的次数
     * @return
     */
    @Select("SELECT COUNT( 1 ) AS keyValue FROM official_car_apply WHERE STATUS = 1 and YEAR(start_time) = #{year} GROUP BY MONTH ( start_time )")
    List<Integer> getAllTimeCount(int year);



}



