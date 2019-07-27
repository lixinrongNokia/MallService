<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>宝贝详情</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        .btn,
        .btn1 {
            width: 66%;
            height: 2.5rem;
            background: #ff4400;
            margin-bottom: 0.125rem;
            -webkit-border-radius: 0.25rem;
            color: #fff;
            text-align: center;
            line-height: 2.5rem
        }
        .btn_down {
            background: #c00000;
        }

        .btn1 {
            width: 32%;
        }
    </style>
</head>

<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <div class="headerRight -mob-share-open">分享</div>
            <!--MOB SHARE BEGIN-->
            <div class="-mob-share-ui" style="display: none">
                <ul class="-mob-share-list">
                    <li class="-mob-share-weibo"><p>新浪微博</p></li>
                    <li class="-mob-share-qzone"><p>QQ空间</p></li>
                    <li class="-mob-share-qq"><p>QQ好友</p></li>
                    <li class="-mob-share-weixin"><p>微信</p></li>
                    <li class="-mob-share-douban"><p>豆瓣</p></li>
                    <li class="-mob-share-facebook"><p>Facebook</p></li>
                    <li class="-mob-share-twitter"><p>Twitter</p></li>
                    <li class="-mob-share-youdao"><p>有道云笔记</p></li>
                    <li class="-mob-share-pengyou"><p>朋友网</p></li>
                </ul>
                <div class="-mob-share-close">取消</div>
            </div>
            <div class="-mob-share-ui-bg"></div>
            <script id="-mob-share"
                    src="http://f1.webshare.mob.com/code/mob-share.js?appkey=1b3052a73a680"></script>

            <!--MOB SHARE END-->
            <h1>宝贝展示</h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
        <div class="btn left">加入购物车</div>
        <div class="btn1 right">马上购买</div>
    </div>
</div>
<!--footer结束 -->
</div>
</body>

<script type="text/javascript">
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/product_content.jsp");
    });
</script>

</html>