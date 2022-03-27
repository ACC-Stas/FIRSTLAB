package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.containers.*;

public class InstallmentBuilder {
    private Installment installment;

    public InstallmentBuilder() {
        this.installment = new Installment();
    }

    public void reset() {
        this.installment = new Installment();
    }

    public void buildCompanyBillId(Id id) {
        installment.setCompanyBillId(id);
    }

    public void buildCompanyBillId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildCompanyBillId(new Id(number));
    }

    public void buildSourceBillId(Id SourceId) {
        installment.setSourceBillId(SourceId);
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
        installment.setSumToPay(sum);
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
        installment.setPercent(sum);
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
        installment.setId(id);
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
        public Installment installment;
        public boolean valid;
        public String description = "";
    }

    public Result getInstallment() {
        Result result = new Result();
        result.valid = true;
        result.installment = this.installment;

        if (installment.getId() == null || installment.getId().getId() < 0) {
            result.valid = false;
            result.description = "Invalid credit id";
        }

        if (installment.getSumToPay() < 0) {
            result.valid = false;
            result.description = "Invalid credit sum";
        }

        if (installment.getPercent() < 0) {
            result.valid = false;
            result.description = "Invalid percent";
        }

        if (installment.getCompanyBillId() == null) {
            result.valid = false;
            result.description = "No bunk";
        } else {

            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(installment.getCompanyBillId(), DataBase.COMPANY_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such company bill in system";
            }
        }

        if (installment.getSourceBillId() == null) {
            result.valid = false;
            result.description = "No source bill id";
        } else {
            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(installment.getSourceBillId(), DataBase.BILLS_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such source bill id in system";
            }
        }

        return result;
    }
}
