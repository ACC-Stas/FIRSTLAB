package main.banksystem;

public class User {
    public User(Passport passport, Id idx, String number, String email, String password, Role role) {
        this.passport = passport;
        this.idx = idx;
        this.number = number;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Id getIdx() {
        return idx;
    }

    public void setIdx(Id idx) {
        this.idx = idx;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Passport passport;
    private Id idx;
    private String number;
    private String email;
    private String password;
    private Role role;
}
