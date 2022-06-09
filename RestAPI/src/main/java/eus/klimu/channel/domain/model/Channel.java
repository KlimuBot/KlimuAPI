package eus.klimu.channel.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A channel represents a type of communication system that the service uses for sending the notifications.
 * The channels default to: Telegram, Email and Desktop.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "channel")
public class Channel {

    /**
     * The identification number of the channel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The name of the channel.
     */
    @Column(name = "name", unique = true)
    private String name;
    /**
     * The icon of the channel.
     */
    private String icon;

    /**
     * Transform a ChannelDTO to a Channel object.
     * @param channelDTO The channel Data Transfer Object that is going to be transformed.
     * @return The transformed channel object.
     */
    public static Channel generateChannel(ChannelDTO channelDTO) {
        return new Channel(channelDTO.getId(), channelDTO.getName(), channelDTO.getIcon());
    }

}
