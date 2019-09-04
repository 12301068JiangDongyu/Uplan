package com.pku.system.model;

import java.util.Date;
import java.io.Serializable;

/**
 * (OfficialCarApply)实体类
 *
 * @author makejava
 * @since 2019-09-02 20:55:33
 */
public class OfficialCarApply implements Serializable {
    private static final long serialVersionUID = -94447932803456646L;
    //id
    private Integer id;
    //公车id
    private Integer carId;
    //公车品牌类型
    private String brand;
    //预约用户id,关联tb_user中id
    private Integer userId;
    //目的地
    private String destination;
    //计划开始用车时间
    private Date startTime;
    //实际归还公车时间
    private Date endTime;
    //申请原因
    private String reason;
    //实际行驶里程数
    private Integer travelDistance;
    //实际使用油耗
    private Double oilUsed;
    //状态  1:通过, 2:不通过
    private Boolean status;
    //备注
    private String remark;
    //数据创建时间
    private Date createTime;
    //数据更新时间
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(Integer travelDistance) {
        this.travelDistance = travelDistance;
    }

    public Double getOilUsed() {
        return oilUsed;
    }

    public void setOilUsed(Double oilUsed) {
        this.oilUsed = oilUsed;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setBrand(String brand) { this.brand = brand; }

    public String getBrand() { return brand; }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}