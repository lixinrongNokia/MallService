<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://iliker.cn/MallService" prefix="iliker" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <style type="text/css">
        .box a {
            padding-right: 15px;
        }

        .layer_text {
            background-color: #fff;
            padding: 20px;
        }

        .layer_text p {
            margin-bottom: 10px;
            text-indent: 2em;
            line-height: 23px;
        }

        .photos-demo img {
            width: 200px;
        }
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer.js"></script>
    <script type="text/javascript">
        function printorder() {
            document.getElementById("printbtn").style.display = "none";
            document.getElementById("footer").style.display = "none";
            window.print();
        }

        /*取消订单*/
        function cancelOrder(orderId) {
            $.ajax({
                type: "POST", url: "cancelOrder.do", data: {"id": orderId}, dataType: "json", success: function (data) {
                    if (data.success) {
                        message('订单已取消');
                    }
                }
            });
        }

        /*审核订单*/
        function confirmOrdering(orderId) {
            $.ajax({
                type: "POST",
                url: "confirmOrdering.do",
                data: {"id": orderId},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        message('订单有效，请财务确认订单已付款');
                    }
                }
            });
        }

        /*确认订单已付款*/
        function confirmPayment(orderId) {
            $.ajax({
                type: "POST",
                url: "confirmPayment.do",
                data: {"id": orderId},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        message('确认已付款，请通知备货');
                    }
                }
            });
        }

        /*订单退款并取消订单*/
        function refundsOrder(orderId) {
            $.ajax({
                type: "POST",
                url: "transferAppAccount.do",
                data: {"id": orderId},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        message('货款已退回客户app账户');
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        }

        /*订单完成配货*/
        function prepareDelivery(orderId) {
            $.ajax({
                type: "POST",
                url: "prepareDelivery.do",
                data: {"id": orderId},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        message('备货完毕通知发货');
                    }
                }
            });
        }

        function message(text) {
            layer.msg(text, {
                time: 0 //不自动关闭
                , btn: ['知道了']
                , yes: function (index) {
                    window.location.reload();
                }
            });
        }

        function rejectRefunds(orderId) {
            var memo = prompt("拒绝理由:", "");
            if ($.trim(memo).length > 0) {
                $.ajax({
                    url: "rejectRefunds.do",
                    data: {"tOrder.id": orderId, "tOrder.memo": "" + memo + ""},
                    type: "POST",
                    dataType: "json",
                    success: function (result) {
                        if (result.success) {
                            message('已处理！');
                        } else {
                            message('发送错误');
                        }
                    }
                });
            }
        }
    </script>
</head>

<body>
<s:if test="%{#orderinfo.orderstate==#waitdeliver}">
    <div id="printbtn" class="v-h">
        <input name="" type="button" value="打印"
               onclick="printorder()"/>
    </div>
</s:if>
<div id="header" class="wrap">
    <div id="logo"></div>
    <div>
        <p>订购人电话:${orderinfo.phone }&nbsp;&nbsp;支付状态:
            <s:if test="%{#orderinfo.paymentstate}"><span
                    style="color:green;font-size:large;font-weight: bold">已支付</span></s:if>
            <s:else><span style="color:yellow">等待付款</span></s:else>
            &nbsp;&nbsp;订单状态:${orderinfo.orderstate }
        </p>
    </div>
    <div>
        <table width="100%" border="1" cellspacing="0" cellpadding="0"
               class="tb1">

            <tr>
                <td class="t1"><strong>订单编号：</strong>${orderinfo.orderid }</td>
                <td class="t2"><strong>订购时间：</strong>${orderinfo.orderdate }</td>
            </tr>


            <tr>
                <td class="t1"><strong>收货人：</strong>${orderinfo.recevername }</td>
                <td class="t2"><strong>联系方式：</strong>${orderinfo.recevertel }</td>
            </tr>
            <tr>
                <td colspan="2" class="t8"><strong>收货地址/(自提点)：</strong>${orderinfo.receveraddr }
                    <s:if test="orderinfo.storeInfo!=null">${orderinfo.storeInfo.address }</s:if></td>
            </tr>
        </table>
    </div>
    <div>
        <table style="text-align: center;margin-top: 16px" width="100%"
               border="1" cellspacing="5" cellpadding="5" class="tb4">
            <thead>
            <tr>
                <td colspan="6">订单项目</td>
            </tr>
            </thead>
            <tr>
                <th class="t3">商品编号</th>
                <th class="t4">商品名称</th>
                <th class="t5">订购数量</th>
                <th class="t5">订购颜色</th>
                <th class="t5">订购尺寸</th>
                <th class="t7">小计</th>
            </tr>
            <s:set var="orderdetails" value="%{#orderinfo.orderdetails}"/>
            <s:iterator value="%{#orderdetails}">
                <tr>
                    <td><s:property value="goods.goodCode"/></td>
                    <td>
                        <div class="p-name">
                            <s:property value="goods.goodName"/>
                        </div>
                    </td>
                    <td><s:property value="orderamount"/></td>
                    <td><s:property value="color"/></td>
                    <td><s:property value="size"/></td>
                    <td>&yen;<s:property value="saletotalprice"/></td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <div>
        <div class="d1">
            商品总金额：&yen;<span style="color:#FF0000">${orderinfo.toalprice}</span>
            运费：&yen;<span style="color:#FF0000">${orderinfo.deliverFee}</span>
        </div>
        <div class="d2">
            <strong>订单支付金额：&yen;<span style="color:#FF0000">${orderinfo.toalprice}</span></strong>
        </div>
    </div>
    <%--已取消字段--%>
    <s:set var="cancel" value="@cn.iliker.mall.entity.stateattr.OrderState@CANCEL.getName()"/>
    <%--待审核字段--%>
    <s:set var="waitconfirm" value="@cn.iliker.mall.entity.stateattr.OrderState@WAITCONFIRM.getName()"/>
    <%--已审核字段--%>
    <s:set var="confirmordered" value="@cn.iliker.mall.entity.stateattr.OrderState@CONFIRMORDERED.getName()"/>
    <%--已收货字段--%>
    <s:set var="received" value="@cn.iliker.mall.entity.stateattr.OrderState@RECEIVED.getName()"/>
    <%--等待备货--%>
    <s:set var="admeasureproduct"
           value="@cn.iliker.mall.entity.stateattr.OrderState@ADMEASUREPRODUCT.getName()"/>
    <%--等待发货字段--%>
    <s:set var="waitdeliver" value="@cn.iliker.mall.entity.stateattr.OrderState@WAITDELIVER.getName()"/>
    <%--退款中--%>
    <s:set var="refunding" value="@cn.iliker.mall.entity.stateattr.OrderState@REFUNDING.getName()"/>
    <%--已关闭--%>
    <s:set var="shutdown" value="@cn.iliker.mall.entity.stateattr.OrderState@SHUTDOWN.getName()"/>
    <%--不允许订单退款的条件--%>
    <%--<s:set var="shutdowns" value="@cn.iliker.mall.entity.stateattr.OrderState@allowsRefunds"/>--%>
    <%--门店自提方式--%>
    <s:set var="storePoint" value="@cn.iliker.mall.entity.stateattr.PostMethod@POINT.getName()"/>
    <%--取消订单并退款--%>
    <s:if test="%{#orderinfo.paymentstate&&#orderinfo.orderstate==#refunding}">
        <iliker:permission module="order" privilege="refundAppAccount">
            <input type="button" value="退回款项至用户app账户" onclick="refundsOrder(${orderinfo.id})"/>
            <input type="button" value="拒绝退款" onclick="rejectRefunds(${orderinfo.id})"/>
        </iliker:permission>
    </s:if>
    <%--取消订单--%>
    <s:if test="%{!#orderinfo.paymentstate&&#orderinfo.orderstate!=#cancel}">
        <iliker:permission module="order" privilege="cancelOrder">
            <input type="button" value="取消订单" onclick="cancelOrder(${orderinfo.id})"/>
        </iliker:permission>
    </s:if>

    <%--审核订单--%>
    <s:if test="%{#orderinfo.orderstate==#waitconfirm}">
        <iliker:permission module="order" privilege="confirmOrder">
            <input type="button" value="审核通过" onclick="confirmOrdering(${orderinfo.id})"/>
            <input type="button" value="无货沟通"/>
        </iliker:permission>
    </s:if>
    <%--财务确认订单已付款--%>
    <s:if test="%{#orderinfo.orderstate==#confirmordered}">
        <iliker:permission module="order" privilege="confirmPayment">
            <input type="button" value="确认订单已付款" onclick="confirmPayment(${orderinfo.id})"/>
        </iliker:permission>
    </s:if>
    <%--等待备货--%>
    <s:if test="%{#orderinfo.orderstate==#admeasureproduct&&#orderinfo.postmethod!=#storePoint}">
        <iliker:permission module="order" privilege="turnWaitdeliver">
            <input type="button" value="完成配货" onclick="prepareDelivery(${orderinfo.id})"/>
        </iliker:permission>
    </s:if>
    <s:url var="loadlogistics" value="loadlogistics.do">
        <s:param name="id" value="%{#orderinfo.id}"/>
        <s:param name="orderid" value="%{#orderinfo.orderid}"/>
    </s:url>
    <%--物流发货--%>
    <s:if test="%{#orderinfo.orderstate==#waitdeliver}">
        <iliker:permission module="order" privilege="turnDelivered">
            <s:a href="%{loadlogistics}"><input type="button" value="物流入单"/></s:a>
        </iliker:permission>
    </s:if>
    <div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</div>

</body>

</html>
