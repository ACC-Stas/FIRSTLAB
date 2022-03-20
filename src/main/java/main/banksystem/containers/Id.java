package main.banksystem.containers;

public class Id implements java.io.Serializable {
    public Id(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    long id;
}
