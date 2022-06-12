package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Data Transfer Object for the localized notification object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class LocalizedNotificationDTO {

    /**
     * The identification number of the localized notification.
     */
    private Long id;
    /**
     * The identification number of the notification type.
     */
    private Long notificationTypeId;
    /**
     * The identification number of the location.
     */
    private Long locationId;

}
