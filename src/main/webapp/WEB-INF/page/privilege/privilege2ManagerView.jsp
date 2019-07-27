<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>用户权限设置</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="privilege2Manager.do" method="post">
    <input type="hidden" name="adminmanager.id" value="${manager.id}"/>
    <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td><font color="#FFFFFF">选择用户所在权限组：</font></td>
        </tr>
        <s:set var="selectPrivilegeGroups" value="%{#manager.privilegeGroups}"/>
        <tr bgcolor="f5f5f5">
            <td><s:iterator value="%{#privilegeGroups}" var="privilegegroup" status="statu">
                <label>
                    <input type="checkbox" name="privilegeGroupIds" value="<s:property value="groupid"/>"
                    <s:iterator value="%{#selectPrivilegeGroups}" var="ug">
                        <s:if test="%{#ug==#privilegegroup}">checked</s:if>
                    </s:iterator>><s:property value="name"/>
                </label><s:if test="%{#statu.index%8==0}"><br></s:if></s:iterator>
            </td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td colspan="2">
                <div align="center">
                    <input type="submit" name="SYS_SET" value=" 确 定 " class="frm_btn">
                </div>
            </td>
        </tr>
    </table>
</form>
<br>
</body>
</html>