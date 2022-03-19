package main.banksystem;

public class Bill {
    public Bill(long id, double money, BillConditions billConditions){
        this.id = id;
        this.money = money;
        this.billConditions = billConditions;
    }

    long id;
    double money;
    BillConditions billConditions;
}
