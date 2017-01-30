<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<body onload="drawGrid()">
<canvas id="myCanvas" width="200" height="200"></canvas>
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

</script>


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

<script>

    var canvas = document.getElementById('myCanvas');
    var context = canvas.getContext('2d');
    var circles = [];

    var draw = function (context, x, y, fillcolor, radius, linewidth, strokestyle) {
        context.beginPath();
        context.arc(x, y, radius, 0, 2 * Math.PI, false);
        context.fillStyle = fillcolor;
        context.fill();
        context.lineWidth = linewidth;
        context.strokeStyle = strokestyle;
        context.stroke();
    };

    var Circle = function(x, y, radius) {
        this.left = x - radius;
        this.top = y - radius;
        this.right = x + radius;
        this.bottom = y + radius;
    };

    var drawCircle = function (context, x, y, fillcolor, radius, linewidth, strokestyle, fontcolor, textalign, fonttype, filltext, circles) {
        draw(context, x, y, fillcolor, radius, linewidth, strokestyle, fontcolor, textalign, fonttype, filltext);
        var circle = new Circle(x, y, radius);
        circles.push(circle);
    };
    var i,j;
    var table = new Array(10);
    for (i = 0; i < 10; i++)
    {
        table[i] = new Array(10);
    }
    for (i=0;i<10;i++)
        for(j=0;j<10;j++)
            table[i][j] = 0; // table of occupied and not circles
    //here to extract from occupied but not working

    for (i=1;i<10;i++)
    {
        for(j=1;j<10;j++)
        {
            if (j == 2)// check if table[i][j]..
                drawCircle(context, i*20, j*20, "white", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
            else
                drawCircle(context, i*20, j*20, "green", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
        }

    }

    canvas.onmousedown=handleMousedown;

    function handleMousedown(e){
        var clickedX = e.pageX - this.offsetLeft;
        var clickedY = e.pageY - this.offsetTop;
        for (var i = 0; i < circles.length; i++) {
            if (clickedX < circles[i].right && clickedX > circles[i].left && clickedY > circles[i].top && clickedY < circles[i].bottom) {
                var xNew = clickedX / 20;
                var yNew = clickedY / 20;
                alert ('clicked number '+ (Math.round(xNew) - 1) + "  " + (Math.round(yNew) - 1)) ;
            }
        }

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

        x.value=(Math.round(clickedX / 20) - 1);
        x.name="x";
        form.appendChild(x);

        y.value=(Math.round(clickedY / 20) - 1);
        y.name="y";
        form.appendChild(y);

        document.body.appendChild(form);
        form.submit();
    }
</script>

</body>
</html>