<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>GO - eine fantastische chinese spiel</title>

</head>
<body>
<h1>${msg}</h1>
<form action="/startGame" method="post">
    <input type="radio" name="multi" value="multiplayer"> true<br>
    <input type="radio" name="multi" value="singleplayer"> false<br>
    <input type="radio" name="size" value="9"> 9<br>
    <input type="radio" name="size" value="13"> 13<br>
    <input type="radio" name="size" value="19"> 19<br>
    <br><br><input type="submit" value="START GAME"/>
</form>

<p><b>Note:</b>Please select your settings</p>

</body>
</html>