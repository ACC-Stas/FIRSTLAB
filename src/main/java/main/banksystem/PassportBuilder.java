package main.banksystem;

import java.time.LocalDate;
import java.util.Calendar;

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
        try {
            passport.setCitizenship(Citizenship.valueOf(citizenship));
        } catch (IllegalArgumentException ignored) {
        }
    }

    void BuildSex(Sex sex) {
        passport.setSex(sex);
    }

    void BuildSex(String sex) {
        try {
            passport.setSex(Sex.valueOf(sex));
        } catch (IllegalArgumentException ignored) {
        }
    }

    void BuildAddress(Address address) {
        passport.setAddress(address);
    }

    void BuildIdx(Id idx) {
        passport.setIdx(idx);
    }

    void BuildBirthday(LocalDate birthday) {
        passport.setBirthday(birthday);
    }

    void BuildBirthday(String birthday) {
        passport.setBirthday(LocalDate.parse(birthday));
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
