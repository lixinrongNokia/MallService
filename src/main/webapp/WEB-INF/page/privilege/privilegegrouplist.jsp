<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>权限组显示</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<form action="#" method="post">
    <html:hidden property="page"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
        <tr>
            <td colspan="4" bgcolor="6f8ac4" align="right">
            </td>
        </tr>
        <tr>
            <td width="30%" bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">代号</font></div>
            </td>
            <td width="8%" nowrap bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">修改</font></div>
            </td>
            <td bgcolor="6f8ac4">
                <div align="center"><font color="#FFFFFF">名称</font></div>
            </td>
            <td width="10%" bgcolor="6f8ac4"></td>
        </tr>
        <!---------------------------LOOP START------------------------------>
        <s:iterator value="%{#privilegeGroups}" var="entry">
            <s:url var="editPrivilegeGroupView" value="editPrivilegeGroupView.do">
                <s:param name="privilegeGroup.groupid" value="#entry.groupid"/>
            </s:url>
            <s:url var="delPrivilegeGroup" value="delPrivilegeGroup.do">
                <s:param name="privilegeGroup.groupid" value="#entry.groupid"/>
            </s:url>
            <tr>
                <td bgcolor="f5f5f5">
                    <div align="center">${entry.groupid}</div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center">
                        <s:a href="%{editPrivilegeGroupView}"><img src="${pageContext.request.contextPath }/images/more_feedback_icon.png" width="15" height="16" border="0"></s:a>
                    </div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center">${entry.name}</div>
                </td>
                <td bgcolor="f5f5f5">
                    <div align="center"><s:a href="%{delPrivilegeGroup}">删除</s:a>
                    </div>
                </td>
            </tr>
        </s:iterator>
        <!----------------------LOOP END------------------------------->
        <tr>
            <td bgcolor="f5f5f5" colspan="4" align="center">
                <table width="100%" border="0" cellspacing="1" cellpadding="3">
                    <tr>
                        <td width="5%"></td>
                        <td width="85%">
                            <a href="addprivilegegroupView.do"><input type="button" class="frm_btn" value="添加权限组"></a>
                            &nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>