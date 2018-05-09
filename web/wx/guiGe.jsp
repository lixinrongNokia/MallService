<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>规格选择</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
</head>
<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <h1>规 格</h1>
            <div class="back" ontouchstart="zy_touch('backDown')" onclick="responseBack()">返回</div>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
    </div>
    <!--footer结束 -->
</div>
</body>
<script>
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/guiGe_content.jsp");
    });
</script>
</html>