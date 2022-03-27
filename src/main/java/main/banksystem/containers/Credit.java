package main.banksystem.containers;

public class Credit implements java.io.Serializable {
    public Credit(Id bankBillId, Id sourceBillId, double sumToPay, double percent){
        this.bankBillId = bankBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
    }

    Id bankBillId;
    Id sourceBillId;
    double sumToPay;

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

    double percent;
}
