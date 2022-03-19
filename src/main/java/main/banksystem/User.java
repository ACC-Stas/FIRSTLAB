package main.banksystem;

public class User {
    public User(Passport passport, int idx, String number, String email, String password, Role role){
        this.passport = passport;
        this.idx = idx;
        this.number = number;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    Passport passport;
    int idx;
    String number;
    String email;
    String password;
    Role role;
}
