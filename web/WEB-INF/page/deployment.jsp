<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>上传更新</title>
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    <meta name="viewport"
          content="width=device-width,target-densitydpi=high-dpi,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>

</head>

<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap" style="width: 50%;height: 300px">
    <form style="margin-top: 30px" action="apk_uploadFile.do"
          enctype="multipart/form-data" method="post">
        <table align="center" id="testTable">
            <tr>
                <td></td>
                <td><input id="apkId" type="hidden" name="id" value="${apkversion.id }"/></td>
            </tr>
            <tr>
                <td class="right">版本号:</td>
                <td class="left"><input id="versionCode" name="versionCode"
                                        value="${apkversion.versionCode }"/></td>
            </tr>
            <tr>
                <td class="right">版本名称:</td>
                <td class="left"><input id="versionName" name="versionName"
                                        value="${apkversion.versionName }"/></td>
            </tr>
            <tr>
                <td class="right">版本更新内容:</td>
                <td class="left"><textarea id="apkContent" style="resize:none" name="content" rows="3" cols="20"
                                           maxlength="200" id="goodDesc"
                                           contenteditable="true">${apkversion.content }</textarea>
                </td>
            </tr>
            <tr>
                <td class="right">apk包名:</td>
                <td class="left"><input id="appName" name="appName" readonly="readonly"
                                        value="${apkversion.appName }"/></td>
            </tr>
            <tr>
                <td class="right">下载路径:</td>
                <td class="left"><input id="downloadUrl" name="url" readonly="readonly"
                                        value="${apkversion.url }"/></td>
            </tr>
            <tr>
                <td class="right">文件</td>
                <td class="left"><input type="file" name="data"/></td>
            </tr>
            <tr>
                <td align="center" colspan="2"><input type="submit" value="更新"/></td>
            </tr>
        </table>
        <span><s:property value="#message"/></span><label><s:property
            value="#error"/></label>
    </form>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
<script type="text/javascript" defer="defer">
    $(function () {
        $.getJSON("apk_queryVersion.do", function (result) {
            if (result != null) {
                $('#apkId').val(result.id);
                $('#versionCode').val(result.versionCode);
                $('#versionName').val(result.versionName);
                $('#appName').val(result.appName);
                $('#apkContent').html(result.content);
                $('#downloadUrl').val(result.url);
            }
        });
    });
</script>
