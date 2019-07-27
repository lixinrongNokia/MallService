<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>管理员显示</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<form action="#" method="post">
    <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
        <tr>
            <td width="5%" nowrap bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">修改</font></div>
            </td>
            <td width="10%" bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">用户名</font></div>
            </td>
            <td width="8%" bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">设置权限</font></div>
            </td>
            <td width="5%" bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">性别</font></div>
            </td>
            <td width="12%" bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">联系电话</font></div>
            </td>
            <td width="10%" bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">电子邮件</font></div>
            </td>
            <td width="9%" bgcolor="6f8ac4"></td>
        </tr>
        <!---------------------------LOOP START------------------------------>
        <s:iterator value="%{#managers}" var="entry">
            <s:url value="privilege2ManagerView.do" var="privilege2ManagerView">
                <s:param value="%{#entry.id}" name="adminmanager.id"/>
            </s:url>
            <s:url value="editManagerView.do" var="editManagerView">
                <s:param value="%{#entry.id}" name="adminmanager.id"/>
            </s:url>
            <s:url value="unlockingManager.do" var="unlockingManager">
                <s:param value="%{#entry.id}" name="adminmanager.id"/>
            </s:url>
            <s:url value="lockingManager.do" var="lockingManager">
                <s:param value="%{#entry.id}" name="adminmanager.id"/>
            </s:url>
            <tr>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:a href="%{editManagerView}">
                        <img src="${pageContext.request.contextPath }/images/more_feedback_icon.png" width="15"
                             height="16" border="0"></s:a></div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:property value="nickname"/></div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:a href="%{privilege2ManagerView}">设置权限</s:a></div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:property value="gender.name"/></div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:property value="phone"/></div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:property value="email"/></div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center">
                        <s:if test="status">
                            <s:a href="%{lockingManager}"><label style="color: red">停用用户</label></s:a>
                        </s:if>
                        <s:else>
                            <s:a href="%{unlockingManager}">启用用户</s:a><label style="color: #B0B0B0">&#40;用户未启用&#41;</label>
                        </s:else>
                    </div>
                </td>
            </tr>
        </s:iterator>
        <!----------------------LOOP END------------------------------->
        <tr>
            <td bgcolor="f5f5f5" colspan="11" align="center">
                <table width="100%" border="0" cellspacing="1" cellpadding="3">
                    <tr>
                        <td width="5%"></td>
                        <td width="85%">
                            <a href="addManager.do"><input type="button" class="frm_btn" value="添加员工"></a> &nbsp;&nbsp;
                            <a href="privilegegroupView.do"><input type="button" class="frm_btn" value="系统权限浏览"></a>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
