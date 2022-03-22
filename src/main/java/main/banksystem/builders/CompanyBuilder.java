package main.banksystem.builders;

import main.banksystem.containers.BIC;
import main.banksystem.containers.Company;
import main.banksystem.containers.Id;
import main.banksystem.containers.Role;

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

    public void BuildBunkId(BIC bic) {
        company.setBankID(bic);
    }

    public void BuildBunkId(String bic) {
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


    public Result getCompany() {
        Result result = new Result();
        result.valid = true;
        result.company = company;


        return result;
    }
}
