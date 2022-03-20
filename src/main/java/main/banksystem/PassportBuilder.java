package main.banksystem;

import java.time.LocalDate;
import java.util.Calendar;

public class PassportBuilder {
    private Passport passport;

    public PassportBuilder() {
        passport = new Passport();
    }

    public void Reset() {
        passport = new Passport();
    }

    public void BuildFullName(FullName fullName) {
        passport.setFullName(fullName);
    }

    public void BuildCitizenship(Citizenship citizenship) {
        passport.setCitizenship(citizenship);
    }

    public void BuildCitizenship(String citizenship) {
        try {
            passport.setCitizenship(Citizenship.valueOf(citizenship));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void BuildSex(Sex sex) {
        passport.setSex(sex);
    }

    public void BuildSex(String sex) {
        try {
            passport.setSex(Sex.valueOf(sex));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void BuildAddress(Address address) {
        passport.setAddress(address);
    }

    public void BuildIdx(Id idx) {
        passport.setIdx(idx);
    }

    public void BuildIdx(String string) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(string));
        } catch (IllegalArgumentException e) {
            return;
        }
        passport.setIdx(idx);
    }

    public void BuildBirthday(LocalDate birthday) {
        passport.setBirthday(birthday);
    }

    public static class Result {
        public boolean valid;
        public Passport passport;
    }

    public Result getPassport() {
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
