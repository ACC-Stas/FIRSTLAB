package main.banksystem.containers;

public class BIC implements java.io.Serializable {
    public BIC(Id id) {
        this.id = id;
    }

    private final Id id;

    public Id getId() {
        return id;
    }
}
