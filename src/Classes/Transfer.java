package Classes;

public class Transfer {
    public Transfer(BIC bankFrom, long billFromId, BIC bankTo, long billToId, double value){
        this.bankFrom = bankFrom;
        this.billFromId = billFromId;
        this.bankTo = bankTo;
        this.billToId = billToId;
        this.value = value;
    }

    BIC bankFrom;
    long billFromId;
    BIC bankTo;
    long billToId;
    double value;

    void startTransfer(){}
}
