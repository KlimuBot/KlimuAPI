package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The Data Transfer Object for the notification type object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class NotificationTypeDTO implements Serializable {

    /**
     * The identification number of the notification type.
     */
    private Long id;
    /**
     * The name of the notification type.
     */
    private String name;
    /**
     * The description of the notification type.
     */
    private String description;
    /**
     * The type of the notification type.
     */
    private String type;

}
