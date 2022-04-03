package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.builders.TransferBuilder;
import main.banksystem.entities.Id;
import main.banksystem.entities.Installment;

@JsonTypeName("RepayInstallmentCommand")
public class RepayInstallmentCommand implements ICommand {
    private ICommand.Type type;
    private Id installmentId;
    private String description = "";
    private double money;
    private TransferCommand transferCommand;

    public RepayInstallmentCommand(Id installmentId, ICommand.Type type, double money) {
        this.type = type;
        this.installmentId = installmentId;
        this.money = money;
        this.transferCommand = null;
        this.description = String.format("User repaying installment %d with sum %f", installmentId.getId(), money);
    }

    @JsonCreator
    public RepayInstallmentCommand() {
        this.type = null;
        this.installmentId = null;
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
        Installment installment = dataBase.download(installmentId, DataBase.INSTALLMENT_PART, Installment.class);
        if (installment == null) {
            description = String.format("No installment with id %d", installmentId.getId());
            return;
        }

        if (money > installment.getSumToPay()) {
            description = String.format("Too much money %f but need to pay %f", money, installment.getSumToPay());
        }

        TransferBuilder builder = new TransferBuilder();
        builder.buildBillFromId(installment.getSourceBillId());
        builder.buildBillToId(installment.getCompanyBillId());
        builder.buildValue(money);
        TransferBuilder.Result transfer = builder.getTransfer();
        if (!transfer.valid) {
            description = transfer.description;
            return;
        }
        ICommand.Type type = new Type(false, false);
        this.transferCommand = new TransferCommand(transfer.transfer, type);

        this.transferCommand.execute();
        installment.setSumToPay(installment.getSumToPay() - money);
        dataBase.save(installmentId, DataBase.INSTALLMENT_PART, installment);
    }

    @Override
    public void undo() {
        transferCommand.undo();
        DataBase dataBase = DataBase.getInstance();

        Installment installment = dataBase.download(installmentId, DataBase.INSTALLMENT_PART, Installment.class);
        installment.setSumToPay(installment.getSumToPay() + money);
        dataBase.save(installmentId, DataBase.INSTALLMENT_PART, installment);
    }

    @Override
    public ICommand.Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

    public Id getInstallmentId() {
        return installmentId;
    }

    public void setInstallmentId(Id installmentId) {
        this.installmentId = installmentId;
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
