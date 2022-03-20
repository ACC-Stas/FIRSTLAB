package main.banksystem;

import java.time.LocalDate;
import java.util.Objects;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public void Reset() {
        user = new User();
    }

    public void BuildPassport(Passport passport) {
        user.setPassport(passport);
    }

    public void BuildIdx(Id idx) {
        user.setIdx(idx);
    }

    public void BuildIdx(String string) {
        Id idx;
        try {
            idx = new Id(Long.parseLong(string));
        } catch (IllegalArgumentException e) {
            return;
        }

        user.setIdx(idx);
    }

    public void BuildNumber(String number) {
        user.setNumber(number);
    }

    public void BuildEmail(String email) {
        user.setEmail(email);
    }

    public void BuildPassword(String password) {
        user.setPassword(password);
    }

    public void BuildRole(Role role) {
        user.setRole(role);
    }

    public void BuildRole(String role) {
        try {
            user.setRole(Role.valueOf(role));
        } catch (IllegalArgumentException ignored) {
        }
    }

    public static class Result {
        public boolean valid;
        public User user;
    }

    Result getUser() {
        Result result = new Result();
        result.valid = true;
        result.user = this.user;
        if (Objects.equals(user.getPassword(), "")) {
            result.valid = false;
        }
        if (user.getRole() == null) {
            result.valid = false;
        }
        if (Objects.equals(user.getEmail(), "")) {
            result.valid = false;
        }

        if (user.getPassport() == null) {
            result.valid = false;
        }

        if (Objects.equals(user.getNumber(), "")) {
            result.valid = false;
        }

        if (user.getIdx() == null) {
            result.valid = false;
        }

        return result;
    }
}
