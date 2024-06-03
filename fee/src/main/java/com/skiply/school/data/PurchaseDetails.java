package com.skiply.school.data;

public class PurchaseDetails {
    private Double tuitionFees;
    private Double customAmount;
    private Double totalAmount;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getCustomAmount() {
        return customAmount;
    }

    public void setCustomAmount(Double customAmount) {
        this.customAmount = customAmount;
    }

    public Double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(Double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


}
