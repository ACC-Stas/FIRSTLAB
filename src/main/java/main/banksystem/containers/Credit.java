package main.banksystem.containers;

public class Credit implements java.io.Serializable {

    Id bankBillId;
    Id sourceBillId;
    double sumToPay;
    double percent;
    Period period;

    public Credit(Id bankBillId, Id sourceBillId, double sumToPay, double percent, Period period){
        this.bankBillId = bankBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
        this.period = period;
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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
