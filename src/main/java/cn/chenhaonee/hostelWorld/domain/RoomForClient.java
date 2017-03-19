package cn.chenhaonee.hostelWorld.domain;

/**
 * Created by nichenhao on 2017/3/20.
 */
public class RoomForClient {
    private long id;
    private String roomName;
    private String roomType;
    private double price;

    public RoomForClient(long id, String roomName, String roomType, double price) {
        this.id = id;
        this.roomName = roomName;
        this.roomType = roomType;
        this.price = price;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
