<#import "/spring.ftl" as spring/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>SIBS Admin</title>
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
        <h5><strong>Welcome to SIBS-Admin</strong></h5>
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
                <th align="center">Email</th>
                <td>${seller.getEmail()}</td>
            </tr>
            <tr>
                <th align="center">Phone number</th>
                <td>${seller.getPhoneNumber()}</td>
            </tr>
            <tr>
                <th align="center">Total clients</th>
                <td>${clientCount}</td>
            </tr>
            <tr>
                <th align="center">Total salary</th>
                <td>${totalSalary}</td>
            </tr>
        </table>
    </div>
</div>
<div class="col-lg-2" align="center">
    <form>
        <input id="searchField" type="text" class="form-control" placeholder="Search by days to end...">
        <span class="input-group-btn">
                        <a class="btn btn-default" type="button" href=""
                           onclick="this.href='/admin/search/'+$('#searchField').val()" >
                            <span class="glyphicon glyphicon-search"></span>
                        </a>
            </span>
    </form>
</div>
