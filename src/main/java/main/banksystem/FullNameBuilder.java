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
}
