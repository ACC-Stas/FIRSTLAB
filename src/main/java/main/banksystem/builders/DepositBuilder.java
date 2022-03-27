package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.containers.*;

public class DepositBuilder {
    private Deposit deposit;

    public DepositBuilder() {
        this.deposit = new Deposit();
    }

    public void reset() {
        this.deposit = new Deposit();
    }

    public void buildBankBillId(Id bankBillId) {
        deposit.setBankBillId(bankBillId);
    }

    public void buildBankBillId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildBankBillId(new Id(number));
    }

    public void buildBillId(Id id) {
        deposit.setBill(id);
    }

    public void buildBillId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildBillId(new Id(number));
    }

    public void buildPercent(double percent) {
        deposit.setPercent(percent);
    }

    public void buildPercent(String percent) {
        double val = -1;
        try {
            val = Double.parseDouble(percent);
        } catch (Exception e) {
            return;
        }

        this.buildPercent(val);
    }

    public void buildId(Id id) {
        deposit.setId(id);
    }

    public void buildId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildId(new Id(number));
    }

    static class Result {
        public Deposit deposit;
        public boolean valid;
        public String description = "";
    }

    public Result getDeposit() {
        Result result = new Result();
        result.valid = true;
        result.deposit = this.deposit;

        if (deposit.getId() == null || deposit.getId().getId() < 0) {
            result.valid = false;
            result.description = "Invalid credit id";
        }

        if (deposit.getPercent() < 0) {
            result.valid = false;
            result.description = "Invalid percent";
        }

        if (deposit.getBankBillId() == null) {
            result.valid = false;
            result.description = "No bunk";
        } else {

            DataBase dataBase = DataBase.getInstance();
            Company company = dataBase.download(deposit.getBankBillId(), DataBase.COMPANY_PART, Company.class);
            if (company == null) {
                result.valid = false;
                result.description = "No such bunk in system";
            } else if (!company.getIsBank()) {
                result.valid = false;
                result.description = "It is company id, not bunk";
            }
        }

        if (deposit.getBill() == null) {
            result.valid = false;
            result.description = "No bill id";
        } else {
            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(deposit.getBill(), DataBase.BILLS_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such bill id in system";
            }
        }

        return result;
    }
}
