package com.pku.system.dto;

/**
 * @ClassName StatisticDto
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/4
 * @Version V1.0
 **/
public class StatisticDto {
    private String keyName;
    private int keyValue;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(int keyValue) {
        this.keyValue = keyValue;
    }
}
