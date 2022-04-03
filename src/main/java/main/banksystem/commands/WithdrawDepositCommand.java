package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.entities.*;

import java.util.Objects;

@JsonTypeName("WithdrawDepositCommand")
public class WithdrawDepositCommand implements ICommand {
    private ICommand.Type type;
    private Id depositId;
    private double value;
    private String description = "";
    private TransferCommand transferCommand = null;

    public WithdrawDepositCommand(Id depositId, Type type, double value) {
        this.type = type;
        this.depositId = depositId;
        this.value = value;
    }

    @JsonCreator
    public WithdrawDepositCommand() {
        this.value = -1;
        this.type = null;
        this.depositId = null;
    }

    public Id getDepositId() {
        return depositId;
    }

    public void setDepositId(Id depositId) {
        this.depositId = depositId;
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
        if (this.value < 0) {
            description = "value less than zero";
            return;
        }

        DataBase dataBase = DataBase.getInstance();
        Deposit deposit = dataBase.download(depositId, DataBase.DEPOSIT_PART, Deposit.class);
        if (deposit == null) {
            description = "no deposit found";
            return;
        }

        if (value > deposit.getValue()) {
            description = "more money than in deposit";
            return;
        }

        TransferBuilder transferBuilder = new TransferBuilder();
        transferBuilder.buildValue(value);
        transferBuilder.buildBillFromId(deposit.getBankBillId());
        transferBuilder.buildBillToId(deposit.getBillId());

        TransferBuilder.Result result = transferBuilder.getTransfer();
        if (!result.valid) {
            description = result.description;
            return;
        }

        this.transferCommand = new TransferCommand(result.transfer, new Type(false, false));
        transferCommand.execute();
        description = transferCommand.getDescription();
        if (transferCommand.isValid()) {
            deposit.setValue(deposit.getValue() - value);
        }

        dataBase.save(depositId, DataBase.DEPOSIT_PART, deposit);
    }

    @Override
    public void undo() {
        transferCommand.undo();

        DataBase dataBase = DataBase.getInstance();
        Deposit deposit = dataBase.download(depositId, DataBase.DEPOSIT_PART, Deposit.class);
        if (deposit == null) {
            description = "no deposit found";
            return;
        }

        description = transferCommand.getDescription();
        if (transferCommand.isValid()) {
            deposit.setValue(deposit.getValue() + value);
        }

        dataBase.save(depositId, DataBase.DEPOSIT_PART, deposit);

    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

    public TransferCommand getTransferCommand() {
        return transferCommand;
    }

    public void setTransferCommand(TransferCommand transferCommand) {
        this.transferCommand = transferCommand;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
