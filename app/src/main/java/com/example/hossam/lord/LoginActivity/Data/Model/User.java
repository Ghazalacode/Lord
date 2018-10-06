package com.example.hossam.lord.LoginActivity.Data.Model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
        private String username;
    @SerializedName("created")
        private String created;
    @SerializedName("modified")
        private String modified;
    @SerializedName("id")
        private float id;


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

        public void setId(float id) {
            this.id = id;
        }
}
