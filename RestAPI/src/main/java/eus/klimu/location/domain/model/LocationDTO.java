package eus.klimu.location.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO implements Serializable {

    private long id;
    private String city;
    private String country;

}
