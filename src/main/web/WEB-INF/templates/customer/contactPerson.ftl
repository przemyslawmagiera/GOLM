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
        <h5><strong>Your contact person</strong></h5>
        <table class="table table-responsive">
            <tr>
                <th align="center">Name</th>
                <td>${seller.getName()}</td>
            </tr>
            <tr>
                <th align="center">Last name</th>
                <td>${seller.getLastName()}</td>
            </tr>
            <tr>
                <th align="center">Phone number</th>
                <td>${seller.getPhoneNumber()}</td>
            </tr>
            <tr>
                <th align="center">E-mail</th>
                <td>${seller.getEmail()}</td>
            </tr>
        </table>
    </div>
</div>
