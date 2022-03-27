package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.containers.Credit;
import main.banksystem.containers.Deposit;
import main.banksystem.containers.Id;

import java.util.Map;

@JsonTypeName("BuildDepositCommand")
public class BuildDepositCommand implements ICommand {
    private ICommand.Type type;
    private Deposit deposit;
    private String description;

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
        this.deposit = deposit;
        this.type = type;
        this.description = String.format("User %d want to create deposit %s", userId.getId(), deposit.toString());
    }

    @JsonCreator
    public BuildDepositCommand() {
        this.deposit = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Credit> credits = dataBase.downloadMap(DataBase.CREDITS_PART, Credit.class);

        if (credits.containsKey(deposit.getId())) {
            return;
        }

        dataBase.save(deposit.getId(), DataBase.CREDITS_PART, deposit);

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(deposit.getId(), DataBase.CREDITS_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}
