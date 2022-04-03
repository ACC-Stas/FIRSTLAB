package main.banksystem.builders;

import main.banksystem.entities.Address;

import java.util.Objects;

public class AddressBuilder {
    private Address address;

    public AddressBuilder() {
        address = new Address();
    }

    public void reset() {
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

    public static class Result {
        public boolean valid;
        public Address address;
        public String description = "";
    }

    public AddressBuilder.Result getAddress() {
        AddressBuilder.Result result = new AddressBuilder.Result();
        result.valid = true;
        result.address = this.address;

        if (address.getCountry() == null || Objects.equals(address.getCountry(), "")) {
            result.description = "no country";
            result.valid = false;
        }

        if (address.getStreetAddress() == null || Objects.equals(address.getStreetAddress(), "")) {
            result.description = "no street address";
            result.valid = false;
        }

        if (address.getCity() == null || Objects.equals(address.getCity(), "")) {
            result.description = "no city";
            result.valid = false;
        }

        return result;
    }

}
