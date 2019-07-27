<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>商品列表</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        .search {
            padding-left: 5rem;
        }
    </style>
</head>

<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" ontouchstart="zy_touch('backDown')" onclick="history.go(-1);">返回</div>
            <div class="search">
                <div class="ipt">
                    <input placeholder="请输入搜索关键字" id="skey"/>
                </div>
                <div class="searchBtn" onclick="searchkey();"></div>
            </div>
        </header>
        <ul class="tab telc">
            <li class="tabFouce">人气</li>
            <li>信用</li>
            <li>价格</li>
            <li>销量</li>
        </ul>
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
<script type="text/javascript">
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/productList_content.jsp");
    });
</script>

</html>