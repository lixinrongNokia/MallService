<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-用户信息</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/user.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
</head>
<body class="um-vp">
<ul class="addressEdu">
    <li style="display:none"></li>
    <li>
        <input id="user_name" placeholder="用户昵称"/>
        <h2>用户昵称</h2>
    </li>
    <li>
        <input id="rank_name" placeholder="会员等级"/>
        <h2>会员等级</h2>
    </li>
    <li>
        <input id="email" placeholder="未填写"/>
        <h2>邮箱</h2>
    </li>
    <li>
        <input id="mobile_phone" placeholder="手机号码"/>
        <h2>手机号码</h2>
    </li>
</ul>
<!-- <input disabled class="footBtn" value="保存" ontouchstart="zy_touch('down1')" onClick="saveaddress(1);">
</body>-->
<script>
    var appUser = localStorage.getItem('appUser');
    var wxuser = localStorage.getItem('wxuser');
    if (wxuser) {
        wxuser = $.parseJSON(wxuser);
        if (wxuser.onbind) {
            initData(wxuser)
        } else {
            $("#user_name").val(wxuser.nickName);
        }
    } else {
        appUser = $.parseJSON(appUser);
        initData(appUser);
    }
    function initData(user) {
        var reg = /(\d{3})\d{4}(\d{4})/;
        var tel = user.phone.replace(reg, "$1****$2");
        $("#mobile_phone").val(tel);
        $("#user_name").val(user.nickName);
        if ('email' in user) {
            $("#email").val(user.email);
        }
        $("#rank_name").val(user.level);
    }

</script>
</body>
</html>