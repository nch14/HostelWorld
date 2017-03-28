package cn.chenhaonee.hostelWorld.model.Member;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Entity
public class MemberCard {
    @Id
    private String id;
    private double balance;
    private Double sumCost;
    private int currentMarks;

    public MemberCard() {
    }

    public MemberCard(String id,double balance) {
        this.id = id;
        sumCost = 0.0;
        this.balance=balance;
        currentMarks =0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Double getSumCost() {
        return sumCost;
    }

    public void setSumCost(Double sumCost) {
        this.sumCost = sumCost;
    }

    public int getCurrentMarks() {
        return currentMarks;
    }

    public void setCurrentMarks(int currentMarks) {
        this.currentMarks = currentMarks;
    }
}
