package main.banksystem.builders;

import main.banksystem.containers.Address;

import java.util.Objects;

public class AddressBuilder {
    private Address address;

    public AddressBuilder() {
        address = new Address();
    }

    public void buildCity(String city) {
        address.setCity(city);
    }

    public void buildCountry(String country) {
        address.setCountry(country);
    }

    public void buildStreetAddress(String streetAddress) {
        address.setStreetAddress(streetAddress);
    }

    public void reset() {
        address = new Address();
    }

    public static class Result {
        public boolean valid;
        public Address address;
    }

    public AddressBuilder.Result getAddress() {
        AddressBuilder.Result result = new AddressBuilder.Result();
        result.valid = true;
        result.address = this.address;

        if (address.getCountry() == null || Objects.equals(address.getCountry(), "")) {
            result.valid = false;
        }

        if (address.getStreetAddress() == null || Objects.equals(address.getStreetAddress(), "")) {
            result.valid = false;
        }

        if (address.getCity() == null || Objects.equals(address.getCity(), "")) {
            result.valid = false;
        }

        return result;
    }

}
