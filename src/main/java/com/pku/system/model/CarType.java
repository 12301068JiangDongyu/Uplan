package com.pku.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Timestamp;
import java.util.Date;

public class CarType {
    private int id;
    private String brand;//品牌型号
    private String capacity;//汽车排量
    private double price;//购入价格（万）

    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp buy_time;//购入日期

    private int seat_num;//车座数
    private int oil_type;//0 柴油，1 汽油
    private int creator;//user_id

    private Date create_time;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(Timestamp buy_time) {
        this.buy_time = buy_time;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public int getOil_type() {
        return oil_type;
    }

    public void setOil_type(int oil_type) {
        this.oil_type = oil_type;
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
