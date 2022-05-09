package eus.klimu.locations.domain.repository;

import eus.klimu.locations.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationID(Long locationID);
}
