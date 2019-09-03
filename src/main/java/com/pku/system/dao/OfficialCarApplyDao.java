package com.pku.system.dao;

import com.pku.system.model.OfficialCarApply;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (OfficialCarApply)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-03 15:09:05
 */
@Mapper
public interface OfficialCarApplyDao {

    /**
     * 通过ID查询单条用车申请数据记录
     * @param id 主键
     * @return 实例对象
     */
    @Select("select * from official_car_apply where id = #{id}")
    public OfficialCarApply queryById(int id);


    /**
     * 通过用户ID查询信息（通过user_id进行全表查询）
     * @param user_id
     * @return 实例对象实例数据
     */
    @Select("select * from official_car_apply where user_id = #{user_id}")
    public List<OfficialCarApply> queryByUserId(int user_id);


    /**
     * 通过公车ID查询信息（通过car_id进行全表查询）
     * @param car_id
     * @return 实例对象实例数据
     */
    @Select("select * from official_car_apply where car_id = #{car_id}")
    public List<OfficialCarApply> queryByCarId(Integer car_id);


    /**
     * 新增申请用车信息操作，提交一条新的记录
     * @param officialCarApply 实例对象
     * @return 插入数据
     */
    @Insert("insert into official_car_apply (id,car_id,brand,user_id,destination,start_time,end_time,reason,travel_distance,oil_used,status,remark,create_time,update_time) values (#{id},#{car_id},#{brand},#{user_id},#{destination},#{start_time},#{end_time},#{reson},#{travel_distance},#{oil_used},#{status},#{remark},#{create_time},#{update_time})")
    void addOfficialCarApply(OfficialCarApply officialCarApply);


    /**
     * 更新申请用车信息操作，提交一条新的记录
     * @param officialCarApply 实例对象
     * @return 更新消息
     */
    @Update("update official_car_apply set car_id=#{car_id},brand=#{brand},user_id=#{user_id},destination=#{destination},start_time=#{start_time},end_time=#{end_time},reason=#{reason},travel_distance=#{travel_distance},oil_used=#{oil_used},status=#{status},remark=#{remark},create_time=#{create_time},update_time=#{update_time} where id = #{id}")
    void updateOfficialCarApply(OfficialCarApply officialCarApply);


    /**
     * 通过user_id字段信息，执行删除申请用车信息操作
     * @param user_id
     * @return 删除用车信息
     */
    @Delete("delete from official_car_apply where user_id = #{user_id}")
    void deleteOfficialCarApply(int user_id);

}