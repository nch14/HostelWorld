package cn.chenhaonee.hostelWorld.model.Inn;

import cn.chenhaonee.hostelWorld.model.User;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Entity
public class InnOwner extends User {

    @OneToOne()
    @JoinTable(name = "inn")
    private Inn inn;

    public Inn getInn() {
        return inn;
    }

    public void setInn(Inn inn) {
        this.inn = inn;
    }
}
