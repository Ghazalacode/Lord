package com.example.hossam.lord.StatsActivity.Data.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivitiesResponse  {


    /**
     * companyno : 102
     * companyname : dell
     * activity : الاكترونيات
     * city : خميس مشيط
     * branch : 2
     * day : Monday
     * date : /Date(1537740000000)/
     * saleno : 0
     * saleval : 0
     * purchno : 0
     * purchval : 0
     * profit : 0
     * expenses : 0
     * saletax : 0
     * item_most :
     * item_worst :
     * emp_most :
     * emp_worst :
     * quan1 : 0
     * quan2 : 0
     * quantot : 0
     */

    private int companyno;
    private String companyname;
    private String activity;
    private String city;
    private int branch;
    private String day;
    private String date;
    private int saleno;
    private float saleval;
    private int purchno;
    private float purchval;
    private float profit;
    private float expenses;
    private int saletax;
    private String item_most;
    private String item_worst;
    private String emp_most;
    private String emp_worst;
    private float quan1;
    private float quan2;
    private float quantot;

    public ActivitiesResponse(int companyno, String companyname, String activity, String city,
                              int branch, String day, String date, int saleno, float saleval, int purchno, float purchval, float profit, float expenses, int saletax, String item_most, String item_worst, String emp_most, String emp_worst,
                              float quan1, float quan2, float quantot) {
        this.companyno = companyno;
        this.companyname = companyname;
        this.activity = activity;
        this.city = city;
        this.branch = branch;
        this.day = day;
        this.date = date;
        this.saleno = saleno;
        this.saleval = saleval;
        this.purchno = purchno;
        this.purchval = purchval;
        this.profit = profit;
        this.expenses = expenses;
        this.saletax = saletax;
        this.item_most = item_most;
        this.item_worst = item_worst;
        this.emp_most = emp_most;
        this.emp_worst = emp_worst;
        this.quan1 = quan1;
        this.quan2 = quan2;
        this.quantot = quantot;
    }

    public int getCompanyno() {
        return companyno;
    }

    public void setCompanyno(int companyno) {
        this.companyno = companyno;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSaleno() {
        return saleno;
    }

    public void setSaleno(int saleno) {
        this.saleno = saleno;
    }

    public float getSaleval() {
        return saleval;
    }

    public void setSaleval(float saleval) {
        this.saleval = saleval;
    }

    public int getPurchno() {
        return purchno;
    }

    public void setPurchno(int purchno) {
        this.purchno = purchno;
    }

    public float getPurchval() {
        return purchval;
    }

    public void setPurchval(float purchval) {
        this.purchval = purchval;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public float getExpenses() {
        return expenses;
    }

    public void setExpenses(float expenses) {
        this.expenses = expenses;
    }

    public int getSaletax() {
        return saletax;
    }

    public void setSaletax(int saletax) {
        this.saletax = saletax;
    }

    public String getItem_most() {
        return item_most;
    }

    public void setItem_most(String item_most) {
        this.item_most = item_most;
    }

    public String getItem_worst() {
        return item_worst;
    }

    public void setItem_worst(String item_worst) {
        this.item_worst = item_worst;
    }

    public String getEmp_most() {
        return emp_most;
    }

    public void setEmp_most(String emp_most) {
        this.emp_most = emp_most;
    }

    public String getEmp_worst() {
        return emp_worst;
    }

    public void setEmp_worst(String emp_worst) {
        this.emp_worst = emp_worst;
    }

    public float getQuan1() {
        return quan1;
    }

    public void setQuan1(float quan1) {
        this.quan1 = quan1;
    }

    public float getQuan2() {
        return quan2;
    }

    public void setQuan2(float quan2) {
        this.quan2 = quan2;
    }

    public float getQuantot() {
        return quantot;
    }

    public void setQuantot(float quantot) {
        this.quantot = quantot;
    }
}
