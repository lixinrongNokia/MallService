<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="audio/mpeg; charset=utf-8"/>
    <title>评论列表</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div style="margin-top: 56px" id="content" class="wrap">

    <div class="list orderList">
        <table>
            <tr class="title">
                <th class="orderId">评论编号</th>
                <th class="userName">评论人昵称</th>
                <th class="price">音频评论路径</th>
                <th class="createTime">文本评论内容</th>
                <th class="status">评论时间</th>
            </tr>
            <s:iterator value="%{#comms}">
                <tr>
                    <td><s:property value='id'/></td>
                    <td><s:property value='nickname'/></td>
                    <td><s:if test="commaudiopath!=''">
                        <%--<audio controls="controls">
                            <source src='/sound/<s:property value="commaudiopath"/>'
                                    type="audio/mpeg">

                        </audio>--%>
                        <audio src="/sound/<s:property value='commaudiopath'/>" controls="controls">
                            您的浏览器不支持 audio 标签
                        </audio>
                    </s:if></td>
                    <td><textarea style="resize:none" rows="3" cols="20" readonly="readonly"><s:property
                            value='commtext'/></textarea></td>
                    <td><s:date name="commtime" format="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </s:iterator>
        </table>

    </div>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
