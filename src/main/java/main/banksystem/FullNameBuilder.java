package main.banksystem;

public class FullNameBuilder {
    private FullName fullName;

    public void Reset() {
        fullName = new FullName();
    }

    public FullNameBuilder() {
        fullName = new FullName();
    }

    public void BuildFirstName(String name) {
        fullName.setFirstName(name);
    }

    public void BuildSecondName(String name) {
        fullName.setSecondName(name);
    }

    public void BuildFatherName(String name) {
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

        if (fullName.getFirstName() == null) {
            result.valid = false;
        }

        if (fullName.getSecondName() == null) {
            result.valid = false;
        }

        if (fullName.getFatherName() == null) {
            result.valid = false;
        }

        return result;
    }
}
