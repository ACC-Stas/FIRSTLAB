package main.banksystem.containers;

public class Id implements java.io.Serializable {
    public Id(long id) {
        this.id = id;
    }

    public Id() {
        this.id = 0L;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    long id;
}
