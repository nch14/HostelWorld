package cn.chenhaonee.hostelWorld.domain;

/**
 * Created by nichenhao on 2017/3/20.
 */
public class PersonalInfo {
    private String cardNum;
    private String level;
    private String discount;
    private int marks;
    private double costTotal;
    private double balance;
    private String visaNum;

    public PersonalInfo(String cardNum, String level, String discount, int marks, double costTotal, double balance, String visaNum) {
        this.cardNum = cardNum;
        this.level = level;
        this.discount = discount;
        this.marks = marks;
        this.costTotal = costTotal;
        this.balance = balance;
        this.visaNum = visaNum;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public PersonalInfo() {

    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getVisaNum() {
        return visaNum;
    }

    public void setVisaNum(String visaNum) {
        this.visaNum = visaNum;
    }
}
