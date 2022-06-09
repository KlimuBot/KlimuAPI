package eus.klimu.location.domain.service.implementation;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.repository.LocationRepository;
import eus.klimu.location.domain.service.definition.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The implementation of the location service. It interacts with the different channels using a channel repository,
 * modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImp implements LocationService {

    /**
     * The connection with the location table on the database.
     */
    private final LocationRepository locationRepository;

    /**
     * Count all the locations on the database.
     * @return The location count.
     */
    @Override
    public Long countAll() {
        long count = locationRepository.count();
        log.info("Found {} locations on the database", count);
        return count;
    }

    /**
     * Get a location from the database, making use of its ID.
     * @param id The ID of the location.
     * @return The location with that ID if found, or a null if not.
     */
    @Override
    public Location getLocationById(long id) {
        log.info("Fetching location with id={}", id);
        return locationRepository.findById(id);
    }

    /**
     * Get a location from the database, making use of its city.
     * @param city The city of the location.
     * @return The location with that city if found, or a null if not.
     */
    @Override
    public Location getLocationByCity(String city) {
        log.info("Fetching city with name={}", city);
        return locationRepository.findByCity(city);
    }

    /**
     * Get a location from the database, making use of its country.
     * @param country The country of the location.
     * @return The location with that country if found, or a null if not.
     */
    @Override
    public Location getLocationByCountry(String country) {
        log.info("Fetching country with name={}", country);
        return locationRepository.findByCountry(country);
    }

    /**
     * Get a location from the database, making use of its city and country.
     * @param city The city of the location.
     * @param country The country of the location.
     * @return The location with that city and country if found, or a null if not.
     */
    @Override
    public Location getLocationByCityAndCountry(String city, String country) {
        log.info("Fetching city with name={} and country with name={}", city, country);
        return locationRepository.findByCityAndCountry(city, country);
    }

    /**
     * Get all the locations from the database.
     * @return A list with all the locations.
     */
    @Override
    public List<Location> getAllLocations() {
        log.info("Fetching all locations");
        return locationRepository.findAll();
    }

    /**
     * Add a new location on the database.
     * @param location The location that is going to be added.
     * @return The location after being saved, with the ID it has on the database.
     */
    @Override
    public Location addNewLocation(Location location) {
        log.info("Saving location ({}) on the database", location.toString());
        return locationRepository.save(location);
    }

    /**
     * Add a list of locations on the database.
     * @param locations The list of locations that are going to be added.
     * @return A list with all the created locations, each one with a new ID.
     */
    @Override
    public List<Location> addAllLocations(List<Location> locations) {
        log.info("Saving {} location(s)", locations.size());
        return locationRepository.saveAll(locations);
    }

    /**
     * Update a location on the database.
     * @param location The locations that is going to be updated.
     * @return The location after being updated.
     */
    @Override
    public Location updateLocation(Location location) {
        log.info("Updating location with id={}", location.getId());
        return locationRepository.save(location);
    }

    /**
     * Delete a location from the database.
     * @param location The location that is going to be deleted.
     */
    @Override
    public void deleteLocation(Location location) {
        log.info("Deleting location with id={}", location.getId());
        locationRepository.delete(location);
    }
}
