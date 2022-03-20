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
    double percent;

    void Update(){}
}
