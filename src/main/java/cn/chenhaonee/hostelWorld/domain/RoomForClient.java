package cn.chenhaonee.hostelWorld.domain;

import cn.chenhaonee.hostelWorld.model.Inn.Room;

/**
 * Created by nichenhao on 2017/3/20.
 */
public class RoomForClient extends Room {

    private String picUrl;
    private double price;

    public RoomForClient(Long id, String roomName, String roomType, double price) {
        super(roomName, roomType);
        setId(id);
        switch (roomType) {
            case "浴缸大床房":
                picUrl = "/imgs/wideBed.jpg";
                break;
            case "海景休闲房":
                picUrl = "/imgs/seaBed.jpg";
                break;
            case "标准双床房":
                picUrl = "/imgs/doubleBed.jpg";
                break;
        }
        this.price = price;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
