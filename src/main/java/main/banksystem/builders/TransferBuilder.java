package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.entities.*;

import java.util.Map;

public class TransferBuilder {
    private Transfer transfer;

    public TransferBuilder() {
        transfer = new Transfer();
    }

    public void reset() {
        transfer = new Transfer();
    }

    public void buildBillToId(Id id) {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
        if (!bills.containsKey(id)) {
            return;
        }
        transfer.setBillToId(id);
    }

    public void buildBillToId(String string) {
        long id = -1;
        try {
            id = Long.parseLong(string);
        }
        catch (Exception e) {
            return;
        }
        Id idx = new Id(id);
        buildBillToId(idx);
    }

    public void buildBillFromId(Id id) {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
        if (!bills.containsKey(id)) {
            return;
        }
        transfer.setBillFromId(id);
    }

    public void buildBillFromId(String string) {
        long id = -1;
        try {
            id = Long.parseLong(string);
        }
        catch (Exception e) {
            return;
        }
        Id idx = new Id(id);
        buildBillFromId(idx);
    }

    public void buildValue(double value) {
        transfer.setValue(value);
    }

    public void buildValue(String string) {
        double value = -1;
        try {
            value = Double.parseDouble(string);
        }
        catch (Exception e) {
            return;
        }
        buildValue(value);
    }

    public static class Result {
        public boolean valid = true;
        public Transfer transfer;
        public String description = "";
    }

    public Result getTransfer() {
        Result result = new Result();
        result.transfer = this.transfer;

        if (transfer.getBillFromId() == null) {
            result.description = "Invalid bill from id";
            result.valid = false;
        }
        if (transfer.getBillToId() == null) {
            result.description = "Invalid bill to id";
            result.valid = false;
        }
        if (transfer.getValue() < 0) {
            result.description = "Invalid transfer value";
            result.valid = false;
        }

        return result;
    }
}
