package main.banksystem.entities;

public enum Period {
    First("3 месяца"), Second("6 месяца"), Third("12 месяца"), Fourth("24 месяца"), Fifth("24+ месяца");

    private String period;

    Period(String name) {
        period = name;
    }

    @Override
    public String toString() {
        return period;
    }

}
