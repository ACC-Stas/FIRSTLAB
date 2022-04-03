package main.banksystem.entities;

public class Installment {
    public Installment(Id companyBillId, Id sourceBillId, double sumToPay, double percent, Id id, Period period){
        this.companyBillId = companyBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
        this.id = id;
        this.period = period;
    }

    public Installment(){
        this.companyBillId = null;
        this.sourceBillId = null;
        this.sumToPay = -1;
        this.percent = -1;
        this.id = null;
        this.period = null;
    }

    Id companyBillId;
    Id sourceBillId;
    double sumToPay;
    Id id;
    double percent;
    Period period;

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Id getCompanyBillId() {
        return companyBillId;
    }

    public void setCompanyBillId(Id companyBillId) {
        this.companyBillId = companyBillId;
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
