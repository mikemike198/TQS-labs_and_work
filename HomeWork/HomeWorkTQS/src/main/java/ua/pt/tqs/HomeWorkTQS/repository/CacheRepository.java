package ua.pt.tqs.HomeWorkTQS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;

import java.time.LocalDateTime;
import java.util.List;

public interface CacheRepository extends JpaRepository<Cache, Long> {

    List<Cache> findByCity(String city);
    List<Cache> findByCountry(String country);
    List<Cache> findByCityAndCountry(String city, String country);
    Cache findById(long id);

}
