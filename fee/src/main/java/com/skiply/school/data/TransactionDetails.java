package com.skiply.school.data;

public class TransactionDetails {
    private String dateOfTxn;
    private String studentName;
    private Long studentId;
    private String refNumber;
    private String cardNumber;
    private String cardType;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getDateOfTxn() {
        return dateOfTxn;
    }

    public void setDateOfTxn(String dateOfTxn) {
        this.dateOfTxn = dateOfTxn;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
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



}
