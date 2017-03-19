package cn.chenhaonee.hostelWorld.model.common;

import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/18.
 */
@Entity
public class Order {
   @Id
   private long id;
   private String peopleWhoReplyThisRoom;
   private String telNumber;
   @OneToOne
   private User user;
   @OneToOne
   private Inn inn;
   private List<String> guests;
   @Temporal(TemporalType.DATE)
   private Date checkIn;
   @Temporal(TemporalType.DATE)
   private Date checkOut;
   @Temporal(TemporalType.DATE)
   private Date arrivalDate;
   @Temporal(TemporalType.DATE)
   private Date leaveDate;
   private double cost;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getPeopleWhoReplyThisRoom() {
      return peopleWhoReplyThisRoom;
   }

   public void setPeopleWhoReplyThisRoom(String peopleWhoReplyThisRoom) {
      this.peopleWhoReplyThisRoom = peopleWhoReplyThisRoom;
   }

   public String getTelNumber() {
      return telNumber;
   }

   public void setTelNumber(String telNumber) {
      this.telNumber = telNumber;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Inn getInn() {
      return inn;
   }

   public void setInn(Inn inn) {
      this.inn = inn;
   }

   public List<String> getGuests() {
      return guests;
   }

   public void setGuests(List<String> guests) {
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

   public String getArrivalDate() {
      return arrivalDate;
   }

   public void setArrivalDate(String arrivalDate) {
      this.arrivalDate = arrivalDate;
   }

   public String getLeaveDate() {
      return leaveDate;
   }

   public void setLeaveDate(String leaveDate) {
      this.leaveDate = leaveDate;
   }

   public double getCost() {
      return cost;
   }

   public void setCost(double cost) {
      this.cost = cost;
   }
}
