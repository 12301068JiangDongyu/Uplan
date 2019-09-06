package com.pku.system.dao;

import com.pku.system.model.DailyOperation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface DailyOperationDao {
@Select("select id,car_id,cost,type,occurrence_time,remark,creator from daily_operation where id = #{id}")
    public DailyOperation selectById(int id);


@Select("select id,car_id,cost,type,occurrence_time,remark,creator from daily_operation where remark = #{remark}")

    public DailyOperation selectByRemark (String remark);


@Select("select id,car_id,cost,type,occurrence_time,remark,creator from daily_operation where type = #{type}")
    public List<DailyOperation> getAllDailyOperation(int type);

@Insert("insert into daily_operation(id,car_id,cost,type,occurrence_time,remark,creator,create_time) values (#{id},#{car_id},#{cost},#{type},#{occurrence_time},#{remark},#{creator},#{create_time})")
    public void addDailyOperation (DailyOperation dailyOperation);

@Update("update daily_operation set car_id = #{car_id},cost = #{cost},type = #{type},occurrence_time = #{occurrence_time},remark = #{remark},creator = #{creator},create_time = #{create_time} where id = #{id}")
    public void updateDailyOperation (DailyOperation dailyOperation);

@Delete("delete from daily_operation where id = #{id}")
    public void deleteDailyOperation (int id);



}
