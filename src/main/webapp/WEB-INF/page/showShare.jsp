<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <meta name="viewport"
          content="width=device-width,target-densitydpi=high-dpi,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <title>分享列表</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#ul li").click(function () {
                $(this).addClass('current');
            });

            var tr = $("#imglist");
            $("textarea").each(function () {
                var node = $(this);
                node.focus(function () {
                    tr.empty();
                    var imgs = node.val();
                    var data = imgs.split("#");
                    $.each(data, function (i, item) {
                        if (item.length != "") {
                            tr.append("<img style='height:240px;width: 180px ' src='/share/" + item + "'/>&nbsp;");
                        }
                    });

                });
            });
            $("#allChecked").bind("change", changeAll);

        });
    </script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap">
    <s:if test="#shares==null||#shares.list.isEmpty()">
        <div style="width: 50%;height: 200px;">
            <span style="color:red;">没有分享信息</span>
        </div>
    </s:if>
    <s:else>
        <div class="list bookList">
            <table style="width: 100%">
                <tr class="title">
                    <th><input type="checkbox" id="allChecked"/><span id="checktext">全选</span></th>
                    <th class="orderId">分享人昵称</th>
                    <th>分享文本</th>
                    <th class="orderId">分享时间</th>
                    <th class="orderId">分享位置</th>
                    <th class="orderId">分享的图片路径</th>
                    <th class="orderId">论评数量</th>
                    <th class="orderId">操作</th>
                </tr>


                <s:iterator value="%{#shares.list}" status="i">
                    <s:url var="commpage" value="comm_findCommByShareId.do">
                        <s:param name="shareid" value="shareId"/>
                    </s:url>

                    <tr <s:if test="#i.index%2==0"> class="odd" </s:if>>
                        <td><input type="checkbox" name="checkboxs"/></td>
                        <td class="thumb"><s:property value='nickname'/></td>
                        <td>
                            <s:property value='content'/>
                        </td>
                        <td><s:date name="releaseTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><s:property value='location'/></td>
                        <td><textarea style="resize:none" rows="6" cols="50" readonly="readonly"><s:property
                                value='pic'/></textarea></td>
                        <td><s:if test="map[shareId]>0"><s:a href="%{commpage}"><s:property value="map[shareId]"/></s:a></s:if><s:else>0</s:else></td>
                        <td><a href="javascript:void(0)" onclick="delShare(<s:property value='shareId'/>)">删除</a></td>
                    </tr>
                </s:iterator>
            </table>
            <div id="imglist"></div>
            <div class="page-spliter">
                    <%--上一页 --%>
                <s:url var="url_pre" value="action_showShare.do">
                    <s:param name="offset" value="offset-1"/>
                    <s:param name="node" value="0"/>
                </s:url>
                <s:url var="index" value="action_showShare.do">
                    <s:param name="offset" value="1"/>
                    <s:param name="node" value="0"/>
                </s:url>
                    <%--下一页 --%>
                <s:url var="url_next" value="action_showShare.do">
                    <s:param name="offset" value="offset+1"/>
                    <s:param name="node" value="0"/>
                </s:url>
                <s:url var="last" value="action_showShare.do">
                    <s:param name="offset" value="totalPage"/>
                    <s:param name="node" value="0"/>
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
                第<s:property value="offset"/>页/总共<s:property value="totalPage"/>页&nbsp;<s:property value="totalSize"/>条记录
                <s:textfield id="sendVal" maxlength="3"/><input type="button"
                                                                onclick="regVal(<s:property value="totalPage"/>)"
                                                                value="go"/>
                <br/><a style="margin-left:90%;margin-right: 12px " href="">全部删除</a>


            </div>
            <div class="button">
                <input class="input-gray" type="button" name="submit"
                       value="一个月前的分享" onclick="findAtTime(1)"/><input class="input-gray" type="button"
                                                                       name="submit" value="两个月前的分享"
                                                                       onclick="findAtTime(2)"/>
            </div>
        </div>
    </s:else>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
