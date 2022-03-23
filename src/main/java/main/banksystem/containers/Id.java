package main.banksystem.containers;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    long id;
}
