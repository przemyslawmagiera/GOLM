<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>GO - eine fantastische chinese spiel</title>

    <script type="text/javascript" src="<@spring.url '/resources/scripts/jquery-2.2.2.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/main.js'/>"></script>
    <link href="<@spring.url '/resources/theme/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/bootstrap-theme.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/main.css'/>" rel="stylesheet"/>

</head>
<body>

<form action="/startGame" method="post">
    <input type="radio" name="multi" value="multiplayer"> true<br>
    <input type="radio" name="multi" value="singleplayer"> false<br>
    <input type="text" name="name"><br>
    <input type="radio" name="size" value="9"> 9<br>
    <input type="radio" name="size" value="13"> 13<br>
    <input type="radio" name="size" value="19"> 19<br>
    <br><br><input type="submit" value="START GAME"/>
</form>

<p><b>Note:</b>Please select your settings</p>

</body>
</html>