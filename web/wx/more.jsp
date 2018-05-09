<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>更多</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
</head>

<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <h1>更多</h1>
            <%--<div class="headerRight" style="width:5rem" ontouchstart="zy_touch('backDown')" onclick="userexit();">退出登录
            </div>--%>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
        <footer>
            <ul>
                <li class="home" onclick="openwin('index');">首页</li>
                <li class="classify" onclick="openwin('classify');">分类</li>
                <li class="shop" onclick="openwin('shop');">购物车
                    <div class="shop_num" id="shop_num" style="display:none"></div>
                </li>
                <li class="user" onclick="openwin('user');">我的</li>
                <li class="moreFouce">更多</li>
            </ul>
        </footer>
    </div>
    <!--footer结束 -->
</div>
</body>
<script type="text/javascript">
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/more_content.jsp");
    });
</script>


</html>