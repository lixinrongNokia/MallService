<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>账户管理</title>
</head>

<body>
<!-- 账户信息 [[ -->
<div id="account_info" class="account-info clearfix">
    <div class="account-money">
        <div class="ai-title clearfix">
            <div class="ai-title-desc">剩余积分</div>
            <span class="js-eye ico-show"><!--不可见图标--></span></div>
        <div id="balance_box" class="ai-monney"><span id="integral">...</span>个<span
                class="frozen i-hide"></span>
        </div>
        <div class="ai-button-list">
            <div class="ai-button"><a class="mod-btn mod-btn-blue-34 @tenpay_v3.homepage.balance.recharge@"
                                      href="#"><span>积分抵扣</span></a>
            </div>
            <div class="ai-button"><a class="mod-btn mod-btn-white-34 @tenpay_v3.homepage.balance.withdraw@"
                                      href="#"><span>积分兑换</span></a>
            </div>
        </div>
    </div>

    <div class="trading-record clearfix">
        <div class="tr-title clearfix">
            <div class="tr-title-desc">最近收支</div>
            <div class="tr-title-opt">
                <a href="#"
                   class="@tenpay_v3.homepage.recentrecords.all@">全部使用情况</a>
            </div>
        </div>
        <div class="tr-list tr-loading">
            <a id="check_all" class="i-hide @tenpay_v3.homepage.recentrecords.all@"
               href="#">查询所有收支记录</a>
            <ul id="record_list">
                <script type="text/x-template" id="transTemplate">
                    <li>
                        <span class="tr-time">{tranDate}</span>
                        <span class="tr-msg">{goodsName}</span>
                        <span class="tr-money">{tranAmount}</span>
                        <span class="tr-result">{tranState}</span>
                                <span class="tr-detail">
                                    <a target="_blank" class="@tenpay_v3.homepage.recentrecords.details@"
                                       href="#">详情</a>
                                </span>
                    </li>
                </script>
            </ul>
        </div>

    </div>
</div>
</body>
</html>
<script>
    jQuery(function () {
        jQuery.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath }/showAccount_getIntegral.do",
            data: {"phone": "" + phone + ""},
            success: function (result) {
                if (result.success) {
                    jQuery('#integral').html(result.integral);
                }
            },
            dataType: "json"
        });
    });
</script>