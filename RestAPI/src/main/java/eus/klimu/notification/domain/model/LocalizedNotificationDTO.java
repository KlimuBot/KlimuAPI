package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class LocalizedNotificationDTO {

    private Long id;
    private Long notificationTypeId;
    private Long locationId;

}
