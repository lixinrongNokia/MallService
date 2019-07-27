<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>My JSP 'pushmsg.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>

</head>

<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap" style="width: 50%;height: 300px">
    <form align="center" style="margin-top: 30px" action="#" method="post" onsubmit="return regpush()">
        <div id="pushobject">
            接收的对象:<select id="pushToWho" name="pushToWho"
                          onchange="getpushObject(this.value)">
            <option selected="selected" value="all">全部</option>
            <option value="city">省份城市</option>
            <option value="person">个人</option>
        </select>
        </div>
        消息标题:<input id="title" name="title"/><br/> 消息内容:<br/>
        <textarea id="description" name="description" style="resize:none" rows="5" cols="30"></textarea>
        <br/> <input style="margin-left: 300px " type="submit" value="发送"/>
    </form>
    <span><s:property value="#pushstatus"/></span>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
