package com.pku.system.dao;

import com.pku.system.dto.StatisticDto;
import com.pku.system.dto.StatisticTimeDto;
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

@Select("SELECT c.license_plate_num AS card,COUNT( 1 ) AS count,SUM( d.cost ) AS cost,DATE_FORMAT(c.run_time,'%Y%m') AS time FROM daily_operation d INNER JOIN car c on d.car_id = c.id WHERE d.type = #{type} GROUP BY d.car_id")
    List<StatisticTimeDto> getStatistics(int type);

@Select("select distinct car_id from daily_operation")
    List<Integer> getALLDistinctCarId();

    /**
     * 员工预约班车次数统计
     * @return
     */
    @Select("select u.real_name as keyName,count(d.creator) as keyValue from daily_operation d INNER JOIN tb_user u on d.creator = u.id WHERE type = 3 group by d.creator order by count(d.creator) DESC LIMIT 10")
    List<StatisticDto> getAllRuleUserCount();

}
