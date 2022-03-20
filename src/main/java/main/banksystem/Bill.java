package main.banksystem;

public class Bill {
    public Bill(Id id, double money, BillConditions billConditions){
        this.id = id;
        this.money = money;
        this.billConditions = billConditions;
    }

    private final Id id;
    double money;
    BillConditions billConditions;

    public Id getId() {
        return id;
    }
}
