package com.pku.system.dao;

import com.pku.system.model.OfficialCarApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
@Mapper
public interface OfficialCarApplyDao {
    @Select("select * from official_car_apply")
    public List<OfficialCarApply> getAllOfficialCarApply();

    @Select("select * from official_car_apply where id = #{id}")
    public OfficialCarApply selectById(int id);
    //这里还要加时间戳
    @Update("update official_car_apply set status = #{status}, update_time=#{update_time} where id = #{id}")
//    public void updateOfficialCarApply(OfficialCarApply officialCarApply);
    public void updateOfficialCarApply(@Param("status") int status,
                                       @Param("update_time") Date update_time,
                                       @Param("id") int id);

    @Select("select * from official_car_apply where status = #{status}")
    public List<OfficialCarApply> selectOfficialCarApplyByStatus(int status);
}
