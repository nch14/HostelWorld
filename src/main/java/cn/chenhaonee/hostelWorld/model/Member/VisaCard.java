package cn.chenhaonee.hostelWorld.model.Member;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by carlos on 2017/3/14.
 */
@Entity
public class VisaCard {
    @Id
    private String cardNum;
    private String cvv;
    private String validDate;
    private double balance;

    public VisaCard(String cardNum, double balance) {
        this.cardNum = cardNum;
        this.balance = balance;
    }

    public VisaCard(String cardNum, String cvv, String validDate, double balance) {
        this.cardNum = cardNum;
        this.cvv = cvv;
        this.validDate = validDate;
        this.balance = balance;
    }

    public VisaCard() {

    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
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
