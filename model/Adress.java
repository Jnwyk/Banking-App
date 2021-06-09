package model;

import java.io.Serializable;

public class Adress implements Serializable {

    private String street;
    private int number;
    private String city;

    public Adress(String street, int number, String city) {
        this.street = street;
        this.number = number;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }
}
