package main.banksystem.entities;

public class Transfer implements java.io.Serializable {
    public Transfer(BIC bankFrom, Id billFromId, BIC bankTo, Id billToId, double value){
        this.bankFrom = bankFrom;
        this.billFromId = billFromId;
        this.bankTo = bankTo;
        this.billToId = billToId;
        this.value = value;
    }

    public Transfer() {
        this.bankFrom = null;
        this.billFromId = null;
        this.bankTo = null;
        this.billToId = null;
        this.value = -10;
    }

    BIC bankFrom;
    Id billFromId;
    BIC bankTo;
    Id billToId;
    double value;


    public BIC getBankFrom() {
        return bankFrom;
    }

    public void setBankFrom(BIC bankFrom) {
        this.bankFrom = bankFrom;
    }

    public Id getBillFromId() {
        return billFromId;
    }

    public void setBillFromId(Id billFromId) {
        this.billFromId = billFromId;
    }

    public BIC getBankTo() {
        return bankTo;
    }

    public void setBankTo(BIC bankTo) {
        this.bankTo = bankTo;
    }

    public Id getBillToId() {
        return billToId;
    }

    public void setBillToId(Id billToId) {
        this.billToId = billToId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
