package eus.klimu.channel.domain.service.definition;

import eus.klimu.channel.domain.model.Channel;

import java.util.List;

/**
 * Definition of the different functions available for a channel.
 */
public interface ChannelService {

    /**
     * Count all the channels.
     * @return The number of channels on the database.
     */
    Long countAll();

    /**
     * Get a channel using its ID.
     * @param id The ID of the channel.
     * @return The channel with that ID, if found.
     */
    Channel getChannel(long id);

    /**
     * Get a channel using its name.
     * @param name The name of the channel.
     * @return The channel with that name, if found.
     */
    Channel getChannel(String name);

    /**
     * Get all the channels.
     * @return A list with all the channels found.
     */
    List<Channel> getAllChannels();

    /**
     * Add a new channel.
     * @param channel The channel that is going to be saved.
     * @return The channel after being saved, with a new ID.
     */
    Channel addNewChannel(Channel channel);

    /**
     * Add a full list of channels.
     * @param channels The list of channels that are going to be saved.
     * @return The list of channels after being saved, with a new ID.
     */
    List<Channel> addAllChannels(List<Channel> channels);

    /**
     * Update a channel.
     * @param channel The channel that is going to be updated.
     * @return The channel after being updated.
     */
    Channel updateChannel(Channel channel);

    /**
     * Delete a channel.
     * @param channel The channel that is going to be deleted.
     */
    void deleteChannel(Channel channel);

}
