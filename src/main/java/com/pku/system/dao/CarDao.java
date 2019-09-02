package com.pku.system.dao;

import com.pku.system.model.Building;
import com.pku.system.model.Car;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarDao {
    @Select("select * from car where id = #{id}")
    public Car selectById(int id);

    @Select("select * from building where buildingNum = #{buildingNum}")
    public Car selectByName(String buildingNum);

    @Select("select * from car")
    public List<Car> getAllCar();

    @Insert("insert into car (id,car) values (#{id},#{carname})")
    public void addCar(Car car);

    @Update("update car set buildingNum=#{buildingNum} where id=#{id}")
    public void updatecar(Car car);

    @Delete("delete from car where id=#{id}")
    public void deletecar(int id);

}
