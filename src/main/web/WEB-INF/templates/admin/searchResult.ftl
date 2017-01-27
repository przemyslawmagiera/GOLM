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
            <h4 class="title">Search results - Credits:</h4>
            <div style="padding-top: 5%;"></div>
            <table class="table table-responsive">
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Start Date</th>
                    <th align="center">End Date</th>
                    <th align="center">Amount</th>
                    <th align="center">CustomerId</th>
                </tr>
            <#list resultsCre as credit>
                <tr class="tr-order">
                    <td>${credit.getId()}</td>
                    <td>${credit.getStartDate()}</td>
                    <td>${credit.getEndDate()}</td>
                    <td>${credit.getAmount()}</td>
                    <td>
                        <a href="/admin/customerDetails?customerId=${credit.getCustomer().getId()}"
                           title="Details">${credit.getCustomer().getId()}</a>
                    </td>
                </tr>
            </#list>
            </table>
        </div>

        <div class="edit_form">
            <h4 class="title">Search results - Investments:</h4>
            <div style="padding-top: 5%;"></div>
            <table class="table table-responsive">
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Start Date</th>
                    <th align="center">End Date</th>
                    <th align="center">Amount</th>
                    <th align="center">CustomerId</th>
                </tr>
            <#list resultsInv as credit>
                <tr class="tr-order">
                    <td>${credit.getId()}</td>
                    <td>${credit.getStartDate()}</td>
                    <td>${credit.getEndDate()}</td>
                    <td>${credit.getAmount()}</td>
                    <td>
                        <a href="/admin/customerDetails?customerId=${credit.getCustomer().getId()}"
                           title="Details">${credit.getCustomer().getId()}</a>
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