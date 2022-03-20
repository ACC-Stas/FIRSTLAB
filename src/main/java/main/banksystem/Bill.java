package main.banksystem;

public class Bill implements java.io.Serializable {
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
