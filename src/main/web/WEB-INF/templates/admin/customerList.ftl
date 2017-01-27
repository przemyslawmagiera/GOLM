<#import "/spring.ftl" as spring/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>SIBS</title>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/jquery-2.2.2.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<@spring.url '/resources/scripts/main.js'/>"></script>
    <link href="<@spring.url '/resources/theme/bootstrap.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/bootstrap-theme.min.css'/>" rel="stylesheet"/>
    <link href="<@spring.url '/resources/theme/main.css'/>" rel="stylesheet"/>
</head>
<body>

<#include "nav.ftl">

<div class="row">
    <div class="col-md-12">
        <div class="edit_form">
            <h4 class="title">Your customers</h4>
            <div style="padding-top: 5%;"></div>
            <table class="table table-responsive">
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Account Number</th>
                    <th align="center">Name</th>
                    <th align="center">Last name</th>
                    <th align="center">Action</th>
                </tr>
            <#list customers as customer>
                <tr class="tr-order">
                    <td>${customer.getId()}</td>
                    <td>${customer.getAccount().getId()}</td>
                    <td>${customer.getName()}</td>
                    <td>${customer.getLastName()}</td>
                    <td>
                        <a href="/admin/removeCustomer?customerId=${customer.getId()}"
                           title="Details">
                            <span class="glyphicon glyphicon-remove"></span>
                        </a>
                        <a href="/admin/customerDetails?customerId=${customer.getId()}"
                           title="Details">
                            <span class="glyphicon glyphicon-wrench"></span>
                        </a>
                    </td>
                </tr>
            </#list>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $(".tr-order").hover(function () {
            $(this).addClass("danger");
        }, function () {
            $(this).removeClass("danger");
        });
    });
</script>
</body>
</html>