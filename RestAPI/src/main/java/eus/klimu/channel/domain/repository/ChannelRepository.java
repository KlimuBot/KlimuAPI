package eus.klimu.channel.domain.repository;

import eus.klimu.channel.domain.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findChannelByName(String name);

}
