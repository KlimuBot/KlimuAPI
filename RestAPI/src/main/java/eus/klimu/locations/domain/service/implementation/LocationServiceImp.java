package eus.klimu.locations.domain.service.implementation;

import eus.klimu.locations.domain.model.Location;
import eus.klimu.locations.domain.repository.LocationRepository;
import eus.klimu.locations.domain.service.definition.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImp implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location saveLocation(Location location) {
        log.info("Saving location {} on the database.", location.getLocationID());
        return locationRepository.save(location);
    }

    @Override
    public Location getLocationByID(Long locationID) {
        return locationRepository.findById(locationID).orElse(null);
    }

    public List<Location> createLocationList(List<Location> list) {
        return locationRepository.saveAll(list);
    }

    public List<Location> getLocationList() {
        return locationRepository.findAll();
    }

    public Location updateLocationById(Location location) {
        Optional<Location> locationFound = locationRepository.findById(location.getLocationID());

        if (locationFound.isPresent()) {
            Location locationUpdate = locationFound.get();
            locationUpdate.setLocationID(location.getLocationID());
            locationUpdate.setCiudad(location.getCiudad());
            locationUpdate.setPais(location.getPais());
            locationUpdate.setDifHoraria(location.getDifHoraria());

            return locationRepository.(location);
        } else {
            return null;
        }
    }

    public String deleteLocationById(long id) {
        locationRepository.deleteById(id);
        return "Location "+ id +" deleted";
    }
}
