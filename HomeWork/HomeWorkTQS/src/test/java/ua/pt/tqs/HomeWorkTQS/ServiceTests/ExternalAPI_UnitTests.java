package ua.pt.tqs.HomeWorkTQS.ServiceTests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;
import ua.pt.tqs.HomeWorkTQS.services.HttpClient;
import ua.pt.tqs.HomeWorkTQS.services.WebQualityService;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExternalAPI_UnitTests {

    @Mock
    private CacheRepository repository;

    @Mock
    private HttpClient client;

    @InjectMocks
    private WebQualityService service;

    @Test
    public void testIfNothingInCacheFetchAPI() {

        JSONParser parser = new JSONParser();

        given(repository.findByCityAndCountry("Aveiro", "Portugal")).willReturn(Collections.emptyList());
        try {
            given(client.getResponse(any())).willReturn((JSONObject) parser.parse("{\"status\":\"success\",\"data\":{\"city\":\"Aveiro\",\"state\":\"Aveiro\",\"country\":\"Portugal\",\"location\":{\"type\":\"Point\",\"coordinates\":[-8.646666666666667,40.635555555555555]},\"current\":{\"weather\":{\"ts\":\"2021-05-13T18:00:00.000Z\",\"tp\":16,\"pr\":1018,\"hu\":88,\"ws\":3.09,\"wd\":300,\"ic\":\"03d\"},\"pollution\":{\"ts\":\"2021-05-13T18:00:00.000Z\",\"aqius\":13,\"mainus\":\"p2\",\"aqicn\":4,\"maincn\":\"p2\"}}}}"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JSONObject data = service.getDataFromSpecificCity("Aveiro", "Aveiro", "Portugal");

        verify(repository, times(1)).save(any());
        assertThat(data).isNotNull();

    }


}
