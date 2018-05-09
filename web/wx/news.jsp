<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>新闻</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/wx/js/zy_control.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_click.js"></script>
</head>
<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <h1>新闻</h1>
            <div class="back" ontouchstart="zy_touch('backDown')" onclick="uexWindow.close();">返回</div>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer"></div>
    <!--footer结束 -->
</div>
</body>
<script>
    zy_init();
    function openage(windowname) {
        uexWindow.open(windowname, '0', windowname + '.jsp', 0, '', '', 0x0);
    }
    window.uexOnload = function (type) {
        uexWindow.setBounce("0");
        if (!type) {
            zy_con("content", "${pageContext.request.contextPath }/wx/news_content.jsp", 0, $$("header").offsetHeight);
        }
        window.onorientationchange = window.onresize = function () {
            zy_resize("content", 0, $$("header").offsetHeight);
        }
    }
</script>
</html>