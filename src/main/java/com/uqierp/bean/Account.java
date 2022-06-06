package com.uqierp.bean;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

	private String username;

    private String password;

    private String salt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
