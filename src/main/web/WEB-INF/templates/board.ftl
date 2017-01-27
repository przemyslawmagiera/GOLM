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
    $( "span" ).text( "That was row " + row + " and col " + col );
    $( this ).css( 'background-color', 'red' );
    });

</script>

</body>
</html></html>