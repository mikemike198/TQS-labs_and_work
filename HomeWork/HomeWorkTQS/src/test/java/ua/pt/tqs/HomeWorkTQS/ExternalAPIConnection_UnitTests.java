package ua.pt.tqs.HomeWorkTQS;

import org.json.simple.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;
import ua.pt.tqs.HomeWorkTQS.services.WebQualityService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExternalAPIConnection_UnitTests {

    @Mock
    private CacheRepository repository;

    @InjectMocks
    private WebQualityService service;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void whenAskedInfoSpecificCity_CheckIfCityStateAndCountryAreCorrect() {
        JSONObject data = (JSONObject) service.getDataFromSpecificCity("Aveiro", "Aveiro", "Portugal");

        String city =(String) data.get("city");
        String country = (String) data.get("country");
        String state = (String) data.get("state");

        assertAll(() -> assertEquals(city, "Aveiro"),
                  () -> assertEquals(country, "Portugal"),
                  () -> assertEquals(state, "Aveiro"));
    }
}
