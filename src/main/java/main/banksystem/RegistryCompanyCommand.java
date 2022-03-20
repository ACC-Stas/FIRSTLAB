package main.banksystem;

public class RegistryCompanyCommand implements ICommand {

    private ICommand.Type type;
    private final User user;
    private final StringConverter<User> converter;

    RegistryCompanyCommand(User user, ICommand.Type type) {
        this.user = user;
        this.type = type;
        this.converter = new StringConverter<>();
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Save(user.getIdx(), "companies.csv", converter.Serialize(this.user));
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.GetInstance();
        dataBase.Remove(user.getIdx(), "companies.csv");

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