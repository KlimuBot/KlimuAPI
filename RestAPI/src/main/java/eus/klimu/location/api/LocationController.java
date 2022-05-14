package eus.klimu.location.api;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.model.LocationDTO;
import eus.klimu.location.domain.service.definition.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> getLocationByCity(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(locationService.getLocationById(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/city/{city}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> getLocationByCity(@PathVariable String city) {
        if (city != null) {
            return ResponseEntity.ok().body(locationService.getLocationByCity(city));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/country/{country}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> getLocationByCountry(@PathVariable String country) {
        if (country != null) {
            return ResponseEntity.ok().body(locationService.getLocationByCountry(country));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/{city}/{country}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> getLocationByCityAndCountry(
            @PathVariable String city,
            @PathVariable String country
    ) {
        if (country != null) {
            return ResponseEntity.ok().body(locationService.getLocationByCityAndCountry(city, country));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok().body(locationService.getAllLocations());
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> createLocation(@RequestBody LocationDTO location) {
        if (location != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/location/create").toUriString())
            ).body(locationService.addNewLocation(Location.generateLocation(location)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Location>> createAllLocations(@RequestBody List<LocationDTO> locations) {
        if (locations != null && !locations.isEmpty()) {
            List<Location> persistentLocations = new ArrayList<>();
            locations.forEach(l -> persistentLocations.add(new Location(l.getId(), l.getCity(), l.getCountry())));

            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/location/create/all").toUriString())
            ).body(locationService.addAllLocations(persistentLocations));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Location> updateLocation(@RequestBody LocationDTO location) {
        if (location != null && locationService.getLocationById(location.getId()) != null) {
            return ResponseEntity.ok().body(locationService.updateLocation(Location.generateLocation(location)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteLocationById(@PathVariable long id) {
        if (id > 0) {
            locationService.deleteLocation(locationService.getLocationById(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/city/{city}")
    public ResponseEntity<Object> deleteLocationByCity(@PathVariable String city) {
        if (city != null) {
            locationService.deleteLocation(locationService.getLocationByCity(city));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/country/{country}")
    public ResponseEntity<Object> deleteLocationByCountry(@PathVariable String country) {
        if (country != null) {
            locationService.deleteLocation(locationService.getLocationByCountry(country));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{city}/{country}")
    public ResponseEntity<Object> deleteLocation(@PathVariable String city, @PathVariable String country) {
        if (city != null && country != null) {
            locationService.deleteLocation(locationService.getLocationByCityAndCountry(city, country));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteLocation(@RequestBody LocationDTO location) {
        if (location != null) {
            locationService.deleteLocation(Location.generateLocation(location));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
