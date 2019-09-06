package com.pku.system.model;

import java.sql.Timestamp;
import java.util.Date;

public class Car {
    private int id;
    private int car_type_id;
    private String license_plate_num;//车牌号
    private Timestamp run_time;//汽车投入使用时间
    private int mileage;//里程数
    private double oil_used;//使用油量
    private double oil_remained;//目前剩余油量
    private int type;//汽车类型 1:班车，2:公车
    private int status;//车辆状态-1:空闲，2:使用中，3：维修中
    private int creator;//创建人id

    private Date create_time;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarTypeId() {
        return car_type_id;
    }

    public void setCar_type_id(int car_type_id) {
        this.car_type_id = car_type_id;
    }

    public String getLicense_plate_num() {
        return license_plate_num;
    }

    public void setLicense_plate_num(String license_plate_num) {
        this.license_plate_num = license_plate_num;
    }

    public Timestamp getRun_time() {
        return run_time;
    }

    public void setRun_time(Timestamp run_time) {
        this.run_time = run_time;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getOil_used() {
        return oil_used;
    }

    public void setOil_used(double oil_used) {
        this.oil_used = oil_used;
    }

    public double getOil_remained() {
        return oil_remained;
    }

    public void setOil_remained(double oil_remained) {
        this.oil_remained = oil_remained;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
