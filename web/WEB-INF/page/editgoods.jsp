<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>修改商品</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <style type="text/css">
        .stocks li input[type="text"] {
            border: none;
            border-bottom-style: solid;
        }

        .stock_li {
            height: auto;
            border: 1px dashed #747474;
        }

        .stock_li2 {
            height: auto;
            border: 1px solid blue;
        }

        .stockItem {
            min-height: 2rem;
            max-height: 10rem;
            overflow: auto;
            list-style: none;
        }
    </style>
</head>

<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap">
    <input id="brandId" type="hidden" value="${good.brand.brandId }">
    <input id="clothestypeId" type="hidden" value="${good.clothestype.id}">
    <form action="updateGood.do" enctype="multipart/form-data" method="post" onsubmit="return regUpdate()">
        <img id="test" src="" alt=""/>
        <table align="center" id="testTable">
            <tr>
                <td></td>
                <td><input type="hidden" name="goods.id" value="${good.id }"/></td>
            </tr>
            <tr>
                <td class="right">品牌:</td>
                <td class="left">
                    <select id="brand" name="goods.brand.brandId">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="right">商品编号 :</td>
                <td class="left"><input maxlength="20" id="goodCode" name="goods.goodCode" type="text"
                                        value="${good.goodCode }"/><label id="label1"></label></td>
            </tr>
            <tr>
                <td class="right">商品名称 :</td>
                <td class="left"><input maxlength="50" id="goodName" name="goods.goodName" type="text"
                                        value="${good.goodName }"/><label id="label2"></label></td>
            </tr>
            <tr>
                <td class="right">商品类别 :</td>
                <td class="left">
                    <select style="width:100px;" id="type" name="goods.clothestype.id">
                    </select>
                    <label id="label3"></label></td>
            </tr>
            <tr>
                <td class="right">商品介绍 :</td>
                <td class="left"><textarea style="resize:none" rows="3" cols="20" maxlength="200" id="goodDesc"
                                           contenteditable="true"
                                           name="goods.goodsDesc">${good.goodsDesc }</textarea><label
                        id="label4"></label></td>
            </tr>
            <tr>
                <td class="right">商品价格 :</td>
                <td class="left"><input id="goodPrice" maxlength="6" name="goods.price" type="text"
                                        value="<s:property value="#good.price"/>"/><label id="label5"></label></td>
            </tr>
            <tr>
                <td class="right">旧颜色 :</td>
                <td class="left"><input id="colors" name="goods.colors" type="text"
                                        value="${good.colors }"/><label id="label8"></label></td>
            </tr>
            <tr>
                <td class="right">旧尺寸 :</td>
                <td class="left"><input id="sizes" name="goods.sizes" type="text"
                                        value="${good.sizes }"/><label id="label9"></label></td>
            </tr>

            <tr>
                <td class="right">颜色与尺寸:</td>
                <td class="left">
                    <ul class="stocks">
                        <s:if test="%{#good.stockInfoSet==null ||#good.stockInfoSet.isEmpty()}">
                            <li class="stock_li">
                                <p>颜色:<input class="color-attr" type="text" placeholder="颜色"
                                             name="goods.stockInfoSet.makeNew[0].color" size="5"
                                             maxlength="5">样式:<input
                                        type="file" name="stockImg" onchange="PreviewImage(this)"/></p>
                                <input type="hidden" value="<s:property value="goods.id"/>"
                                       name="goods.stockInfoSet.makeNew[0].goods.id"/>
                                <ul class="stockItem">
                                    <li>规格:<input placeholder="尺寸" type="text"
                                                  name="goods.stockInfoSet.makeNew[0].stockItems.makeNew[0].size"
                                                  size="5"
                                                  maxlength="5">库存:<input type="number" min="0"
                                                                          name="goods.stockInfoSet.makeNew[0].stockItems.makeNew[0].stockCount"
                                                                          size="5"
                                                                          maxlength="5">
                                    </li>
                                </ul>
                                <input onclick="addStock_cell(this)" type="button" value="添加规格"/>
                            </li>
                        </s:if>
                        <s:else>
                            <s:iterator var="stockInfo" value="%{#good.stockInfoSet}" status="i">
                                <li class="stock_li"
                                    <s:if test="#i.index%2==0">style="border: 3px solid #BC03B1;" </s:if>>
                                    <p>颜色:<input class="color-attr" type="text" readonly="readonly" placeholder="颜色"
                                                 value="<s:property value="color"/> "
                                                 name="goods.stockInfoSet.makeNew[${i.index}].color" size="5"
                                                 maxlength="5">样式:<input
                                            type="file" name="stockImg" onchange="PreviewImage(this)"/></p>
                                    <input type="hidden" value="<s:property value="stockId"/>"
                                           name="goods.stockInfoSet.makeNew[${i.index}].stockId"/>
                                    <input type="hidden" value="<s:property value="goods.id"/>"
                                           name="goods.stockInfoSet.makeNew[${i.index}].goods.id"/>
                                    <ul class="stockItem">
                                        <s:iterator var="stockItem" value="%{#stockInfo.stockItems}" status="j">
                                            <li>规格:<input placeholder="尺寸" type="text"
                                                          value="<s:property value="size"/>"
                                                          name="goods.stockInfoSet.makeNew[${i.index}].stockItems.makeNew[${j.index}].size"
                                                          size="5"
                                                          maxlength="5">
                                                <input type="hidden" value="<s:property value="stockInfo.stockId"/>"
                                                       name="goods.stockInfoSet.makeNew[${i.index}].stockItems.makeNew[${j.index}].stockInfo.stockId"/>
                                                <input type="hidden" value="<s:property value="stockItemId"/>"
                                                       name="goods.stockInfoSet.makeNew[${i.index}].stockItems.makeNew[${j.index}].stockItemId"/>
                                                库存:<input type="number" min="0"
                                                          value="<s:property value="stockCount"/>"
                                                          name="goods.stockInfoSet.makeNew[${i.index}].stockItems.makeNew[${j.index}].stockCount"
                                                          size="5"
                                                          maxlength="5">
                                            </li>
                                        </s:iterator>
                                    </ul>
                                    <input onclick="addStock_cell(this)" type="button" value="添加规格"/>
                                </li>
                            </s:iterator>
                        </s:else>
                        <li><input onclick="addStock_item(this)" type="button" value="添加颜色"/></li>
                    </ul>
                </td>
            </tr>

            <s:generator var="imgs" separator="#" val="#good.imgpath"/>
            <s:iterator var="nodeimg" value="#imgs" status="i">
                <tr>
                    <td>封面图片${i.index+1}</td>
                    <td>
                        <input name="markname" value="<s:property value='nodeimg'/>" type="hidden"/>
                        <div id="imgPreview" style='width:150px; height:150px;'>
                            <img id="goodimg${i.index}" height="150px" src='/goodsimg/<s:property value="nodeimg"/>'/>
                        </div>
                        <input type="file" name="goodimg" onchange="PreviewImage(this,'goodimg${i.index}')"/>
                        <input type="button" value="删除" onclick="delimg(this)"/><br/>
                    </td>
                </tr>
            </s:iterator>
            <tr>
                <td colspan="2">添加封面图<input name="goodimg" id="goodimg0" type="file" multiple=true
                                            onchange="PreviewImage(this,'goodimg0')"/><label id="label6"></label>
                </td>
            </tr>
            <s:if test="#good.illustrations!=null">
                <s:generator var="imgs" separator="#" val="#good.illustrations"/>
                <s:iterator var="nodeimg" value="#imgs" status="i">
                    <tr>
                        <td>详情图片${i.index+1}</td>
                        <td>
                            <input value="<s:property value='nodeimg'/>" type="hidden"/>
                            <div style='width:150px; height:150px;'>
                                <img id="goodimg${i.index}" height="150px"
                                     src='/goodsimg/<s:property value="nodeimg"/>'/>
                            </div>
                            <input type="file" name="goodimg" onchange="PreviewImage(this,'goodimg${i.index}')"/>
                            <input type="button" value="删除" onclick="delimg(this)"/><br/>
                        </td>
                    </tr>
                </s:iterator>
            </s:if>
            <tr>
                <td colspan="2">添加详情图<input name="illustrations" id="illustrations" type="file" multiple=true
                                            onchange="PreviewImage(this,'illustrations')"/><label id="label7"></label>
                </td>
            </tr>
        </table>
        <div style="align-content: center">
            <input style="margin-left:50%" type="submit" value="确认"/>&nbsp;&nbsp;&nbsp;
            <input type="reset" value="取消" onclick="window.close()"/>
            <s:property value="errormsg"/>
        </div>
    </form>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
<script defer="defer">
    var clothestypeId = $('#clothestypeId').val();
    var brandId = $('#brandId').val();
    $(function () {
        changeBrand();
        changeCrowd();
        var inputTag = $('.color-attr');
        if ($.trim($(inputTag[0]).val()).length === 0) {
            $(inputTag[0]).one('focus', dd);
        }
    });

    var dd = function() {
        var data2 = prompt("输入颜色", "");
        var inputTag = $('.color-attr');
        if ($.trim(data2).length > 0) {
            $(inputTag[0]).val(data2);
            $(inputTag[0]).attr('readonly', 'readonly');
        } else {
            $(inputTag[0]).blur();
            $(inputTag[0]).one('focus', dd);
        }
    };
    function changeBrand() {
        var brandSelect = $('#brand');
        $.ajax({
            url: "queryBrand.do",
            dataType: "JSON",
            success: function (result) {
                $.each(result.brands, function (i, item) {
                    var op = $('<option value="' + item.brandId + '">' + item.brandName + '</option>');
                    $(op).appendTo(brandSelect);
                });
                $(brandSelect).val(brandId);
            }
        });
    }

    function changeCrowd() {
        var selectType = $('#type');
        $.ajax({
            url: "queryCrowd.do",
            dataType: "JSON",
            success: function (result) {
                $.each(result, function (i, item) {
                    var optgroup = $('<optgroup label="' + item.crowdName + '">');
                    $.each(item.data, function (j, _item) {
                        $('<option value="' + _item.id + '">' + _item.name + '</option>').appendTo(optgroup);
                        $(selectType).append(optgroup);
                    });
                });
                $(selectType).val(clothestypeId);
            }
        });
    }
</script>
