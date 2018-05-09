<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>爱内秀-我的账户</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
</head>

<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <h1>我的账户</h1>
            <div class="headerRight" style="width:5rem" onclick="userexit();">退出登录
            </div>
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
                <li class="home" ontouchstart="zy_touch('navDown')" onclick="openwin('index');">首页</li>
                <li class="classify" ontouchstart="zy_touch('navDown')" onclick="openwin('classify');">分类</li>
                <li class="shop" ontouchstart="zy_touch('navDown')" onclick="openwin('shop');">购物车
                    <div class="shop_num" id="shop_num" style="display:none"></div>
                </li>
                <li class="userFouce">我的</li>
                <li class="more" ontouchstart="zy_touch('navDown')" onclick="openwin('more');">更多</li>
            </ul>
        </footer>
    </div>
    <!--footer结束 -->
</div>
</body>
<script type="text/javascript">
    $(function () {
        var openid = localStorage.getItem('openid');
        var appUser = localStorage.getItem("appUser");
        var wxuser = localStorage.getItem('wxuser');
        if (openid || appUser) {
            $(function () {
                $("#content").load("${pageContext.request.contextPath }/wx/user_content.jsp");
            });
        } else {
            localStorage.callAddress = 'user';
            openwin('login');
        }
    });
</script>

</html>