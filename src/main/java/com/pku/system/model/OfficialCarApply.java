package com.pku.system.model;

import java.util.Date;

/**
 * (OfficialCarApply)实体类
 */
public class OfficialCarApply{
    //id
    private Integer id;
    //公车id
    private Integer car_id;
    //公车品牌类型
    private String brand;
    //预约用户id,关联tb_user中id
    private Integer user_id;
    //目的地
    private String destination;
    //计划开始用车时间
    private Date start_time;
    //实际归还公车时间
    private Date end_time;
    //申请原因
    private String reason;
    //实际行驶里程数
    private Integer travel_distance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCar_id() {
        return car_id;
    }

    public void setCar_id(Integer car_id) {
        this.car_id = car_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTravel_distance() {
        return travel_distance;
    }

    public void setTravel_distance(Integer travel_distance) {
        this.travel_distance = travel_distance;
    }

    public Double getOil_used() {
        return oil_used;
    }

    public void setOil_used(Double oil_used) {
        this.oil_used = oil_used;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    //实际使用油耗
    private Double oil_used;
    //状态  1:通过, 2:不通过
    private Boolean status;
    //备注
    private String remark;
    //数据创建时间
    private Date create_time;
    //数据更新时间
    private Date update_time;


}