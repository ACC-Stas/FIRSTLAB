package main.banksystem;

import java.time.LocalDate;
import java.util.Calendar;

public class Passport {
    public Passport(FullName fullName, Citizenship citizenship, Sex sex, Address address, Id idx, LocalDate birthday) {
        this.fullName = fullName;
        this.citizenship = citizenship;
        this.sex = sex;
        this.address = address;
        this.idx = idx;
        this.birthday = birthday;
    }

    public Passport() {
        this.fullName = null;
        this.citizenship = null;
        this.sex = null;
        this.address = null;
        this.idx = null;
        this.birthday = null;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Citizenship citizenship) {
        this.citizenship = citizenship;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Id getIdx() {
        return idx;
    }

    public void setIdx(Id idx) {
        this.idx = idx;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    private FullName fullName;
    private Citizenship citizenship;
    private Sex sex;
    private Address address;
    private Id idx;
    private LocalDate birthday;
}
