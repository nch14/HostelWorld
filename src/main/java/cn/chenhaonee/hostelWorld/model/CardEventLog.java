package cn.chenhaonee.hostelWorld.model;

import java.util.Date;

/**
 * Created by nichenhao on 2017/3/13.
 */
public class CardEventLog {
    private CardEvent cardEvent;
    private Date date;

    public CardEvent getCardEvent() {
        return cardEvent;
    }

    public void setCardEvent(CardEvent cardEvent) {
        this.cardEvent = cardEvent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
