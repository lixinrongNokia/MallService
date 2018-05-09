<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>附近门店</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/near.css">
    <script src="${pageContext.request.contextPath }/wx/js/zy_control.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_click.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_json.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/zy_tmpl.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/ext.js"></script>
</head>
<body class="um-vp" ontouchstart>
<div class="storeTitle">
    <h1 id="shoptitle"></h1>
    <p id="shopdate"></p>
    <p id="shopaddress"></p>
</div>
<div class="discount">
    <ul id="actlist">
    </ul>
</div>
</body>
<script>
    zy_init();
    window.uexOnload = function (type) {
        if (!type) {
            getshopdetial();
        }
    }
</script>
</html>