package main.banksystem;

public class Deposit implements java.io.Serializable {
    public Deposit(long bankBillId, double percent, Bill bill){
        this.bankBillId = bankBillId;
        this.percent = percent;
        this.bill = bill;
    }

    long bankBillId;
    double percent;
    Bill bill;

    void Update(){}
}
