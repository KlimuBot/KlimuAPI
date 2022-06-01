package eus.klimu.notification.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class UserNotificationDTO implements Serializable {

    private Long id;
    private Long channelId;
    private Collection<Long> localizedNotifications;

}
