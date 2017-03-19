package cn.chenhaonee.hostelWorld.model;

import cn.chenhaonee.hostelWorld.model.Member.VisaCard;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Entity
public abstract class User {
    @Id
    private String username;
    private String passwordHash;
    @OneToOne
    private VisaCard visaCard;
    @Enumerated(EnumType.STRING)
    private Role role;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User() {
    }

    public User(String username, String passwordHash, VisaCard visaCard) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.visaCard = visaCard;
    }

    public VisaCard getVisaCard() {
        return visaCard;
    }

    public void setVisaCard(VisaCard visaCard) {
        this.visaCard = visaCard;
    }
}
