package ua.pt.tqs.HomeWorkTQS.controllers;

public class Search {

    public String city;
    public String country;
    public String state;

    public Search(String city, String country, String state) {
        this.city = city;
        this.country = country;
        this.state = state;
    }

    public Search() {
        city = "";
        country = "";
        state ="";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
