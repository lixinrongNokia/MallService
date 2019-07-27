<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>添加类别</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <style type="text/css">
        .span {
            color: red;
        }
    </style>
</head>

<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap">
    <form style="margin-top: 30px" action="launchcrowd.do" enctype="multipart/form-data" method="post"
          onsubmit="regTypeName()">
        <table align="center" id="testTable">
            <tr>
                <td class="right">大类别:</td>
                <td class="left">
                    <select id="crowdname" name="clothestype.crowd.id">

                    </select>
                    <label id="label1"></label></td>
            </tr>
            <tr>
                <td class="right">子类名 :</td>
                <td class="left"><input maxlength="50" id="clothestname" name="clothestype.name" type="text"/><label
                        id="label2"></label></td>
            </tr>


            <tr>
                <td class="right">属性图片 :</td>
                <td class="left"><input id="goodimg" name="clothestimg" type="file"/> <span>(允许GIF,PNG图片)</span><label
                        id="label6"></label></td>
            </tr>
            <tr>
                <td colspan="2"><input style="margin-left:200px" type="submit"
                                       value="添加" id="submit"/><span class="span"><s:property value="message"/></span>
                </td>

            </tr>
        </table>
    </form>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
<script>
    var select = $('#crowdname');
    $(function () {
        $.getJSON("loadClassify.do", function (result) {
            if (result.success) {
                $.each(result.data, function (i, item) {
                    $('<option value="' + item.id + '">' + item.name + '</option>').appendTo(select);
                });
            }
        })
    });
    function regTypeName() {
        $('#label2').html('');
        $('#label6').html('');
        var typeName = $('#clothestname').val();
        var goodsImg = $('#goodimg').val();
        if (typeName == "") {
            $('#label2').html('类别名不能为空');
            return false;
        }
        if (goodsImg == "") {
            $('#label6').html('请上传类别图');
            return false;
        }
        return true;
    }
</script>
