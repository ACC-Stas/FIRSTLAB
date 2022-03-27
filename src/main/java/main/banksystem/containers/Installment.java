package main.banksystem.containers;

public class Installment {
    public Installment(Id companyBillId, Id sourceBillId, double sumToPay, double percent, Id id){
        this.companyBillId = companyBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
        this.id = id;
    }

    Id companyBillId;
    Id sourceBillId;
    double sumToPay;
    Id id;

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

    double percent;
}
