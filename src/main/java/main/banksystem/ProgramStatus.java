package main.banksystem;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

public class ProgramStatus {
    private User user;
    private Id company;
    private Id bank;
    private static ProgramStatus status;

    private ProgramStatus() {
        company = null;
        user = null;
        bank = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Id getCompany() {
        return company;
    }

    public void setCompany(Id company) {
        this.company = company;
    }

    public Id getBank() {
        return bank;
    }

    public void setBank(Id bank) {
        this.bank = bank;
    }

    public static ProgramStatus getInstance() {
        if (status == null) {
            status = new ProgramStatus();
        }
        return status;
    }
}
