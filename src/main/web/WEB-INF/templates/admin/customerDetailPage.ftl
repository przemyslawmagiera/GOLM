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
    <div class="col-md-12" align="center">
        <h5><strong>Customer detail page</strong></h5>
        <table class="table table-responsive">
            <tr>
                <th align="center">Account balance</th>
                <td>${customer.getAccount().getBalance()} PLN</td>
            </tr>
            <tr>
                <th align="center">Name</th>
                <td>${customer.getName()}</td>
            </tr>
            <tr>
                <th align="center">Last name</th>
                <td>${customer.getLastName()}</td>
            </tr>
            <tr>
                <th align="center">Address</th>
                <td>${customer.getAddress()}</td>
            </tr>
            <tr>
                <th align="center">City</th>
                <td>${customer.getCity()}</td>
            </tr>
            <tr>
                <th align="center">Phone number</th>
                <td>${customer.getPhoneNumber()}</td>
            </tr>
        </table>
    </div>
    <div class="col-md-12">
    <div class="edit_form">
        <h4 class="title">${customer.getName()}'s investments</h4>
        <div style="padding-top: 5%;"></div>
        <table class="table table-responsive">
            <tr>
                <th align="center">Id</th>
                <th align="center">Start Date</th>
                <th align="center">End Date</th>
                <th align="center">Amount</th>
                <th align="center">Action</th>
            </tr>
        <#list investments as investment>
            <tr class="tr-order">
                <td>${investment.getId()}</td>
                <td>${investment.getStartDate()}</td>
                <td>${investment.getEndDate()}</td>
                <td>${investment.getAmount()}</td>
                <td>
                    <a href="/admin/removeInvestment?investmentId=${investment.getId()}"
                       title="Withdraw">
                        <span class="glyphicon glyphicon-remove"></span>
                    </a>
                </td>
            </tr>
        </#list>
        </table>
    </div>
    </div>
    <div class="col-md-12">
        <div class="edit_form">
            <h4 class="title">${customer.getName()}'s credits</h4>
            <div style="padding-top: 5%;"></div>
            <table class="table table-responsive">
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Start Date</th>
                    <th align="center">End Date</th>
                    <th align="center">Amount</th>
                    <th align="center">Action</th>
                </tr>
            <#list credits as credit>
                <tr class="tr-order">
                    <td>${credit.getId()}</td>
                    <td>${credit.getStartDate()}</td>
                    <td>${credit.getEndDate()}</td>
                    <td>${credit.getAmount()}</td>
                    <td>
                        <a href="/admin/removeCredit?creditId=${credit.getId()}"
                           title="Pay off">
                            <span class="glyphicon glyphicon-remove"></span>
                        </a>
                    </td>
                </tr>
            </#list>
            </table>
        </div>
    </div>
</div>
