package main.banksystem.containers;

import java.util.ArrayList;

public class User implements java.io.Serializable {
    public User(Passport passport, Id idx, String login, String number, String email, String password, Role role) {
        this.passport = passport;
        this.idx = idx;
        this.number = number;
        this.email = email;
        this.password = password;
        this.role = role;
        this.login = login;
        this.billIds = new ArrayList<Id>();
        this.salaryIds = new ArrayList<Id>();
    }

    public User() {
        this.billIds = new ArrayList<Id>();
        this.salaryIds = new ArrayList<Id>();
    }

    private Passport passport;
    private Id idx;
    private String number;
    private String email;
    private String password;
    private String login;
    private Role role;
    private ArrayList<Id> billIds;
    private ArrayList<Id> salaryIds;

    public ArrayList<Id> getSalaryIds() {
        return salaryIds;
    }

    public void setSalaryIds(ArrayList<Id> salaryIds) {
        this.salaryIds = salaryIds;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Id getIdx() {
        return idx;
    }

    public void setIdx(Id idx) {
        this.idx = idx;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Id> getBillIds() {
        return billIds;
    }

    public void setBillIds(ArrayList<Id> billIds) {
        this.billIds = billIds;
    }
}
