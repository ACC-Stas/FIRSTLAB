package main.banksystem.containers;

public enum Percent {
    First(5), Second(10), Third(15), Fourth(20), Fifth(25);

    private int percent;

    Percent(int name) {
        percent = name;
    }

    @Override
    public String toString() {
        return String.valueOf(percent);
    }
}
