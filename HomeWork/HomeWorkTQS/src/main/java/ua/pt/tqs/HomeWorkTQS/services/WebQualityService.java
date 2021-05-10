package ua.pt.tqs.HomeWorkTQS.services;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Service
public class WebQualityService {

    private URL url;
    private String key;

    public WebQualityService() {
        this.key = "a31160fa-9ac8-4105-9e77-ad3fc53cfe71";
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

        JSONObject data = (JSONObject) fetchFromAPI(url);

        if (data != null ) {
            return data;
        }
        // {"data":{"country":"Portugal","current":{"weather":{"pr":1020,"ic":"01d","tp":20,"ws":0.95,"hu":57,"wd":243,"ts":"2021-05-05T10:00:00.000Z"},"pollution":{"aqius":29,"maincn":"p1","ts":"2021-05-05T08:00:00.000Z","mainus":"p2","aqicn":12}},"city":"Vila Real","location":{"coordinates":[-7.7908333333333335,41.37138888888889],"type":"Point"},"state":"Vila Real"},"status":"success"}
        return null;
    }

    public JSONObject getDataFromSpecificCity(String city, String state, String country) {

        try {
            this.url = new URL("http://api.airvisual.com/v2/city?city="+city+"&state="+state+"&country="+country+"&key="+key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JSONObject data = (JSONObject) fetchFromAPI(url);

        if (data != null ) {
            return data;
        }
        // {"data":{"country":"Portugal","current":{"weather":{"pr":1020,"ic":"01d","tp":20,"ws":0.95,"hu":57,"wd":243,"ts":"2021-05-05T10:00:00.000Z"},"pollution":{"aqius":29,"maincn":"p1","ts":"2021-05-05T08:00:00.000Z","mainus":"p2","aqicn":12}},"city":"Vila Real","location":{"coordinates":[-7.7908333333333335,41.37138888888889],"type":"Point"},"state":"Vila Real"},"status":"success"}
        return null;
    }
}
