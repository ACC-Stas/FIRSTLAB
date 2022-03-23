package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Bill;
import main.banksystem.containers.Id;

import java.util.Map;
import java.util.Objects;

@JsonTypeName("RegistryCommand")
public class BuildBillCommand implements ICommand {
    private ICommand.Type type;
    private Bill bill;
    private String description;
    private static final StringConverter<Bill> converter = new StringConverter<>();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Bill getUser() {
        return bill;
    }

    public void setUser(Bill user) {
        this.bill = user;
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
    public BuildBillCommand(@JsonProperty("user") Bill bill, @JsonProperty("type") ICommand.Type type) {
        this.bill = user;
        this.type = type;
        this.description = String.format("User %s want's to register in system. His passport id is %d",
                user.getLogin(), user.getPassport().getIdx().getId());
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.GetInstance();

        Map<Id, User> users = dataBase.DownloadMap(DataBase.USER_PART, User.class);
        for (User user : users.values()) {
            if (Objects.equals(this.user.getLogin(), user.getLogin())) {
                break;
            }
        }


        dataBase.Save(user.getIdx(), DataBase.USER_PART, converter.Serialize(this.user));
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Remove(user.getIdx(), DataBase.USER_PART);

    }

    @Override
    public Type GetType() {
        return type;
    }

    @Override
    public void SetType(ICommand.Type type) {
        this.type = type;
    }
}
