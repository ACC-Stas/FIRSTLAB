package main.banksystem.entities;

import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;

public class Deposit implements java.io.Serializable {
    public Deposit(Id bankBillId, double percent, double value, Id billId, Id id) {
        this.bankBillId = bankBillId;
        this.percent = percent;
        this.value = value;
        this.billId = billId;
        this.id = id;
    }

    public Deposit() {
        this.bankBillId = null;
        this.percent = -1;
        this.value = -1;
        this.billId = null;
        this.id = null;
    }

    Id bankBillId;
    double percent;
    double value;
    Id billId;
    Id id;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

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

    public Id getBillId() {
        return billId;
    }

    public void setBillId(Id billId) {
        this.billId = billId;
    }
}
