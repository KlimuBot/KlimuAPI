package eus.klimu.location.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String country;

    public static Location generateLocation(LocationDTO locationDTO) {
        return new Location(locationDTO.getId(), locationDTO.getCity(), locationDTO.getCountry());
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}
