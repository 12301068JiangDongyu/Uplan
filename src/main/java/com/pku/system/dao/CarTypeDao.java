package com.pku.system.dao;

import com.pku.system.model.Car;
import com.pku.system.model.CarType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CarTypeDao {
    @Select("select * from car_type where id = #{id}")
    public CarType selectById(int id);

    @Select("select * from car_type where buildingNum = #{buildingNum}")
    public CarType selectByBrand(String brand);

    @Select("select id,brand,capacity,price,buy_time,seat_num,oil_type from car_type")
    public List<CarType> getAllCarType();

    @Insert("insert into car_type (id,brand,capacity,price,buy_time,seat_num,oil_type,creator,create_time,update_time) values (#{id},#{brand},#{capacity},#{price},#{buy_time},#{seat_num},#{oil_type},#{creator},#{create_time},#{update_time})")
    public void addCarType(CarType carType);

    @Update("update car_type set brand=#{brand},capacity=#{capacity},price=#{price},buy_time=#{buy_time},seat_num=#{seat_num},oil_type=#{oil_type},creator=#{creator} where id=#{id}")
    public void updateCarType(CarType carType);

    @Delete("delete from car_type where id=#{id}")
    public void deleteCarType(int id);

}
