package main.banksystem.builders;

import main.banksystem.DataBase;
import main.banksystem.entities.Id;
import main.banksystem.entities.Passport;
import main.banksystem.entities.Role;
import main.banksystem.entities.User;

import java.util.Map;
import java.util.Objects;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public void reset() {
        user = new User();
    }

    public void buildPassport(Passport passport) {
        user.setPassport(passport);
    }

    public void buildIdx(Id idx) {
        user.setIdx(idx);
    }

    public void buildIdx(String string) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(string));
        } catch (IllegalArgumentException e) {
            return;
        }

        user.setIdx(idx);
    }

    public void buildNumber(String number) {
        user.setNumber(number);
    }

    public void buildEmail(String email) {
        user.setEmail(email);
    }

    public void buildPassword(String password) {
        user.setPassword(password);
    }

    public void buildRole(Role role) {
        user.setRole(role);
    }

    public void buildRole(String role) {
        try {
            user.setRole(Role.valueOf(role));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public void buildLogin(String login) {
        user.setLogin(login);
    }

    public static class Result {
        public boolean valid;
        public User user;
        public String description = "";
    }

    public Result getUser() {
        Result result = new Result();
        result.valid = true;
        result.user = this.user;
        if (user.getPassword() == null || Objects.equals(user.getPassword(), "")) {
            result.description = "No password";
            result.valid = false;
        }
        if (user.getRole() == null) {
            result.description = "No role";
            result.valid = false;
        }
        if (user.getEmail() == null || Objects.equals(user.getEmail(), "")) {
            result.description = "No email";
            result.valid = false;
        }

        if (user.getPassport() == null) {
            result.description = "No passport";
            result.valid = false;
        }

        if (user.getNumber() == null || Objects.equals(user.getNumber(), "")) {
            result.description = "No number";
            result.valid = false;
        }

        if (user.getIdx() == null) {
            result.description = "No idx";
            result.valid = false;
        }

        if (user.getLogin() == null || Objects.equals(user.getLogin(), "")) {
            result.description = "No login";
            result.valid = false;
        } else {
            DataBase dataBase = DataBase.getInstance();
            Map<Id, User> users = dataBase.downloadMap(DataBase.USER_PART, User.class);
            for (User loopUser : users.values()) {
                if (Objects.equals(user.getLogin(), loopUser.getLogin())) {
                    result.valid = false;
                    result.description = "Login is already taken";
                    break;
                }
            }
        }

        return result;
    }
}
