<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>修改权限组</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script language="JavaScript">
        function checkfm(form) {
            if (trim(form.name.value) == "") {
                alert("权限组名称不能为空！");
                form.name.focus();
                return false;
            }
            return true;
        }
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="editPrivilegeGroup.do" method="post" onsubmit="return checkfm(this)">
    <input type="hidden" name="privilegeGroup.groupid" value="<s:property value="#privilegeGroup.groupid"/>"/>
    <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td colspan="2"><font color="#FFFFFF">修改权限组：</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="22%">
                <div align="right">权限组名称：</div>
            </td>
            <td width="78%"><input name="privilegeGroup.name" size="20" maxlength="20"
                                   value="<s:property value="#privilegeGroup.name"/> "/>
                <font color="#FF0000">*</font></td>
        </tr>
        <s:set var="selectprivileges" value="%{#privilegeGroup.systemPrivileges}"/>
        <tr bgcolor="f5f5f5">
            <td width="22%">
                <div align="right">选择权限：</div>
            </td>
            <td width="78%"><s:iterator value="%{#privileges}" var="privilege" status="statu">
                <input type="checkbox" name="systemPrivilegePK"
                       value="<s:property value="%{#privilege.id.module}"/>,<s:property value="%{#privilege.id.privilege}"/>"
                <s:iterator value="%{#selectprivileges}" var="sp">
                       <s:if test="%{#sp==#privilege}">checked="checked"</s:if>
                </s:iterator>
                ><s:property value="%{#privilege.name}"/><s:if test="%{#statu.index%4==0}"><br></s:if>
            </s:iterator></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td colspan="2">
                <div align="center">
                    <input type="submit" name="SYS_SET" value="保存" class="frm_btn">
                    <input type="reset" value="重置">
                    <input type="button" value="取消">
                </div>
            </td>
        </tr>
    </table>
</form>
<br>
</body>
</html>