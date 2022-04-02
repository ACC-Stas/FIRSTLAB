package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.IndexGenerator;
import main.banksystem.entities.*;

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

    @Override
    public String toString() {
        return description;
    }

    public RegistryCompanyCommand(Company company, ICommand.Type type) {
        this.company = company;
        this.type = type;
        this.description = String.format("Company %s wants to register in system. Its PAN %d.",
                company.getjName(), company.getPAN().getId());
    }

    @JsonCreator
    public RegistryCompanyCommand() {
        this.company = null;
        this.type = null;
        this.description = "No description";

    }

    @Override
    public void execute() {

        IndexGenerator indexGenerator = IndexGenerator.getInstance();
        Bill bill = new Bill(new Id(indexGenerator.generateIdx(IndexGenerator.BILLS_IDX)), 10000, BillConditions.ACTIVE);
        company.setBillCompanyId(bill.getId());

        DataBase dataBase = DataBase.getInstance();
        dataBase.save(bill.getId(), DataBase.BILLS_PART, bill);
        dataBase.save(company.getPAN(), DataBase.COMPANY_PART, this.company);
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(company.getPAN(), DataBase.COMPANY_PART);
        dataBase.remove(company.getBillCompanyId(), DataBase.BILLS_PART);
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