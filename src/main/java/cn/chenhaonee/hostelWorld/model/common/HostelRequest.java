package cn.chenhaonee.hostelWorld.model.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nichenhao on 2017/3/21.
 */
@Entity
public class HostelRequest {
    @Id
    @GeneratedValue
    private Long id;

    private String innId;

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

    private String type;

    public HostelRequest(String innId, String nameForInn, String nameForInnOwner, String telNumber, String address,
                         String emailAddress, String hostelDesc, String wideBed,
                         String doubleBed, String seaBed, String type) {
        this.innId = innId;
        this.nameForInn = nameForInn;
        this.nameForInnOwner = nameForInnOwner;
        this.telNumber = telNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.applyDate = Calendar.getInstance().getTime();
        this.hostelDesc = hostelDesc;
        this.wideBed = wideBed;
        this.doubleBed = doubleBed;
        this.seaBed = seaBed;
        this.type = type;
    }

    public HostelRequest() {

    }

    public String getInnId() {
        return innId;
    }

    public void setInnId(String innId) {
        this.innId = innId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
