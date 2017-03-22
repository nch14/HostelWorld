package cn.chenhaonee.hostelWorld.domain;

import sun.dc.pr.PRError;

/**
 * Created by nichenhao on 2017/3/21.
 */
public class RoomDetailClient extends RoomForClient {
    private String state;

    public RoomDetailClient(Long id, String roomName, String roomType, double price, String state) {
        super(id, roomName, roomType, price);
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
