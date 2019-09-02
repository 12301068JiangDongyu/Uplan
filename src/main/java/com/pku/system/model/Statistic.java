package com.pku.system.model;

/**
 * @ClassName Statistic
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/2
 * @Version V1.0
 **/
public class Statistic {
    private int id;

    private int useNum;

    private int carId;

    private int oilCost;

    private int repairsNum;

    private double maintenanceCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUseNum() {
        return useNum;
    }

    public void setUseNum(int useNum) {
        this.useNum = useNum;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getOilCost() {
        return oilCost;
    }

    public void setOilCost(int oilCost) {
        this.oilCost = oilCost;
    }

    public int getRepairsNum() {
        return repairsNum;
    }

    public void setRepairsNum(int repairsNum) {
        this.repairsNum = repairsNum;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }
}
