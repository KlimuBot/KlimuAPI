package eus.klimu.channel.domain.repository;

import eus.klimu.channel.domain.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Access to the database for the channels.
 * Extends from a JpaRepository.
 */
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    /**
     * Find a channel based on its name.
     * @param name The name of the channel the function is looking for.
     * @return An optional value that may contain the channel if it was found.
     */
    Optional<Channel> findChannelByName(String name);

}
