package cn.chenhaonee.hostelWorld.model;

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
    private Double sumCost;
    private int currentMarks;
    @OneToMany
    private List<CardEventLog> cardEventLogList;

    public MemberCard() {
    }

    public MemberCard(String id) {
        this.id = id;
        sumCost = 0.0;
        currentMarks =0;
        cardEventLogList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

   /* public List<CardEventLog> getCardEventLogList() {
        return cardEventLogList;
    }

    public void setCardEventLogList(List<CardEventLog> cardEventLogList) {
        this.cardEventLogList = cardEventLogList;
    }*/
}
