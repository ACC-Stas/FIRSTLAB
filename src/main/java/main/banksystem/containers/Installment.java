package main.banksystem.containers;

public class Installment {
    public Installment(Id companyBillId, Id sourceBillId, double sumToPay, double percent){
        this.companyBillId = companyBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
    }

    Id companyBillId;
    Id sourceBillId;
    double sumToPay;
    double percent;

    void Update(){}
}
