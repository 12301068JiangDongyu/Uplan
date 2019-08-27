package com.pku.system.model;

public class WSocketMessage {
    //success、fail、timeout
    private String judge;
    //记录当前时间
    private String nowTime;
    //记录消息内容
    private String message;
    //设备ownId
    private String ownId;
    //教学楼教室号
    private String buildClass;
    //device、video 区分设备管理和视频管理
    private String tab;
    //回传deviceInfo中所有status 状态码
    private DeviceInfo deviceInfo;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getBuildClass() {
        return buildClass;
    }

    public void setBuildClass(String buildClass) {
        this.buildClass = buildClass;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
