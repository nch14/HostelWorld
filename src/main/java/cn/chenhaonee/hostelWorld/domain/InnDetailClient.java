package cn.chenhaonee.hostelWorld.domain;

import cn.chenhaonee.hostelWorld.model.Inn.Room;

import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/21.
 */
public class InnDetailClient {
    private String id;

    private String nameForInn;

    private String nameForInnOwner;

    private String telNumber;

    private String address;

    private String emailAddress;

    private Date applyDate;

    private Date avalibleDate;

    private String hostelDesc;

    private String wideBed;

    private String doubleBed;

    private String seaBed;

    private String cardNum;

    public InnDetailClient() {
    }

    public InnDetailClient(String id, String nameForInn, String nameForInnOwner, String telNumber, String address, String emailAddress, Date applyDate, Date avalibleDate, String hostelDesc, List<Room> rooms, String cardNum) {
        this.id = id;
        this.nameForInn = nameForInn;
        this.nameForInnOwner = nameForInnOwner;
        this.telNumber = telNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.applyDate = applyDate;
        this.avalibleDate = avalibleDate;
        this.hostelDesc =hostelDesc;
        this.wideBed = "";
        this.doubleBed = "";
        this.seaBed = "";
        for (Room room:rooms){
            switch (room.getRoomType()){
                case "浴缸大床房":
                    wideBed+=room.getRoomName()+" ";
                    break;
                case "标准双床房":
                    doubleBed +=room.getRoomName()+" ";
                    break;
                case "海景休闲房":
                    seaBed +=room.getRoomName()+" ";
                    break;
            }
        }
        this.cardNum = cardNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameForInn() {
        return nameForInn;
    }

    public void setNameForInn(String nameForInn) {
        this.nameForInn = nameForInn;
    }

    public String getNameForInnOwner() {
        return nameForInnOwner;
    }

    public void setNameForInnOwner(String nameForInnOwner) {
        this.nameForInnOwner = nameForInnOwner;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getAvalibleDate() {
        return avalibleDate;
    }

    public void setAvalibleDate(Date avalibleDate) {
        this.avalibleDate = avalibleDate;
    }

    public String getHostelDesc() {
        return hostelDesc;
    }

    public void setHostelDesc(String hostelDesc) {
        this.hostelDesc = hostelDesc;
    }

    public String getWideBed() {
        return wideBed;
    }

    public void setWideBed(String wideBed) {
        this.wideBed = wideBed;
    }

    public String getDoubleBed() {
        return doubleBed;
    }

    public void setDoubleBed(String doubleBed) {
        this.doubleBed = doubleBed;
    }

    public String getSeaBed() {
        return seaBed;
    }

    public void setSeaBed(String seaBed) {
        this.seaBed = seaBed;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
