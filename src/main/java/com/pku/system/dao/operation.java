package com.pku.system.dao;

import com.pku.system.model.DailyOperation;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface operation {
@Select("select * from dailyOperation where id = #{id}")
    public DailyOperation selectById(int id);


@Select("select * from dailyOperation where remark = #{remark}")

    public DailyOperation selectByRemark (String remark);



@Select("select * from dailyOperation")
    public List<DailyOperation> getAllDailyOperation();

@Insert("insert into dailyOperation(id ,carId,cost,type,ocurrenceTime,remark,creator,createTime) values (#{id},#{carId},#{cost},#{type},#{ocurrenceTime},#{remark},#{creator},#{createTime})")
    public void addDailyOperation (DailyOperation dailyOperation);

@Update("update dailyOperation set carId = #{carId},cost = #{cost},type = #{type},ocurrenceTime = #{ocurrenceTime},remark = #{remark},creator = #{creator},createTime = #{CreateTime} where id = #{id}")
    public void updateDailyOperation (DailyOperation dailyOperation);

@Delete("delete from dailyOperation where id = #{id}")
    public void deleteDailyOperation (int id);



}
