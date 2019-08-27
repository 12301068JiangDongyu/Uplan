package com.pku.system.model;

import java.util.List;

public class DeviceInfo {
    private int id;
    private String buildingNum;
    private String classroomNum;
    private int singlechipTypeId;
    private int singlechipStatus;
    private int raspberryTypeId;
    private int raspberryStatus;
    private int raspberryStreamStatus;
    private int cameraStatus;
    private int computerTypeId;
    private int computerStatus;
    private int projectorTypeId;
    private int projectorStatus;

    private ComputerType computerType;
    private SinglechipType singlechipType;
    private ProjectorType projectorType;
    private RaspberryType raspberryType;
    private Building building;

    public List<Camera> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<Camera> cameraList) {
        this.cameraList = cameraList;
    }

    private List<Camera> cameraList;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getRaspberryStreamStatus() {
        return raspberryStreamStatus;
    }

    public void setRaspberryStreamStatus(int raspberryStreamStatus) {
        this.raspberryStreamStatus = raspberryStreamStatus;
    }

    public int getSinglechipStatus() {
        return singlechipStatus;
    }

    public void setSinglechipStatus(int singlechipStatus) {
        this.singlechipStatus = singlechipStatus;
    }

    public int getRaspberryStatus() {
        return raspberryStatus;
    }

    public void setRaspberryStatus(int raspberryStatus) {
        this.raspberryStatus = raspberryStatus;
    }

    public int getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(int cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public int getComputerStatus() {
        return computerStatus;
    }

    public void setComputerStatus(int computerStatus) {
        this.computerStatus = computerStatus;
    }

    public int getProjectorStatus() {
        return projectorStatus;
    }

    public void setProjectorStatus(int projectorStatus) {
        this.projectorStatus = projectorStatus;
    }

    public ComputerType getComputerType() {
        return computerType;
    }

    public ProjectorType getProjectorType() {
        return projectorType;
    }
    public void setComputerType(ComputerType computerType) {
        this.computerType = computerType;
    }

    public SinglechipType getSinglechipType() {
        return singlechipType;
    }

    public void setSinglechipType(SinglechipType singlechipType) {
        this.singlechipType = singlechipType;
    }

    public void setProjectorType(ProjectorType projectorType) {
        this.projectorType = projectorType;
    }

    public RaspberryType getRaspberryType() {
        return raspberryType;
    }

    public void setRaspberryType(RaspberryType raspberryType) {
        this.raspberryType = raspberryType;
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

    public int getSinglechipTypeId() {
        return singlechipTypeId;
    }

    public void setSinglechipTypeId(int singlechipTypeId) {
        this.singlechipTypeId = singlechipTypeId;
    }

    public int getRaspberryTypeId() {
        return raspberryTypeId;
    }

    public void setRaspberryTypeId(int raspberryTypeId) {
        this.raspberryTypeId = raspberryTypeId;
    }

    public int getComputerTypeId() {
        return computerTypeId;
    }

    public void setComputerTypeId(int computerTypeId) {
        this.computerTypeId = computerTypeId;
    }

    public int getProjectorTypeId() {
        return projectorTypeId;
    }

    public void setProjectorTypeId(int projectorTypeId) {
        this.projectorTypeId = projectorTypeId;
    }
}
