package main.banksystem.builders;

import main.banksystem.containers.Company;
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

    public void BuildType(String name) {
        try {
            company.setType(Company.Type.valueOf(name));
        } catch (IllegalArgumentException ignored) {
        }
    }



    public Result getCompany() {
        Result result = new Result();
        result.valid = true;
        result.company = company;


        return result;
    }
}
