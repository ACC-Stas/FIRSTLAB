package main.banksystem;
import main.banksystem.containers.User;

public class ProgramStatus {
    private User user;
    private static ProgramStatus status;

    private ProgramStatus() {
        User user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ProgramStatus getInstance() {
        if (status == null) {
            status = new ProgramStatus();
        }
        return status;
    }
}
