package ua.pt.tqs.HomeWorkTQS.RepositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class CacheRepositoryTest {

    @Autowired
    private CacheRepository repository;

    @Autowired
    private TestEntityManager manager;

    private Cache cache;
    private Cache cache1;
    private Cache cache2;
    private Cache cache3;

    @BeforeEach
    public void setUp() {
        cache = manager.persistAndFlush(
                new Cache("Aveiro",
                        "Aveiro",
                        "Portugal",
                        "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1021,\"ic\":\"03d\",\"tp\":16,\"ws\":4.63,\"hu\":77,\"wd\":320,\"ts\":\"2021-05-12T13:00:00.000Z\"},\"pollution\":{\"aqius\":12,\"maincn\":\"p1\",\"ts\":\"2021-05-12T12:00:00.000Z\",\"mainus\":\"p1\",\"aqicn\":13}},\"city\":\"Aveiro\",\"location\":{\"coordinates\":[-8.646666666666667,40.635555555555555],\"type\":\"Point\"},\"state\":\"Aveiro\"}",
                        LocalDateTime.now(),
                        30));

        cache1 = manager.persistAndFlush(
                new Cache("Porto",
                        "Porto",
                        "Portugal",
                        "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1021,\"ic\":\"02d\",\"tp\":16,\"ws\":4.12,\"hu\":72,\"wd\":290,\"ts\":\"2021-05-12T13:00:00.000Z\"},\"pollution\":{\"aqius\":33,\"maincn\":\"o3\",\"ts\":\"2021-05-12T12:00:00.000Z\",\"mainus\":\"o3\",\"aqicn\":26}},\"city\":\"Porto\",\"location\":{\"coordinates\":[-8.658888888888889,41.1475],\"type\":\"Point\"},\"state\":\"Porto\"}",
                        LocalDateTime.now(),
                        30));

        cache2 = manager.persistAndFlush(
                new Cache("Madrid",
                        "Madrid",
                        "Spain",
                        "{\"country\":\"Spain\",\"current\":{\"weather\":{\"pr\":1017,\"ic\":\"03d\",\"tp\":19,\"ws\":8.23,\"hu\":37,\"wd\":250,\"ts\":\"2021-05-12T16:00:00.000Z\"},\"pollution\":{\"aqius\":31,\"maincn\":\"o3\",\"ts\":\"2021-05-12T18:00:00.000Z\",\"mainus\":\"o3\",\"aqicn\":24}},\"city\":\"Madrid\",\"location\":{\"coordinates\":[-3.645277777777778,40.407777777777774],\"type\":\"Point\"},\"state\":\"Madrid\"}",
                        LocalDateTime.now(),
                        30));

        cache3 = manager.persistAndFlush(
                new Cache("Lisbon",
                        "Lisbon",
                        "Portugal",
                        "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1022,\"ic\":\"02d\",\"tp\":18,\"ws\":4.63,\"hu\":52,\"wd\":310,\"ts\":\"2021-05-12T16:00:00.000Z\"},\"pollution\":{\"aqius\":8,\"maincn\":\"p2\",\"ts\":\"2021-05-12T18:00:00.000Z\",\"mainus\":\"p2\",\"aqicn\":3}},\"city\":\"Lisbon\",\"location\":{\"coordinates\":[-9.131168,38.721676],\"type\":\"Point\"},\"state\":\"Lisbon\"}",
                        LocalDateTime.now(),
                        30));
    }


    @Test
    public void askByCity_returnCacheByCity() {
        List<Cache> result = repository.findByCity("Aveiro");

        assertThat(result.get(0)).isEqualTo(cache);
    }

    @Test
    public void askByCountry_returnCacheByCountry() {

        List<Cache> result = repository.findByCountry("Portugal");

        List<Cache> listInitial = new ArrayList<>();
        listInitial.add(cache);
        listInitial.add(cache1);
        listInitial.add(cache3);

        assertThat(result).isEqualTo(listInitial);
    }

    @Test
    public void askByCityAndCountry_returnCacheByCityAndCountry() {

        List<Cache> result = repository.findByCityAndCountry("Aveiro", "Portugal");

        assertThat(result.get(0)).isEqualTo(cache);

        List<Cache> result1 = repository.findByCityAndCountry("Madrid", "Spain");

        assertThat(result1.get(0)).isEqualTo(cache2);

    }

    @Test
    public void askAll_returnAllTheCache(){
        List<Cache> result = repository.findAll();

        List<Cache> listInitial = new ArrayList<>();
        listInitial.add(cache);
        listInitial.add(cache1);
        listInitial.add(cache2);
        listInitial.add(cache3);

        assertThat(result).isEqualTo(listInitial);
    }

}
