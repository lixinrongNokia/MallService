<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户管理</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap">
    <div class="list orderList">
        <s:if test="#request.userdata==null">
            <div style="width: 50%;height: 200px;">
                <span style="color:green;">没有数据</span>
            </div>
        </s:if>
        <s:else>
            <table width="800px" align="center"
                   style="text-align: center;margin-top: 16px">
                <tr class="title">
                    <th>身高</th>
                    <th>上胸围</th>
                    <th>下胸围</th>
                    <th>腰围</th>
                    <th>臀围</th>
                    <th>体重</th>
                    <th>身型分类</th>
                    <th>bmi</th>
                    <th>杯型</th>
                    <th>标签</th>
                    <th>裤码</th>
                    <th>同步时间</th>
                </tr>
                <s:iterator value="%{userdata}">
                    <tr>
                        <td class="thumb"><s:property value="height"/></td>
                        <td class="title"><s:property value="onchest"/></td>
                        <td><s:property value="underchest"/></td>
                        <td><s:property value="waist"/></td>
                        <td><s:property value="hip"/></td>
                        <td><s:property value="weight"/></td>
                        <td><s:property value="bodytype"/></td>
                        <td><s:property value="bmi"/></td>
                        <td><s:property value="cuptype"/></td>
                        <td><s:property value="tags"/></td>
                        <td><s:property value="pants"/></td>
                        <td><s:property value="syntime"/></td>
                    </tr>
                </s:iterator>
            </table>
        </s:else>
        <%-- <div class="page-spliter">
            <a href="#">&lt;</a>
            <a href="#">首页</a>
            <span class="current">1</span>
            <a href="#">2</a>
            <a href="#">3</a>
            <a href="#">4</a>
            <span>...</span>
            <a href="#">尾页</a>
            <a href="#">&gt;</a>
        </div> --%>
    </div>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
