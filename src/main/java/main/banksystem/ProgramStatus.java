package main.banksystem;
import main.banksystem.entities.Id;
import main.banksystem.entities.User;

public class ProgramStatus {
    private User user;
    private Id company;
    private static ProgramStatus status;

    private ProgramStatus() {
        company = null;
        user = null;
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

    public static ProgramStatus getInstance() {
        if (status == null) {
            status = new ProgramStatus();
        }
        return status;
    }
}
