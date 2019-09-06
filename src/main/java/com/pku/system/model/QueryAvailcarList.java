package com.pku.system.model;

public class QueryAvailcarList {
    private int id;
    private String brand;
    private String license_plate_num;
    private String status;

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

    public String getLicense_plate_num() {
        return license_plate_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = "可用";
    }

    public void setLicense_plate_num(String license_plate_num) {
        this.license_plate_num = license_plate_num;
    }
}
