package eus.klimu.locations.domain.service.definition;

import eus.klimu.locations.domain.model.Location;

public interface LocationService {

    Location saveLocation(Location location);
    Location getLocationByID(Long locationID);
}
