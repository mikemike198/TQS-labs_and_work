package ua.pt.tqs.HomeWorkTQS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;
import ua.pt.tqs.HomeWorkTQS.repository.CacheRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class CacheRepositoryTest {

    @Autowired
    private CacheRepository repository;

    @Autowired
    private TestEntityManager manager;

    @Test
    public void getCache_returnCacheByName() {
        Cache cache = manager.persistAndFlush(
                new Cache("Aveiro",
                        "Aveiro",
                        "Portugal",
                        "{\"country\":\"Portugal\",\"current\":{\"weather\":{\"pr\":1020,\"ic\":\"01d\",\"tp\":20,\"ws\":0.95,\"hu\":57,\"wd\":243,\"ts\":\"2021-05-05T10:00:00.000Z\"},\"pollution\":{\"aqius\":29,\"maincn\":\"p1\",\"ts\":\"2021-05-05T08:00:00.000Z\",\"mainus\":\"p2\",\"aqicn\":12}},\"city\":\"Vila Real\",\"location\":{\"coordinates\":[-7.7908333333333335,41.37138888888889],\"type\":\"Point\"},\"state\":\"Vila Real\"}",
                        LocalDateTime.now(),
                        30));
        List<Cache> result = repository.findByCity("Aveiro");

        assertThat(result.get(0)).isEqualTo(cache);
    }
}
