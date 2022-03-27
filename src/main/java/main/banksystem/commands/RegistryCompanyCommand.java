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
    private final StringConverter<Company> converter;

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

    public RegistryCompanyCommand(Company company, ICommand.Type type) {
        this.company = company;
        this.type = type;
        this.converter = new StringConverter<>();
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.save(company.getPAN(), DataBase.COMPANY_PART, converter.serialize(this.company));
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