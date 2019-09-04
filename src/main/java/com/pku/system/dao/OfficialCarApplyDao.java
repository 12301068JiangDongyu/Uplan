package com.pku.system.dao;

import com.pku.system.model.OfficialCarApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;
@Mapper
public interface OfficialCarApplyDao {
    @Select("select * from official_car_apply")
    public List<OfficialCarApply> getAllOfficialCarApply();

    @Select("select * from official_car_apply where id = #{id}")
    public OfficialCarApply selectById(int id);
    //这里还要加时间戳
    @Update("update official_car_apply set status = #{0} where id = #{1}")
    public void updateOfficialCarApply(int id, int status);

    @Select("select * from official_car_apply where status = #{status}")
    public List<OfficialCarApply> selectOfficialCarApplyByStatus(int status);
}
