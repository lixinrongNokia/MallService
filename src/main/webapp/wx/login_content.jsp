<%@ page pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>爱内秀-登录</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/login.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        .account_link a {
            margin-left: 2rem;
            text-decoration: none;
        }

        .footer {
            margin: 45px 0 60px !important
        }

        .footer {
            margin-bottom: 40px
        }

        .footer .info {
            font-size: 14px;
            text-align: center;
            color: #666
        }

        .footer .info .protocol {
            color: blue;
        }
    </style>
</head>

<body class="um-vp">
<div class="con" style="margin-top:3rem">
    <div class="user"></div>
    <div class="ipt">
        <input type="password" style="display:none">
        <input type="tel" autocomplete="off" placeholder="手机号" value="${phone}" id="phone"/>
    </div>
</div>
<div class="con">
    <div class="password"></div>
    <div class="ipt">
        <input type="password" style="display:none">
        <input type="password" autocomplete="off" placeholder="登陆密码" value="${nickName}" id="password"
               maxlength="16"/>
    </div>
</div>
<!--<div id="autoFouce" onclick="javascript:turnon('auto'); turnoff('autoFouce')">自动登录</div>
<div id="auto" onclick="javascript:turnoff('auto'); turnon('autoFouce')">自动登录</div>-->
<div class="btn telc" onclick="login();">立即登录</div>
<!--<div class="btn telc" ontouchstart="zy_touch('down1')" onclick="autologin();">一键登录</div>-->
<div class="account_link"><a href="javascript:" onclick=localStorage.callAddress='login';openwin('find_pwd')>找回密码</a>
</div>
<div class="footer">
    <div class="info">登陆即代表阅读并同意<label class="protocol">服务条款</label></div>
</div>
</body>
</html>
<script>
    $(function () {
        $('.protocol').click(function () {
            window.open("/protocol/index.html");
        });
    });
</script>