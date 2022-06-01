package eus.klimu.channel.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@XmlRootElement
@AllArgsConstructor
public class ChannelDTO implements Serializable {

    private Long id;
    private String name;
    private String icon;

}
