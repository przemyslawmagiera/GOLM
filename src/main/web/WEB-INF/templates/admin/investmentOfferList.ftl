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
            <h4 class="title">Investment Offers</h4>
            <div style="padding-top: 5%;"></div>
            <table class="table table-responsive">
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Max</th>
                    <th align="center">Min</th>
                    <th align="center">Interest</th>
                    <th align="center">Reward</th>
                    <th align="center">Action</th>
                </tr>
            <#list offers as offer>
                <tr class="tr-order">
                    <td>${offer.getId()}</td>
                    <td>${offer.getMaxAmount()}</td>
                    <td>${offer.getMinAmount()}</td>
                    <td>${offer.getInterest()}</td>
                    <td>${offer.getSellerReward()}</td>
                    <td>
                        <a href="/admin/removeInvestmentOffer?investmentOfferId=${offer.getId()}"
                           title="Details">
                            <span class="glyphicon glyphicon-remove"></span>
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