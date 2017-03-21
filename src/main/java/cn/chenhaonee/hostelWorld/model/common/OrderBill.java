package cn.chenhaonee.hostelWorld.model.common;

import cn.chenhaonee.hostelWorld.model.Inn.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/18.
 */
@Entity
public class OrderBill {
    @Id
    private String id;
    private String telNumber;
    private String username;
    private String inn;
    @OneToOne
    private Room room;

    @ElementCollection
    @CollectionTable
    private List<String> guests;

    private Date makeTime;
    private Date checkIn;
    private Date checkOut;
    private Date arrivalDate;
    private Date leaveDate;

    private String state;

    private double cost;

    public OrderBill() {
    }

    public OrderBill(String id, String username, String inn, Room room, Date arrivalDate, Date leaveDate) {
        this.id = id;
        this.makeTime = Calendar.getInstance().getTime();
        this.username = username;
        this.inn = inn;
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.leaveDate = leaveDate;
    }

    public Date getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGuests(List<String> guests) {
        this.guests = guests;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public List<String> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<String> guests) {
        this.guests = guests;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }
}
