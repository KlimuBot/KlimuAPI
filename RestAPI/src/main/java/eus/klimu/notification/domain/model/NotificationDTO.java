package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The Data Transfer Object for the notification object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class NotificationDTO implements Serializable {

    /**
     * The identification number of the notification.
     */
    private Long id;
    /**
     * The message of the notification.
     */
    private String message;
    /**
     * The date when the notification was created.
     */
    private String date;
    /**
     * The identification number of the type of the notification.
     */
    private Long notificationTypeId;
    /**
     * The identification number of the location the notification was generated on.
     */
    private Long locationId;

}
