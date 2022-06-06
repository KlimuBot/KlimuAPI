package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class NotificationDTO implements Serializable {

    private Long id;
    private String message;
    private String date;
    private Long notificationTypeId;
    private Long locationId;

}
