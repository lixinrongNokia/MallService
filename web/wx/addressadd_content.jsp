<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-管理地址</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/user.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/pccs.js"></script>
</head>
<body class="um-vp">
<ul class="addressEdu">
    <li>
        <select name="province" id="province" class=" left select1">
        </select>
        <select name="city" id="city" class=" left select1">
        </select>
        <select name="county" id="county" class=" left select2">
        </select>
    </li>
    <li>
        <input id="address" placeholder="请输入街道地址"/>
        <h2>街道地址</h2>
    </li>
    <li>
        <input id="consignee" placeholder="请输入收货人姓名"/>
        <h2>收货人姓名</h2>
    </li>
    <li>
        <input id="mobile" placeholder="请输入手机号码"/>
        <h2>手机号码</h2>
    </li>
    <!-- <li style="line-height:3.875rem">设为默认地址
     <div class="right" style="margin-top:1.25rem">
       <div id="autoFouce" onclick="javascript:turnon('auto'); turnoff('autoFouce')"></div>
       <div id="auto" onclick="javascript:turnoff('auto'); turnon('autoFouce')"></div>
       </div>
     </li>-->
</ul>
<input class="footBtn" type="button" value="保存地址" onClick="saveaddress()">
</body>
<script>
    setup();
</script>
</html>