package main.banksystem.builders;

import main.banksystem.containers.*;

import java.time.LocalDate;

public class PassportBuilder {
    private Passport passport;

    public PassportBuilder() {
        passport = new Passport();
    }

    public void reset() {
        passport = new Passport();
    }

    public void buildFullName(FullName fullName) {
        passport.setFullName(fullName);
    }

    public void buildCitizenship(Citizenship citizenship) {
        passport.setCitizenship(citizenship);
    }

    public void buildCitizenship(String citizenship) {
        try {
            passport.setCitizenship(Citizenship.valueOf(citizenship));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void buildSex(Sex sex) {
        passport.setSex(sex);
    }

    public void buildSex(String sex) {
        try {
            passport.setSex(Sex.valueOf(sex));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void buildAddress(Address address) {
        passport.setAddress(address);
    }

    public void buildIdx(Id idx) {
        passport.setIdx(idx);
    }

    public void buildIdx(String string) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(string));
        } catch (IllegalArgumentException e) {
            return;
        }
        passport.setIdx(idx);
    }

    public void buildBirthday(LocalDate birthday) {
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
