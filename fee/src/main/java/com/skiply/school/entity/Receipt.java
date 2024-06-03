package com.skiply.school.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Receipt {

    @Id
    @NotNull
    private Long studentId;

    @Schema(hidden = true)
    private String paymentDate;

    @Schema(description = "Card Number", minimum = "13", maximum = "16")
    private String cardNumber;

    @Schema(description = "Card issuer type", defaultValue = "Mastercard")
    private String cardType = "Mastercard";

    @Schema(description = "Currency", defaultValue = "0.0")
    private Double customAmount = 0.0;

    @Schema(hidden = true)
    private Double totalAmount;

    @Schema(hidden = true)
    private String refNumber;

    @Schema(description = "Currency", defaultValue = "AED")
    private String currency = "AED";

    @Schema(hidden = true)
    private Double fees;

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Double getCustomAmount() {
        return customAmount;
    }

    public void setCustomAmount(Double customAmount) {
        this.customAmount = customAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
