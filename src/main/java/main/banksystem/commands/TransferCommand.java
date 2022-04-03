package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.entities.*;

import java.util.Map;

@JsonTypeName("TransferCommand")
public class TransferCommand implements ICommand {
    private ICommand.Type type;
    private Transfer transfer;
    private String description = "";
    private boolean valid = true;

    public TransferCommand(Transfer transfer, Type type) {
        this.type = type;
        this.transfer = transfer;
        this.description = String.format("Transfer from %d to %d sum of %f",
                transfer.getBillFromId().getId(), transfer.getBillToId().getId(), transfer.getValue());
    }

    @JsonCreator
    public TransferCommand() {
        this.type = null;
        this.transfer = null;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
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

    @Override
    public void execute() {
        valid = false;
        type.setSaveable(false);
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        if (!bills.containsKey(transfer.getBillFromId())) {
            description = "No bill to get many from";
            return;
        }
        if (!bills.containsKey(transfer.getBillToId())) {
            description = "No bill to get many to";
            return;
        }

        Bill from = bills.get(transfer.getBillFromId());
        Bill to = bills.get(transfer.getBillToId());

        if (from.getMoney() < transfer.getValue()) {
            description = "Not enough money on from bill";
            return;
        }

        if (from.getBillConditions() != BillConditions.ACTIVE) {
            description = "From bill is not active";
            return;
        }

        if (to.getBillConditions() != BillConditions.ACTIVE) {
            description = "To bill is not active";
            return;
        }

        from.setMoney(from.getMoney() - transfer.getValue());
        to.setMoney(to.getMoney() + transfer.getValue());

        dataBase.save(from.getId(), DataBase.BILLS_PART, from);
        dataBase.save(to.getId(), DataBase.BILLS_PART, to);
        valid = true;
        type.setSaveable(true);
    }

    @Override
    public void undo() {
        valid = false;
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        if (!bills.containsKey(transfer.getBillFromId())) {
            description = "No bill to get many to";
            return;
        }
        if (!bills.containsKey(transfer.getBillToId())) {
            description = "No bill to get many from";
            return;
        }

        Bill from = bills.get(transfer.getBillFromId());
        Bill to = bills.get(transfer.getBillToId());

        if (to.getMoney() < transfer.getValue()) {
            description = "Not enough money on from bill";
            return;
        }

        if (from.getBillConditions() != BillConditions.ACTIVE) {
            description = "To bill is not active";
            return;
        }

        if (to.getBillConditions() != BillConditions.ACTIVE) {
            description = "From bill is not active";
            return;
        }

        from.setMoney(from.getMoney() + transfer.getValue());
        to.setMoney(to.getMoney() - transfer.getValue());

        dataBase.save(from.getId(), DataBase.BILLS_PART, from);
        dataBase.save(to.getId(), DataBase.BILLS_PART, to);

        valid = true;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}