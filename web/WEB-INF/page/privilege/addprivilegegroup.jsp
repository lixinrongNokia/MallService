<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>添加权限组</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/vip.css" type="text/css">
    <SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
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
<form action="addprivilegegroup.do" method="post">
    <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td colspan="2"><font color="#FFFFFF">添加权限组：</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="22%">
                <div align="right">权限组名称：</div>
            </td>
            <td width="78%"><label>
                <input name="privilegeGroup.name" size="20" maxlength="20"/>
            </label>
                <font color="#FF0000">*</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="22%">
                <div align="right">选择权限：</div>
            </td>
            <td width="78%">
                <s:iterator value="%{#privileges}" var="privilegeInfo" status="statu">
                    <label>
                        <input type="checkbox" name="systemPrivilegePK"
                               value="${privilegeInfo.id.module},${privilegeInfo.id.privilege}">
                    </label>
                    <s:property value="name"/> <s:if test="#statu.index%5==0"><br></s:if>
                </s:iterator></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td colspan="2">
                <div align="center">
                    <input type="submit" name="SYS_SET" value=" 确 定 " class="frm_btn">
                    <input type="reset" name="SYS_SET" value="重置" class="frm_btn">
                </div>
            </td>
        </tr>
    </table>
</form>
<br>
</body>
</html>