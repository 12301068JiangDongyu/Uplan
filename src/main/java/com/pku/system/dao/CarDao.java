package com.pku.system.dao;

import com.pku.system.model.Building;
import com.pku.system.model.Car;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CarDao {
    @Select("select * from car where id = #{id}")
    public Car selectById(int id);

    @Select("select * from car where license_plate_num = #{license_plate_num}")
    public Car selectByLicense_Plate_Num(String license_plate_num);

    @Select("select id,car_type_id,license_plate_num,run_time,mileage,oil_used,oil_remained,type,status,creator from car")
    public List<Car> getAllCar();

    @Insert("insert into car (id,car_type_id,license_plate_num,run_time,mileage,oil_used,oil_remained,type,status,creator,create_time,update_time) values (#{id},#{car_type_id},#{license_plate_num},#{run_time},#{mileage},#{oil_used},#{oil_remained},#{type},#{status},#{creator},#{create_time},#{update_time})")
    public void addCar(Car car);

    @Update("update car set car_type_id=#{car_type_id},license_plate_num=#{license_plate_num},run_time=#{run_time},mileage=#{mileage},oil_used=#{oil_used},oil_remained=#{oil_remained},type=#{type},status=#{status},creator=#{creator},create_time=#{create_time},update_time=#{update_time} where id=#{id}")
    public void updateCar(Car car);

    @Delete("delete from car where id=#{id}")
    public void deleteCar(int id);

    //用车审批通过，将car的状态改为不可用。
    @Update("update car set status=#{status}, update_time=#{update_time} where id=#{id}")
    public void updateCarById(Car car);
}
