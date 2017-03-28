package cn.chenhaonee.hostelWorld.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by chenhaonee on 2017/3/27.
 */
@Entity
public class MemberShipValid {

    public static final int IN_USE=1;
    public static final int STOP=2;
    public static final int DESTROY=3;

    @Id
    @GeneratedValue
    private Long id;
    private String memberCardNum;
    private Date nextCheckTime;
    private int state;
    private String username;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public MemberShipValid() {
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberCardNum() {
        return memberCardNum;
    }

    public void setMemberCardNum(String memberCardNum) {
        this.memberCardNum = memberCardNum;
    }

    public Date getNextCheckTime() {
        return nextCheckTime;
    }

    public void setNextCheckTime(Date nextCheckTime) {
        this.nextCheckTime = nextCheckTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MemberShipValid(String memberCardNum, Date nextCheckTime, String username) {
        this.memberCardNum = memberCardNum;
        this.nextCheckTime = nextCheckTime;
        this.username = username;

    }
}
