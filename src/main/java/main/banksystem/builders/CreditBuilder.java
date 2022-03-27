package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.containers.*;

import javax.xml.transform.Source;

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
        credit.setBankBillId(SourceId);
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
        credit.setSumToPay(sum);
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
        credit.setBankBillId(id);
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
            Company company = dataBase.download(credit.getBankBillId(), DataBase.COMPANY_PART, Company.class);
            if (company == null) {
                result.valid = false;
                result.description = "No such bunk in system";
            } else if (!company.getIsBank()) {
                result.valid = false;
                result.description = "It is company id, not bunk";
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
