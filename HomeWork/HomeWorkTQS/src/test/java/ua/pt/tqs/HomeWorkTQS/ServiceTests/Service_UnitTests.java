package ua.pt.tqs.HomeWorkTQS.ServiceTests;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;
import ua.pt.tqs.HomeWorkTQS.services.HttpClient;
import ua.pt.tqs.HomeWorkTQS.services.Statistics;
import ua.pt.tqs.HomeWorkTQS.services.WebQualityService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Service_UnitTests {

    @Mock
    private CacheRepository repository;

    @Mock
    private HttpClient client;

    @InjectMocks
    private WebQualityService service;

    private Cache cache1;
    private Cache cache2;
    private Cache cache3;

    @BeforeEach
    public void setUp() {
        cache1 = new Cache("Aveiro",
                "Aveiro",
                "Portugal",
                "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1021,\"ic\":\"03d\",\"tp\":16,\"ws\":4.63,\"hu\":77,\"wd\":320,\"ts\":\"2021-05-12T13:00:00.000Z\"},\"pollution\":{\"aqius\":12,\"maincn\":\"p1\",\"ts\":\"2021-05-12T12:00:00.000Z\",\"mainus\":\"p1\",\"aqicn\":13}},\"city\":\"Aveiro\",\"location\":{\"coordinates\":[-8.646666666666667,40.635555555555555],\"type\":\"Point\"},\"state\":\"Aveiro\"}",
                LocalDateTime.now(),
                30);
        cache2 = new Cache("Porto",
                "Porto",
                "Portugal",
                "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1021,\"ic\":\"02d\",\"tp\":16,\"ws\":4.12,\"hu\":72,\"wd\":290,\"ts\":\"2021-05-12T13:00:00.000Z\"},\"pollution\":{\"aqius\":33,\"maincn\":\"o3\",\"ts\":\"2021-05-12T12:00:00.000Z\",\"mainus\":\"o3\",\"aqicn\":26}},\"city\":\"Porto\",\"location\":{\"coordinates\":[-8.658888888888889,41.1475],\"type\":\"Point\"},\"state\":\"Porto\"}",
                LocalDateTime.now(),
                30);
        cache3 = new Cache("Madrid",
                "Madrid",
                "Spain",
                "{\"country\":\"Spain\",\"current\":{\"weather\":{\"pr\":1017,\"ic\":\"03d\",\"tp\":19,\"ws\":8.23,\"hu\":37,\"wd\":250,\"ts\":\"2021-05-12T16:00:00.000Z\"},\"pollution\":{\"aqius\":31,\"maincn\":\"o3\",\"ts\":\"2021-05-12T18:00:00.000Z\",\"mainus\":\"o3\",\"aqicn\":24}},\"city\":\"Madrid\",\"location\":{\"coordinates\":[-3.645277777777778,40.407777777777774],\"type\":\"Point\"},\"state\":\"Madrid\"}",
                LocalDateTime.now(),
                30);
        cache1.setNumberRequests(4);
        cache2.setNumberRequests(3);
        cache3.setNumberRequests(6);
    }


    @Test
    public void testIfServiceFetchCacheFromRepository() {
        List<Cache> response = new ArrayList<>();

        String apiString = "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1021,\"ic\":\"03d\",\"tp\":16,\"ws\":4.63,\"hu\":77,\"wd\":320,\"ts\":\"2021-05-12T13:00:00.000Z\"},\"pollution\":{\"aqius\":12,\"maincn\":\"p1\",\"ts\":\"2021-05-12T12:00:00.000Z\",\"mainus\":\"p1\",\"aqicn\":13}},\"city\":\"Aveiro\",\"location\":{\"coordinates\":[-8.646666666666667,40.635555555555555],\"type\":\"Point\"},\"state\":\"Aveiro\"}";


        response.add(new Cache("Aveiro",
                "Aveiro",
                "Portugal",
                apiString,
                LocalDateTime.now(),
                30));

        given(repository.findByCityAndCountry("Aveiro", "Portugal")).willReturn(response);

        JSONObject data = service.getDataFromSpecificCity("Aveiro", "Aveiro", "Portugal");

        assertThat(data).isNotNull();
        assertThat(data.toString()).isEqualTo(apiString);
    }

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
        try {
            verify(client, times(1)).getResponse(any());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(data).isNotNull();
    }

    @Test
    public void testIfGivenNoCityAndNoCountry_returnAllCacheStatistics() {
        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);
        caches.add(cache2);
        caches.add(cache3);

        given(repository.findAll()).willReturn(caches);

        List<Statistics> result = service.getStatistics(Optional.empty(), Optional.empty());

        List<Statistics> expected = new ArrayList<>();
        expected.add(new Statistics(4, "Aveiro", "Portugal"));
        expected.add(new Statistics(3, "Porto", "Portugal"));
        expected.add(new Statistics(6, "Madrid", "Spain"));

        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result.toString()).isEqualTo(expected.toString());
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).findByCity(any());
        verify(repository, times(0)).findByCountry(any());
        verify(repository, times(0)).findByCityAndCountry(any(), any());
    }

    @Test
    public void testIfGivenCityAndNoCountry_returnCityCacheStatistics() {

        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);

        given(repository.findByCity("Aveiro")).willReturn(caches);

        List<Statistics> result = service.getStatistics(Optional.of("Aveiro"), Optional.empty());

        List<Statistics> expected = new ArrayList<>();
        expected.add(new Statistics(4, "Aveiro", "Portugal"));

        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result.toString()).isEqualTo(expected.toString());
        verify(repository, times(0)).findAll();
        verify(repository, times(1)).findByCity("Aveiro");
        verify(repository, times(0)).findByCountry(any());
        verify(repository, times(0)).findByCityAndCountry(any(), any());
    }

    @Test
    public void testIfGivenCountryAndNoCity_returnCountryCacheStatistics() {

        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);
        caches.add(cache2);

        given(repository.findByCountry("Portugal")).willReturn(caches);

        List<Statistics> result = service.getStatistics(Optional.empty(), Optional.of("Portugal"));

        List<Statistics> expected = new ArrayList<>();
        expected.add(new Statistics(4, "Aveiro", "Portugal"));
        expected.add(new Statistics(3, "Porto", "Portugal"));

        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result.toString()).isEqualTo(expected.toString());
        verify(repository, times(0)).findAll();
        verify(repository, times(0)).findByCity(any());
        verify(repository, times(1)).findByCountry("Portugal");
        verify(repository, times(0)).findByCityAndCountry(any(), any());
    }

    @Test
    public void testIfGivenCountryAndCity_returnCacheStatistics() {
        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);

        given(repository.findByCityAndCountry("Aveiro","Portugal")).willReturn(caches);

        List<Statistics> result = service.getStatistics(Optional.of("Aveiro"), Optional.of("Portugal"));

        List<Statistics> expected = new ArrayList<>();
        expected.add(new Statistics(4, "Aveiro", "Portugal"));

        assertThat(result.size()).isEqualTo(expected.size());
        assertThat(result.toString()).isEqualTo(expected.toString());
        verify(repository, times(0)).findAll();
        verify(repository, times(0)).findByCity(any());
        verify(repository, times(0)).findByCountry(any());
        verify(repository, times(1)).findByCityAndCountry("Aveiro", "Portugal");
    }

    @Test
    public void testIfGivenNoCityAndNoCountry_returnAllCache() {
        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);
        caches.add(cache2);
        caches.add(cache3);

        given(repository.findAll()).willReturn(caches);

        List<Cache> result = service.getCache(Optional.empty(), Optional.empty());

        assertThat(result).isEqualTo(caches);
        verify(repository, times(1)).findAll();
        verify(repository, times(0)).findByCityAndCountry(any(), any());
        verify(repository, times(0)).findByCity(any());
        verify(repository, times(0)).findByCountry(any());
    }

    @Test
    public void testIfGivenCityAndNoCountry_returnCityCache() {
        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);

        given(repository.findByCity("Aveiro")).willReturn(caches);

        List<Cache> result = service.getCache(Optional.of("Aveiro"), Optional.empty());

        assertThat(result).isEqualTo(caches);
        verify(repository, times(0)).findAll();
        verify(repository, times(0)).findByCityAndCountry(any(), any());
        verify(repository, times(1)).findByCity("Aveiro");
        verify(repository, times(0)).findByCountry(any());
    }

    @Test
    public void testIfGivenCountryAndNoCity_returnCountryCache() {
        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);
        caches.add(cache2);

        given(repository.findByCountry("Portugal")).willReturn(caches);

        List<Cache> result = service.getCache(Optional.empty(), Optional.of("Portugal"));

        assertThat(result).isEqualTo(caches);
        verify(repository, times(0)).findAll();
        verify(repository, times(0)).findByCityAndCountry(any(), any());
        verify(repository, times(0)).findByCity(any());
        verify(repository, times(1)).findByCountry("Portugal");
    }

    @Test
    public void testIfGivenCountryAndCity_returnCache() {
        List<Cache> caches = new ArrayList<>();
        caches.add(cache1);

        given(repository.findByCityAndCountry("Porto", "Portugal")).willReturn(caches);

        List<Cache> result = service.getCache(Optional.of("Porto"), Optional.of("Portugal"));

        assertThat(result).isEqualTo(caches);
        verify(repository, times(0)).findAll();
        verify(repository, times(1)).findByCityAndCountry("Porto", "Portugal");
        verify(repository, times(0)).findByCity(any());
        verify(repository, times(0)).findByCountry(any());
    }



}
