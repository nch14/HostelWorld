package cn.chenhaonee.hostelWorld.model.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by nichenhao on 2017/3/21.
 */
@Entity
public class RoomArrangement {
    @Id
    @GeneratedValue
    private Long id;

    private Long roomId;

    private String username;

    private String innName;

    private Date date;

    public RoomArrangement() {
    }

    public RoomArrangement(Long roomId, String username, String innName, Date date) {
        this.roomId = roomId;
        this.username = username;
        this.innName = innName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
