<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改管理员</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <script language="javascript" type="text/javascript">
        history.go(1);
    </script>
</head>

<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div align="center">
    <form action="editManager.do" method="post" onsubmit="return regAccount()"
          style="width: 50%;background: #CCCCCC;margin-top: 50px; margin-bottom:50px;padding-top: 50px;padding-bottom: 50px">
        <input type="hidden" id="method" value="edit">
        <input name="adminmanager.id" value="<s:property value="#manager.id"/>" type="hidden"/>
        <p>修改户名:<input type="text" id="uname" autocomplete="off" name="adminmanager.nickname" placeholder="账户名"
                       maxlength="36" value="<s:property value="#manager.nickname"/>"/><span id="nameSpan"></span></p>
        <hr/>
        <p>修改性别:
            <label><input type="radio" name="adminmanager.gender"
                          <s:if test="#manager.gender.name==\"男\"">checked="checked"</s:if> value="MAN"/>男</label>&nbsp;&nbsp;
            <label><input type="radio" name="adminmanager.gender"
                          <s:if test="#manager.gender.name==\"女\"">checked="checked"</s:if> value="WOMEN"/>女</label>
        </p>
        <hr/>
        <p><label>修改邮箱:<input type="email" id="email" autocomplete="off" name="adminmanager.email" placeholder="输入邮箱"
                              maxlength="36" value="<s:property value="#manager.email"/>"/></label></p>
        <hr/>
        <p>联系电话:<input type="password" style="display:none"><input id="phone" autocomplete="off" name="adminmanager.phone"
                       value="<s:property value="#manager.phone"/>" placeholder="输入联系电话"/></p>
        <hr/>
        <p>修改密码:<input type="password" style="display:none"><input type="password" id="password" autocomplete="off" name="adminmanager.password"
                       placeholder="设置登陆密码"/><span
                id="pwdSpan"></span></p>
        <hr/>
        <p>确认密码:<input type="password" id="regpassword" autocomplete="off" name="regpassword"/></p>
        <hr/>
        <input type="submit" value="修改"/><span style="color: red;">${errorMessage}</span>
    </form>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
