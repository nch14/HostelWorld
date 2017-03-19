package cn.chenhaonee.hostelWorld.model.Inn;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Entity
public class Inn {

    @Id
    private String id;

    @OneToOne(mappedBy = "inn")
    private InnOwner innOwner;

    private String nameForInn;

    private String nameForInnOwner;

    private String telNumber;

    private String address;

    private String emailAddress;

    private Date applyDate;

    private Date avalibleDate;

    @OneToMany
    private List<Room> rooms;

    public Inn() {
    }

    public Inn(String id, InnOwner innOwner, String nameForInn, String nameForInnOwner, String telNumber, String address, String emailAddress, Date applyDate, Date avalibleDate, List<Room> rooms) {
        this.id = id;
        this.innOwner = innOwner;
        this.nameForInn = nameForInn;
        this.nameForInnOwner = nameForInnOwner;
        this.telNumber = telNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.applyDate = applyDate;
        this.avalibleDate = avalibleDate;
        this.rooms = rooms;
    }

    public InnOwner getInnOwner() {
        return innOwner;
    }

    public void setInnOwner(InnOwner innOwner) {
        this.innOwner = innOwner;
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

    public Date getAvalibleDate() {
        return avalibleDate;
    }

    public void setAvalibleDate(Date avalibleDate) {
        this.avalibleDate = avalibleDate;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
