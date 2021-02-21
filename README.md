# AFK-RPC  

AFK RPC is a simple program to show when you're unavailable on Discord. It uses a restful API  
to start/stop being AFK. Feel free to address any concerns or requests in the [issues tab](https://github.com/Tvanderb/AFK-RPC/issues).

<br />
<br />

## Installation

In order to have the best experience with AFK RPC you need to have it running on a computer (duh)  
that is connected to the Internet, has Discord open, and is logged into your account on Discord. I  
also recommend that you have the device port-forwarded so that you can use it from anywhere.

### 1.

> Download the latest release Jar.

### 2.

> Move it to wherever you wish.

### 3.

> Create a new application in [Discord](https://discord.com/developers/applications) to use for the rich  
> presence and copy the client ID.

### 4.

> Either setup a script to run it automatically on startup, or run:
> ```
> java -jar AFK_RPC-v1.0-ALPHA.jar <PORT> <YOUR COPIED CLIENT ID>
> ```

Now, open up a browser and go to ``http://localhost:portyouchose/go-afk?amount=1&&unit=h`` this will make  
you show up as AFK for an hour. To turn this off go to ``http://localhost:portyouchose/stop``, that will turn off the Rich Presence. If neither URLs work, double check the port and make sure you followed the installation procedure correctly.

<br />

## API Documentation

In these examples I will use the host ``localhost:3000``. Just remember, change this to whatever hostname is right for your setup. (by the way, these requests are all "GET" to allow integration with things like Siri Shortcuts)

<br />

##### GET ``/go-afk``

Go AFK. This end point requires two parameters, both transported via url-encoded.

| Name   | Expected Value                     |
|--------|------------------------------------|
| unit   | s(econd), m(inute), h(our), d(ay)  |
| amount | The amount of units as a double.   |

For example ``http://localhost:3000/go-afk?unit=m&amount=1.5`` will make you AFK for one and a half minutes.  

###### Example JSON Response:
```json
{
  "id": 1,
  "content": {
    "message": "Started presence. End time: Tue Dec 15 01:05:54 EST 2020"
  },
  "status": 200
}
```

###### Example XML Response:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<response>
  <content xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="defaultResponse">
    <message>Started presence. End time: Tue Dec 15 01:06:44 EST 2020</message>
  </content>
  <id>1</id>
  <status>200</status>
</response>
```

<br/>

##### GET ``/stop``

Stops being AFK. Ends the rich presence.

###### Example JSON Response:
```json
{
  "id": 2,
  "content": {
    "message": "Stopped"
  },
  "status": 200
}
```

###### Example XML Response:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<response>
  <content xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="defaultResponse">
    <message>Stopped</message>
  </content>
  <id>2</id>
  <status>200</status>
</response>

```
