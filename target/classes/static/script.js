var stompClient = null;
var currentUser = $.ajax({type: "GET", url: "/api/me", async: false}).responseText;

function setConnected(connected, connectedToRoom) {

    document.getElementById('connectToRoom').disabled = connected;
    document.getElementById('subscribeToSelf').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('title').innerHTML = connectedToRoom ? 'Chat Room' : 'Personal Messages';
    document.getElementById('response').innerHTML = '';
}

function connectToRoom() {
    var roomName = document.getElementById('roomName').value;

    var socket = new SockJS('/room');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true, true);
        stompClient.subscribe('/topic/messages.' + roomName, function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function subscribeToSelf() {
    var socket = new SockJS('/user');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true, false);
        stompClient.subscribe('/topic/users.' + currentUser, function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false, true);
}

function sendMessage() {
    var content = document.getElementById('messageContent').value;
    var roomName = document.getElementById('roomName').value;

    // HACK as I couldn't get @SendToUser working with RabbitMQ queues
    var path = "/room/";
    if (roomName.includes("user")) {
        path = "/user";
    }

    if (!stompClient) { // allows for sending message to room without actually joining it
        var socket = new SockJS(path);
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
        });
    }
    // send message over websocket to all active subscribers
    stompClient.send("/app" + path + "/" + roomName, {}, JSON.stringify({'sender': currentUser, 'content': content}));
    // persist message in postgresql for future retrieval
    $.post("/api/messages", {sender: currentUser, roomName: roomName, content: content});
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(messageOutput.sender + ": " + messageOutput.content));
    response.appendChild(p);
}

function getAllRoomNames() {
    var roomNamesList = document.getElementById('roomNamesList');
    // getting all ACTIVE rooms. (can also fetch all rooms from postgresql)
    $.getJSON("/api/rabbitmq/rooms", function (data) {
        var activeRooms = (data.length > 0) ? data.toString() : "none";
        roomNamesList.innerHTML = "List of active rooms: " + activeRooms;
    });
}