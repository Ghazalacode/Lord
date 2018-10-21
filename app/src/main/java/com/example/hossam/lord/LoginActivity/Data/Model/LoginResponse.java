package com.example.hossam.lord.LoginActivity.Data.Model;




public class LoginResponse {



        private int id;
        private int company_id;
        private String username;
        private String pwd;

    public LoginResponse(int id, int company_id, String username, String pwd) {
        this.id = id;
        this.company_id = company_id;
        this.username = username;
        this.pwd = pwd;
    }

// Getter Methods

        public float getId() {
        return id;
    }

        public float getCompany_id() {
        return company_id;
    }

        public String getUsername() {
        return username;
    }

        public String getPwd() {
        return pwd;
    }

        // Setter Methods

        public void setId(int id) {
        this.id = id;
    }

        public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

        public void setUsername(String username) {
        this.username = username;
    }

        public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    }





