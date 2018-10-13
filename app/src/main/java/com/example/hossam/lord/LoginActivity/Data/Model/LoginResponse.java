package com.example.hossam.lord.LoginActivity.Data.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("company_id")
    private Integer company_id;
    @SerializedName("username")
    private String username;
    @SerializedName("pwd")
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;

    }

    public Integer getCompany_id() {
        return company_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginResponse(Integer id, Integer company_id, String username, String password) {
        this.id = id;
        this.company_id = company_id;
        this.username = username;
        this.password = password;
    }
}




