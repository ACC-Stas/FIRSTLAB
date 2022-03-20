package main.banksystem;

import java.util.Calendar;

public class Passport {
    public Passport(FullName fullName, Citizenship citizenship, Sex sex, Address address, Id idx, Calendar birthday){
        this.fullName = fullName;
        this.citizenship = citizenship;
        this.sex = sex;
        this.address = address;
        this.idx = idx;
        this.birthday = birthday;
    }

    FullName fullName;
    Citizenship citizenship;
    Sex sex;
    Address address;
    Id idx;
    Calendar birthday;
}
