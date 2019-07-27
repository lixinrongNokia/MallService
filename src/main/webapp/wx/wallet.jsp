<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>爱内秀-账户管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
    <style type="text/css">
        a {
            text-decoration: blink;
        }

        .clearfix {
            width: 100%;
            margin-bottom: 1px;
            list-style-type: none;
        }

        .clearfix li {
            text-align: center;
            float: left;
            width: 48%;
            height: auto;
        }

        #content1 {
            clear: left;
        }
    </style>
</head>

<body>
<div class="main-menu js-test-menu" id="heder_nav_wrap">
    <ul class="clearfix">
        <li id="nav_my_cft" data-type="main">
            <a href="#content1">我的余额 </a>
        </li>
        <li id="nav_seting_account" data-type="seting_account">
            <a href="#content1">账户设置</a>
        </li>
    </ul>
    <!-- 内容 [[ -->
    <div id="content1">

    </div>
</div>

</body>
</html>
<script>
    jQuery('#heder_nav_wrap').tabs();
    var selectid = 0;
    jQuery('#content1').load('${pageContext.request.contextPath }/wx/walletView.jsp');
    jQuery(function () {
        jQuery('.clearfix li a').click(function () {
            var a = jQuery(this);
            var index = a.parent().index();
            if (selectid == index) {
                return;
            }
            selectid = index;
            switch (selectid) {
                case 0:
                    jQuery('#content1').load('${pageContext.request.contextPath }/wx/walletView.jsp');
                    break;
                case 1:
                    jQuery('#content1').load('${pageContext.request.contextPath }/wx/accountSetting.jsp');
                    break;
            }
        });
    });
</script>