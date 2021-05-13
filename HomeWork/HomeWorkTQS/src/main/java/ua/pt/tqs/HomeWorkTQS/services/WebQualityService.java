package ua.pt.tqs.HomeWorkTQS.services;

import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;

@Service
public class WebQualityService {

    // {"data":{"country":"Portugal","current":{"weather":{"pr":1020,"ic":"01d","tp":20,"ws":0.95,"hu":57,"wd":243,"ts":"2021-05-05T10:00:00.000Z"},"pollution":{"aqius":29,"maincn":"p1","ts":"2021-05-05T08:00:00.000Z","mainus":"p2","aqicn":12}},"city":"Vila Real","location":{"coordinates":[-7.7908333333333335,41.37138888888889],"type":"Point"},"state":"Vila Real"},"status":"success"}


    private URL url;
    private String key;
    private CacheRepository cache;
    private HttpClient client;

    public WebQualityService(CacheRepository repository, HttpClient client) {

        this.key = "a31160fa-9ac8-4105-9e77-ad3fc53cfe71";
        this.cache = repository;
        this.client = client;
    }

    private JSONObject fetchFromAPI(URL url)  {
        try {
            JSONObject res = client.getResponse(url.toString());
            JSONObject data = (JSONObject) res.get("data");
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getDataFromNearestCity() {
        try {
            this.url = new URL("http://api.airvisual.com/v2/nearest_city?key=" + key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JSONObject data = fetchFromAPI(url);
        System.out.print("Service data" + data);
        return data;
    }

    public JSONObject getDataFromSpecificCity(String city, String state, String country) {

        try {
            this.url = new URL("http://api.airvisual.com/v2/city?city="+city+"&state="+state+"&country="+country+"&key="+key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        List<Cache> results = cache.findByCityAndCountry(city, country);

        // If exists then it will return the api of the cache,
        // if not we will save in cache and return api info
        for(Cache result: results) {
            LocalDateTime date = result.getAdditionDate().plusMinutes(result.getMinutesToLive());
            LocalDateTime now = LocalDateTime.now();
            System.out.println(date);
            System.out.println(now);
            boolean isBefore = date.isBefore(now);
            if (isBefore) {
                break;
            }
            JSONParser parser = new JSONParser();
            JSONObject finalResult = null;
            try {
                finalResult = (JSONObject) parser.parse(result.getApiString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result.incrementNumberRequests();
            cache.save(result);
            return finalResult;
        }

        JSONObject data = (JSONObject) fetchFromAPI(url);

        if (data != null) {
            Cache toSave = new Cache(city, state, country, data.toString(), LocalDateTime.now(), 30);
            cache.save(toSave);
        }

        return data;
    }

    public List<Cache> getCache(Optional<String> city, Optional<String> country) {
        List<Cache> cacheReturned;

        if (city.isPresent() && country.isPresent()) {
            cacheReturned = cache.findByCityAndCountry(city.get(), country.get());
            if (cacheReturned.isEmpty()) {
                JSONObject apiData = getDataFromSpecificCity(city.get(), city.get(), country.get());
                Cache newCache = new Cache(
                        apiData.get("city").toString(),
                        apiData.get("state").toString(),
                        apiData.get("country").toString(),
                        apiData.toString(),
                        LocalDateTime.now(),
                        30);
                cache.save(newCache);
                Cache cacheToReturn = cache.findByCityAndCountry(
                        apiData.get("city").toString(),
                        apiData.get("country").toString()).get(0);
                cacheReturned.add(cacheToReturn);
            }
        } else if (city.isPresent()) {
            cacheReturned = cache.findByCity(city.get());
        } else if (country.isPresent()) {
           cacheReturned = cache.findByCountry(country.get());
        } else {
            cacheReturned = cache.findAll();
            if (cacheReturned.isEmpty()) {
                JSONObject apiData = getDataFromNearestCity();
                Cache newCache = new Cache(
                        apiData.get("city").toString(),
                        apiData.get("state").toString(),
                        apiData.get("country").toString(),
                        apiData.toString(),
                        LocalDateTime.now(),
                        30);

                cache.save(newCache);
                Cache cacheToReturn = cache.findByCityAndCountry(
                        apiData.get("city").toString(),
                        apiData.get("country").toString()).get(0);
                cacheReturned.add(cacheToReturn);
            }
        }

        return cacheReturned;
    }

    public List<Statistics> getStatistics(Optional<String> city, Optional<String> country) {
        List<Cache> allCache;

        if (city.isPresent() && country.isPresent()) {
            allCache = cache.findByCityAndCountry(city.get(), country.get());
        } else if (city.isPresent()) {
            allCache = cache.findByCity(city.get());
        } else if (country.isPresent()) {
            allCache = cache.findByCountry(country.get());
        } else {
            allCache = cache.findAll();
        }

        List<Statistics> response = new ArrayList<>();

        for(Cache cache: allCache) {
            String cacheCountry = cache.getCountry();
            String cacheCity = cache.getCity();
            int nRequests = cache.getNumberRequests();
            Statistics stat = new Statistics(nRequests,cacheCity,cacheCountry);
            response.add(stat);
        }

        return response;
    }


}
