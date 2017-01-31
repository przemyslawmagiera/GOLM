<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<body onload="drawCircles(${occupied})">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<span>Select a cell! you re ${player}</span>
<br>
<#assign x = ((size+1)*20)> <#-- replace variable x -->

<canvas id="myCanvas" width="${x}" height="${x}"></canvas>
<br>
<button onclick="sendForm()" name="OK">REQUEST</button>

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
    var newSize = ${size}+1
    var table = new Array(newSize);
    for (i = 0; i < newSize; i++)
    {
        table[i] = new Array(newSize);
    }
    for (i=0;i<newSize;i++)
        for(j=0;j<newSize;j++)
            table[i][j] = 'F'; // table of occupied and not circles
    //here to extract from occupied but not working
    var occupied = "${occupied}";
    var parts = occupied.split(",");
    var length = parts.length;
    for(i=0; i<length-1;i=i+3)
    {
        table[parts[i+1]][parts[i]] = parts[i+2];
    }

        for (i=1;i<newSize;i++)
        {
            for(j=1;j<newSize;j++)
            {
                if (table[i-1][j-1]=="F")// check if table[i][j]..
                    drawCircle(context, i*20, j*20, "yellow", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
                else if(table[i-1][j-1]=="W")
                    drawCircle(context, i*20, j*20, "white", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
                else
                    drawCircle(context, i*20, j*20, "black", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
            }

        }


    canvas.onmousedown=handleMousedown;
    var selected = "";
    function sendForm() {
        var form = document.createElement("form");
        var x = document.createElement("input");
        var player = document.createElement("input");
        var size = document.createElement("input");
        form.method = "POST";
        form.action = "/proceedPassRequest";

        player.value=${player};
        player.name="player";
        form.appendChild(player);

        size.value=${size};
        size.name="size";
        form.appendChild(size);

        x.value=selected;
        x.name="selected";
        form.appendChild(x);

        document.body.appendChild(form);
        form.submit();
    }

    function handleMousedown(e){
        var check = 0;
        var clickedX = e.pageX - this.offsetLeft;
        var clickedY = e.pageY - this.offsetTop;
        for (var i = 0; i < circles.length; i++) {
            if (clickedX < circles[i].right && clickedX > circles[i].left && clickedY > circles[i].top && clickedY < circles[i].bottom) {
                var xNew = (Math.round(clickedX / 20) - 1);
                var yNew = (Math.round(clickedY / 20) - 1);
                if(check == 0) {
                    drawCircle(context, 20 * (Math.floor(i /${size}) + 1), (i % ${size}+1) * 20, "green", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
                    selected = selected + xNew + "," + yNew + " ";
                    check = 1;
                }
            }
        }
    }
</script>

</body>
</html>