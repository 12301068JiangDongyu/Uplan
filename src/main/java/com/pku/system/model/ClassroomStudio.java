package com.pku.system.model;

import java.util.List;

public class ClassroomStudio {
    private String buildingName;
    private String classroomName;
    private List<String> addressList;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "ClassroomStudio{" +
                "buildingName='" + buildingName + '\'' +
                ", classroomName='" + classroomName + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
