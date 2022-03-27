package main.banksystem.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import main.banksystem.DataBase;
import main.banksystem.StringConverter;
import main.banksystem.containers.Bill;
import main.banksystem.containers.Id;
import main.banksystem.containers.Role;

import java.util.Map;

@JsonTypeName("BuildBillCommand")
public class BuildBillCommand implements ICommand {
    private ICommand.Type type;
    private Bill bill;
    private String description;
    private Role role;
    private static final StringConverter<Bill> converter = new StringConverter<>();

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
    public BuildBillCommand(@JsonProperty("bill") Bill bill, @JsonProperty("type") ICommand.Type type) {
        this.bill = bill;
        this.type = type;
        this.description = String.format("User want's to create new bill.");
    }

    @Override
    public void execute() {
        DataBase dataBase = DataBase.getInstance();

        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
        dataBase.save(bill.getId(), DataBase.BILLS_PART, converter.serialize(this.bill));
    }

    @Override
    public void undo() {
        DataBase dataBase = DataBase.getInstance();
        dataBase.remove(bill.getId(), DataBase.BILLS_PART);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(ICommand.Type type) {
        this.type = type;
    }
}
