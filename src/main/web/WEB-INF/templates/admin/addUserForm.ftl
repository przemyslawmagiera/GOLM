<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Add Product</title>

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
            <h1><strong>Add new customer</strong></h1>
            <div>
                <form action="/admin/processUserForm" method="post" >
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    <input type="text" name="username" placeholder="Username..."/>
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    <input type="password" name="password" placeholder="Password..."/>
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    <input type="text" name="name" placeholder="Name..."/>
                    <span class="glyphicon glyphicon-console" aria-hidden="true"></span>
                    <input type="text" name="lastName" placeholder="Last name..."/>
                    <br><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
                    <input type="number" name="phone" placeholder="Phone..."/>
                    <br><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    <input type="text" name="address" placeholder="Address..."/>
                    <br><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    <input type="text" name="postal" placeholder="Postal code..."/>
                    <br><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    <input type="text" name="city" placeholder="City..."/>
                    <br><br><input type="submit" value="Add customer"/>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

