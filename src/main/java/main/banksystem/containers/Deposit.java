package main.banksystem.containers;

import main.banksystem.containers.Bill;

public class Deposit implements java.io.Serializable {
    public Deposit(Id bankBillId, double percent, Id bill, Id id){
        this.bankBillId = bankBillId;
        this.percent = percent;
        this.bill = bill;
        this.id = id;
    }

    public Deposit() {
        this.bankBillId = null;
        this.percent = -1;
        this.bill = null;
        this.id = null;
    }

    Id bankBillId;
    double percent;
    Id bill;
    Id id;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Id getBankBillId() {
        return bankBillId;
    }

    public void setBankBillId(Id bankBillId) {
        this.bankBillId = bankBillId;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Id getBill() {
        return bill;
    }

    public void setBill(Id bill) {
        this.bill = bill;
    }
}
