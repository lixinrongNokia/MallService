<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>活动发布</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<%
    String goodid = request.getParameter("goodid");
%>
<script type="text/javascript">
    function reginput() {
        return $("#count").val() != "";
    }
</script>
<div id="content" class="wrap">
    <div class="list bookList">
        <form style="margin-top: 30px" action="launchFlashSale.do" method="post" onsubmit="return reginput()">
            <table align="center" id="testTable">
                <tr>
                    <td class="right">商品货号 :</td>
                    <td class="left"><input name="flashsale.goods.id" readonly="readonly" value="<%=goodid%>"/></td>
                </tr>
                <tr>
                    <td class="right">开始时间</td>
                    <td class="left">
                        <select name="flashsale.startTime">
                            <option selected="selected" value="8">08:00</option>
                            <option value="12">12:00</option>
                            <option value="16">16:00</option>
                            <option value="20">20:00</option>
                        </select></td>
                </tr>
                <tr>
                    <td class="right">结束时间</td>
                    <td class="left">
                        <select name="flashsale.endTime">
                            <option selected="selected" value="12">12:00</option>
                            <option value="16">16:00</option>
                            <option value="20">20:00</option>
                            <option value="0">00:00</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="right">数量</td>
                    <td class="left"><input name="flashsale.count" id="count" type="text"/></td>
                </tr>
                </tr>
                <tr>
                    <td class="right">折扣价</td>
                    <td class="left">
                        <select name="flashsale.discount">
                            <option value="0.45">45折</option>
                            <option value="0.5">5折</option>
                            <option value="0.6">6折</option>
                            <option value="0.7">7折</option>
                            <option value="0.8">8折</option>
                            <option value="0.85">85折</option>
                            <option value="0.9">9折</option>
                            <option selected="selected" value="0.9">9折</option>
                        </select></td>
                </tr>

            </table>
            <input style="margin-left:auto;margin-right:auto; width: 960px;height: 50px" type="submit" value="添加"/>
            <s:property value="message"/>

        </form>
    </div>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
