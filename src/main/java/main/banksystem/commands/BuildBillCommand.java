package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.IndexGenerator;
import main.banksystem.containers.*;

import java.util.Map;

@JsonTypeName("BuildBillCommand")
public class BuildBillCommand implements ICommand {
    private ICommand.Type type;
    private Bill bill;
    private String description;
    private Id userId;
    private Id bankId;

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

    public BuildBillCommand(Bill bill, ICommand.Type type, Id userId, Id bankId) {
        this.bill = bill;
        this.type = type;
        this.bankId = bankId;
        this.userId = userId;
        this.description = String.format("User want's to create new bill.");
    }

    public BuildBillCommand() {
        this.bill = null;
        this.type = null;
        this.bankId = null;
        this.userId = null;
        this.description = String.format("User want's to create new bill.");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);

        if (!bills.containsKey(bill.getId())){
            dataBase.save(bill.getId(), DataBase.BILLS_PART, this.bill);
        }
        else {
            IndexGenerator generator = IndexGenerator.getInstance();
            bill.setId(new Id(generator.generateIdx(IndexGenerator.BILLS_IDX)));
            dataBase.save(bill.getId(), DataBase.BILLS_PART, this.bill);
        }

        Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
        User user = users.get(userId);
        user.getBillIds().add(bill.getId());
        dataBase.save(user.getIdx(), DataBase.USER_PART, user);

        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        Company bank = companies.get(bankId);
        bank.getSpecialistIds().add(bill.getId());
        dataBase.save(bank.getPAN(), DataBase.COMPANY_PART, bank);
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(bill.getId(), DataBase.BILLS_PART);

        Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
        User user = users.get(userId);
        user.getBillIds().remove(bill.getId());
        dataBase.save(user.getIdx(), DataBase.USER_PART, user);

        Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
        Company bank = companies.get(bankId);
        bank.getSpecialistIds().remove(bill.getId());
        dataBase.save(bank.getPAN(), DataBase.COMPANY_PART, bank);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Id getUserId() {
        return userId;
    }

    public void setUserId(Id userId) {
        this.userId = userId;
    }

    public Id getBankId() {
        return bankId;
    }

    public void setBankId(Id bankId) {
        this.bankId = bankId;
    }

}
