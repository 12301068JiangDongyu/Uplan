package com.pku.system.dto;

/**
 * @ClassName StatisticTimeDto
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/5
 * @Version V1.0
 **/
public class StatisticTimeDto {
    //车牌号
    private String card;
    //记录条数
    private int count;
    //总花费
    private double cost;
    //时间，yyyy-mm格式
    private String time;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
