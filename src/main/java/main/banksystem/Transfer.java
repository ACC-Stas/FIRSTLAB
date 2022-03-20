package main.banksystem;

public class Transfer {
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
