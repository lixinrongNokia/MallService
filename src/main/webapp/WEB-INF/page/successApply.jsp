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
<div id="content" class="wrap" style="height: 540px;width: auto">
    <s:if test="#request.generalList==null">
        <span style="color:red;">没有数据</span>
    </s:if>
    <s:else>

        <div class="list bookList">
                <table cellpadding="8px" cellspacing="0" border="1px"
                       style="width: 100%;text-align: center;margin-top: 54px">
                    <tr>
                        <td>用户昵称</td>
                        <td>转账金额</td>
                        <td>操作时间</td>
                        <td>备注</td>
                        <td>操作</td>
                    </tr>
                    <s:iterator value="%{#generalList.list}" status="i">
                        <s:url var="singleUrl" value="transfer_singleOperation.do">
                            <s:param name="transferId" value="transferId"/>
                        </s:url>
                        <tr <s:if test="#i.index%2==0"> class="odd" </s:if>>
                            <td><s:property value='nickname'/></td>
                            <td><s:property value='amount'/></td>
                            <td><s:date name="manageTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><s:property value='note'/></td>
                            <td><s:a href="#">查看详情</s:a></td>
                        </tr>
                    </s:iterator>
                </table>
            <div class="page-spliter">
                <s:url var="url_pre" value="pageLoad_successApply.do">
                    <s:param name="offset" value="offset-1"/>
                </s:url>
                <s:url var="index" value="pageLoad_successApply.do">
                    <s:param name="offset" value="1"/>
                </s:url>
                <s:url var="url_next" value="pageLoad_successApply.do">
                    <s:param name="offset" value="offset+1"/>
                </s:url>
                <s:url var="last" value="pageLoad_successApply.do">
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
                ${offset}
                页/总共
                <s:property value="totalPage"/>
                页&nbsp;
                <s:property value="%{#generalList.totalSize}"/>
                条记录
            </div>
        </div>
    </s:else>
</div>
