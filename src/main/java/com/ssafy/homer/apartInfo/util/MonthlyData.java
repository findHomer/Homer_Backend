package com.ssafy.homer.apartInfo.util;

public class MonthlyData {
    // 월별 데이터 집계 클래스
    private int totalAmount;
    private int count;

    public MonthlyData(int totalAmount, int count) {
        this.totalAmount = totalAmount;
        this.count = count;
    }

    public void addDeal(int amount) {
        totalAmount += amount;
        count++;
    }

    public float getAverage() {
        return count == 0 ? 0 : (float) totalAmount / count;
    }
}
