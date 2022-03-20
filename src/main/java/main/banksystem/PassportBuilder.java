package main.banksystem;

import java.util.Calendar;
import java.util.Objects;

public class PassportBuilder {
    private Passport passport;

    PassportBuilder() {
        passport = new Passport();
    }

    public void Reset() {
        passport = new Passport();
    }

    void BuildFullName(FullName fullName) {
        passport.setFullName(fullName);
    }

    void BuildCitizenship(Citizenship citizenship) {
        passport.setCitizenship(citizenship);
    }

    void BuildCitizenship(String citizenship) {
        if (Objects.equals(citizenship, "UKRAINE")) {
            passport.setCitizenship(Citizenship.UKRAINE);
            return;
        }

        if (Objects.equals(citizenship, "BELARUS")) {
            passport.setCitizenship(Citizenship.BELARUS);
            return;
        }

        if (Objects.equals(citizenship, "RUSSIA")) {
            passport.setCitizenship(Citizenship.RUSSIA);
            return;
        }

        if (Objects.equals(citizenship, "USE")) {
            passport.setCitizenship(Citizenship.USA);
            return;
        }

        if (Objects.equals(citizenship, "CHINA")) {
            passport.setCitizenship(Citizenship.CHINA);
        }
    }

    void BuildSex(Sex sex) {
        passport.setSex(sex);
    }

    void BuildAddress(Address address) {
        passport.setAddress(address);
    }

    void BuildIdx(Id idx) {
        passport.setIdx(idx);
    }

    void BuildBirthday(Calendar birthday) {
        passport.setBirthday(birthday);
    }

    static class Result {
        public boolean valid;
        public Passport passport;
    }

    Result getPassport() {
        Result result = new Result();
        result.valid = true;
        result.passport = this.passport;

        if (passport.getSex() == null) {
            result.valid = false;
        }

        if (passport.getBirthday() == null) {
            result.valid = false;
        }

        if (passport.getAddress() == null) {
            result.valid = false;
        }

        if (passport.getIdx() == null) {
            result.valid = false;
        }

        if (passport.getCitizenship() == null) {
            result.valid = false;
        }

        if (passport.getFullName() == null) {
            result.valid = false;
        }

        return result;
    }
}
