package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.containers.*;

import java.util.Map;
import java.util.Objects;

public class CompanyBuilder {
    private Company company;

    public CompanyBuilder() {
        company = new Company();
    }

    public void Reset() {
        company = new Company();
    }

    public static class Result {
        public boolean valid;
        public Company company;
        public String description = "";
    }

    public void BuildType(Company.Type type) {
        company.setType(type);
    }

    public void BuildType(String type) {
        try {
            company.setType(Company.Type.valueOf(type));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void BuildName(String name) {
        company.setjName(name);
    }

    public void BuildBankId(BIC bic) {
        company.setBankID(bic);
    }

    public void BuildBankId(String bic) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(bic));
        } catch (IllegalArgumentException e) {
            return;
        }

        company.setBankID(new BIC(idx));
    }

    public void BuildPAN(Id pan) {
        company.setPAN(pan);
    }

    public void BuildPAN(String pan) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(pan));
        } catch (IllegalArgumentException e) {
            return;
        }
        company.setPAN(idx);
    }

    public void BuildAddress(Address address) {
        company.setjAddress(address);
    }

    public void BuildCompanyId(Id id) {
        company.setBillCompanyId(id);
    }

    public void BuildCompanyId(String string) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(string));
        } catch (IllegalArgumentException e) {
            return;
        }

        company.setBillCompanyId(idx);
    }

    public void BuildIsBank(String isBank) {
        if (isBank == "Bank"){
            company.setIsBank(true);
        }
        else if (isBank == "Company"){
            company.setIsBank(false);
        }
    }

    public Result getCompany() {
        Result result = new Result();
        result.valid = true;
        result.company = company;

        if (company.getType() == null) {
            result.valid = false;
        }

        if (company.getjName() == null || Objects.equals(company.getjName(), "")) {
            result.valid = false;
        } else {
            DataBase dataBase = DataBase.getInstance();
            Map<Id, Company> companies = dataBase.downloadMap(DataBase.COMPANY_PART, Company.class);
            for (Company company : companies.values()) {
                if (Objects.equals(this.company.getjName(), company.getjName())) {
                    result.valid = false;
                    result.description = "Company name is already taken";
                    break;
                }
            }
        }

        DataBase dataBase = DataBase.GetInstance();
        Map<Id, Company> companies = dataBase.DownloadMap(DataBase.COMPANY_PART, Company.class);
        for (Company loopCompany : companies.values()) {
            if (Objects.equals(company.getPAN(), loopCompany.getPAN())) {
                result.valid = false;
                result.description = "PAN is already taken";
                break;
            }
        }

        if (company.getBankID() == null) {
            result.valid = false;
        }

        if (company.getjAddress() == null) {
            result.valid = false;
        }

        return result;
    }
}
