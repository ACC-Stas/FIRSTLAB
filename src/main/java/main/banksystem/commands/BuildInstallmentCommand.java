package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.entities.Id;
import main.banksystem.entities.Installment;
import main.banksystem.entities.User;

import java.util.Map;

@JsonTypeName("BuildInstallmentCommand")
public class BuildInstallmentCommand implements ICommand {
    private Id userId;
    private ICommand.Type type;
    private Installment installment;
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
    public ICommand.Type getType() {
        return type;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
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

    public BuildInstallmentCommand(Id userId, Installment installment, ICommand.Type type) {
        this.userId = userId;
        this.installment = installment;
        this.type = type;
        this.description = String.format("User %d want to create installment %s", userId.getId(), installment.getId().toString());

        TransferBuilder transferBuilder = new TransferBuilder();
        transferBuilder.buildBillFromId(installment.getCompanyBillId());
        transferBuilder.buildBillToId(installment.getSourceBillId());
        transferBuilder.buildValue(installment.getSumToPay());
        TransferBuilder.Result transfer = transferBuilder.getTransfer();
        if (!transfer.valid) {
            this.description = transfer.description;
            return;
        }
        this.transferCommand = new TransferCommand(transfer.transfer, new Type(false, false));
    }

    @JsonCreator
    public BuildInstallmentCommand() {
        this.userId = null;
        this.installment = null;
        this.type = null;
        this.description = String.format("No description");

    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Installment> installments = dataBase.downloadMap(DataBase.INSTALLMENT_PART, Installment.class);

        if (installments.containsKey(installment.getId())) {
            return;
        }

        installment.setSumToPay(installment.getSumToPay() + installment.getSumToPay() * installment.getPercent() / 100);

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getInstallmentIds().add(this.installment.getId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        dataBase.save(installment.getId(), DataBase.INSTALLMENT_PART, installment);

        transferCommand.execute();

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();

        User user = dataBase.download(this.userId, DataBase.USER_PART, User.class);
        user.getInstallmentIds().remove(this.installment.getId());
        dataBase.save(this.userId, DataBase.USER_PART, user);

        dataBase.remove(installment.getId(), DataBase.INSTALLMENT_PART);

        transferCommand.undo();

    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}
