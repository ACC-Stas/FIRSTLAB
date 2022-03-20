package main.banksystem;

public class Id implements ISerializable {
    public Id(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    long id;

    public String Serialize() {
        return Long.toString(id);
    }
}
