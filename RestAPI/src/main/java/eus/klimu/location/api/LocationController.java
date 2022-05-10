package eus.klimu.location.api;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping(
            value = "/city/{city}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> getLocationByCity(@PathVariable String city) {
        return ResponseEntity.ok().body(locationService.getLocationByCity(city));
    }

    @GetMapping(
            value = "/country/{country}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> getLocationByCountry(@PathVariable String country) {
        return ResponseEntity.ok().body(locationService.getLocationByCountry(country));
    }

    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok().body(locationService.getAllLocations());
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        if (location != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(locationService.addNewLocation(location));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create-all",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<Location>> createAllLocations(@RequestBody List<Location> locations) {
        if (locations != null && locations.size() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(locationService.addAllLocations(locations));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> updateLocation(@RequestBody Location location) {
        if (location != null && locationService.getLocationById(location.getId()) != null) {
            return ResponseEntity.ok().body(locationService.updateLocation(location));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpStatus deleteLocationById(@PathVariable long id) {
        if (id > 0) {
            locationService.deleteLocation(locationService.getLocationById(id));
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(value = "/delete/city/{city}")
    public HttpStatus deleteLocationByCity(@PathVariable String city) {
        if (city != null) {
            locationService.deleteLocation(locationService.getLocationByCity(city));
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(value = "/delete/country/{country}")
    public HttpStatus deleteLocationByCountry(@PathVariable String country) {
        if (country != null) {
            locationService.deleteLocation(locationService.getLocationByCountry(country));
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping(value = "/delete/{city}/{country}")
    public HttpStatus deleteLocation(@PathVariable String city, @PathVariable String country) {
        if (city != null && country != null) {
            locationService.deleteLocation(locationService.getLocationByCityAndCountry(city, country));
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
