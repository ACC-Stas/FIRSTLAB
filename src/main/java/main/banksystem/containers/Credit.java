package main.banksystem.containers;

public class Credit implements java.io.Serializable {
    public Credit(Id bankBillId, Id sourceBillId, double sumToPay, double percent, Id id){
        this.bankBillId = bankBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
        this.id = id;
    }

    public Credit(){
        this.bankBillId = null;
        this.sourceBillId = null;
        this.sumToPay = -1;
        this.percent = -1;
        this.id = null;
    }

    Id bankBillId;
    Id sourceBillId;
    double sumToPay;
    Id id;
    double percent;

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

    public Id getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(Id sourceBillId) {
        this.sourceBillId = sourceBillId;
    }

    public double getSumToPay() {
        return sumToPay;
    }

    public void setSumToPay(double sumToPay) {
        this.sumToPay = sumToPay;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
