package eus.klimu.channel.domain.service.implementation;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.repository.ChannelRepository;
import eus.klimu.channel.domain.service.definition.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChannelServiceImp implements ChannelService {

    private final ChannelRepository channelRepository;

    @Override
    public Channel getChannel(long id) {
        log.info("Fetching channel with id={}", id);
        return channelRepository.findById(id).orElse(null);
    }

    @Override
    public Channel getChannel(String name) {
        log.info("Fetching channel with name={}", name);
        return channelRepository.findChannelByName(name).orElse(null);
    }

    @Override
    public List<Channel> getAllChannels() {
        log.info("Fetching all channels from database");
        return channelRepository.findAll();
    }

    @Override
    public Channel addNewChannel(Channel channel) {
        log.info("Saving new channel {}", channel);
        return channelRepository.save(channel);
    }

    @Override
    public List<Channel> addAllChannels(List<Channel> channels) {
        log.info("Saving {} channel(s) on the database", channels.size());
        return channelRepository.saveAll(channels);
    }

    @Override
    public Channel updateChannel(Channel channel) {
        log.info("Updating channel with id={}", channel.getId());
        return channelRepository.save(channel);
    }

    @Override
    public void deleteChannel(Channel channel) {
        log.info("Deleting channel with id={}", channel.getId());
        channelRepository.delete(channel);
    }
}
