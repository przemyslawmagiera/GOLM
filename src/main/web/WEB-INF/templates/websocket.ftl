<!DOCTYPE html>
<html>

<#--<style>-->
    <#--canvas, img { display:block; margin:1em auto; border:1px solid black; }-->
    <#--canvas { background:url(http://html.bleaudev.dk/canvas/apartments.jpg) }-->
<#--</style>-->
<body>
<canvas id="myCanvas" width="200" height="200"></canvas>
</body>
</html>
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
    for (i=1;i<10;i++)
    {
        for(j=1;j<10;j++)
            drawCircle(context, i*20, j*20, "white", 10, 1, "#003300", "white", "center", "bold 32px Arial", "1", circles);
    }

    canvas.onmousedown=handleMousedown;

    function handleMousedown(e){
        var clickedX = e.pageX - this.offsetLeft;
        var clickedY = e.pageY - this.offsetTop;
        for (var i = 0; i < circles.length; i++) {
            if (clickedX < circles[i].right && clickedX > circles[i].left && clickedY > circles[i].top && clickedY < circles[i].bottom) {
                alert ('clicked number '+ (Math.floor(clickedX)/20) + "  " + (Math.floor(clickedY))/20) ;
            }
        }
    }
</script>