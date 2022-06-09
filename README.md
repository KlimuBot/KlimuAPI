# KlimuAPI

REST petition management service for the different elements of the KLIMU system. This application manages the authentication of all other applications in the KLIMU system. It also manages the access to the database. Most of the methods of this service require the user to be logged on the application.

The application uses _JSON Web Tokens_ for the authentication, so if any request requires them, they must be sent on the headers as **accessToken** and **refreshToken**.

## Index

1. [Rest Application](#rest)
2. [Data Variables](#data)
3. [Application Authentication](#auth)

<h2 id="rest">Rest Application</h2>

---

The KLIMU rest application provides different ways to interact with the database, allowing multiple types of requests from any location. This service can be found on the main server for the KLIMU system, located under the domain https://klimu.eus/RestAPI/.

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

<h3 id="access">1. <b>AccessController</b></h3>

Manage the user access to the server, providing _Json Web Tokens_ to those users that have already been registered on the database. It also provides different methods to manage the access to the services.

#### 1.1 GET REQUEST - Authenticate User Token

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

#### 1.2 GET REQUEST - Refresh User Tokens

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

#### 1.3 GET REQUEST - Deny Access

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

#### 1.4 POST REQUEST - Login

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

<h3 id="channel">2. <b>ChannelController</b></h3>

Create, update, delete and get information for the different communication channels that the service is going to be provided on. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 2.1 GET REQUEST - Get Channel by ID

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
        <td>A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 2.2 GET REQUEST - Get Channel by name

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

#### 2.3 GET REQUEST - Get all Channels

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

#### 2.4 POST REQUEST - Create a new Channel

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
        <td>A 201 created with the channel as a JSON if created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 2.5 POST REQUEST - Create an X amount of Channels

---

    Save an X amount of new Channels to the database, once they are added, an ID will be added to those Channels.

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
        <td>A 201 created with the channels as a JSON if created or a 400 bad request if they weren't.</td>
    </tr>
</table>

<br>

#### 2.6 PUT REQUEST - Update a Channel

---

    Modify an existing channel on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/channel/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A Data Transfer Object for the channel that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok with the channels as a JSON if updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 2.7 DELETE REQUEST - Delete a Channel by ID

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
        <td>Response Entity</td>
        <td>A 200 ok if the channels was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 2.8 DELETE REQUEST - Delete a Channel

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
        <td>A Channel Data Transfer Object for the channel that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the channel was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="local_notification">3. <b>LocalizedNotificationController</b></h3>

Create, update, delete and get information for the Localized Notifications. They represent the different types of notification a user wants to receive through the system. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 3.1 GET REQUEST - Get a Localized Notification by ID

---

    Get a localized notification based on it's ID.

<ul>
    <li>Consumes: text/plain</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>id</td>
        <td>The ID of the localized notification the function is going to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the localized notification as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 3.2 GET REQUEST - Get a Localized Notification

---

    Get a localized notification based on it's location and it's notification type.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>location</td>
        <td>A Data Transfer Object of a location.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>notificationType</td>
        <td>A Data Transfer Object of a notification type.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the localized notification as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.3 GET REQUEST - Get all the Localized Notifications

---

    Get all the localized notifications from the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.4 GET REQUEST - Get all the Localized Notifications

---

    Get all the localized notifications by location from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/all/location

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>location</td>
        <td>A Data Transfer Object of a location.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.5 GET REQUEST - Get all the Localized Notifications

---

    Get all the localized notifications by location from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/all/type

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>notificationType</td>
        <td>A Data Transfer Object of a notification type.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.6 POST REQUEST - Save a new Localized Notification

---

    Save a new localized notification on the database, once it's added, an ID will be added to that localized notification.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channel</td>
        <td>A Data Transfer Object of the localized notification.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 201 created with the channel as a JSON if found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.7 POST REQUEST - Save an X amount of Localized Notifications

---

    Save an X amount of new localized notification to the database, once they are added, an ID will be added to those localized notifications.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A list of Data Transfer Objects for the localized notifications that are going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 201 created with the localized notifications as a JSON if created or a 400 bad request if they weren't.</td>
    </tr>
</table>

<br>

### 3.8 PUT REQUEST - Update a Localized Notification

---

    Modify an existing channel on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>id</td>
        <td>A Data Transfer Object for the localized notification that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Channel</td>
        <td>A 200 ok with the localized notification as a JSON if updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.9 DELETE REQUEST - Delete a Localized Notification by ID

---

    Delete a channel from the database based on it's ID.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A Localized Notification Data Transfer Object for the localized notification that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the localized notification was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

### 3.10 DELETE REQUEST - Delete a Localized Notification

---

    Delete a channel from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/localized-notification/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>channels</td>
        <td>A Channel Data Transfer Object for the localized notification that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Channels</td>
        <td>A 200 ok if the localized notification was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="location">4. <b>LocationController</b></h3>

Create, update, delete and get information for the Notifications. They represent the different places the notifications can come from. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 4.1 GET REQUEST - Get a Location by ID

---

    Get a location by its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>id</td>
        <td>The id of the location to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Location</td>
        <td>A 200 ok if the location was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.2 GET REQUEST - Get a Location by City

---

    Get a location by the name of the city.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/city/{city}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>city</td>
        <td>The name of the city to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Location</td>
        <td>A 200 ok if the location was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.3 GET REQUEST - Get a Location by Country

---

    Get a location by the name of the country.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/country/{country}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>country</td>
        <td>The name of the country to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Location</td>
        <td>A 200 ok if the location was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.4 GET REQUEST - Get a Location by City and Country

---

    Get a location by the name of the city and the country.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/{city}/{country}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>city</td>
        <td>The name of the city to look for.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>country</td>
        <td>The name of the country to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Location</td>
        <td>A 200 ok if the location was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.5 GET REQUEST - Get all Locations

---

    Get all the different locations from the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Locations</td>
        <td>A 200 ok with all the different locations.</td>
    </tr>
</table>

<br>

#### 4.6 POST REQUEST - Save a new Location

---

    Save a new location on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Location</td>
        <td>The location that is going to be stored.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Location</td>
        <td>A 200 ok if the location was saved on the database or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.7 POST REQUEST - Save an X amount of Locations

---

    Save an X amount of locations on the database.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>List of Locations</td>
        <td>The locations that are going to be stored.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Locations</td>
        <td>A 200 ok if the locations were saved on the database or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.8 PUT REQUEST - Update a Location

---

    Update a location on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Location</td>
        <td>The location that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Location</td>
        <td>A 200 ok if the location was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.9 DELETE REQUEST - Delete a Location by ID

---

    Delete a location based on it's ID.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the location that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the location was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.10 DELETE REQUEST - Delete a Location by City

---

    Delete a location based on the name of the city.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/delete/city/{city}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the city of the location that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the location was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.11 DELETE REQUEST - Delete a Location by Country

---

    Delete a location based on the name of the city.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/delete/country/{country}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the country of the location that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the location was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.12 DELETE REQUEST - Delete a Location by City and Country

---

    Delete a location based on the name of the city and the country.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/delete/{city}/{country}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the city of the location that is going to be deleted.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the country of the location that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the location was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 4.13 DELETE REQUEST - Delete a Location

---

    Delete a location from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/location/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Location</td>
        <td>The location that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the location was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="notification">5. <b>NotificationController</b></h3>

Create, update, delete and get information for the Notifications. They represent the different notifications generated by the KLIMU alert system. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 5.1 GET REQUEST - Get a Notification by ID

---

    Get a notification based on its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the notification to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Notification</td>
        <td>A 200 ok if the notification was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.2 GET REQUEST - Get a Notification by Location

---

    Get a list of notifications based on a location.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/location

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Location</td>
        <td>The location of the notification to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.3 GET REQUEST - Get a Notification by Type

---

    Get a list of notifications based on its type.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/type

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Type</td>
        <td>The type of the notification to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.4 GET REQUEST - Get a Notification by Location and Date

---

    Get a list of notifications for a specific date and a location.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/date/location

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Date</td>
        <td>The date the notifications must be from.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>Location</td>
        <td>The location the notifications must be from.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.5 GET REQUEST - Get a Notification by Type and Date

---

    Get a list of notifications for a specific date and type.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/date/type

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Date</td>
        <td>The date the notifications must be from.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>Type</td>
        <td>The type the notifications must be from.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.6 GET REQUEST - Get all Notifications

---

    Get all the notifications stored on the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.7 GET REQUEST - Get the last 50 Notifications

---

    Get the last 50 notifications stored on the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/all/limited

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.8 POST REQUEST - Save a new Notification

---

    Save a new Notification on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Notification</td>
        <td>The notification that is going to be stored.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Notification</td>
        <td>A 200 ok if the notification was created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.9 POST REQUEST - Save an X amount of Locations

---

    Save an X amount of notifications on the database.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>List of Notifications</td>
        <td>The notification that is going to be stored.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notifications were created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.10 UPDATE REQUEST - Update a Notification

---

    Update a notification on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Notification</td>
        <td>The notification that is going to be stored.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Notification</td>
        <td>A 200 ok if the notification was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.11 DELETE REQUEST - Delete a Notification by ID

---

    Delete a notification by its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the notification that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the notification was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 5.12 DELETE REQUEST - Delete a Notification

---

    Delete a notification from the database.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Notification</td>
        <td>The notification that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the notification was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="type_notification">6. <b>NotificationTypeController</b></h3>

Create, update, delete and get information for the Notification Types. They represent the different notifications that can be generated. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 6.1 GET REQUEST - Get a Notification Type by ID

---

    Get a notification type based on its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the notification type to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Notification Type</td>
        <td>A 200 ok if the notification type was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.2 GET REQUEST - Get a Notification Type by name

---

    Get a notification type based on its name.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/name/{name}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the notification type to look for.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Notification Type</td>
        <td>A 200 ok if the notification type was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.3 GET REQUEST - Get all the Notification Types

---

    Get all the notification types from the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notification Types</td>
        <td>A 200 ok if the notification types were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.4 GET REQUEST - Get all the Notification Types by Type

---

    Get all the notification types of an specific type (Dangerous, Warning or Information).

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/all/{type}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The notification type to filter with.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notification Types</td>
        <td>A 200 ok if the notification types were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.5 POST REQUEST - Save a new Notification Type

---

    Save a new notification type on the database. Once it's saved, it is returned with the ID generated by the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Notification Type</td>
        <td>The notification type that is going to be saved.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Notification</td>
        <td>A 200 ok if the notification type was created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.6 POST REQUEST - Save an X amount of Notification Types

---

    Save an X new amount of notification types on the database.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>List of Notification Types</td>
        <td>The list of the notification type that are going to be saved.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notification types were created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.7 PUT REQUEST - Update a Notification Type

---

    Update a notification type on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Notification Type</td>
        <td>The notification type that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of Notifications</td>
        <td>A 200 ok if the notification type was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.8 DELETE REQUEST - Delete a Notification Type by ID

---

    Delete a notification type based on its ID.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the notification type that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the notification type was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 6.9 DELETE REQUEST - Delete a Notification Type

---

    Delete a notification type from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/notification-type/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Notification Type</td>
        <td>The notification type that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the notification type was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="role">7. <b>RoleController</b></h3>

Create, update, delete and get information for the Roles. They represent the different types of users the application has, granting different permissions depending on the role. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 7.1 GET REQUEST - Get a Role by ID

---

    Get a role based on its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the role that is going to be fetch.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the role was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.2 GET REQUEST - Get a Role by name

---

    Get a role based on its name.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/name/{roleName}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the role that is going to be fetch.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the role was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.3 GET REQUEST - Get all Roles

---

    Get all roles from the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the roles were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.4 POST REQUEST - Save a new Role

---

    Save a new role on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Role</td>
        <td>The role that is going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the role was created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.5 POST REQUEST - Save an X amount of Roles

---

    Save an X amount of roles on the database.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>List of Roles</td>
        <td>The list of roles that are going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the roles were created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.6 PUT REQUEST - Update a Role

---

    Update a role on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Role</td>
        <td>The role that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the role was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.7 PUT REQUEST - Set a Role to an AppUser

---

    Set the role of a user.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/set

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The username of the user.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the role.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Role</td>
        <td>A 200 ok if the role was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.8 DELETE REQUEST - Delete a Role by ID

---

    Delete a request based on its ID.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the role that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the role was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 7.9 DELETE REQUEST - Delete a Role

---

    Delete a role from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/role/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Role</td>
        <td>The role that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the role was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="user">8. <b>UserController</b></h3>

Create, update, delete and get information for the AppUsers. They save the information about the users of the application. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 8.1 GET REQUEST - Get an AppUser by ID

---

    Fetch an AppUser based on its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the AppUser that is going to be fetched.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.2 GET REQUEST - Get an AppUser by name

---

    Fetch an AppUser based on its username.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/username/{username}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The username of the AppUser that is going to be fetched.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.3 GET REQUEST - Get an AppUser by ChatId

---

    Fetch an AppUser based on its telegramId.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/chatId/{chatId}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The telegramId of the AppUser that is going to be fetched.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.4 GET REQUEST - Get an AppUser from a Token

---

    Get an AppUser from a token.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/from-token/{token}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The token the AppUser that is going to be fetched from.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was generated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.5 GET REQUEST - Get all the AppUsers with a LocalizedNotification

---

    Get a list of AppUsers that have a localized notification configured.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/{locationId}/{typeId}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the location that is going to be used on the query.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the notification type that is going to be used on the query.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUsers were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.6 POST REQUEST - Save a new AppUser

---

    Save a new AppUser on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>AppUser</td>
        <td>The AppUser that is going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.7 POST REQUEST - Save an X amount of AppUsers

---

    Save an X amount of AppUsers on the database.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>List of AppUsers</td>
        <td>The lisf of AppUsers that are going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUsers were created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.8 POST REQUEST - Add a new LocalizedNotification to an AppUser

---

    Add a new localized notification to an AppUser.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/add/{chatId}/{channel}/{locationId}/{typeId}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The telegramId of the user that is going to be updated.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the channel that the notification is going to be created for.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the location for the notification.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the notification type for the notification.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the localized notification was created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.9 POST REQUEST - Set a ChatId to an AppUser

---

    Set the chat ID of a user based on the username.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/set/chatId/{username}/{chatId}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The username of the AppUser that is going to be created.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>AppUser</td>
        <td>The telegramId of the AppUser that is going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the relation was created, a 404 if the AppUser was not found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.10 PUT REQUEST - Update an AppUser

---

    Update an AppUser on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>AppUser</td>
        <td>The AppUser that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.11 PUT REQUEST - Update an AppUser's localized notification

---

    Update an AppUser's localized notification list for an specific channel.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/remove/{username}/{channelName}/{notificationId}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The username of the AppUser that is going to be updated.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>String</td>
        <td>The name of the Channel that is going to be updated.</td>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The id of the Localized Notification that is going to be removed from the user's list.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>AppUser</td>
        <td>A 200 ok if the AppUser was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.12 DELETE REQUEST - Delete an AppUser by ID

---

    Delete an AppUser based on its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the AppUser that is going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the AppUser was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 8.13 DELETE REQUEST - Delete an AppUser

---

    Delete an AppUser from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>AppUser</td>
        <td>The AppUser that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the AppUser was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h3 id="user_notification">9. <b>UserNotificationController</b></h3>

Create, update, delete and get information for the UserNotifications. They represent the different notifications a user has configured for a specific channel. Takes care of all the CRUD methods, working with the database through an internal Service.

#### 9.1 GET REQUEST - Get a User Notification by ID

---

    Get a user notification based on its ID.

<ul>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the user notification that is going to be fetched.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>User Notification</td>
        <td>A 200 ok if the AppUser was found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.2 GET REQUEST - Get all the User Notifications

---

    Get all the user notifications from the database.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>return</td>
        <td>List of User Notifications</td>
        <td>A 200 ok if the User Notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.3 GET REQUEST - Get all User Notifications for a Channel

---

    Get all the user notifications for an specific channel.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/all/channel

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Channel</td>
        <td>The channel that is going to be used on the query.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of User Notifications</td>
        <td>A 200 ok if the User Notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.4 GET REQUEST - Get all User Notifications for a Localized Notification

---

    Get all the user notifications for an specific localized notification.

<ul>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/all/notification

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>LocalizedNotification</td>
        <td>The localized notification that is going to be used on the query.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of User Notifications</td>
        <td>A 200 ok if the User Notifications were found or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.5 POST REQUEST - Save a new User Notification

---

    Save a new user notification on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/create

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>User Notification</td>
        <td>The User Notification that is going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>User Notification</td>
        <td>A 200 ok if the User Notification was created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.6 POST REQUEST - Save an X amount of User Notifications

---

    Save an X amount of user notifications on the database.

<ul>
    <li>Consumes: application/json</li>
    <li>Produces: application/json</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/create/all

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>List of User Notifications</td>
        <td>The User Notifications that are going to be created.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>List of User Notifications</td>
        <td>A 200 ok if the User Notifications were created or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.7 PUT REQUEST - Update a User Notification

---

    Update a user notification on the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Produces: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/update

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>User Notification</td>
        <td>The User Notification that is going to be updated.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>User Notification</td>
        <td>A 200 ok if the User Notification was updated or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.8 DELETE REQUEST - Delete a User Notification by ID

---

    Delete a user notification based on its ID.

<ul>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/delete/{id}

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>Long</td>
        <td>The ID of the User Notification that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the User Notification was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

#### 9.9 DELETE REQUEST - Delete a User Notification

---

    Delete a user notification from the database.

<ul>
    <li>Consumes: application/json, application/xml</li>
    <li>Requires: Access and Refresh tokens as headers.</li>
</ul>

> https://klimu.eus/RestAPI/user-notification/delete

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>param</td>
        <td>User Notification</td>
        <td>The User Notification that is going to be deleted.</td>
    </tr>
    <tr>
        <td>return</td>
        <td>Response Entity</td>
        <td>A 200 ok if the User Notification was deleted or a 400 bad request if it wasn't.</td>
    </tr>
</table>

<br>

<h2 id="data">Data Variables</h2>

---

The application makes use of different data type variables for transmitiing the data between services. Those data types are transformed back a for between normal objects and Data Transfer Objects. Those DTOs are used to transmit the data from a request to another, avoiding any destruction of data in the process. The different data types are:

- [Channel](#channel_data)
- [Localized Notification](#ln_data)
- [Location](#location_data)
- [Notification](#n_data)
- [Notification Type](#ntype_data)
- [Role](#role_data)
- [AppUser](#user_data)
- [User Notification](#userN_data)

<h3 id="channel_data">Channel</h3>

---

A channel represents a type of communication system that the service uses for sending the notifications. The channels default to: Telegram, Email and Desktop.

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Long</td>
        <td>id</td>
        <td>The identification number of the channel.</td>
    </tr>
    <tr>
        <td>String</td>
        <td>name</td>
        <td>The name of the channel.</td>
    </tr>
    <tr>
        <td>String</td>
        <td>icon</td>
        <td>The icon of the channel.</td>
    </tr>
</table>

<h3 id="ln_data">Localized Notification</h3>

---



<h3 id="location_data">Location</h3>

---

A place on the globe that allows the users to configure notifications for that place.

<table>
    <tr>
        <th>Type</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>Long</td>
        <td>id</td>
        <td>The identification number of the location.</td>
    </tr>
    <tr>
        <td>String</td>
        <td>city</td>
        <td>The city of the location.</td>
    </tr>
    <tr>
        <td>String</td>
        <td>country</td>
        <td>The country of the location.</td>
    </tr>
</table>

<h3 id="n_data">Notification</h3>

---


<h3 id="ntype_data">Notification Type</h3>

---


<h3 id="role_data">Role</h3>

---


<h3 id="user_data">AppUser</h3>

---


<h3 id="userN_data">User Notification</h3>

---


<h2 id="auth">Application Authentication</h2>

---

The application makes use of JSON Web Tokens to authenticate the user, using an access token for the authentication and a refresh token to generate a new access token. Once the user is authenticated, the generated access token will last for 5 minutes, while the refresh token will last for 6 hours. The tokens are generated after making a request (GET or POST) to https://klimu.eus/RestAPI/login, sending a **username** and a **password** as form parameters.