package com.pku.system.model;

public class PullInfo {
    private int id;
    private String buildingNum;
    private String classroomNum;
    private String[] pullList;

    public String[] getPullList() {
        return pullList;
    }

    public void setPullList(String[] pullList) {
        this.pullList = pullList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getClassroomNum() {
        return classroomNum;
    }

    public void setClassroomNum(String classroomNum) {
        this.classroomNum = classroomNum;
    }
}
