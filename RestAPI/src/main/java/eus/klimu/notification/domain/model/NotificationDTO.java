package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO implements Serializable {

    private Long id;
    private String message;
    private Date date;
    private Long notificationTypeId;
    private Long locationId;

}
