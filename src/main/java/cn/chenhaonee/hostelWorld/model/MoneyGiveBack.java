package cn.chenhaonee.hostelWorld.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by nichenhao on 2017/3/22.
 */
@Entity
public class MoneyGiveBack {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String innName;

    private double alreadyPaid;

    public MoneyGiveBack(String innName, double alreadyPaid) {
        this.innName = innName;
        this.alreadyPaid = alreadyPaid;
    }

    public MoneyGiveBack() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public double getAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(double alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
    }

    public void addAlreadyPaid(double paid) {
        this.alreadyPaid += paid;
    }
}
