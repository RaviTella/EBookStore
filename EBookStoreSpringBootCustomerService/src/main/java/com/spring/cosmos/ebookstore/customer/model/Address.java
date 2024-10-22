package com.spring.cosmos.ebookstore.customer.model;

import org.apache.commons.lang3.StringUtils;

public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String Country;

    public Address(String street, String city, String state, String zip, String country) {
        this.street = street;
        this.city = StringUtils.capitalize(city);
        this.state = StringUtils.capitalize(state);
        this.zip = zip;
        Country = StringUtils.capitalize(country);
    }

    public String getStreet() {
        return street;
    }


    public String getCity() {
        return city;
    }


    public String getState() {
        return state;
    }


    public String getZip() {
        return zip;
    }


    public String getCountry() {
        return Country;
    }

}
