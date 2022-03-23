package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Role;
import main.banksystem.containers.User;

@JsonTypeName("RegistryCompanyCommand")
public class RegistryCompanyCommand implements ICommand {

    private ICommand.Type type;
    private final User user;
    private Role role;
    private final StringConverter<User> converter;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Role getApproveLevel() {
        return role;
    }

    @Override
    public void setApproveLevel(Role role) {
        this.role = role;
    }

    private String description;

    RegistryCompanyCommand(User user, ICommand.Type type) {
        this.user = user;
        this.type = type;
        this.converter = new StringConverter<>();
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Save(user.getIdx(), DataBase.COMPANY_PART, converter.Serialize(this.user));
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Remove(user.getIdx(), DataBase.COMPANY_PART);

    }

    @Override
    public ICommand.Type GetType() {
        return type;
    }

    @Override
    public void SetType(ICommand.Type type) {
        this.type = type;
    }
}