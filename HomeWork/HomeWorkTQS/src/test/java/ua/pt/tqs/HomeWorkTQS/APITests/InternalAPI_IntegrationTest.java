package ua.pt.tqs.HomeWorkTQS.APITests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;
import ua.pt.tqs.HomeWorkTQS.services.Statistics;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class InternalAPI_IntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CacheRepository repository;

    private Cache cache1;
    private Cache cache2;
    private Cache cache3;

    @BeforeEach
    public void setUp(){
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

        repository.saveAndFlush(cache1);
        repository.saveAndFlush(cache2);
        repository.saveAndFlush(cache3);
    }

    @AfterEach
    public void tearUp() {
        repository.deleteAll();
    }

    @Test
    public void testIfGivenCityAndCountry_returnRightOneFromCache() {
        String url = "/api/search?city=Aveiro&country=Portugal";
        ResponseEntity<Cache[]> entity = client.getForEntity(url, Cache[].class);

        for(Cache cache: entity.getBody()) {
            assertThat(cache.getCity()).isEqualTo("Aveiro");
            assertThat(cache.getCountry()).isEqualTo("Portugal");
            assertThat(cache.getNumberRequests()).isEqualTo(4);
        }
    }

    @Test
    public void testIfGivenCountry_returnAllFromCache() {
        String url = "/api/search?country=Portugal";
        ResponseEntity<Cache[]> entity = client.getForEntity(url, Cache[].class);
        Cache[] cachelist = {cache1,cache2};
        for(int i = 0; i < entity.getBody().length; i++) {
            assertThat(entity.getBody()[i].getCity()).isEqualTo(cachelist[i].getCity());
            assertThat(entity.getBody()[i].getCountry()).isEqualTo(cachelist[i].getCountry());
            assertThat(entity.getBody()[i].getId()).isEqualTo(cachelist[i].getId());
            assertThat(entity.getBody()[i].getDistrict()).isEqualTo(cachelist[i].getDistrict());
            assertThat(entity.getBody()[i].getApiString()).isEqualTo(cachelist[i].getApiString());
            assertThat(entity.getBody()[i].getAdditionDate()).isEqualTo(cachelist[i].getAdditionDate());
            assertThat(entity.getBody()[i].getMinutesToLive()).isEqualTo(cachelist[i].getMinutesToLive());
            assertThat(entity.getBody()[i].getNumberRequests()).isEqualTo(cachelist[i].getNumberRequests());
        }
    }

    @Test
    public void testIfGivenCity_returnRightCityFromCache() {
        String url = "/api/search?city=Aveiro";
        ResponseEntity<Cache[]> entity = client.getForEntity(url, Cache[].class);
        for(int i = 0; i < entity.getBody().length; i++) {
            assertThat(entity.getBody()[i].getCity()).isEqualTo(cache1.getCity());
            assertThat(entity.getBody()[i].getCountry()).isEqualTo(cache1.getCountry());
            assertThat(entity.getBody()[i].getId()).isEqualTo(cache1.getId());
            assertThat(entity.getBody()[i].getDistrict()).isEqualTo(cache1.getDistrict());
            assertThat(entity.getBody()[i].getApiString()).isEqualTo(cache1.getApiString());
            assertThat(entity.getBody()[i].getAdditionDate()).isEqualTo(cache1.getAdditionDate());
            assertThat(entity.getBody()[i].getMinutesToLive()).isEqualTo(cache1.getMinutesToLive());
            assertThat(entity.getBody()[i].getNumberRequests()).isEqualTo(cache1.getNumberRequests());
        }
    }

    @Test
    public void testIfAskedACityAndCountryThatsNotOnCache_saveItOnCacheAndReturnFromAPI() {
        String url = "/api/search?city=Lisbon&country=Portugal";
        ResponseEntity<Cache[]> entity = client.getForEntity(url, Cache[].class);

        assertThat(repository.findByCityAndCountry("Lisbon", "Portugal")).isNotEmpty();
        assertThat(entity.getBody()).isNotEmpty();
        assertThat(entity.getBody()[0].getCity()).isEqualTo("Lisbon");
    }

    @Test
    public void testIfAskedARequestAlreadyInCache_IncreaseNumberOfRequests() {
        String url = "/api/search?city=Aveiro&country=Portugal";
        ResponseEntity<Cache[]> entity = client.getForEntity(url, Cache[].class);

        assertThat(repository.findByCityAndCountry("Aveiro", "Portugal").get(0).getNumberRequests()).isEqualTo(5);
        assertThat(entity.getBody()[0].getNumberRequests()).isEqualTo(5);
    }

    @Test
    public void testIfStaticsMatchValuesInCache() {
        String url = "/api/cache/stats";
        ResponseEntity<Statistics[]> entity = client.getForEntity(url, Statistics[].class);
        List<Cache> allCache = repository.findAll();
        int i = 0;
        for(Statistics stat: entity.getBody()) {
            for(Cache c: allCache) {
                if(c.getCity().equals(stat.getCity())) {
                    assertThat(stat.getnRequests()).isEqualTo(c.getNumberRequests());
                    assertThat(stat.getCountry()).isEqualTo(c.getCountry());
                }
            }
        }
    }

    @Test
    public void testIfNumberOfRequestsAreRegistering() {
        String url = "/api/search?city=Aveiro&country=Portugal";
        ResponseEntity<Cache[]> entity = client.getForEntity(url, Cache[].class);
        url = "/api/cache/stats";
        ResponseEntity<Statistics[]> stats = client.getForEntity(url, Statistics[].class);

        for(Statistics stat: stats.getBody()) {
            if(stat.getCity().equals("Aveiro")) {
                assertThat(stat.getnRequests()).isEqualTo(5);
            }
        }
    }


}
