package main.banksystem.builders;

import main.banksystem.containers.Address;

public class AddressBuilder {
    private Address address;

    public AddressBuilder() {
        address = new Address();
    }

    public void BuildCity(String city) {
        address.setCity(city);
    }

    public void BuildCountry(String country) {
        address.setCountry(country);
    }

    public void BuildStreetAddress(String streetAddress) {
        address.setStreetAddress(streetAddress);
    }

    public void Reset() {
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

        if (address.getCountry() == null) {
            result.valid = false;
        }

        if (address.getStreetAddress() == null) {
            result.valid = false;
        }

        if (address.getCity() == null) {
            result.valid = false;
        }

        return result;
    }

}
