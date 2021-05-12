package ua.pt.tqs.HomeWorkTQS.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cache {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String district;
    private String country;
    @Column(length = 1000)
    private String apiString;
    private LocalDateTime additionDate;
    private Integer minutesToLive;
    private int numberRequests;

    protected Cache() {}

    public Cache(String city, String district, String country, String apiString, LocalDateTime additionDate, Integer minutesToLive) {
        this.numberRequests = 1;
        this.city = city;
        this.district = district;
        this.country = country;
        this.apiString = apiString;
        this.additionDate = additionDate;
        this.minutesToLive = minutesToLive;
    }

    @Override
    public String toString() {
        return "Cache{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", country='" + country + '\'' +
                ", apiString='" + apiString + '\'' +
                ", additionDate=" + additionDate +
                ", minutesToLive=" + minutesToLive +
                ", numberRequests=" + numberRequests +
                '}';
    }

    public LocalDateTime getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(LocalDateTime additionDate) {
        this.additionDate = additionDate;
    }

    public Integer getMinutesToLive() {
        return minutesToLive;
    }

    public void setMinutesToLive(Integer minutesToLive) {
        this.minutesToLive = minutesToLive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getApiString() {
        return apiString;
    }

    public void setApiString(String apiString) {
        this.apiString = apiString;
    }

    public int getNumberRequests() {
        return numberRequests;
    }

    public void setNumberRequests(int numberRequests) {
        this.numberRequests = numberRequests;
    }

    public void incrementNumberRequests() {
        this.numberRequests += 1;
    }
}
