package com.example.hossam.lord.LoginActivity.Data.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    @SerializedName("user")
    User UserObject;
    @SerializedName("token")
    private String token;

    public LoginResponse(User userObject, String token) {
        UserObject = userObject;
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "UserObject=" + UserObject +
                ", token='" + token + '\'' +
                '}';
    }

// Getter Methods

    public User getUser() {
        return UserObject;
    }

    public String getToken() {
        return token;
    }

    // Setter Methods

    public void setUser(User userObject) {
        this.UserObject = userObject;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public class User implements Serializable{

        @SerializedName("username")
        private String username;
        @SerializedName("created")
        private String created;
        @SerializedName("modified")
        private String modified;
        @SerializedName("id")
        private Long id;

        public User(String username, String created, String modified, Long id) {
            this.username = username;
            this.created = created;
            this.modified = modified;
            this.id = id;
        }

        // Getter Methods

        public String getUsername() {
            return username;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public float getId() {
            return id;
        }

        // Setter Methods

        public void setUsername(String username) {
            this.username = username;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

}




