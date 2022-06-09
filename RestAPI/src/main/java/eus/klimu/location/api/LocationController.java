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

/**
 * Create, update, delete and get information for the Notifications. They represent the different places the
 * notifications can come from. Takes care of all the CRUD methods, working with the database through an internal
 * Service.
 */
@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    /**
     * A class that allows modifying the different locations.
     */
    private final LocationService locationService;

    /**
     * <p>Get a location by its ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/{id}">https://klimu.eus/RestAPI/location/{id}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The id of the location to look for.
     * @return A 200 ok if the location was found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get a location by the name of the city.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/city/{city}">https://klimu.eus/RestAPI/location/city/{city}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param city The name of the city to look for.
     * @return A 200 ok if the location was found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get a location by the name of the country.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/country/{country}">https://klimu.eus/RestAPI/location/country/{country}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param country The name of the country to look for.
     * @return A 200 ok if the location was found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get a location by the name of the city and the country.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/{city}/{country}">https://klimu.eus/RestAPI/location/{city}/{country}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param city The name of the city to look for.
     * @param country The name of the country to look for.
     * @return A 200 ok if the location was found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get all the different locations from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/all">https://klimu.eus/RestAPI/location/all</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @return A 200 ok with all the different locations.
     */
    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok().body(locationService.getAllLocations());
    }

    /**
     * <p>Save a new location on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/create">https://klimu.eus/RestAPI/location/create</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param location The location that is going to be stored.
     * @return A 200 ok if the location was saved on the database or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Save an X amount of locations on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/create/all">https://klimu.eus/RestAPI/location/create/all</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param locations The locations that are going to be stored.
     * @return A 200 ok if the locations were saved on the database or a 400 bad request if it wasn't.
     */
    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Location>> createAllLocations(@RequestBody List<LocationDTO> locations) {
        if (locations != null && !locations.isEmpty()) {
            List<Location> persistentLocations = new ArrayList<>();
            locations.forEach(l -> persistentLocations.add(Location.generateLocation(l)));

            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/location/create/all").toUriString())
            ).body(locationService.addAllLocations(persistentLocations));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Update a location on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/update">https://klimu.eus/RestAPI/location/update</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param location The location that is going to be updated.
     * @return A 200 ok if the location was updated or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Delete a location based on it's ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/delete/{id}">https://klimu.eus/RestAPI/location/delete/{id}</a></p>
     *
     * <ul>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the location that is going to be deleted.
     * @return A 200 ok if the location was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteLocationById(@PathVariable long id) {
        if (id > 0) {
            locationService.deleteLocation(locationService.getLocationById(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a location based on the name of the city.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/delete/city/{city}">https://klimu.eus/RestAPI/location/delete/city/{city}</a></p>
     *
     * <ul>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param city The name of the city of the location that is going to be deleted.
     * @return A 200 ok if the location was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/city/{city}")
    public ResponseEntity<Object> deleteLocationByCity(@PathVariable String city) {
        if (city != null) {
            locationService.deleteLocation(locationService.getLocationByCity(city));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a location based on the name of the city.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/delete/country/{country}">https://klimu.eus/RestAPI/location/delete/country/{country}</a></p>
     *
     * <ul>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param country The name of the country of the location that is going to be deleted.
     * @return A 200 ok if the location was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/country/{country}")
    public ResponseEntity<Object> deleteLocationByCountry(@PathVariable String country) {
        if (country != null) {
            locationService.deleteLocation(locationService.getLocationByCountry(country));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a location based on the name of the city and the country.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/delete/{city}/{country}">https://klimu.eus/RestAPI/location/delete/{city}/{country}</a></p>
     *
     * <ul>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param city The name of the city of the location that is going to be deleted.
     * @param country The name of the country of the location that is going to be deleted.
     * @return A 200 ok if the location was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/{city}/{country}")
    public ResponseEntity<Object> deleteLocation(@PathVariable String city, @PathVariable String country) {
        if (city != null && country != null) {
            locationService.deleteLocation(locationService.getLocationByCityAndCountry(city, country));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a location from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/location/delete">https://klimu.eus/RestAPI/location/delete</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param location The location that is going to be deleted.
     * @return A 200 ok if the location was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteLocation(@RequestBody LocationDTO location) {
        if (location != null && locationService.getLocationById(location.getId()) != null) {
            locationService.deleteLocation(Location.generateLocation(location));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
