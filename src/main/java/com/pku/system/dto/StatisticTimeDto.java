package com.pku.system.dto;

/**
 * @ClassName StatisticTimeDto
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/5
 * @Version V1.0
 **/
public class StatisticTimeDto {
    private String year;
    private String month;
    private int count;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
