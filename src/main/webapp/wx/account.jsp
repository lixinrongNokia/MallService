<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>爱内秀-账户</title>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        ul {
            list-style: none;
        }

        .common-wrapper {
            width: 100%;
            position: relative;
            max-width: 640px;
            min-width: 320px;
            overflow: hidden;
            margin: 0 auto;
            font-size: 16px;
            background-color: white;
            padding-bottom: 10px;
        }

        .money-nav {
            background: #fff;
            overflow: hidden;
            height: 2.15625em;
            background: -moz-linear-gradient(top, #fff, #ddd);
            background: -webkit-gradient(linear, 50% 0, 50% 100%, from(#fff), to(#ddd));
            background: -webkit-linear-gradient(top, #fff, #ddd);
            background: -o-linear-gradient(top, #fff, #ddd);
            background-image: linear-gradient(180deg, #fff, #eee 50%, #ddd 50%);
            background-size: 100% 1px;
            background-repeat: no-repeat;
            background-position: bottom;
            background-color: #fff;
            display: -webkit-box;
            display: -moz-box;
        }

        .money-nav li {
            line-height: 2em;
            text-align: center;
            position: relative;
            height: 100%;
            display: block;
            -webkit-box-flex: 1;
            -moz-box-flex: 1;
        }

        .money-nav li a {
            display: block;
            color: #a5a5a5;
            text-decoration: none;
            font-size: .8em;
            width: 100%;
        }

        .money-nav li a.nav-cur {
            color: #ed4e51;
        }

        .money-nav li a.nav-cur:after {
            position: absolute;
            content: "";
            width: 80%;
            height: .1154em;
            background: #f24b48;
            bottom: -1px;
            left: 50%;
            margin-left: -40%;
        }

        .nav-cur {
            color: #f24b48;
        }
    </style>
</head>

<body id="body">
<div id="main" class="box-ver box">
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <h1>我的账户</h1>
        </header>
    </div>

    <div class="common-wrapper">
        <ul class="money-nav">
            <li><a href='javascript:' class="nav-cur">余额</a></li>
            <li><a href='javascript:'>积分</a></li>
            <li><a href='javascript:'>卡券</a></li>
        </ul>
        <div id="content">
        </div>
    </div>
</div>
</body>
</html>
<script>
    var appUser = $.parseJSON(localStorage.getItem("appUser"));
    var wxuser = $.parseJSON(localStorage.getItem("wxuser"));
    var user = appUser != null ? appUser : wxuser;
    var phone = user.phone;
    var currentid = 0;
    $(function () {
        $('#content').load('${pageContext.request.contextPath }/wx/wallet.jsp');
        $('.money-nav li a').click(function () {
            var li = $(this);
            var selectid = li.parent().index();
            if (currentid == selectid) {
                return;
            }
            currentid = selectid;
            $('.money-nav li a').removeClass('nav-cur');
            li.addClass('nav-cur');
            switch (currentid) {
                case 0:
                    $('#content').load('${pageContext.request.contextPath }/wx/wallet.jsp');
                    break;
                case 1:
                    $('#content').load('${pageContext.request.contextPath }/wx/integral_account.jsp');
                    break;
                case 2:
                    $('#content').load('${pageContext.request.contextPath }/wx/giveaccount.jsp?phone=' + phone);
                    break;
            }
        });
    });
</script>
