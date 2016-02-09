package com.reit.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "REIT_USER")
public class User implements Serializable {

    @Id
    @Column(name = "N_USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "C_USER_NAME")
    private String userName;

    @Column(name = "C_USER_DETAILS")
    private String userDetails;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long projectId) {
        this.userId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }


}
