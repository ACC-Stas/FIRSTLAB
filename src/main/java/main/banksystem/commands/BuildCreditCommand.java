package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.entities.Credit;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

import java.util.Map;
import java.util.Objects;

@JsonTypeName("BuildCreditCommand")
public class BuildCreditCommand implements ICommand {
    private ICommand.Type type;
    private Credit credit;
    private String description;
    private Id userId;
    private TransferCommand transferCommand;

    public TransferCommand getTransferCommand() {
        return transferCommand;
    }

    public void setTransferCommand(TransferCommand transferCommand) {
        this.transferCommand = transferCommand;
    }

    public Id getUserId() {
        return userId;
    }

    public void setUserId(Id userId) {
        this.userId = userId;
    }

    @Override
    public Type getType() {
        return type;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
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

    public BuildCreditCommand(Id userId, Credit credit, ICommand.Type type) {
        this.userId = userId;
        this.credit = credit;
        this.type = type;
        this.description = String.format("User %d want to create credit %d", userId.getId(), credit.getId().getId());

        TransferBuilder transferBuilder = new TransferBuilder();
        transferBuilder.buildBillFromId(credit.getBankBillId());
        transferBuilder.buildBillToId(credit.getSourceBillId());
        transferBuilder.buildValue(credit.getSumToPay());
        TransferBuilder.Result transfer = transferBuilder.getTransfer();
        if (!transfer.valid) {
            this.description = transfer.description;
            return;
        }
        this.transferCommand = new TransferCommand(transfer.transfer, new Type(false, false));
    }

    @JsonCreator
    public BuildCreditCommand() {
        this.userId = null;
        this.credit = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Credit> credits = dataBase.downloadMap(DataBase.CREDIT_PART, Credit.class);

        if (credits.containsKey(credit.getId())) {
            return;
        }

        transferCommand.execute();
        if (!Objects.equals(transferCommand.getDescription(), "Everything is good")) {
            description = transferCommand.getDescription();
            return;
        }

        credit.setSumToPay(credit.getSumToPay() + credit.getSumToPay() * credit.getPercent() / 100);

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getCreditIds().add(this.credit.getId());
        dataBase.save(this.userId, DataBase.USER_PART, user);
        dataBase.save(credit.getId(), DataBase.CREDIT_PART, credit);
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();

        transferCommand.undo();
        if (!Objects.equals(transferCommand.getDescription(), "Everything is good")) {
            description = transferCommand.getDescription();
            return;
        }

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getCreditIds().remove(this.credit.getId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        dataBase.remove(credit.getId(), DataBase.CREDIT_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

}
