<%--
  Created by IntelliJ IDEA.
  User: WDHTC
  Date: 2016/7/12
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>

<div id="content" class="wrap" style="height: 540px;width: auto">
    <s:if test="#request.generalList==null">
        <span style="color:red;">没有数据</span>
    </s:if>
    <s:else>
        <div class="list bookList">
            <form action="transfer_moreOperation.do" method="post">
                <table cellpadding="8px" cellspacing="0" border="1px"
                       style="width: 100%;text-align: center;margin-top: 54px">
                    <tr>
                        <td></td>
                        <td>用户昵称</td>
                        <td><%--申请人电话--%></td>
                        <td><%--收款账号--%></td>
                        <td><%--真实姓名--%></td>
                        <td>转账金额</td>
                        <td>申请时间</td>
                        <td>备注</td>
                        <td>操作</td>
                    </tr>
                    <s:iterator value="%{#generalList.list}" status="i">
                        <s:url var="singleUrl" value="transfer_singleOperation.do">
                            <s:param name="transferId" value="transferId"/>
                        </s:url>
                        <tr <s:if test="#i.index%2==0"> class="odd" </s:if>>
                            <td><input type="hidden" name="transfers[<s:property value='#i.index'/>].transferId"
                                       value="<s:property value='transferId'/>"></td>
                            <td><input readonly="readonly" type="text"
                                       value="<s:property value='nickname'/>"></td>
                            <td><input readonly="readonly" name="transfers[<s:property value='#i.index'/>].phone" type="hidden"
                                       value="<s:property value='phone'/>"></td>
                            <td><input readonly="readonly" name="transfers[<s:property value='#i.index'/>].account" type="hidden"
                                       value="<s:property value='account'/>"></td>
                            <td><input readonly="readonly" name="transfers[<s:property value='#i.index'/>].realname" type="hidden"
                                       value="<s:property value='realname'/>"></td>
                            <td><input readonly="readonly" name="transfers[<s:property value='#i.index'/>].amount" type="text"
                                       value="<s:property value='amount'/>"></td>
                            <td><s:date name="putinforTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><input readonly="readonly" name="transfers[<s:property value='#i.index'/>].note" type="text"
                                       value="<s:property value='note'/>"></td>
                            <td><s:a href="%{singleUrl}">转账</s:a></td>
                        </tr>
                    </s:iterator>
                    <tr>
                        <td colspan="8" align="right"></td>
                        <td><input type="submit" value="批量转账"/></td>
                    </tr>
                </table>
            </form>
            <div class="page-spliter">
                <s:url var="url_pre" value="pageLoad_showApply.do">
                    <s:param name="offset" value="offset-1"/>
                </s:url>
                <s:url var="index" value="pageLoad_showApply.do">
                    <s:param name="offset" value="1"/>
                </s:url>
                <s:url var="url_next" value="pageLoad_showApply.do">
                    <s:param name="offset" value="offset+1"/>
                </s:url>
                <s:url var="last" value="pageLoad_showApply.do">
                    <s:param name="offset" value="totalPage"/>
                </s:url>
                <s:if test="offset>1">
                    <s:a href="%{url_pre}">上一页</s:a>
                </s:if>
                <s:if test="offset!=1">
                    <s:a href="%{index}">首页</s:a>
                </s:if>
                <s:if test="offset!=totalPage">
                    <s:a href="%{last}">尾页</s:a>
                </s:if>
                <s:if test="offset<totalPage">
                    <s:a href="%{url_next}">下一页</s:a>
                </s:if>
                第
                <s:property value="offset"/>
                页/总共
                <s:property value="totalPage"/>
                页&nbsp;
                <s:property value="%{#generalList.totalSize}"/>
                条记录
            </div>
        </div>
    </s:else>
</div>

