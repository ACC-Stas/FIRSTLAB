package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.entities.Deposit;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

import java.util.Map;

@JsonTypeName("BuildDepositCommand")
public class BuildDepositCommand implements ICommand {
    private Id userId;
    private ICommand.Type type;
    private Deposit deposit;
    private String description;
    private TransferCommand transferCommand;

    public Id getUserId() {
        return userId;
    }

    public void setUserId(Id userId) {
        this.userId = userId;
    }

    public TransferCommand getTransferCommand() {
        return transferCommand;
    }

    public void setTransferCommand(TransferCommand transferCommand) {
        this.transferCommand = transferCommand;
    }

    @Override
    public Type getType() {
        return type;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public BuildDepositCommand(Id userId, Deposit deposit, ICommand.Type type) {
        this.userId = userId;
        this.deposit = deposit;
        this.type = type;
        this.description = String.format("User %d want to create deposit %s", userId.getId(), deposit.toString());

        TransferBuilder transferBuilder = new TransferBuilder();
       // transferBuilder.buildBillFromId(deposit.);
        //transferBuilder.buildBillToId(installment.getSourceBillId());
       // transferBuilder.buildValue(installment.getSumToPay());
        TransferBuilder.Result transfer = transferBuilder.getTransfer();
        if (!transfer.valid) {
            this.description = transfer.description;
            return;
        }
        this.transferCommand = new TransferCommand(transfer.transfer, new Type(false, false));
    }

    @JsonCreator
    public BuildDepositCommand() {
        this.userId = null;
        this.deposit = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Deposit> deposits = dataBase.downloadMap(DataBase.DEPOSIT_PART, Deposit.class);

        if (deposits.containsKey(deposit.getId())) {
            return;
        }

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getDepositIds().add(this.deposit.getId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        dataBase.save(deposit.getId(), DataBase.DEPOSIT_PART, deposit);

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getDepositIds().remove(this.deposit.getId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        dataBase.remove(deposit.getId(), DataBase.DEPOSIT_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}
