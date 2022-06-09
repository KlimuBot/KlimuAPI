package eus.klimu.channel.domain.service.implementation;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.repository.ChannelRepository;
import eus.klimu.channel.domain.service.definition.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The implementation of the channel service. It interacts with the different channels using a channel repository,
 * modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelServiceImp implements ChannelService {

    /**
     * The connection with the channel table on the database.
     */
    private final ChannelRepository channelRepository;

    /**
     * Get the count from the database.
     * @return The channel count.
     */
    @Override
    public Long countAll() {
        long count = channelRepository.count();
        log.info("Found {} channels on the database", count);
        return count;
    }

    /**
     * Get the channel from the database, making use of its id.
     * @param id The ID of the channel.
     * @return The channel with that ID if found, or a null if not.
     */
    @Override
    public Channel getChannel(long id) {
        log.info("Fetching channel with id={}", id);
        return channelRepository.findById(id).orElse(null);
    }

    /**
     * Get a channel from the database, making use of its name.
     * @param name The name of the channel.
     * @return The channel with that name if found, or null if not.
     */
    @Override
    public Channel getChannel(String name) {
        log.info("Fetching channel with name={}", name);
        return channelRepository.findChannelByName(name).orElse(null);
    }

    /**
     * Get all the channels from the database.
     * @return All the channels on the database.
     */
    @Override
    public List<Channel> getAllChannels() {
        log.info("Fetching all channels from database");
        return channelRepository.findAll();
    }

    /**
     * Add a new channel on the database.
     * @param channel The channel that is going to be saved.
     * @return The channel after being saved, with the ID it has on the database.
     */
    @Override
    public Channel addNewChannel(Channel channel) {
        log.info("Saving new channel {}", channel);
        return channelRepository.save(channel);
    }

    /**
     * Add a list of channels on the database.
     * @param channels The list of channels that are going to be saved.
     * @return A list with all the created channels, each one with a new ID.
     */
    @Override
    public List<Channel> addAllChannels(List<Channel> channels) {
        log.info("Saving {} channel(s) on the database", channels.size());
        return channelRepository.saveAll(channels);
    }

    /**
     * Update a channel on the database.
     * @param channel The channel that is going to be updated.
     * @return The channel after being updated.
     */
    @Override
    public Channel updateChannel(Channel channel) {
        log.info("Updating channel with id={}", channel.getId());
        return channelRepository.save(channel);
    }

    /**
     * Delete a channel from the database.
     * @param channel The channel that is going to be deleted.
     */
    @Override
    public void deleteChannel(Channel channel) {
        log.info("Deleting channel with id={}", channel.getId());
        channelRepository.delete(channel);
    }
}
