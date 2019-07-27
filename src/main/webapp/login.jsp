<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta name="misapplication-tap-highlight" content="no"/>
    <meta name="HandheldFriendly" content="true"/>
    <meta name="MobileOptimized" content="320"/>
    <title>登陆页面</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script>
        function changeVal() {
            var time = new Date();
            var regcode = document.getElementById("regcode");
            regcode.src = "createimgcode_createcode.do?time=" + time;
        }

        $(function () {
            $('#loginBtn').click(function () {
                var password = encode64($('#pwd').val());
                $('#pwd').val(password);
            });
        });
        history.go(1);
    </script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
</div>
<div id="login">
    <h2>用户登陆</h2>
    <form name="fm" method="post" action="managerLogin.do" onsubmit="return regcheck()">
        <table style="text-align: center;border-collapse:separate; border-spacing:10px;">
            <tr>
                <td>用户名</td>
                <td><input id="uname" autocomplete="off" class="input-text" type="text" name="adminmanager.nickname" placeholder="输入用户名"
                           maxlength="36" value="${nickname}"/></td>
                <td><span id="span1"></span></td>
            </tr>
            <tr>
                <td>密&nbsp;码</td>
                <td><input id="pwd" autocomplete="off" class="input-text" type="password" name="adminmanager.password" maxlength="20" value="${pwd}"/></td>
                <td><a href="findPwdFor1.do" target="_self" style="text-decoration:underline">忘记密码</a></td>
            </tr>
            <tr>
                <td>校证码</td>
                <td><input id="imgcode" class="input-text" name="imgcode" maxlength="10"/></td>
                <td><img style="cursor:pointer;" src="createimgcode_createcode.do"
                         id="regcode" onclick="changeVal()"
                         alt="点击刷新"/></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input id="loginBtn" type="image" src="images/button_login.gif"
                                                     name="submit"/></td>
                <td><span id="span2">${error}</span></td>
            </tr>
        </table>

    </form>
    <%--<input type="image" src="createimgcode_pickupCode.do?tOrder.id=313">--%>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>