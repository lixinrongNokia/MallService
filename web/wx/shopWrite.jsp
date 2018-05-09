<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-确认订单</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        .pay {
            -webkit-box-sizing: border-box;
            widht: 100%;
            height: 3rem;
            padding: 0 0.5rem;
            border-top: 1px solid #cbcbcb;
            background: #ddd;
            font-size: 0.75rem;
            line-height: 3rem;
        }

        .div1 {
            clear: both;
        }

        .pay span {
            font-size: 1.25rem;
            color: #ff5f0c;
            margin-left: 0;
            text-align: left;
        }
        .showbar{
            width: 100%;
        }

        .pay .btn {
            background: #f40;
            color: #fff;
            font-size: 1.0rem;
            text-align: center;
        }
    </style>
</head>
<body ontouchstart>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <h1>提交订单</h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
        <div class="showbar">商品总价:￥<span id="goodsTotalPrice"></span>&nbsp;邮费:￥<span id="deliverFee"></span><input type="hidden" id="orderamount"></div>
        <div class="pay">
            <div class="left">订单总价:￥<span id="total">0.00</span></div>
            <div class="btn right">提交订单</div>
            <%--openwin('shopOk');--%>
        </div>
        <!--footer结束 -->
    </div>
</div>
</body>
<script>
    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/shopWrite_content.jsp");
    });
</script>
</html>