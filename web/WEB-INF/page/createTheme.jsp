<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>主题活动创建</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/onlink.js"></script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div align="center">
    <form action="createTheme.do" method="post" onsubmit="return themeRegInput() " enctype="multipart/form-data">
        <table>
            <tr>
                <td>活动标题</td>
                <td><input name="theme.themeName" id="themeName"></td>
            </tr>
            <tr>
                <td>活动简介</td>
                <td>
                    <textarea style="resize:none" rows="3"
                              cols="20" maxlength="200" id="introduction"
                              name="theme.introduction"></textarea>
                </td>
            </tr>
            <tr>
                <td>活动彩页</td>
                <td><input name="themeURL" id="themeURL" type="file" onchange="PreviewImage(this,this.id)"></td>
            </tr>
            <tr>
                <td>活动开始时间</td>
                <%--html5日期时间控件属性--%>
                <td><input id="startTime" type="date" name="theme.startTime"></td>
            </tr>
            <tr>
                <td>活动结束时间</td>
                <td><input id="endTime" type="date" name="theme.endTime"></td>
            </tr>
            <tr>
                <th colspan="2"><input type="submit" value="生成"></th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
