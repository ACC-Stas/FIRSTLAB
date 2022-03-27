package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.containers.*;

import java.util.Map;

public class TransferBuilder {
    private Transfer transfer;

    public TransferBuilder() {
        transfer = new Transfer();
    }

    public void Reset() {
        transfer = new Transfer();
    }

    public void BuildBillToId(Id id) {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
        if (!bills.containsKey(id)) {
            return;
        }
        transfer.setBillToId(id);
    }

    public void BuildBillFromId(Id id) {
        DataBase dataBase = DataBase.getInstance();
        Map<Id, Bill> bills = dataBase.downloadMap(DataBase.BILLS_PART, Bill.class);
        if (!bills.containsKey(id)) {
            return;
        }
        transfer.setBillToId(id);
    }

    public void BuildValue(double value) {
        transfer.setValue(value);
    }

    public static class Result {
        public boolean valid = true;
        public Transfer transfer;
        public String description = "";
    }

    public Result getUser() {
        Result result = new Result();
        result.transfer = this.transfer;

        if (transfer.getBillFromId() == null) {
            result.valid = false;
        }
        if (transfer.getBillToId() == null) {
            result.valid = false;
        }
        if (transfer.getValue() < 0) {
            result.valid = false;
        }

        return result;
    }
}
