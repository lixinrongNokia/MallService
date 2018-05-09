<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>商品管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/switch.css" media="screen" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <style type="text/css">
        .focus {
            border: 1px solid #f00;
            background: #fcc;
        }

        input {
            height: 48px;
            width: 108px;
        }
    </style>
</head>
<script type="text/javascript">
    $(function () {
        var tr = $("#imglist");
        $("textarea").each(function () {
            var node = $(this);
            node.focus(function () {
                tr.empty();
                var imgs = node.val();
                var data = imgs.split("#");
                $.each(data, function (i, item) {
                    if (item.length != "") {
                        tr.append("<img style='height:150px;width: 150px ' src='${tempImgDir}/" + item + "'/>&nbsp;");
                    }
                });
            });
        });
    });
    /*选择框改变事件*/
    function changeVal(tag) {
        var crowdid = tag.value;
        var typeselect = $(tag).next();
        typeselect.find("option").not(":first").remove();
        if (crowdid == -1) {
            return;
        }
        $.ajax({
            url: "querytype.do",
            data: {
                "crowdid": crowdid
            },
            dataType: "JSON",
            success: function (result) {
                var data = $.parseJSON(result);
                $.each(data, function (i, item) {
                    typeselect.append("<option value='" + item.id + "'>"
                            + item.name + "</option>");
                });
            }
        });
    }

    function changeVal2(select) {
        var input = $(select).next();
        var index = select.selectedIndex;
        $(select).val(select.options[index].text);
    }
</script>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>

<div id="content" class="wrap2">
    <div class="list bookList">
        <form action="ultimateSubmit.do" method="post" onsubmit="return regTable()">
            <table id="retable" style="text-align: center;margin-top: 8px" width="960px"
                   height="150px" cellpadding="5px" cellspacing="1px">
                <tr>
                    <th class="orderId">序号</th>
                    <th class="orderId">品牌</th>
                    <th class="orderId">商品编号</th>
                    <th class="orderId">商品名称</th>
                    <th class="orderId">商品系列</th>
                    <th class="orderId">商品介绍</th>
                    <th class="orderId">商品价格</th>
                    <th class="orderId">颜色</th>
                    <th class="orderId">尺寸</th>
                    <th class="orderId">分佣比例</th>
                    <th class="orderId">库存</th>
                    <th class="orderId">图片展示</th>
                </tr>
                <s:iterator var="good" value="%{#session.goodList}" status="i">
                    <tr <s:if test="#i.index%2==0"> class="odd" </s:if>>
                        <td><s:property value="#i.index+1"/></td>
                            <%--================================================================================================--%>
                        <s:if test="brand==null">
                            <td>
                                <select id="nobrandId<s:property value='#i.index'/>"
                                        name="goodses[<s:property value="#i.index"/>].brand.brandId" class="focus"
                                        onchange="changeVal2(this)">
                                    <option selected="selected">==请选择==</option>
                                    <s:iterator value="#session.brands">
                                        <option value="<s:property value='brandId'/>"><s:property
                                                value="BrandName"/></option>
                                    </s:iterator>
                                </select>
                                <input type="hidden" name="goodses[<s:property value="#i.index"/>].brand.BrandName">
                            </td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].brand.brandId" type="hidden"
                                       value="<s:property value='brand.brandId'/>"> <input
                                    name="goodses[<s:property value="#i.index"/>].brand.BrandName"
                                    value="<s:property value='brand.BrandName'/>"></td>
                        </s:else>
                            <%--================================================================================================--%>
                        <s:if test="goodCode==null||goodCode==''">
                            <td><input id="nogoodCode" name="goodses[<s:property value="#i.index"/>].goodCode"
                                       class="focus"
                                       type="text"></td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].goodCode"
                                       value="<s:property value='goodCode'/>"></td>
                        </s:else>
                            <%--================================================================================================--%>
                        <s:if test="goodName==null||goodName==''">
                            <td><input id="nogoodName" name="goodses[<s:property value="#i.index"/>].goodName"
                                       class="focus"
                                       type="text"></td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].goodName"
                                       value="<s:property value='goodName'/>"></td>
                        </s:else>
                            <%--================================================================================================--%>
                        <s:if test="clothestype==null">
                            <td>
                                <select class="focus" onchange="changeVal(this)"
                                        style="width:100px;">
                                    <option selected="selected" value="-1">--请选择--</option>
                                    <s:iterator value="%{#session.crowdNames}">
                                        <option value="<s:property value="id"/>"><s:property
                                                value="name"/></option>
                                    </s:iterator>
                                </select>
                                <select id="noclothestypeId" style="width:100px;" id="type"
                                        name="goodses[<s:property value="#i.index"/>].clothestype.id"
                                        onchange="changeVal2(this)">
                                    <option selected="selected" value="-1">--请选择--</option>
                                </select>
                                <input type="hidden" name="goodses[<s:property value="#i.index"/>].clothestype.name">
                            </td>
                        </s:if>
                        <s:else>
                            <td><input type="hidden" name="goodses[<s:property value="#i.index"/>].clothestype.id"
                                       value="<s:property value='clothestype.id'/>"><input
                                    name="goodses[<s:property value="#i.index"/>].clothestype.name"
                                    value="<s:property value='clothestype.name'/>"></td>
                        </s:else>
                            <%--======================================================================================================--%>
                        <s:if test="goodsDesc==null||goodsDesc==''">
                            <td><textarea id="nogoodsDesc" name="goodses[<s:property value="#i.index"/>].goodsDesc"
                                          class="focus"
                                          style="resize:none"
                                          rows="8" cols="30"></textarea></td>
                        </s:if>
                        <s:else>
                            <td><textarea name="goodses[<s:property value="#i.index"/>].goodsDesc" style="resize:none"
                                          rows="8"
                                          cols="30"><s:property value="goodsDesc"/></textarea>
                            </td>
                        </s:else>
                            <%--=======================================================================================================--%>
                        <s:if test="price==null">
                            <td><input id="noprice" name="goodses[<s:property value="#i.index"/>].price" class="focus"
                                       type="text">
                            </td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].price"
                                       value="<s:property value='price'/>"></td>
                        </s:else>
                            <%--=======================================================================================================--%>
                        <s:if test="colors==null||colors==''">
                            <td><input id="nocolors" name="goodses[<s:property value="#i.index"/>].colors" class="focus"
                                       type="text">
                            </td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].colors"
                                       value="<s:property value='colors'/>"></td>
                        </s:else>
                            <%--===================================================================================================--%>
                        <s:if test="sizes==null||sizes==''">
                            <td><input id="nosizes" name="goodses[<s:property value="#i.index"/>].sizes" class="focus"
                                       type="text">
                            </td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].sizes"
                                       value="<s:property value='sizes'/>"></td>
                        </s:else>
                            <%--======================================================================================================--%>
                        <s:if test="divided_into==null">
                            <td><input id="nodivided_into" name="goodses[<s:property value="#i.index"/>].divided_into"
                                       class="focus"
                                       type="text"></td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].divided_into"
                                       value="<s:property value='divided_into'/>"></td>
                        </s:else>
                            <%--====================================================================================================--%>
                        <s:if test="stock==null">
                            <td><input id="nostock" name="goodses[<s:property value="#i.index"/>].stock" class="focus"
                                       type="text">
                            </td>
                        </s:if>
                        <s:else>
                            <td><input name="goodses[<s:property value="#i.index"/>].stock"
                                       value="<s:property value='stock'/>"></td>
                        </s:else>
                            <%--=======================================================================================================--%>
                        <s:if test="illustrations==null||illustrations==''">
                            <td><textarea id="noillustrations"
                                          name="goodses[<s:property value="#i.index"/>].illustrations" class="focus"
                                          style="resize:none"
                                          rows="8" cols="30"></textarea></td>
                        </s:if>
                        <s:else>
                            <td><textarea name="goodses[<s:property value="#i.index"/>].illustrations"
                                          style="resize:none" rows="8"
                                          cols="30"><s:property
                                    value='illustrations'/></textarea></td>
                        </s:else>
                    </tr>
                </s:iterator>
                <th colspan="12" style="align-content: center"><input type="submit" value="终极提交">${errorMsg}</th>
            </table>
        </form>
        <div id="imglist"></div>
    </div>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
