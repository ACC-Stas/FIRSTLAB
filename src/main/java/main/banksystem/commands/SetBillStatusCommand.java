package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.entities.Bill;
import main.banksystem.entities.BillConditions;
import main.banksystem.entities.Id;

@JsonTypeName("SetBillStatusCommand")
public class SetBillStatusCommand implements ICommand {
    private Id billId;
    private BillConditions previousCondition;
    private BillConditions newCondition;
    private ICommand.Type type;
    private String description;

    public SetBillStatusCommand(Id billId, BillConditions previousCondition, BillConditions newCondition, ICommand.Type type) {
        this.billId = billId;
        this.previousCondition = previousCondition;
        this.newCondition = newCondition;
        this.type = type;
        this.description = String.format("Change condition of %d bill from %s to %s",
                billId.getId(), previousCondition.toString(), newCondition.toString());
    }

    @JsonCreator
    public SetBillStatusCommand() {
        this.billId = null;
        this.previousCondition = null;
        this.newCondition = null;
        this.type = null;
        this.description = "No description";
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Bill bill = dataBase.download(this.billId, DataBase.BILLS_PART, Bill.class);
        if (bill == null) {
            this.description = "No bill set status on";
            return;
        }

        bill.setBillConditions(this.newCondition);
        dataBase.save(bill.getId(), DataBase.BILLS_PART, bill);
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        Bill bill = dataBase.download(this.billId, DataBase.BILLS_PART, Bill.class);
        if (bill == null) {
            this.description = "No bill set status on";
            return;
        }

        bill.setBillConditions(this.previousCondition);
        dataBase.save(bill.getId(), DataBase.BILLS_PART, bill);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public Id getBillId() {
        return billId;
    }

    public void setBillId(Id billId) {
        this.billId = billId;
    }

    public BillConditions getPreviousCondition() {
        return previousCondition;
    }

    public void setPreviousCondition(BillConditions previousCondition) {
        this.previousCondition = previousCondition;
    }

    public BillConditions getNewCondition() {
        return newCondition;
    }

    public void setNewCondition(BillConditions newCondition) {
        this.newCondition = newCondition;
    }

}
