package com.example.hossam.lord.StatsActivity.Data.Model;

import android.widget.TextView;

public class Employee {

    String employeeName;
    String date;

    public Employee(String employeeName, String date) {
        this.employeeName = employeeName;
        this.date = date;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }
}
