# KlimuAPI

REST petition management service for the different elements of the KLIMU system. This application manages the authentication of all other applications in the KLIMU system. It also manages the access to the database.

This service can be found on the main server for the KLIMU system, located under the domain https://klimu.eus/RestAPI/. Most of the methods of this service require the user to be logged on the application.

The application uses _JSON Web Tokens_ for the authentication, so if any request requires them, they must be sent on the headers as **accessToken** and **refreshToken**.

The following methods can be found on the API:

- [Access Request Methods](#access)
- [Channel Request Methods](#channel)
- [LocalizedNotification Request Methods](#local_notification)
- [Location Request Methods](#location)
- [Notification Request Methods](#notification)
- [NotificationType Request Methods](#type_notification)
- [Role Request Methods](#role)
- [User Request Methods](#user)
- [UserNotification Request Methods](#user_notification)

<h2 id="access">1. AccessController</h2>

Manage the user access to the server, providing _Json Web Tokens_ to those users that have already been registered on the database. It also provides different methods to manage the access to the services.

### 1.1 GET REQUEST - Authenticate User Token

---

    Get a UsernamePasswordToken from a JWT token. Transforms the UsernamePasswordToken into a JSON string. The token must be set appended to the URL.

<ul>
    <li>Consumes: text/plain</li>
    <li>Produces: text/plain</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/access/auth/{token}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Token</td>
        <td>The JWT token that is going to be used for getting the UsernamePasswordToken.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>String</td>
        <td>A 200 ok with the UsernamePasswordToken if everything went well. If not, returns a 400 bad request.</td>
    </tr>
</table>

<br>

### 1.2 GET REQUEST - Refresh User Tokens

---

    Get a new pack of tokens (accessToken and refreshToken) from the server as a JSON. Takes the access and a refresh token from the headers. One of the tokens need to not be expired for it to work. Content must be sent as RequestBody.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as header.</li>
</ul>

> https://klimu.eus/RestAPI/access/refresh

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>request</td>
        <td>The HttpServletRequest made to the server.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Map</td>
        <td>A 200 ok with a json that contains the access and the refresh token. If not, returns a 400 bad request.</td>
    </tr>
</table>

<br>

### 1.3 GET REQUEST - Deny Access

---

    Obtains an error message as a JSON with a DENIED type of response.

> https://klimu.eus/RestAPI/access/denied

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>String</td>
        <td>A 403 error with a message explaining that the user has no authorities.</td>
    </tr>
</table>

<br>

### 1.4 POST REQUEST - Login

---

    Log into the application using a username and a password and get the access and refresh token. Content must be sent as Form-Data.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json</li>
</ul>

> https://klimu.eus/RestAPI/login

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>username</td>
        <td>The username for the authentication.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>password</td>
        <td>The password for the authentication.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>void</td>
        <td>A 200 successful or a 403 forbbiden.</td>
    </tr>
</table>

<br>

<h2 id="channel">2. ChannelController</h2>

Create, update, delete and get information for the different communication channels that the service is going to be provided on. Takes care of all the CRUD methods, working with the database through an internal Service.

### 2.1 GET REQUEST - Get Channel by ID

---

    Get a channel based on it's ID.

<ul>
    <li>Consumes: text/plain</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>id</td>
        <td>The ID of the channel the function is going to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the channel as a JSON if found or a 4000 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 2.2 GET REQUEST - Get Channel by name

---

    Get a channel based on it's name.

<ul>
    <li>Consumes: text/plain</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/name/{name}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>name</td>
        <td>The name of the channel the function is going to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 2.3 GET REQUEST - Get all Channels

---

    Get all the Channels from the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok with a list of channels as a JSON if found or a 400 bad request if they weren't.</td>
    </tr>
</table>

<br>

### 2.4 POST REQUEST - Create a new Channel

---

    Add a new Channel to the database, once it's added, an ID will be added to that Channel.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channel</td>
        <td>A Data Transfer Object for the channel that is going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the channel as a JSON if created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 2.5 POST REQUEST - Create an X amount of Channels

---

    Add an X amount of new Channels to the database, once they are added, an ID will be added to those Channels.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A list of Data Transfer Objects for the channels that are going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok with the channels as a JSON if created or a 400 bad request if they weren't.</td>
    </tr>
</table>

<br>

### 2.6 PUT REQUEST - Update a Channel

---

    Modify an existing channel on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/create/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A list of Data Transfer Objects for the channels that are going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok with the channels as a JSON if updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 2.7 DELETE REQUEST - Delete a Channel by ID

---

    Delete a channel from the database based on it's ID.

<ul>
    <li>Consumes: text/plain</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>id</td>
        <td>The id of the channel that is going to be deleted</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok with the channels was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 2.8 DELETE REQUEST - Delete a Channel

---

    Delete a channel from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A ChannelDTO for the channel that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok with the channels was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h2 id="local_notification">3. LocalizedNotificationController</h2>

<h2 id="location">4. LocationController</h2>

<h2 id="notification">5. NotificationController</h2>

<h2 id="type_notification">6. NotificationTypeController</h2>

<h2 id="role">7. RoleController</h2>

<h2 id="user">8. UserController</h2>

<h2 id="user_notification">9. UserNotificationController</h2>