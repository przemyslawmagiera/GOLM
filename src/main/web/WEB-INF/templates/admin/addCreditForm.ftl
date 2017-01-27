<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Add credit</title>

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
            <h1><strong>Add new credit</strong></h1>
            <div>
                <form action="/admin/processCreditForm" method="post" >
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    <input type="number" step="0.01" max="2000000"  name="amount" placeholder="amount..."/>
                    <br><span class="glyphicon glyphicon-dashboard" aria-hidden="true"></span>
                    <input type="number" name="customerId" max="2000000"  placeholder="customer id..."/>
                    <br><span class="glyphicon glyphicon-dashboard" aria-hidden="true"></span>
                    <input type="number" name="offerId" max="2000000" placeholder="offer id..."/>
                    <br><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
                    <input type="number" name="days" max="2000000" placeholder="days..."/>
                    <br><br><input type="submit" value="Add credit"/>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

