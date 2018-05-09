<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>搜索列表</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <style>
        .search {
            padding-left: 5rem;
        }
    </style>
</head>
<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" ontouchstart="zy_touch('backDown')" onclick="uexWindow.close();">返回</div>
            <div class="search">
                <div class="ipt" id="title"><input placeholder="请输入搜索关键字" id="skey" readonly/>
                </div>
                <div class="searchBtn" ontouchstart="zy_touch('searchBtnDown')" onClick="voicesearch1();"></div>
            </div>
        </header>
        <ul class="tab telc">
            <li class="tabFouce" ontouchstart="zy_touch('tabDown')">人气</li>
            <li ontouchstart="zy_touch('tabDown')">信用</li>
            <li ontouchstart="zy_touch('tabDown')">价格</li>
            <li ontouchstart="zy_touch('tabDown')">销量</li>
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
<script>
    zy_init();
    window.uexOnload = function (type) {
        setValue('skey', localStorage.skey);
        uexWindow.setBounce("0");
        if (!type) {
            zy_con("content", "${pageContext.request.contextPath }/wx/search_content.jsp", 0, $$("header").offsetHeight);
        }
        window.onorientationchange = window.onresize = function () {
            zy_resize("content", 0, $$("header").offsetHeight);
        }
    }
</script>
</html>