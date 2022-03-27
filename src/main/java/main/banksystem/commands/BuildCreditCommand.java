package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.containers.Credit;
import main.banksystem.containers.Id;

import java.util.Map;

@JsonTypeName("BuildCreditCommand")
public class BuildCreditCommand implements ICommand {
    private ICommand.Type type;
    private Credit credit;
    private String description;

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

    public BuildCreditCommand(Id userId, Credit credit, ICommand.Type type) {
        this.credit = credit;
        this.type = type;
        this.description = String.format("User %d want to create credit %s", userId.getId(), credit.toString());
    }

    @JsonCreator
    public BuildCreditCommand() {
        this.credit = null;
        this.type = null;
        this.description = String.format("No description");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Credit> credits = dataBase.downloadMap(DataBase.CREDITS_PART, Credit.class);

        if (credits.containsKey(credit.getId())) {
            return;
        }

        dataBase.save(credit.getId(), DataBase.CREDITS_PART, credit);

    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(credit.getId(), DataBase.CREDITS_PART);
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

}
