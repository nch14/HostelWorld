package cn.chenhaonee.hostelWorld.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by carlos on 2017/3/14.
 */
@Entity
public class VisaCard {
    @Id
    private String cardNum;
    private double balance;

    public VisaCard(String cardNum, double balance) {
        this.cardNum = cardNum;
        this.balance = balance;
    }

    public VisaCard() {

    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
