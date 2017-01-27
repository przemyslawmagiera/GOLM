<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Log in to SIBS!</title>

    <script type="text/javascript" src="<@spring.url '/resources/scripts/jquery-2.2.2.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/main.js'/>"></script>
    <link href="<@spring.url '/resources/theme/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/bootstrap-theme.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/main.css'/>" rel="stylesheet"/>

</head>
<body>
<div class="container">
    <div class="jumbotron">
        <div align="center">
            <h3 class="text-danger">${msg}</h3>
            <br/><br/>
            <a href="/admin" class="btn btn-default btn-lg">
                Home
            </a>
        </div>
    </div>
</div>
</body>
</html>

