<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>新闻列表</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/news.css">
    <script src="${pageContext.request.contextPath }/wx/js/zy_control.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_click.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_json.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_tmpl.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/ext.js"></script>
</head>
<body class="um-vp" ontouchstart>
<div class="list">
    <ul id="newslist">

    </ul>
</div>
</body>
<script>
    zy_init();
    window.uexOnload = function (type) {
        if (!type) {
            uexWindow.setBounce("1");
            uexWindow.showBounceView("0", "none", "0");
            uexWindow.showBounceView("1", "none", "0");
            getnewslist();
        }
    }
</script>
</html>