package com.pku.system.model;

import java.sql.Timestamp;
import java.util.Date;

public class DailyOperation{

    private int id;
    private int car_id;   //汽车id
    private double cost;  //花费的钱数
    private int type;    //1、维修，2、加油，3、违章
    private Timestamp  ocurrence_time;  //事件发生时间
    private String remark;  //备注信息
    private int creator;//创建人ID
    private Timestamp  create_time;  //创建记录时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        car_id = car_id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp  getOcurrence_time() {
        return ocurrence_time;
    }

    public void setOcurrence_time(Timestamp ocurrence_time) {
        ocurrence_time = ocurrence_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        create_time = create_time;
    }
}
