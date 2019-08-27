package com.pku.system.model;

public class Camera {
    private int cameraId;
    private int cameraTypeId;
    private int cameraStatus;
    private String cameraAngle;
    private int did;
    private String cameraTypeName;

    public String getCameraTypeName() {
        return cameraTypeName;
    }

    public void setCameraTypeName(String cameraTypeName) {
        this.cameraTypeName = cameraTypeName;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getCameraTypeId() {
        return cameraTypeId;
    }

    public void setCameraTypeId(int cameraTypeId) {
        this.cameraTypeId = cameraTypeId;
    }

    public int getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(int cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public String getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(String cameraAngle) {
        this.cameraAngle = cameraAngle;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }
}
