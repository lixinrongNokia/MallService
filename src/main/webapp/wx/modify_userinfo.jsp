<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>爱内秀-用户</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
</head>

<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <h1>修改资料</h1>
            <div class="back" onclick="openwin('user')">返回
            </div>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
    </div>
    <!--footer结束 -->
</div>
</body>
<script defer="defer">
    $('#content').load('${pageContext.request.contextPath }/wx/modify_userinfo_content.jsp');
</script>
</html>