package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.entities.*;

import java.util.Map;

@JsonTypeName("RepayCreditCommand")
public class RepayCreditCommand implements ICommand {
    private ICommand.Type type;
    private Id creditId;
    private String description = "";
    private double money;
    private TransferCommand transferCommand;

    public RepayCreditCommand(Id creditId, ICommand.Type type, double money) {
        this.type = type;
        this.creditId = creditId;
        this.money = money;
        this.description = String.format("User repaying credit %d with sum %f", creditId.getId(), money);
    }

    @JsonCreator
    public RepayCreditCommand() {
        this.type = null;
        this.creditId = null;
        this.money = -1;
        this.transferCommand = null;
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
        DataBase dataBase = DataBase.getInstance();
        Credit credit = dataBase.download(creditId, DataBase.CREDIT_PART, Credit.class);
        if (credit == null) {
            description = String.format("No credit with id %d", creditId.getId());
            return;
        }

        if (money > credit.getSumToPay()) {
            description = String.format("Too much many %f but need to pay %f", money, credit.getSumToPay());
        }

        TransferBuilder builder = new TransferBuilder();
        builder.buildBillFromId(credit.getSourceBillId());
        builder.buildBillToId(credit.getBankBillId());
        builder.buildValue(money);
        TransferBuilder.Result transfer = builder.getTransfer();
        if (!transfer.valid) {
            description = transfer.description;
            return;
        }
        ICommand.Type type = new Type(false, false);
        this.transferCommand = new TransferCommand(transfer.transfer, type);

        this.transferCommand.execute();
        if (!transferCommand.isValid()) {
            description = transferCommand.getDescription();
            return;
        }

        credit.setSumToPay(credit.getSumToPay() - money);
        dataBase.save(creditId, DataBase.CREDIT_PART, credit);
    }

    @Override
    public void undo() {
        transferCommand.undo();
        if (!transferCommand.isValid()) {
            description = transferCommand.getDescription();
            return;
        }

        DataBase dataBase = DataBase.getInstance();

        Credit credit = dataBase.download(creditId, DataBase.CREDIT_PART, Credit.class);
        credit.setSumToPay(credit.getSumToPay() + money);
        dataBase.save(creditId, DataBase.CREDIT_PART, credit);
    }

    @Override
    public ICommand.Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

    public Id getCreditId() {
        return creditId;
    }

    public void setCreditId(Id creditId) {
        this.creditId = creditId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public TransferCommand getTransferCommand() {
        return transferCommand;
    }

    public void setTransferCommand(TransferCommand transferCommand) {
        this.transferCommand = transferCommand;
    }

}
