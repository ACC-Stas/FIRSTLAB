package main.banksystem.containers;

public class Bill {
    public Bill(Id id, double money, BillConditions billConditions){
        this.id = id;
        this.money = money;
        this.billConditions = billConditions;
    }
    public Bill() {
        this.id = null;
        this.money = 0;
        this.billConditions = null;
    }

    private Id id;
    double money;
    BillConditions billConditions;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public BillConditions getBillConditions() {
        return billConditions;
    }

    public void setBillConditions(BillConditions billConditions) {
        this.billConditions = billConditions;
    }
}
