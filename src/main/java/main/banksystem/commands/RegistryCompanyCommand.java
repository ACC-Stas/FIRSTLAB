package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Company;
import main.banksystem.containers.Role;

@JsonTypeName("RegistryCompanyCommand")
public class RegistryCompanyCommand implements ICommand {

    private ICommand.Type type;
    private final Company company;
    private Role role;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public RegistryCompanyCommand(Company company, ICommand.Type type) {
        this.company = company;
        this.type = type;
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.save(company.getPAN(), DataBase.COMPANY_PART, this.company);
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(company.getPAN(), DataBase.COMPANY_PART);

    }

    @Override
    public ICommand.Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}