<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<s:if test="#SubLogistic!=null">
    <script type="javascript">
        alert("物流跟踪以订阅");
    </script>
    <%
        session.removeAttribute("SubLogistic");
    %>
</s:if>
<div id="content" class="wrap2">
    <form style="text-align: center;margin-top: 16px" action="allorder.do"
          method="post" onsubmit="return regcheckinput()" target="_parent">
        <%--<%--已取消字段--%>
        <%--<s:set var="cancel" value="@cn.iliker.mall.entity.stateattr.OrderState@CANCEL.getName()"/>--%>
        <%--待审核字段--%>
        <%--<s:set var="waitconfirm" value="@cn.iliker.mall.entity.stateattr.OrderState@WAITCONFIRM.getName()"/>--%>
        <%--已审核字段--%>
        <%--<s:set var="confirmordered" value="@cn.iliker.mall.entity.stateattr.OrderState@CONFIRMORDERED.getName()"/>--%>
        <%--已收货字段--%>
        <%--<s:set var="received" value="@cn.iliker.mall.entity.stateattr.OrderState@RECEIVED.getName()"/>--%>
        <%--等待备货--%>
        <%--<s:set var="admeasureproduct" value="@cn.iliker.mall.entity.stateattr.OrderState@ADMEASUREPRODUCT.getName()"/>--%>
        <%--等待发货字段--%>
        <s:set var="waitdeliver" value="@cn.iliker.mall.entity.stateattr.OrderState@WAITDELIVER.getName()"/>
        <%--退款中--%>
        <%--<s:set var="refunding" value="@cn.iliker.mall.entity.stateattr.OrderState@REFUNDING.getName()"/>--%>
        <%--已关闭--%>
        <%--<s:set var="shutdown" value="@cn.iliker.mall.entity.stateattr.OrderState@SHUTDOWN.getName()"/>--%>
        <select id="propertyName" name="propertyName"
                onchange="listenerChange()">
            <option selected="selected" value="all">--请选择--</option>
            <option value="tradeNo">交易号</option>
            <option value="orderid">订单号</option>
            <option value="waitpayment">等待付款</option>
            <option value="waitconfirm">待审核</option>
            <option value="packaging">等待配货</option>
            <option value="waitdeliver">等待发货</option>
            <option value="cancel">已取消</option>
            <option value="delivered">已发货</option>
            <option value="received">已收货</option>
            <option value="refunding">退款中</option>
        </select>&nbsp;<input disabled="disabled" id="queryVal" name="queryVal"/> <input type="image"
                                                                                         src="images/button_search.gif"/>
    </form>
    <s:if test="#orders==null||#orders.size==0">
        <div style="margin:auto ;width: 50%;height: 200px;">
            <span style="color:red;">没有相关信息</span>
        </div>
    </s:if>
    <s:else>
        <div class="list orderList">
            <table>
                <tr class="title">
                    <th></th>
                    <th>订单编号</th>
                    <th>商品总数量</th>
                    <th>收货人</th>
                    <th>付款方式/配送方式</th>
                    <th>收货地址/自提点</th>
                    <th>联系电话</th>
                    <th>订单总金额</th>
                    <th>下单时间</th>
                    <th>支付状态</th>
                    <th>交易号</th>
                    <th>订单状态</th>
                    <th>操作</th>
                </tr>
                <s:iterator value="%{#orders}">
                    <%--订单详情--%>
                    <s:url var="orderdetail" value="orderdetailinfo.do">
                        <s:param name="id" value="id"/>
                    </s:url>
                    <tr>
                        <td><input type="hidden" name="id"
                                   value="<s:property value="id"/>"/></td>
                        <td><s:property value="orderid"/></td>
                        <td class="thumb"><s:property value="orderamount"/></td>
                        <td><s:property value="recevername"/></td>
                        <td><s:property value="paymethod"/><br/> <s:property
                                value="postmethod"/></td>
                        <td><s:property value="receveraddr"/> <s:property
                                value="point"/></td>
                        <td><s:property value="recevertel"/></td>
                        <td><s:property value="toalprice"/></td>
                        <td><s:date name="orderdate" format="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <s:if test="paymentstate"><span style="color:green">已支付<br/>&#40;<s:property
                                    value="paymentTool"/> &#41;</span></s:if>
                            <s:else><span style="color:yellow">等待付款</s:else>
                        </td>
                        <td><s:property value="tradeNo"/></td>
                        <td><s:property value="orderstate"/></td>
                        <td><s:a href="%{orderdetail}">订单详情</s:a>
                        </td>
                    </tr>
                </s:iterator>
            </table>
            <div class="page-spliter">
                    <%--上一页 --%>
                <s:url var="url_pre" value="allorder.do">
                    <s:param name="offset" value="offset-1"/>
                </s:url>
                <s:url var="index" value="allorder.do">
                    <s:param name="offset" value="1"/>
                </s:url>
                    <%--下一页 --%>
                <s:url var="url_next" value="allorder.do">
                    <s:param name="offset" value="offset+1"/>
                </s:url>
                <s:url var="last" value="allorder.do">
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
                    <%--<s:textfield id="sendVal" maxlength="3"/><input type="button" onclick="regVal(${totalPage})" value="go"/>--%>
            </div>
                <%--<div class="button"><input class="input-gray" type="submit" name="submit" value="查看一个月前的订单" /><input class="input-gray" type="submit" name="submit" value="查看一个月前的订单" /></div> --%>
        </div>
    </s:else>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
