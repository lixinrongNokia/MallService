<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>爱内秀-分类</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        /*5+ 引擎环境下自动隐藏无关元素*/
        .html5plus .html5plus-hide {
            display: none
        }
    </style>
</head>

<body>
<script>
    if(navigator.userAgent.indexOf("Html5Plus") > -1) {
        document.body.classList.add("html5plus");
    }
</script>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header" class="html5plus-hide">
        <header>
            <h1>分类导航</h1>
            <div class="searchSub" onclick="voicesearch();"></div>
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
                <li class="home" onclick="openwin('index');">首页
                </li>
                <li class="classifyFouce">分类</li>
                <li class="shop" onclick="openwin('shop');">购物车
                    <div class="shop_num" id="shop_num" style="display:none"></div>
                </li>
                <li class="user" onclick="openwin('user');">我的</li>
                <li class="more" onclick="openwin('more');">更多</li>
            </ul>
        </footer>
    </div>
    <!--footer结束 -->
</div>
</body>
<script type="text/javascript">
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/classify_content.jsp");
    });
</script>

</html>