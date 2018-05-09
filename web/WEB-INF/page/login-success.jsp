<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>登陆成功</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script language="javascript" type="text/javascript">
        history.go(1);
    </script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
</div>
<div id="content" class="wrap">
    <div class="wrap" style="text-align: center">
        <p style="margin-top: 30px">你上次登录的时间是: <s:property value="footprint"/></p>
    </div>
    <div class="success">
        <div class="information">
            <p>恭喜：登陆成功！</p>
            <p><a href="main.do">点此进入首页&gt;&gt;</a></p>
        </div>
    </div>
</div>
<div id="footer" style="" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
