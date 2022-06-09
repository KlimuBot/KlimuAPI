package eus.klimu.location.domain.repository;

import eus.klimu.location.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access to the database for the locations.
 * Extends from a JpaRepository.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find a location based on its ID.
     * @param id The ID of the location.
     * @return A location if found.
     */
    Location findById(long id);

    /**
     * Find a location based on its city.
     * @param city The city of the location.
     * @return A location if found.
     */
    Location findByCity(String city);

    /**
     * Find a location based on its country.
     * @param country The country of the location.
     * @return A location if found.
     */
    Location findByCountry(String country);

    /**
     * Find a location based on its city and country.
     * @param city The city of the location.
     * @param country The country of the location.
     * @return A location if found.
     */
    Location findByCityAndCountry(String city, String country);

}
