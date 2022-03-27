package main.banksystem.builders;

import main.banksystem.containers.FullName;

import java.util.Objects;

public class FullNameBuilder {
    private FullName fullName;

    public FullNameBuilder() {
        fullName = new FullName();
    }

    public void reset() {
        fullName = new FullName();
    }

    public void buildFirstName(String name) {
        fullName.setFirstName(name);
    }

    public void buildSecondName(String name) {
        fullName.setSecondName(name);
    }

    public void buildFatherName(String name) {
        fullName.setFatherName(name);
    }

    public static class Result {
        public boolean valid;
        public FullName fullName;
    }

    public Result getFullName() {
        Result result = new Result();
        result.valid = true;
        result.fullName = this.fullName;

        if (fullName.getFirstName() == null || Objects.equals(fullName.getFirstName(), "")) {
            result.valid = false;
        }

        if (fullName.getSecondName() == null || Objects.equals(fullName.getSecondName(), "")) {
            result.valid = false;
        }

        if (fullName.getFatherName() == null || Objects.equals(fullName.getFatherName(), "")) {
            result.valid = false;
        }

        return result;
    }
}
