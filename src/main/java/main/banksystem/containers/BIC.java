package main.banksystem.containers;

public class BIC implements java.io.Serializable {
    public BIC(Id id) {
        this.id = id;
    }

    private Id id;

    public void setId(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
