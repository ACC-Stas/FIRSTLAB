package main.banksystem.commands;

import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.User;

public class RegistryCommand implements ICommand {

    private ICommand.Type type;
    private final User user;
    private final StringConverter<User> converter;

    public RegistryCommand(User user, ICommand.Type type) {
        this.user = user;
        this.type = type;
        this.converter = new StringConverter<>();
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Save(user.getIdx(), "users.csv", converter.Serialize(this.user));
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Remove(user.getIdx(), "users.csv");

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
