<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>忘记密码:爱芬儿后台管理</TITLE>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <SCRIPT type="text/javascript">
        <!--
        function trim(stringToTrim) {
            return stringToTrim.replace(/^\s+|\s+$/g, "");
        }
        function validateForm(form) {
            var password = form.password.value;
            var confirm_password = form.regpassword.value;
            if (password == null || trim(password) == "") {
                alert("新密码不能为空");
                form.password.focus();
                return false;
            }
            if (password.length < 6 || password.length > 16) {
                alert("密码的长度不正确,正确的长度为6-16位字符");
                form.password.focus();
                return false;
            }
            if (password != confirm_password) {
                alert("两次输入的密码不一致，请重新输入。");
                form.password.focus();
                return false;
            }
            return true;
        }
        //-->
    </SCRIPT>
    <META content="MSHTML 6.00.2900.2769" name=GENERATOR>
</HEAD>
<BODY>
<TABLE style="cellSpacing:15; cellPadding:0; width:100%;min-width: 320px; border:0;">
    <TBODY>
    <TR>
        <TD vAlign=bottom>
            <NOBR><A class=ablue2 href="${pageContext.request.contextPath}/forwardedLogin.do">返回</A></NOBR>
            <SPAN class=important> &gt; </SPAN>
            <NOBR class=font-title>忘记密码</NOBR>
        </TD>
    </TR>
    </TBODY>
</TABLE>
<TABLE style="cellSpacing:15; cellPadding:0; width:100%;min-width: 320px; border:0;align-content: center;">
    <TBODY>
    <TR>
        <TD vAlign=top align=middle>
            <TABLE cellSpacing=0 cellPadding=0 width="65%" border=0 align="center">
                <TBODY>
                <TR>
                    <TD vAlign=top width="99%" colSpan=4>
                        <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center
                               border=0>
                            <TBODY>
                            <TR>
                                <TD vAlign=top align=left width=10 bgColor=#ddddcc><IMG height=28
                                                                                        src="${pageContext.request.contextPath }/images/az-tan-top-left-round-corner.gif"
                                                                                        width=10 border=0></TD>
                                <TD vAlign=bottom noWrap width="20%" bgColor=#ddddcc
                                    height=28><SPAN class=title>重设新密码&nbsp;&nbsp;</SPAN></TD>
                                <TD vAlign=bottom align=right width="79%"
                                    bgColor=#ddddcc>&nbsp;</TD>
                                <TD vAlign=top align=right width=10 bgColor=#ddddcc><IMG height=28
                                                                                         src="${pageContext.request.contextPath }/images/az-tan-top-right-round-corner.gif"
                                                                                         width=10 border=0></TD>
                            </TR>
                            </TBODY>
                        </TABLE>
                    </TD>
                </TR>
                </TBODY>
            </TABLE>
            <TABLE id="" cellSpacing=0 cellPadding=0 width="65%" bgColor=#ddddcc
                   border=0 align="center">
                <TBODY>
                <TR bgColor=#ddddcc>
                    <TD vAlign=top bgColor=#ddddcc>
                        <TABLE cellSpacing=3 cellPadding=0 width="100%" align=center
                               bgColor=#ddddcc border=0>
                            <TBODY>
                            <TR>
                                <TD vAlign=top bgColor=#ffffff>
                                    <TABLE class=font9 height=200 cellSpacing=10 cellPadding=0
                                           width="100%" align=center border=0>
                                        <TBODY>
                                        <TR>
                                            <TD vAlign=top>
                                                <form action="updatePwd.do"
                                                      method="post"
                                                      onsubmit="return validateForm(this)">
                                                    <INPUT TYPE="hidden" NAME="adminmanager.nickname"
                                                           value="${uname}">
                                                    <INPUT TYPE="hidden" NAME="validateCode"
                                                           value="${validateCode}">
                                                    <table border="0" cellpadding="4" cellspacing="4" width="100%">
                                                        <tbody>
                                                        <tr align="left">
                                                            <td colspan="3" class="font-error"></td>
                                                        </tr>
                                                        <tr align="left">
                                                            <td colspan="3"><p><span class="font-step">最后一步：</span><span
                                                                    class="font14b">输入您的新密码</span></p></td>
                                                        </tr>
                                                        <tr align="left">
                                                            <td colspan="3" class="font9">当您登陆帐户时请输入以下密码。
                                                            </td>
                                                        </tr>
                                                        <tr align="left">
                                                            <td class="font12b" align="right" width="21%">您的新密码是</td>
                                                            <td width="31%"><input type="password" name="adminmanager.password"
                                                                                   maxlength="16" size="30"></td>
                                                            <td class="font9" width="48%">
                                                                密码要求由英文字母（a-z大小写均可）、阿拉伯数字(0-9)组成且长度为6-16位字符
                                                            </td>
                                                        </tr>
                                                        <tr align="left">
                                                            <td class="font12b" align="right">重新输入一遍</td>
                                                            <td><input type="password" name="regpassword"
                                                                       maxlength="16" size="30"></td>

                                                        </tr>
                                                        <tr align="left">
                                                            <td>&nbsp;</td>
                                                            <td align="right">
                                                                <input type="image" alt="完成"
                                                                       src="${pageContext.request.contextPath }/images/az-finish.gif"
                                                                       border=0 name="image1" ID=Image1/>
                                                            </td>
                                                            <td>&nbsp;</td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </form>
                                            </TD>
                                        </TR>
                                        </TBODY>
                                    </TABLE>
                                </TD>
                            </TR>
                            </TBODY>
                        </TABLE>
                    </TD>
                </TR>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="65%" bgColor=#ddddcc border=0 align="center">
                <TBODY>
                <TR vAlign=bottom>
                    <TD align=left bgColor=#ddddcc height=10><IMG height=10
                                                                  src="${pageContext.request.contextPath }/images/az-tan-bottom-left-round-corner.gif"
                                                                  width=10
                                                                  border=0></TD>
                    <TD align=right bgColor=#ddddcc height=10><IMG height=10
                                                                   src="${pageContext.request.contextPath }/images/az-tan-bottom-right-round-corner.gif"
                                                                   width=10
                                                                   border=0></TD>
                </TR>
                </TBODY>
            </TABLE>
        </TD>
    </TR>
    </TBODY>
</TABLE>
</BODY>
</HTML>