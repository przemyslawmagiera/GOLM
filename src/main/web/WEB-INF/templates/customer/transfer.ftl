<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>SIBS Transfer</title>

    <script type="text/javascript" src="<@spring.url '/resources/scripts/jquery-2.2.2.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/main.js'/>"></script>
    <link href="<@spring.url '/resources/theme/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/bootstrap-theme.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/main.css'/>" rel="stylesheet"/>

</head>
<body>

<#include "nav.ftl">

<div class="page-container">

    <div class="row" align="center">
        <form class="form" action="/customer/processTransfer" method="post">
            <div class="col-md-12" align="center">
                <div class="register_form" align="center">
                    <h4 class="title">New transfer</h4>
                    <div class="form-group">
                        <p><label>Account id*:</label></p>
                        <input type="text" class="form-control" value="" name="accId" pattern="^[0-9]+$"
                               required/>
                    </div>
                    <div class="form-group">
                        <p><label>Name*:</label></p>

                        <input type="text" value="" pattern="^[a-zA-Z]+$"
                               class="form-control" name="name" required/>
                    </div>

                    <div class="form-group">
                        <p><label>Last name*:</label></p>

                        <input type="text" class="form-control" value="" pattern="^[a-zA-Z]+$" name="Last name"
                               required/>
                    </div>

                    <div class="form-group">
                        <p><label>Amount*:</label></p>

                        <input type="number" class="form-control" value="" pattern="^[0-9]+$" name="amount"
                               required/>
                    </div>

                    <div align="center">
                        <a href="/customer" class="btn btn-default" role="button">Back</a>&nbsp&nbsp
                        <input class="btn btn-custom" type="submit" value="Confirm"/>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>