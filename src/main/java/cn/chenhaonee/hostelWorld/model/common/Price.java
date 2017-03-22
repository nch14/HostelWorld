package cn.chenhaonee.hostelWorld.model.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Entity
public class Price {
    @Id
    @GeneratedValue
    private Long id;
    private String innOwnerName;
    private String roomType;
    private double price;

    public Price(String innOwnerName, String roomType, double price) {
        this.innOwnerName = innOwnerName;
        this.roomType = roomType;
        this.price = price;
    }

    public Price() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInnOwnerName() {
        return innOwnerName;
    }

    public void setInnOwnerName(String innOwnerName) {
        this.innOwnerName = innOwnerName;
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
