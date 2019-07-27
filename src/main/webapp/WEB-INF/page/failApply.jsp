<%--
  Created by IntelliJ IDEA.
  User: WDHTC
  Date: 2016/7/12
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/showtransfer.js"></script>
<div id="content" class="wrap" style="height: 540px;width: auto">
    <s:if test="#failData==null||#failData.isEmpty()">
        <span style="color:red;">没有数据</span>
    </s:if>
    <s:else>
        <div class="list bookList">
            <form action="transfer_moreOperation.do" method="post" onsubmit="return moreselect()">
                <table cellpadding="8px" cellspacing="0" border="1px"
                       style="width: 100%;text-align: center;margin-top: 54px">
                    <tr>
                        <td>申请人电话</td>
                        <td>收款账号</td>
                        <td>真实姓名</td>
                        <td>转账金额</td>
                        <td>申请时间</td>
                        <td>处理时间</td>
                        <td>失败原因</td>
                        <td>操作</td>
                    </tr>
                    <s:iterator value="%{#failData}" status="i">
                        <s:url var="singleUrl" value="transfer_singleOperation.do">
                            <s:param name="transferId" value="transferId"/>
                        </s:url>
                        <tr <s:if test="#i.index%2==0"> class="odd" </s:if>>
                            <td><s:property value="transfer.phone"/></td>
                            <td><s:property value="transfer.account"/></td>
                            <td><s:property value="transfer.realname"/></td>
                            <td><s:property value="transfer.amount"/></td>
                            <td><s:property value="transfer.putinforTime"/></td>
                            <td><s:property value="transfer.manageTime"/></td>
                            <td><s:property value="remark"/></td>
                            <td><s:a href="#">返还账户</s:a></td>
                        </tr>
                    </s:iterator>
                </table>
            </form>
        </div>
    </s:else>
</div>
