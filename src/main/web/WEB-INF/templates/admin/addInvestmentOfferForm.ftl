<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Add offer</title>

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
            <h1><strong>Add new investemnt offer</strong></h1>
            <div>
                <form action="/admin/processInvestmentOfferForm" method="post" >
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    <input type="number" step="0.01" name="interest" placeholder="interest..."/>
                    <br><span class="glyphicon glyphicon-dashboard" aria-hidden="true"></span>
                    <input type="number" step="0.01" name="max" placeholder="max..."/>
                    <br><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
                    <input type="number" step="0.01" name="min" placeholder="min..."/>
                    <br><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    <input type="number" step="0.01" name="reward" placeholder="reward..."/>
                    <br><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                    <br><br><input type="submit" value="Add offer"/>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

