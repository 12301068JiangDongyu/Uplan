package com.pku.system.dao;

import com.pku.system.model.Statistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName StatisticDao
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Mapper
public interface StatisticDao {
    @Select("select * from statistic")
    List<Statistic> selectAll();

}
