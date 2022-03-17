package Classes;

import java.util.List;

public class Company {
    public Company(String jName, TypeCompany typeCompany, long PAN, BIC bankID, Address jAddress, long billCompanyId) {
        this.jName = jName;
        this.typeCompany = typeCompany;
        this.PAN = PAN;
        this.bankID = bankID;
        this.jAddress = jAddress;
        this.billCompanyId = billCompanyId;
    }

    String jName;
    TypeCompany typeCompany;
    long PAN;
    BIC bankID;
    Address jAddress;
    long billCompanyId;
    List<Long> billsIds;

    public void paySalary(){}
}
