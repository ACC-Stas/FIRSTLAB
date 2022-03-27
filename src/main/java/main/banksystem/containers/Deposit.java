package main.banksystem.containers;

import main.banksystem.containers.Bill;

public class Deposit implements java.io.Serializable {
    public Deposit(long bankBillId, double percent, Bill bill){
        this.bankBillId = bankBillId;
        this.percent = percent;
        this.bill = bill;
    }

    public long getBankBillId() {
        return bankBillId;
    }

    public void setBankBillId(long bankBillId) {
        this.bankBillId = bankBillId;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    long bankBillId;
    double percent;
    Bill bill;
}
