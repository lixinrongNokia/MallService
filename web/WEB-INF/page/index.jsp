<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户管理</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script language="javascript" type="text/javascript">
        history.go(1);
    </script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap">
    <div class="list orderList">
        <table width="800px" align="center" style="text-align: center;margin-top: 16px">
            <tr class="title">
                <%--<th class="view">用户姓名</th>--%>
                <th>昵称</th>
                <th class="price">性别</th>
                <th class="price">出生日期</th>
                <th class="nums">邮箱</th>
                <%--<th class="price">电话</th>--%>
                <th class="price">头像路径</th>
                <th class="price">自我简介</th>
                <th>操作</th>
            </tr>
            <s:iterator value="%{#generalList.list}">
                <s:url var="userdata" value="userdataaction.do">
                    <s:param name="uid" value="uid"/>
                </s:url>
                <tr>
                        <%--<td class="thumb"><s:property value="realname"/></td>--%>
                    <td><s:property value="nickname"/></td>
                    <td class="title"><s:property value="sex"/></td>
                    <td><s:property value="birthday"/></td>
                    <td><s:property value="email"/></td>
                        <%--<td><s:property value="phone"/></td>--%>
                    <td><img style="width: 180px" src='/share/<s:property value="headimg"/>'></td>
                    <td><s:property value="signature"/></td>
                    <td><s:a href="%{userdata}">身形数据</s:a>&nbsp;
                        <a href="#" onclick="alertDialog(<s:property value="phone"/>)">发消息</a>
                    </td>
                </tr>
            </s:iterator>
        </table>

        <div class="page-spliter">
            <s:url var="url_pre" value="allUser.do">
                <s:param name="offset" value="offset-1"/>
            </s:url>
            <s:url var="index" value="allUser.do">
                <s:param name="offset" value="1"/>
            </s:url>
            <s:url var="url_next" value="allUser.do">
                <s:param name="offset" value="offset+1"/>
            </s:url>
            <s:url var="last" value="allUser.do">
                <s:param name="offset" value="totalPage"/>
            </s:url>
            <s:if test="offset>1">
                <s:a href="%{url_pre}">上一页</s:a>
            </s:if>
            <s:if test="offset!=1">
                <s:a href="%{index}">首页</s:a>
            </s:if>
            <s:if test="offset!=totalPage">
                <s:a href="%{last}">尾页</s:a>
            </s:if>
            <s:if test="offset<totalPage">
                <s:a href="%{url_next}">下一页</s:a>
            </s:if>
            第
            <s:property value="offset"/>
            页/总共
            <s:property value="totalPage"/>
            页&nbsp;
            <s:property value="totalSize"/>
            条记录
            <s:textfield id="sendVal" maxlength="3"/>
            <input type="button" onclick="regVal2(${totalSize})" value="go"/>
        </div>
    </div>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
<script>
    function alertDialog(phone) {
        var data = prompt("输入内容", "");
        if ($.trim(data).length > 0) {
            $.ajax({
                url: "sendIMToPerson.do",
                data: {"phone": "" + phone + "", "message": "" + data + ""},
                type: "POST",
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        alert('发送成功！');
                    } else {
                        alert('发送错误');
                    }
                }
            });
        }
    }
</script>
