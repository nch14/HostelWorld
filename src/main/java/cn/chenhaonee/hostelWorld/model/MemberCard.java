package cn.chenhaonee.hostelWorld.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Entity
public class MemberCard {
    @Id
    private Long id;
    private Double sumCost;
    private int currentMarks;
    //private List<CardEventLog> cardEventLogList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
