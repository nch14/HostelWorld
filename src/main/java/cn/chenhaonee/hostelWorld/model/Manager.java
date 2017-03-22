package cn.chenhaonee.hostelWorld.model;

import cn.chenhaonee.hostelWorld.model.Member.VisaCard;

import javax.persistence.Entity;

/**
 * Created by nichenhao on 2017/3/22.
 */
@Entity
public class Manager extends User {
    public Manager(String username, String passwordHash) {
        super(username, passwordHash, null);
        setRole(Role.Manager);
    }

    public Manager() {

    }
}
