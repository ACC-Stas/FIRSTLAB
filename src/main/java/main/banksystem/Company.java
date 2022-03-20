package main.banksystem;

import java.util.List;

public class Company {
    public Company(String jName, TypeCompany typeCompany, Id PAN, BIC bankID, Address jAddress, Id billCompanyId) {
        this.jName = jName;
        this.typeCompany = typeCompany;
        this.PAN = PAN;
        this.bankID = bankID;
        this.jAddress = jAddress;
        this.billCompanyId = billCompanyId;
    }


    String jName;
    TypeCompany typeCompany;
    Id PAN;
    BIC bankID;
    Address jAddress;
    Id billCompanyId;
    List<Id> billsIds;

    public void paySalary() {
    }
}
