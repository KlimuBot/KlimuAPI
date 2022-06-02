package eus.klimu.channel.api;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.model.ChannelDTO;
import eus.klimu.channel.domain.service.definition.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Create, update, delete and get information for the different communication channels that the service is going to be
 * provided on. Takes care of all the CRUD methods, working with the database through an internal Service.
 */
@Controller
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    /**
     * <h1>GET REQUEST</h1>
     * <h2>Get a Channel by ID</h2>
     *
     * <p>Get a channel based on it's ID.</p>
     *
     * <p><a href='https://klimu.eus/RestAPI/channel/{id}'>https://klimu.eus/RestAPI/channel/{id}</a></p>
     *
     * <ul>
     *     <li>Consumes: text/plain</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the channel the function is going to look for.
     * @return A 200 ok with the channel as a JSON if found or a 4000 bad request if it wasn't.
     */
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

    /**
     * <h1>GET REQUEST</h1>
     * <h2>Get a Channel by it's name</h2>
     *
     * <p>Get a channel based on it's name.</p>
     *
     * <ul>
     *     <li>Consumes: text/plain</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href='https://klimu.eus/RestAPI/channel/name/{name}'>https://klimu.eus/RestAPI/channel/name/{name}</a></p>
     *
     * @param name The name of the channel the function is looking for.
     * @return A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.
     */
    @GetMapping(
            value = "/name/{name}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> getChannelByName(@PathVariable String name) {
        if (name != null) {
            return ResponseEntity.ok().body(channelService.getChannel(name));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <h1>GET REQUEST</h1>
     * <h2>Get all the Channels</h2>
     *
     * <p>Get all the Channels from the database.</p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href='https://klimu.eus/RestAPI/channel/all'>https://klimu.eus/RestAPI/channel/all</a></p>
     *
     * @return A list with all the channels from the database.
     */
    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Channel>> getAllChannels() {
        return ResponseEntity.ok().body(channelService.getAllChannels());
    }

    /**
     * <h1>POST REQUEST</h1>
     * <h2>Create a new Channel</h2>
     *
     * <p>Add a new Channel to the database, once it's added, an ID will be added to that Channel.</p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href="https://klimu.eus/RestAPI/channel/create">https://klimu.eus/RestAPI/channel/create</a></p>
     *
     * @param channel A Data Transfer Object for the channel that is going to be created.
     * @return A 200 ok with the channel as a JSON if created or a 400 bad request if it wasn't.
     */
    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> createChannel(@RequestBody ChannelDTO channel) {
        if (channel != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/channel/create").toUriString())
            ).body(channelService.addNewChannel(Channel.generateChannel(channel)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <h1>POST REQUEST</h1>
     * <h2>Create an X amount of Channels</h2>
     *
     * <p>Add an X amount of new Channels to the database, once they are added, an ID will be added
     * to those Channels.</p>
     *
     * <ul>
     *     <li>Consumes: application/json</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href="https://klimu.eus/RestAPI/channel/create/all">https://klimu.eus/RestAPI/channel/create/all</a></p>
     *
     * @param channels A list of Data Transfer Objects for the channels that are going to be created.
     * @return A 200 ok with the channels as a JSON if created or a 400 bad request if they weren't.
     */
    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Channel>> createAllLocations(@RequestBody List<ChannelDTO> channels) {
        if (channels != null && !channels.isEmpty()) {
            List<Channel> persistentChannels = new ArrayList<>();
            channels.forEach(c -> persistentChannels.add(Channel.generateChannel(c)));

            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/channel/create/all").toUriString())
            ).body(channelService.addAllChannels(persistentChannels));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <h1>PUT REQUEST</h1>
     * <h2>Update a channel</h2>
     *
     * <p>Modify an existing channel on the database.</p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href="https://klimu.eus/RestAPI/channel/create/update">https://klimu.eus/RestAPI/channel/create/update</a></p>
     *
     * @param channel The channel that is going to be updated.
     * @return A 200 ok with the channel as a JSON if updated or a 400 bad request if it wasn't.
     */
    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Channel> updateLocation(@RequestBody ChannelDTO channel) {
        if (channel != null && channelService.getChannel(channel.getId()) != null) {
            return ResponseEntity.ok().body(channelService.updateChannel(Channel.generateChannel(channel)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <h1>DELETE REQUEST</h1>
     * <h2>Delete a Channel by ID</h2>
     *
     * <p>Delete a channel from the database based on it's ID.</p>
     *
     * <ul>
     *     <li>Consumes: text/plain</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href="https://klimu.eus/RestAPI/channel/delete/{id}">https://klimu.eus/RestAPI/channel/delete/{id}</a></p>
     *
     * @param id The id of the channel that is going to be deleted
     * @return A 200 ok with the channels was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteLocationById(@PathVariable long id) {
        if (id > 0) {
            channelService.deleteChannel(channelService.getChannel(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <h1>DELETE REQUEST</h1>
     * <h2>Delete a Channel</h2>
     *
     * <p>Delete a channel from the database.</p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href="https://klimu.eus/RestAPI/channel/delete">https://klimu.eus/RestAPI/channel/delete</a></p>
     *
     * @param channel A Channel Data Transfer Object for the channel that is going to be deleted.
     * @return A 200 ok with the channels was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteLocation(@RequestBody ChannelDTO channel) {
        if (channel != null && channelService.getChannel(channel.getId()) != null) {
            channelService.deleteChannel(Channel.generateChannel(channel));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
