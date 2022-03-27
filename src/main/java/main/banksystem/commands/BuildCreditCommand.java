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
    public BuildCreditCommand(Credit credit, ICommand.Type type) {
        this.credit = credit;
        this.type = type;
        this.description = String.format("User bla-bla-bla");
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}
