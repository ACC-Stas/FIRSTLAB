package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

import java.util.Map;
import java.util.Objects;

@JsonTypeName("RegistryCommand")
public class RegistryCommand implements ICommand {

    private ICommand.Type type;
    private User user;
    private String description;

    public RegistryCommand(User user, ICommand.Type type) {
        this.user = user;
        this.type = type;
        this.description = String.format("User %s wants to register in system. With passport id %d.",
                user.getPassport().getFullName().getFirstName(), user.getPassport().getIdx().getId());
    }

    @JsonCreator
    public RegistryCommand() {
        this.user = null;
        this.type = null;
        this.description = "No description";
    }

    @Override
    public Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
        for (User user : users.values()) {
            if (Objects.equals(this.user.getLogin(), user.getLogin())) {
                break;
            }
        }

        dataBase.save(user.getIdx(), DataBase.USER_PART, this.user);
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(user.getIdx(), DataBase.USER_PART);

    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

}
