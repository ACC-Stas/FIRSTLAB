package main.banksystem;

public class Installment {
    public Installment(long companyBillId, long sourceBillId, double sumToPay, double percent){
        this.companyBillId = companyBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
    }

    long companyBillId;
    long sourceBillId;
    double sumToPay;
    double percent;

    void Update(){}
}
