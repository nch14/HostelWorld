package cn.chenhaonee.hostelWorld.model.Inn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    private String roomName;
    private String roomType;

    public Room(String roomName, String roomType) {
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public Room() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
