package eus.klimu.location.domain.service.definition;

import eus.klimu.location.domain.model.Location;

import java.util.List;

/**
 * Definition of the different functions available for a location.
 */
public interface LocationService {

    /**
     * Count all the locations.
     * @return The number of locations on the database.
     */
    Long countAll();

    /**
     * Get all the locations.
     * @return A list with all the locations.
     */
    List<Location> getAllLocations();

    /**
     * Get a location based on its ID.
     * @param id The ID of the location.
     * @return The location with the ID, if found.
     */
    Location getLocationById(long id);

    /**
     * Get a location based on its city.
     * @param city The city of the location.
     * @return The location with the city, if found.
     */
    Location getLocationByCity(String city);

    /**
     * Get a location based on its country.
     * @param country The country of the location.
     * @return The location with the country, if found.
     */
    Location getLocationByCountry(String country);

    /**
     * Get a location based on its city and its country.
     * @param city The city of the location.
     * @param country The country of the location.
     * @return The location with the city and the country, if found.
     */
    Location getLocationByCityAndCountry(String city, String country);

    /**
     * Add a new location.
     * @param location The location that is going to be added.
     * @return The location after being saved, with a new ID.
     */
    Location addNewLocation(Location location);

    /**
     * Add a full list of locations.
     * @param locations The list of locations that are going to be added.
     * @return The list of locations after being saved, with a new ID.
     */
    List<Location> addAllLocations(List<Location> locations);

    /**
     * Update a location.
     * @param location The locations that is going to be updated.
     * @return The location after being updated.
     */
    Location updateLocation(Location location);

    /**
     * Delete a location.
     * @param location The location that is going to be deleted.
     */
    void deleteLocation(Location location);

}
