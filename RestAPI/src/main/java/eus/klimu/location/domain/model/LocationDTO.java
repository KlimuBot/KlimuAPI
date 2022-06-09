package eus.klimu.location.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The Data Transfer Object for the location object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class LocationDTO implements Serializable {

    /**
     * The identification number of the location.
     */
    private Long id;
    /**
     * The city of the location.
     */
    private String city;
    /**
     * The country of the location.
     */
    private String country;

}
