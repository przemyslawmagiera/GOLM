<!DOCTYPE html>
<html>
<head>
    <title>Hello WebSocket</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script type="text/javascript">
        var stompClient = null;
        var yourTurn = true;
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
            stompClient.send("/app/golm", {}, JSON.stringify({ 'name': name }));
        }

        function showGreeting(message) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }

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

        $( "#tableContainer" ).append( generateGrid( 5, 5) );

        $( "td" ).click(function() {
            var index = $( "td" ).index( this );
            var row = Math.floor( ( index ) / 5) + 1;
            var col = ( index % 5 ) + 1;

            var form = document.createElement("form");
            var x = document.createElement("input");
            var y = document.createElement("input");
            form.method = "POST";
            form.action = "/game/moveRequest";

            x.value=row;
            x.name="x";
            form.appendChild(x);

            y.value=row;
            y.name="y";
            form.appendChild(y);

            document.body.appendChild(form);
            form.submit();


            $( "span" ).text( "That was row " + row + " and col " + col );
            $( this ).css( 'background-color', 'red' );
        });
    </script>
</head>
<body onload="disconnect()">
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
    </div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name" />
        <button id="sendName" onclick="sendName();">Send</button>
        <p id="response"></p>
    </div
    <div id="tableContainer"></div>
    <style>
        td {
            border: 1px solid;
            width: 50px;
            height: 50px;
        }
    </style>

</div>
</body>
</html>