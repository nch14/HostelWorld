package cn.chenhaonee.hostelWorld.model.Member;

import cn.chenhaonee.hostelWorld.model.Role;
import cn.chenhaonee.hostelWorld.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by nichenhao on 2017/3/18.
 */
@Entity
public class Member extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private MemberCard memberCard;

    public Member() {
    }

    public Member(String username, String passwordHash, VisaCard visaCard, MemberCard memberCard) {
        super(username, passwordHash, visaCard);
        this.memberCard = memberCard;
        this.setRole(Role.Member);

    }

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }
}
