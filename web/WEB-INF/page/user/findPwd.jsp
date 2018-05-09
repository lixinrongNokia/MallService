<%--
  Created by IntelliJ IDEA.
  User: LIXINRONG
  Date: 2016/11/9
  Time: 9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>忘记密码:爱芬儿后台管理</title>
    <script type="text/javascript">
        <!--
        function trim(stringToTrim) {
            return stringToTrim.replace(/^\s+|\s+$/g, "");
        }
        function validateForm(form) {
            var userName = form.uname.value;
            if (userName == null || trim(userName) == "") {
                alert("会员名不能为空");
                form.uname.focus();
                return false;
            }
            return true;
        }
        //-->
    </script>
</head>
<body>
<TABLE style="cellSpacing:15; cellPadding:0; width:100%;min-width: 320px; border:0;">
    <TBODY>
    <TR>
        <TD vAlign=bottom>
            <NOBR><A class=ablue2 href="forwardedLogin.do">返回</A></NOBR>
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
            <TABLE cellSpacing=0 cellPadding=0 width="65%" bgColor=#ddddcc
                   border=0 align="center">
                <TBODY>
                <TR bgColor=#ddddcc>
                    <TD vAlign=top width="99%" bgColor=#ddddcc>
                        <TABLE cellSpacing=3 cellPadding=0 width="100%" align=center
                               bgColor=#ddddcc border=0>
                            <TBODY>
                            <TR>
                                <TD vAlign=top width="99%" bgColor=#ffffff>
                                    <TABLE height=200 cellSpacing=10 cellPadding=0
                                           width="100%" align=center border=0>
                                        <TBODY>
                                        <TR>
                                            <TD vAlign=top>
                                                <form action="findPwdFor2.do" method="post" name="getp"
                                                      onsubmit="return validateForm(this)">
                                                    <INPUT TYPE="hidden" NAME="method" value="getpassword">
                                                    <TABLE cellSpacing=4 cellPadding=4 width="100%" border=0>
                                                        <TBODY>
                                                        <TR align=left>
                                                            <TD class="font-error" colSpan=3></TD>
                                                        </TR>
                                                        <TR align=left>
                                                            <TD class="font14" colSpan=3>
                                                                忘记密码了吗？不用着急，只要3步就可以重设您的新密码，简单方便。
                                                            </TD>
                                                        </TR>
                                                        <TR align=left>
                                                            <TD colSpan=3><SPAN
                                                                    class="font-step">第一步：</SPAN><STRONG
                                                                    class="font14b">请输入您注册时填写的用户名，点击继续</STRONG></TD>
                                                        </TR>
                                                        <TR align=left>
                                                            <TD class="font12b" align="right" width="27%">会员名</TD>
                                                            <TD width="39%"><INPUT maxLength="20" size="30"
                                                                                   name="adminmanager.nickname"></TD>
                                                            <TD width="34%"><INPUT id="Image1" type="image" alt="继续"
                                                                                   src="${pageContext.request.contextPath }/images/az-continue-arrow.gif"
                                                                                   border=0
                                                                                   name=image1><span>${msg}</span></TD>
                                                        </TR>
                                                        <TR align=left>
                                                            <TD colSpan=3>
                                                                <HR class=dashes noShade SIZE=1>
                                                            </TD>
                                                        </TR>
                                                        <TR align=left>
                                                            <TD class=font9 colSpan=3>
                                                                <P class=font9> 如果您忘记密码且不再使用注册时的E-mail，建议向管理员申请新账号。</P>
                                                            </TD>
                                                        </TR>
                                                        </TBODY>
                                                    </TABLE>
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
<div style="text-align: center">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
