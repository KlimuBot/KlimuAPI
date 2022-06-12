package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

/**
 * The Data Transfer Object for the user notification object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class UserNotificationDTO implements Serializable {

    /**
     * The identification number of the user notification.
     */
    private Long id;
    /**
     * The identification number of the channel of the user notification.
     */
    private Long channelId;
    /**
     * A list with the identification numbers of the localized notifications
     * of the user notification.
     */
    private Collection<Long> localizedNotifications;

}
