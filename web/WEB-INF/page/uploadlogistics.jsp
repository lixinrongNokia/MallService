<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>提交物流单号</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer.js"></script>
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
</head>
<%--<script type="text/javascript">
    function changeVal2(select) {
        var input = $(select).next();
        var index = select.selectedIndex;
        $(input).val(select.options[index].text);
        alert($(input).val());
    }
</script>--%>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap2">
    <form id="fromSubmit" action="upLogisticsinfo.do" method="get">
        <table align="center" cellpadding="5" cellspacing="0" border="1"
               style="text-align: center;" width="50%">
            <tr>
                <td colspan="2"><input type="hidden" name="id" value="${id }"/></td>
            </tr>
            <tr>
                <td>订单号</td>
                <td><input name="orderId" value="${orderid }" disabled="disabled"/></td>
            </tr>
            <tr>
                <td>物流单号</td>
                <td><input name="logisticscode" size="25" maxlength="20"/></td>
            </tr>
            <tr>
                <td>物流公司</td>
                <td>
                    <select name="companyCode">
                        <option selected="selected" value="-1">--请选择--</option>
                        <s:iterator value="%{couriercompanys}">
                            <option value='<s:property value="companyCode"/>'><s:property
                                    value="companyName"/></option>
                        </s:iterator>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" value="提交" onclick="submitAction()"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
<script type="text/javascript">
    function submitAction() {
        var formBox=$('#fromSubmit');
        $.ajax({
            type:$(formBox).attr('method') ,
            url: $(formBox).attr('action'),
            data: $(formBox).serialize(),
            dataType: "json",
            success: function (result) {
                console.log(JSON.stringify(result));
                if(result.success){//success
                    layer.msg(result.msg, {
                        time: 0 //不自动关闭
                        , btn: ['已完成发货']
                        , yes: function (index) {
                            window.history.go(-1);
                        }
                    });
                }else {
                    layer.msg(result.msg, {
                        time: 0 //不自动关闭
                        , btn: ['知道了']
                        , yes: function (index) {
                            window.history.go(-1);
                        }
                    });
                }
            },
            error: function(data) {
                alert("error:"+data.responseText);
            }
        });
    }
</script>
