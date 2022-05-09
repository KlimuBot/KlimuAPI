package eus.klimu.locations.api;

import eus.klimu.locations.domain.model.Location;
import eus.klimu.locations.domain.service.definition.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{locationID}")
    public ResponseEntity<Location> getLocation(@PathVariable Long locationID) {
        return ResponseEntity.ok().body(locationService.getLocationByID(locationID));
    }

    @PostMapping("/save")
    public ResponseEntity<Location> saveLocation(@RequestBody Location location) {
        Location newLocation = locationService.saveLocation(location);
        if (location != null) {
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/location/save").toUriString())
            ).body(newLocation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
