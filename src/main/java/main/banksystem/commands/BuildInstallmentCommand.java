package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.containers.Credit;
import main.banksystem.containers.Id;
import main.banksystem.containers.Installment;

import java.util.Map;

@JsonTypeName("BuildInstallmentCommand")
public class BuildInstallmentCommand implements ICommand {
    private ICommand.Type type;
    private Installment installment;
    private String description;

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

    public BuildInstallmentCommand(Id userId, Installment installment, ICommand.Type type) {
        this.installment = installment;
        this.type = type;
        this.description = String.format("User %d want to create installment %s", userId.getId(), installment.toString());
    }

    @JsonCreator
    public BuildInstallmentCommand() {
        this.installment = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Credit> credits = dataBase.downloadMap(DataBase.CREDITS_PART, Credit.class);

        if (credits.containsKey(installment.getId())) {
            return;
        }

        dataBase.save(installment.getId(), DataBase.CREDITS_PART, installment);

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(installment.getId(), DataBase.CREDITS_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}
