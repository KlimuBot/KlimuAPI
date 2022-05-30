package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UserNotificationDTO implements Serializable {

    private Long id;
    private Long channelId;
    private Collection<Long> localizedNotifications;

}
