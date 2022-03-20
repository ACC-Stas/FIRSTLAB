package main.banksystem.containers;

public class Transfer implements java.io.Serializable {
    public Transfer(BIC bankFrom, Id billFromId, BIC bankTo, Id billToId, double value){
        this.bankFrom = bankFrom;
        this.billFromId = billFromId;
        this.bankTo = bankTo;
        this.billToId = billToId;
        this.value = value;
    }

    BIC bankFrom;
    Id billFromId;
    BIC bankTo;
    Id billToId;
    double value;

    void startTransfer(){}
}
