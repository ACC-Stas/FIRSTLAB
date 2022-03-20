package main.banksystem;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public void Reset() {
        user = new User();
    }

    public void BuildPassport(Passport passport) {
        user.passport = passport;
    }

    public void BuildIdx(Id idx) {
        user.idx = idx;
    }

    public void BuildNumber(String number) {
        user.number = number;
    }

    public void BuildEmail(String email) {
        user.email = email;
    }

    public void BuildPassword(String password) {
        user.password = password;
    }

    public void BuildRole(Role role) {
        user.role = role;
    }

    public void BuildRole(String role) {
        try {
            user.setRole(Role.valueOf(role));
        } catch (IllegalArgumentException ignored) {
        }
    }
}
