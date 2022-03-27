package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Credit;
import main.banksystem.containers.Id;
import main.banksystem.containers.Role;
import main.banksystem.containers.User;

import java.util.Map;
import java.util.Objects;

@JsonTypeName("BuildCreditCommand")
public class BuildCreditCommand implements ICommand {
    private ICommand.Type type;
    private Credit credit;
    private String description;
    private Role role;

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

    @JsonCreator
    public BuildCreditCommand(Id userId, Credit credit, ICommand.Type type) {
        this.credit = credit;
        this.type = type;
        this.description = String.format("User %d want to create credit %s", userId.getId(), credit.toString());
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
