package cn.chenhaonee.hostelWorld.model.Member;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Entity
public class CardEventLog {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private CardEvent cardEvent;
    private Date date;

    public CardEventLog(CardEvent cardEvent) {
        this.cardEvent = cardEvent;
        date=new Date();
    }

    public CardEventLog() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
