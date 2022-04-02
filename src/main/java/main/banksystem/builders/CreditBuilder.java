package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.entities.*;

public class CreditBuilder {
    private Credit credit;

    public CreditBuilder() {
        this.credit = new Credit();
    }

    public void reset() {
        this.credit = new Credit();
    }

    public void buildBankBillId(Id bankBillId) {
        credit.setBankBillId(bankBillId);
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

    public void buildSourceBillId(Id SourceId) {
        credit.setSourceBillId(SourceId);
    }

    public void buildSourceBillId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildSourceBillId(new Id(number));
    }

    public void buildSumToPay(double sum) {
        credit.setSumToPay(sum);
    }

    public void buildSumToPay(String sum) {
        double val = -1;
        try {
            val = Double.parseDouble(sum);
        } catch (Exception e) {
            return;
        }

        this.buildSumToPay(val);
    }

    public void buildPercent(double sum) {
        credit.setPercent(sum);
    }

    public void buildPercent(String sum) {
        double val = -1;
        try {
            val = Double.parseDouble(sum);
        } catch (Exception e) {
            return;
        }

        this.buildPercent(val);
    }

    public void buildId(Id id) {
        credit.setId(id);
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

    public static class Result {
        public Credit credit;
        public boolean valid;
        public String description = "";
    }

    public Result getCredit() {
        Result result = new Result();
        result.valid = true;
        result.credit = this.credit;

        if (credit.getId() == null || credit.getId().getId() < 0) {
            result.valid = false;
            result.description = "Invalid credit id";
        }

        if (credit.getSumToPay() < 0) {
            result.valid = false;
            result.description = "Invalid credit sum";
        }

        if (credit.getPercent() < 0) {
            result.valid = false;
            result.description = "Invalid percent";
        }

        if (credit.getBankBillId() == null) {
            result.valid = false;
            result.description = "No bunk";
        } else {
            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(credit.getBankBillId(), DataBase.BILLS_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such bill in system";
            }
        }

        if (credit.getSourceBillId() == null) {
            result.valid = false;
            result.description = "No source bill id";
        } else {
            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(credit.getSourceBillId(), DataBase.BILLS_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such source bill id in system";
            }
        }

        return result;
    }
}
