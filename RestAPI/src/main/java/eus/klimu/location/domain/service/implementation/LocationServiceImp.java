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
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location getLocationByCity(String city) {
        return locationRepository.findByCity(city);
    }

    @Override
    public Location getLocationByCountry(String country) {
        return locationRepository.findByCountry(country);
    }

    @Override
    public Location getLocationByCityAndCountry(String city, String country) {
        return locationRepository.findByCityAndCountry(city, country);
    }

    @Override
    public Location addNewLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> addAllLocations(List<Location> locations) {
        return locationRepository.saveAll(locations);
    }

    @Override
    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Location location) {
        locationRepository.delete(location);
    }
}
