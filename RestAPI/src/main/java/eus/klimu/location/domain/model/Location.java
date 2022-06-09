package eus.klimu.location.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A place on the globe that allows the users to configure notifications for that place.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "location")
public class Location {

    /**
     * The identification number of the location.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The city of the location.
     */
    private String city;
    /**
     * The country of the location.
     */
    private String country;

    /**
     * Transform a LocationDTO to a Location object.
     * @param locationDTO The location Data Transfer Object that is going to be transformed.
     * @return The transformed location object.
     */
    public static Location generateLocation(LocationDTO locationDTO) {
        return new Location(locationDTO.getId(), locationDTO.getCity(), locationDTO.getCountry());
    }

    /**
     * Transform a Location to a String.
     * @return The location as following: city, country
     */
    @Override
    public String toString() {
        return city + ", " + country;
    }
}
