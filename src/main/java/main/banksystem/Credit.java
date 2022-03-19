package main.banksystem;

public class Credit {
    public Credit(long bankBillId, long sourceBillId, double sumToPay, double percent){
        this.bankBillId = bankBillId;
        this.sourceBillId = sourceBillId;
        this.sumToPay = sumToPay;
        this.percent = percent;
    }

    long bankBillId;
    long sourceBillId;
    double sumToPay;
    double percent;

    void Update(){}
}
