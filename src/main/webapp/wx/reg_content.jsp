<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-注册</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/login.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script src='${pageContext.request.contextPath }/wx/js/layer.js'></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
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
<input type="hidden" id="code"/>
<input type="hidden" id="backPhone"/>
<input type="hidden" id="time"/>
<ul class="reg">
    <li><span>手机号</span>
        <div class="ipt">
            <input style="width: 60%;" type="tel" autocomplete="off" placeholder="有效手机号" id="phoneNum"/>
            <input style="width: 40%;background-color: #FF0000;float: right;font-size: medium;border-radius: 10px;margin: 0;" id="getSMSCodeBtn" type="button" value="获取验证码"/>
        </div>
    </li>
    <li><span>验证码</span>
        <div class="ipt"><input type="text" autocomplete="off" placeholder="短信验证码" id="smscode"></div>
    </li>
    <li><span>昵 称</span>
        <div class="ipt"><input type="text" autocomplete="off" style="font-size: 16px;" placeholder="不以数字开头的汉字.拼音.字母组合" maxlength="10" id="nickname"></div>
    </li>
    <li><span>密 码</span>
        <div class="ipt"><input type="password" autocomplete="off" maxlength="16" placeholder="密码" id="password"></div>
    </li>
</ul>
<div class="btn telc" onclick="reguser();">注 册</div>
<div class="footer">
    <div class="info">注册即代表阅读并同意<label class="protocol">服务条款</label></div>
</div>
</body>
<script>
    $(function () {
        $("#getSMSCodeBtn").bind("click", getsmscode);
        $('.protocol').click(function () {
            window.open("/protocol/index.html");
        });
    });
</script>
</html>