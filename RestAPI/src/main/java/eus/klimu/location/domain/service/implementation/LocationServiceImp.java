package eus.klimu.location.domain.service.implementation;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.repository.LocationRepository;
import eus.klimu.location.domain.service.definition.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImp implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        log.info("Fetching all locations");
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(long id) {
        log.info("Fetching location with id={}", id);
        return locationRepository.findById(id);
    }

    @Override
    public Location getLocationByCity(String city) {
        log.info("Fetching city with name={}", city);
        return locationRepository.findByCity(city);
    }

    @Override
    public Location getLocationByCountry(String country) {
        log.info("Fetching country with name={}", country);
        return locationRepository.findByCountry(country);
    }

    @Override
    public Location getLocationByCityAndCountry(String city, String country) {
        log.info("Fetching city with name={} and country with name={}", city, country);
        return locationRepository.findByCityAndCountry(city, country);
    }

    @Override
    public Location addNewLocation(Location location) {
        log.info("Saving location ({}) on the database", location.toString());
        return locationRepository.save(location);
    }

    @Override
    public List<Location> addAllLocations(List<Location> locations) {
        log.info("Saving {} location(s)", locations.size());
        return locationRepository.saveAll(locations);
    }

    @Override
    public Location updateLocation(Location location) {
        log.info("Updating location with id={}", location.getId());
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Location location) {
        log.info("Deleting location with id={}", location.getId());
        locationRepository.delete(location);
    }
}
