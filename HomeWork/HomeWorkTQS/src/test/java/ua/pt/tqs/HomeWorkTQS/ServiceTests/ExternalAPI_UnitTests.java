package ua.pt.tqs.HomeWorkTQS.ServiceTests;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.pt.tqs.HomeWorkTQS.services.HttpClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ExternalAPI_UnitTests {

    private HttpClient client = new HttpClient();


    @Test
    public void testIfCityAndCountryReturnedAreTheSameAsTheRequest() {
        String city = "Aveiro";
        String state = "Aveiro";
        String country = "Portugal";
        String key ="a31160fa-9ac8-4105-9e77-ad3fc53cfe71";
        try {
            URL url = new URL("http://api.airvisual.com/v2/city?city="+city+"&state="+state+"&country="+country+"&key="+key);
            JSONObject response = client.getResponse(url.toString());
            String cityResponse = (String)((JSONObject)response.get("data")).get("city");
            String stateResponse = (String)((JSONObject)response.get("data")).get("state");
            String countryResponse = (String)((JSONObject)response.get("data")).get("country");

            assertAll(
                    () -> assertEquals(city, cityResponse),
                    () -> assertEquals(country, countryResponse),
                    () -> assertEquals(state, stateResponse)
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}
