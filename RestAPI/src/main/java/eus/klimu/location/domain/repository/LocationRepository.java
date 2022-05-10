package eus.klimu.location.domain.repository;

import eus.klimu.location.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findById(long id);
    Location findByCity(String city);
    Location findByCountry(String country);
    Location findByCityAndCountry(String city, String country);

}
