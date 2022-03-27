package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Company;
import main.banksystem.containers.Role;

@JsonTypeName("RegistryCompanyCommand")
public class RegistryCompanyCommand implements ICommand {

    private ICommand.Type type;
    private Company company;
    private String description;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
    public RegistryCompanyCommand(Company company, ICommand.Type type) {
        this.company = company;
        this.type = type;
        this.description = String.format("Company %s want's to register in system. Its PAN id is %d",
                company.getjName(), company.getPAN().getId());

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