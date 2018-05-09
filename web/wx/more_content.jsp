<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>更多</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/user.css">
</head>
<body class="um-vp" ontouchstart>
<div style="width:100%; height:1rem"></div>
<ul class="list">
    <li onclick="openwin('near');"><img
            src="${pageContext.request.contextPath }/wx/css/res-apple/dizhi.png">查看门店
    </li>
   <%-- <li ontouchstart="zy_touch('down')" onclick="sancode();"><img
            src="${pageContext.request.contextPath }/wx/css/res-apple/ewm.png">扫码查价
    </li>
    <li ontouchstart="zy_touch('down')" onclick="voicesearch();"><img
            src="${pageContext.request.contextPath }/wx/css/res-apple/search1.png">语音搜索
    </li>
    <li ontouchstart="zy_touch('down')" onclick="openwin('newsList');"><img
            src="${pageContext.request.contextPath }/wx/css/res-apple/dingdan.png">新闻中心
    </li>--%>
</ul>
<%--<ul class="list" style="text-indent:1rem">
    <div class="checkbox right">
        <input type="checkbox">
        <label data-on="是" data-off="否"></label>
    </div>
    <li>是否接受推送</li>
    <li ontouchstart="zy_touch('down')" onClick="clear1();">清空缓存</li>
    <li ontouchstart="zy_touch('down')">版本更新</li>
</ul>--%>
<!--<div class="quit" ontouchstart="zy_touch('down1')" onClick="finish()">退出应用</div>-->
</body>

</html>