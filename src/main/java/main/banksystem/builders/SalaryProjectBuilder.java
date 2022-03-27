package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.containers.*;

public class SalaryProjectBuilder {
    private SalaryProject salaryProject;

    public SalaryProjectBuilder() {
        salaryProject = new SalaryProject();
    }

    public void reset() {
        salaryProject = new SalaryProject();
    }

    public void buildBillFromId(Id id) {
        salaryProject.setBillFromId(id);
    }

    public void buildBillFromId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildBillFromId(new Id(number));
    }

    public void buildBillToId(Id SourceId) {
        salaryProject.setBillToId(SourceId);
    }

    public void buildSourceBillId(String string) {
        long number = -1;
        try {
            number = Long.parseLong(string);
        } catch (Exception e) {
            return;
        }

        this.buildBillToId(new Id(number));
    }

    public void buildSum(double sum) {
        salaryProject.setSum(sum);
    }

    public void buildSumToPay(String sum) {
        double val = -1;
        try {
            val = Double.parseDouble(sum);
        } catch (Exception e) {
            return;
        }

        this.buildSum(val);
    }

    public void buildId(Id id) {
        salaryProject.setSalaryProjectId(id);
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
        public SalaryProject salaryProject;
        public boolean valid;
        public String description = "";
    }

    public Result getSalaryProject() {
        Result result = new Result();
        result.valid = true;
        result.salaryProject = this.salaryProject;

        if (salaryProject.getSalaryProjectId() == null || salaryProject.getSalaryProjectId().getId() < 0) {
            result.valid = false;
            result.description = "Invalid salary project id";
        }

        if (salaryProject.getSum() < 0) {
            result.valid = false;
            result.description = "Invalid salary sum";
        }

        if (salaryProject.getBillFromId() == null) {
            result.valid = false;
            result.description = "No bill from id";
        } else {
            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(salaryProject.getBillFromId(), DataBase.COMPANY_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such bill in system";
            }
        }

        if (salaryProject.getBillToId() == null) {
            result.valid = false;
            result.description = "No bill to id";
        } else {
            DataBase dataBase = DataBase.getInstance();
            Bill bill = dataBase.download(salaryProject.getBillToId(), DataBase.BILLS_PART, Bill.class);
            if (bill == null) {
                result.valid = false;
                result.description = "No such bill to id in system";
            }
        }

        return result;
    }
}
