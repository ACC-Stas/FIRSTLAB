package main.banksystem;

public class Credit {
    public Credit(Id bankBillId, Id sourceBillId, double sumToPay, double percent){
        this.bankBillId = bankBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
    }

    Id bankBillId;
    Id sourceBillId;
    double sumToPay;
    double percent;

    void Update(){}
}
