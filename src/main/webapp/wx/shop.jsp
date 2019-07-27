<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>爱内秀-购物车</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>

        .pay {
            -webkit-box-sizing: border-box;
            widht: 100%;
            height: 3rem;
            padding: 0 0.5rem;
            border-top: 1px solid #cbcbcb;
            background: #ddd;
            font-size: 0.75rem;
            line-height: 3rem;
            text-align: right
        }

        .pay span {
            font-size: 1.25rem;
            color: #ff5f0c
        }

        .btn {
            width: 25%;
            height: 2rem;
            margin-top: 0.4375rem;
            margin-left: 0.5rem;
            -webkit-border-radius: 0.25rem;
            background: #f40;
            color: #fff;
            font-size: 1.125rem;
            text-shadow: 0 2px 2px rgba(0, 0, 0, .6);
            text-align: center;
            line-height: 2rem
        }
    </style>
</head>

<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="headerRight" onClick="ask()">清空</div>
            <h1>购物车<span></span></h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
        <div id="pay" class="pay">
            <div class="btn right" onclick="regAccount('shopWrite');">结 算</div>
            总价(不含运费)：<span id="total">￥0.00</span><span style="width: 6rem;"></span>
        </div>
        <footer>
            <ul>
                <li class="home" onclick="openwin('index');">首页
                </li>
                <li class="classify" onclick="openwin('classify');">分类</li>
                <li class="shopFouce">购物车
                    <div class="shop_num" id="shop_num" style="display:none"></div>
                </li>
                <li class="user" onclick="openwin('user');">我的</li>
                <li class="more" onclick="openwin('more');">更多</li>
            </ul>
        </footer>
        <!--footer结束 -->
    </div>
</div>
</body>
<script type="text/javascript">
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/shop_content.jsp");
    });
</script>

</html>