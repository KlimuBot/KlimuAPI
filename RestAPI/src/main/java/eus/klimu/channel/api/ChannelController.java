package eus.klimu.channel.api;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.service.definition.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> getChannelById(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(channelService.getChannel(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/{name}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> getChannelByName(@PathVariable String name) {
        if (name != null) {
            return ResponseEntity.ok().body(channelService.getChannel(name));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Channel>> getAllChannels() {
        return ResponseEntity.ok().body(channelService.getAllChannels());
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) {
        if (channel != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/channel/create").toUriString())
            ).body(channelService.addNewChannel(channel));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Channel>> createAllLocations(@RequestBody List<Channel> channels) {
        if (channels != null && channels.size() > 0) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/channel/create/all").toUriString())
            ).body(channelService.addAllChannels(channels));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> updateLocation(@RequestBody Channel channel) {
        if (channel != null && channelService.getChannel(channel.getId()) != null) {
            return ResponseEntity.ok().body(channelService.updateChannel(channel));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteLocationById(@PathVariable long id) {
        if (id > 0) {
            channelService.deleteChannel(channelService.getChannel(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteLocation(@RequestBody Channel channel) {
        if (channel != null) {
            channelService.deleteChannel(channel);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
