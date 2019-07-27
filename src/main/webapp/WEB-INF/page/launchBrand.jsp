<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>品牌添加</title>

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
<div id="content" class="wrap">
    <form style="margin-top: 30px" action="launchbrand.do" enctype="multipart/form-data" method="post" onsubmit="">
        <table align="center" id="testTable">
            <tr>
                <td class="right">品牌名称:</td>
                <td class="left"><input maxlength="20" id="brandName" name="brandName" type="text"/><label
                        id="label1"></label></td>
            </tr>
            <tr>
                <td class="right">品牌图片 :</td>
                <td class="left"><input id="imgUrl" name="brandImg" type="file"/> <span>(允许GIF,PNG图片)</span><label
                        id="label6"></label></td>
            </tr>
            <tr>
                <td colspan="2"><input style="margin-left:200px" type="submit"
                                       value="添加" id="submit"/></td>
                <s:property value="saveerror"/>
            </tr>
        </table>
    </form>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
