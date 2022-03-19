package main.banksystem;

public class FullName {
    private String firstName;
    private String secondName;
    private String fatherName;


    public FullName(String firstName, String secondName, String fatherName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.fatherName = fatherName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFatherName() {
        return fatherName;
    }
}
