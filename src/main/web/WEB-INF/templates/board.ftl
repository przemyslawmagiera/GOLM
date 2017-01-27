<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<span>Select a cell!</span>
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

</body>
</html>