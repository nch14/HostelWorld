package cn.chenhaonee.hostelWorld.model.Inn;

import cn.chenhaonee.hostelWorld.model.Member.VisaCard;
import cn.chenhaonee.hostelWorld.model.Role;
import cn.chenhaonee.hostelWorld.model.User;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Entity
public class InnOwner extends User {

    @OneToOne
    private Inn inn;

    public InnOwner() {
    }

    public InnOwner(String username, String passwordHash, VisaCard visaCard, Inn inn) {
        super(username, passwordHash, visaCard);
        setRole(Role.Inn);
        this.inn = inn;
    }

    public Inn getInn() {
        return inn;
    }

    public void setInn(Inn inn) {
        this.inn = inn;
    }
}
