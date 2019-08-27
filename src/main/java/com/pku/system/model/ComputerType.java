package com.pku.system.model;

public class ComputerType {
    private int computerTypeId;
    private String computerTypeName;
    private String memorySize;
    private String diskSize;
    private String operatingSystem;

    public int getComputerTypeId() {
        return computerTypeId;
    }

    public void setComputerTypeId(int computerTypeId) {
        this.computerTypeId = computerTypeId;
    }

    public String getComputerTypeName() {
        return computerTypeName;
    }

    public void setComputerTypeName(String computerTypeName) {
        this.computerTypeName = computerTypeName;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(String diskSize) {
        this.diskSize = diskSize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
