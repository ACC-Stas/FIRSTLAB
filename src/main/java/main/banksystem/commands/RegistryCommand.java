package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Id;
import main.banksystem.containers.Role;
import main.banksystem.containers.User;

import java.util.Map;
import java.util.Objects;

@JsonTypeName("RegistryCommand")
public class RegistryCommand implements ICommand {
    private ICommand.Type type;
    private User user;
    private String description;

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

    @JsonCreator
    public RegistryCommand(@JsonProperty("user") User user, @JsonProperty("type") ICommand.Type type) {
        this.user = user;
        this.type = type;
        this.description = String.format("User %s want's to register in system. His passport id is %d",
                user.getLogin(), user.getPassport().getIdx().getId());
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
