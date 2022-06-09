package eus.klimu.channel.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The Data Transfer Object for the channel object.
 */
@Getter
@Setter
@XmlRootElement
@AllArgsConstructor
public class ChannelDTO implements Serializable {

    /**
     * The identification number of the channel.
     */
    private Long id;
    /**
     * The name of the channel.
     */
    private String name;
    /**
     * The icon of the channel.
     */
    private String icon;

}
