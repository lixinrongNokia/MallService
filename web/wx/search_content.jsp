<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>商品列表</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/classify.css">
</head>
<body class="um-vp" ontouchstart>
<div class="producList">
    <ul id="searchlist">
    </ul>
</div>
</body>
<script>
    zy_init();

    window.uexOnload = function (type) {
        if (!type) {
            searchgoods();
        }
    }
</script>
</html>