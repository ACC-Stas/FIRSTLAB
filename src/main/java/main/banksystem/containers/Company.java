package main.banksystem.containers;

import java.util.ArrayList;
import java.util.List;

public class Company implements java.io.Serializable {
    private String jName;
    private Type type;
    private Id PAN;
    private BIC bankID;
    private Address jAddress;
    private Id billCompanyId;
    private boolean isBank;
    private List<Id> specialistIds;
    private List<Id> salaryProjectIds;

    public Company(String jName, Type type, Id PAN, BIC bankID, Address jAddress, Id billCompanyId) {
        this.jName = jName;
        this.type = type;
        this.PAN = PAN;
        this.bankID = bankID;
        this.jAddress = jAddress;
        this.billCompanyId = billCompanyId;
        specialistIds = new ArrayList<>();
        salaryProjectIds = new ArrayList<>();
    }

    public Company() {
        this.jName = null;
        this.type = null;
        this.PAN = null;
        this.bankID = null;
        this.jAddress = null;
        this.billCompanyId = null;
        this.isBank = false;
        specialistIds = new ArrayList<>();
        salaryProjectIds = new ArrayList<>();
    }

    public enum Type {
        IE,
        LLC,
        CJSC,
        ABOBA
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Id getPAN() {
        return PAN;
    }

    public void setPAN(Id PAN) {
        this.PAN = PAN;
    }

    public BIC getBankID() {
        return bankID;
    }

    public void setBankID(BIC bankID) {
        this.bankID = bankID;
    }

    public Address getjAddress() {
        return jAddress;
    }

    public void setjAddress(Address jAddress) {
        this.jAddress = jAddress;
    }

    public Id getBillCompanyId() {
        return billCompanyId;
    }

    public void setBillCompanyId(Id billCompanyId) {
        this.billCompanyId = billCompanyId;
    }

    public boolean getIsBank() {
        return isBank;
    }

    public void setIsBank(boolean bank) {
        isBank = bank;
    }

    public List<Id> getSpecialistIds() {
        return specialistIds;
    }

    public void setSpecialistIds(List<Id> specialistIds) {
        this.specialistIds = specialistIds;
    }

    public List<Id> getSalaryProjectIds() {
        return salaryProjectIds;
    }

    public void setSalaryProjectIds(List<Id> salaryProjectIds) {
        this.salaryProjectIds = salaryProjectIds;
    }
}
