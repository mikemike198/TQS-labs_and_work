package ua.pt.tqs.HomeWorkTQS.services;

import org.springframework.stereotype.Service;

import javax.persistence.Entity;

public class Statistics {
    private int nRequests;
    private String city;
    private String country;

    public Statistics(int nRequests, String city, String country) {
        this.nRequests = nRequests;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "nRequests=" + nRequests +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public int getnRequests() {
        return nRequests;
    }

    public void setnRequests(int nRequests) {
        this.nRequests = nRequests;
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
