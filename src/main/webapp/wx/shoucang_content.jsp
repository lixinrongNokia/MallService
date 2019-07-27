<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>我的收藏</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <style>
        body {
            background: #f5f5f5;
        }

        .list li {
            -webkit-box-sizing: border-box;
            width: 100%;
            height: 6rem;
            border-bottom: 1px dashed #c2bbae;
            line-height: 1em;
            position: relative;
            overflow: hidden;
        }

        .goods {
            -webkit-box-sizing: border-box;
            height: 6rem;
            margin-right: 2.5rem;
            padding: 0.5rem 0 0.5rem 0.5rem;
            font-size: 0.875rem;
            color: #333;
            word-break: break-all;
            overflow: hidden;
        }

        .goods img {
            -webkit-box-sizing: border-box;
            width: 5rem;
            height: 5rem;
            margin-right: 0.25rem;
            border: 1px solid #c2bbae;
            float: left;
        }

        .goods p {
            margin: 0 0 0.325rem;
            line-height: 1.5em;
            color: #343434
        }

        .goods strong {
            font-size: 1.125rem;
            color: #f50
        }

        .goods span {
            margin-left: 0.5em;
            font-size: 0.875rem;
            text-decoration: line-through
        }

        .listRight {
            width: 2.5rem;
            height: 6rem;
            overflow: hidden;
            position: absolute;
            top: 0;
            right: 0;
            z-index: 999;
        }

        .listRight div {
            width: 2.5rem;
            height: 2.5rem;
            background-size: 2rem auto !important;
            text-indent: -100rem
        }

        .listRight div:nth-child(1) {
            margin-top: 0.5rem;
            background: url(css/res-apple/addShop.png) no-repeat center
        }

        .listRight div:nth-child(2) {
            background: url(css/res-apple/del.png) no-repeat center
        }

        .shopDown {
            background: url(css/res-apple/addShop_down.png) no-repeat center !important;
        }

        .delDown {
            background: url(css/res-apple/del_down.png) no-repeat center !important;
        }
    </style>
</head>
<body class="um-vp" ontouchstart>
<div class="list">
    <ul id="favlist">

    </ul>
</div>
</body>
<script>
</script>
</html>