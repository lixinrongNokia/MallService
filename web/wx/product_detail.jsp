<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>商品详情</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        html body img {
            width: 100%;
            margin: 0;
            padding: 0;
        }

        * {
            margin: 0;
            padding: 0;
            border: 0;
        }

        img {
            float: left;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>商品详情
            <div class="back" onclick="responseBack()">返回</div>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content">
        <ul class="lists"></ul>
    </div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">

    </div>
    <!--footer结束 -->
</div>
</body>
<script>
    var href = location.href;
    href = href.substr(href.lastIndexOf('=') + 1, href.length);
    if (href == 'null'||href=='undefined') {
        jQuery('.lists').html('没有更多内容');
    } else {
        var pic = href.split("#");
        var tag = '';
        for (var i = 0; i < pic.length; i++) {
            tag += '<li><img src="/goodsimg/' + pic[i] + '"/></li>';
        }
        jQuery('.lists').html(tag);
    }

</script>
</html>