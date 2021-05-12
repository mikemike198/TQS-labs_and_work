package ua.pt.tqs.HomeWorkTQS.services;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    public WebQualityService(CacheRepository repository) {

        this.key = "a31160fa-9ac8-4105-9e77-ad3fc53cfe71";
        this.cache = repository;
    }

    private JSONObject fetchFromAPI(URL url) {
        JSONObject data = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int code = conn.getResponseCode();

            if (code != 200) {
                throw new RuntimeException("HttpResponseCode: " + code);
            } else {

                String jsonString = "";

                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    jsonString += scanner.nextLine();
                }
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(jsonString);

                data = (JSONObject) data_obj.get("data");
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
            return data;
    }

    public JSONObject getDataFromNearestCity() {
        try {
            this.url = new URL("http://api.airvisual.com/v2/nearest_city?key=" + key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return (JSONObject) fetchFromAPI(url);
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
            System.out.println("Yes");
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

    public List<Cache> getAllCache() {
        Iterable<Cache> all = cache.findAll();
        List<Cache> allCache = new ArrayList<>();
        all.forEach(allCache::add);
        return allCache ;
    }

    public List<Cache> getCityCache(String city) {
        Iterable<Cache> all = cache.findByCity(city);
        System.out.print(all);
        List<Cache> allCache = new ArrayList<>();
        all.forEach(allCache::add);
        return allCache;
    }

    public List<Cache> getCountryCache(String country) {
        Iterable<Cache> all = cache.findByCountry(country);
        System.out.print(all);
        List<Cache> allCache = new ArrayList<>();
        all.forEach(allCache::add);
        return allCache;
    }

    public List<Cache> getCityCountryCache(String city, String country) {
        Iterable<Cache> all = cache.findByCityAndCountry(city, country);
        System.out.print(all);
        List<Cache> allCache = new ArrayList<>();
        all.forEach(allCache::add);
        return allCache;
    }


}
