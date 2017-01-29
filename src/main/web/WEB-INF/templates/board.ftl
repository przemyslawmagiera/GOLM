<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<body onload="drawGrid()">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script type="text/javascript">
    var stompClient = null;
    function setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
        document.getElementById('response').innerHTML = '';
    }
    function connect() {
        var socket = new SockJS('/golm');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function(greeting){
                console.log(greeting);
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }
    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }
    function sendName() {
        var name = document.getElementById('name').value;
        stompClient.send("/app/golm", {}, JSON.stringify({ 'info': name }));
    }
    function showGreeting(message) {
        var response = document.getElementById('response');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(message));
        response.appendChild(p);
    }
</script>
<div>
    <button id="connect" onclick="connect();">Connect</button>
    <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
</div>
<div id="conversationDiv">
    <label>What is your name?</label><input type="text" id="name" />
    <button id="sendName" onclick="sendName();">Send</button>
    <p id="response"></p>
</div>
<span>Select a cell! you re ${player}, ${occupied}</span>
<div id="tableContainer"></div>
<style>
td {
border: 1px solid;
width: 50px;
height: 50px;
}
</style>


<script>

function generateGrid( rows, cols ) {
    rows = ${size}
    cols = ${size}
    var grid = "<table>";
    for ( row = 1; row <= rows; row++ ) {
    grid += "<tr>";
        for ( col = 1; col <= cols; col++ ) {
        grid += "<td></td>";
        }
        grid += "</tr>";
    }
    return grid;
    }


    $( "#tableContainer" ).append( generateGrid( ${size}, ${size}) );

    $( "td" ).click(function() {
    var index = $( "td" ).index( this );
    var row = Math.floor( ( index ) / ${size});
    var col = ( index % ${size} );

    var form = document.createElement("form");
    var x = document.createElement("input");
    var y = document.createElement("input");
    var player = document.createElement("input");
    var size = document.createElement("input");
    form.method = "POST";
    form.action = "/game/moveRequest";

    player.value=${player};
    player.name="player";
    form.appendChild(player);

    size.value=${size};
    size.name="size";
    form.appendChild(size);

    x.value=row;
    x.name="x";
    form.appendChild(x);

    y.value=col;
    y.name="y";
    form.appendChild(y);

    document.body.appendChild(form);
    form.submit();


    $( "span" ).text( "That was row " + row + " and col " + col );
    $( this ).css( 'background-color', 'black' );
    });

</script>

</body>
</html>