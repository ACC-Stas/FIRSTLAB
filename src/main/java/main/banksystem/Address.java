package main.banksystem;

public class Address {
    public Address(String country, String city, String streetAddress){
        this.country = country;
        this.city = city;
        this.streetAddress = streetAddress;
    }
    private String country;
    private String city;
    private String streetAddress;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
}
