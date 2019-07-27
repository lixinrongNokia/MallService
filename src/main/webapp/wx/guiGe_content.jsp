<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>规 格</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        select {
            -webkit-box-sizing: border-box;
            width: 70%;
            margin: 0.5rem 15%;
            padding-left: 0.5em;
            height: 2.5rem;
            background: #fff;
            -webkit-box-shadow: inset 2px 3px 4px rgba(194, 194, 194, .75);
            border: solid 1px #bebebe;
            -webkit-appearance: none;
            display: block;
            overflow: hidden
        }

        .lable {
            width: 100%;
            overflow: hidden
        }

        .btn {
            width: 75%;
            height: 2.5rem;
            margin: 2rem auto;
            background: #ff2323;
            font-size: 1.125rem;
            color: #fff;
            line-height: 2.5rem;
            text-shadow: 0 2px 2px rgba(0, 0, 0, .6);
            overflow: hidden
        }
    </style>
</head>
<body class="um-vp" ontouchstart>
<select id="attr0" style="display:none">
</select>
<select id="attr1" style="display:none">
</select>
<select id="attr2" style="display:none">
</select>
<select id="attr3" style="display:none">
</select>
<select id="attr4" style="display:none">
</select>
<div class="btn telc" ontouchstart="zy_touch('down1')" onclick="selattr();" id="sub" style="display:none">确定</div>
</body>
<script>
    getgoodsattr();
</script>
</html>