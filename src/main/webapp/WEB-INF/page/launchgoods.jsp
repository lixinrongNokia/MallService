<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>


<!DOCTYPE HTML>
<html>
<head>

    <title>添加商品</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <style type="text/css">
    </style>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <style type="text/css">
        table {
            border-collapse: collapse;
        }

        .stocks li input[type="text"] {
            border: none;
            border-bottom-style: solid;
        }

        .stock_li {
            height: auto;
            border: 1px dashed #747474;
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
    <span>一键导入:</span><input type="image" src="images/flight_butn_add_normal.png" alt="导入商品"
                             onclick="window.location='exportGoodsView.do'">
    <span>下载导入表格:</span><input type="image" src="images/myn.png" alt="导入商品"
                               onclick="window.location='downloadExcel.do'">
    <form style="margin-top: 30px" action="launchgood.do"
          enctype="multipart/form-data" method="post"
          onsubmit="return regLaunch()">
        <table align="center" id="testTable">
            <tr>
                <td class="right">品牌:</td>
                <td class="left">
                    <select id="brand" name="goods.brand.brandId">
                        <option selected="selected" value="0">--请选择--</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="right">商品货号 :</td>
                <td class="left"><input maxlength="20" id="goodCode"
                                        name="goods.goodCode" type="text" onblur="exists(this.value)"/><label
                        id="label1"></label></td>
            </tr>
            <tr>
                <td class="right">商品名称 :</td>
                <td class="left"><input maxlength="50" id="goodName"
                                        name="goods.goodName" type="text"/><label id="label2"></label></td>
            </tr>
            <tr>
                <td class="right">商品类别:</td>
                <td class="left">
                    <select style="width:68px;" id="type" name="goods.clothestype.id">
                        <option selected="selected" value="-1">-请选择-</option>
                    </select>
                    <label id="label3"></label></td>
            </tr>
            <tr>
                <td class="right">颜色与尺寸:</td>
                <td class="left">
                    <ul class="stocks">
                        <li class="stock_li">
                            <p>颜色:<input class="color-attr" type="text" placeholder="颜色" name="stockInfoSet.makeNew[0].color" size="5"
                                         maxlength="5">样式:<input
                                    type="file" name="stockImg" onchange="PreviewImage(this)"/></p>
                            <ul class="stockItem">
                                <li>规格:<input placeholder="尺寸" type="text"
                                              name="stockInfoSet.makeNew[0].stockItems.makeNew[0].size" size="5"
                                              maxlength="5">库存:<input type="number" min="0"
                                                                      name="stockInfoSet.makeNew[0].stockItems.makeNew[0].stockCount"
                                                                      size="5"
                                                                      maxlength="5">
                                </li>
                            </ul>
                            <input onclick="addStock_cell(this)" type="button" value="添加规格"/>
                        </li>
                        <li><input onclick="addStock_item(this)" type="button" value="添加颜色"/></li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td class="right">商品介绍 :</td>
                <td class="left"><textarea style="resize:none" rows="3"
                                           cols="20" maxlength="200" id="goodDesc"
                                           name="goods.goodsDesc"></textarea><label
                        id="label4"></label></td>
            </tr>
            <tr>
                <td class="right">商品价格 :</td>
                <td class="left"><input id="goodPrice" maxlength="6"
                                        name="goods.price" type="text"/><label id="label5"></label></td>
            </tr>
            <tr id="titleimg">
                <td class="right">商品封面图 :</td>
                <td class="left">
                    <input name="goodimg" id="goodimg0" type="file" multiple=true
                           onchange="PreviewImage(this)"/>
                    <label id="label6"></label>
                </td>
            </tr>
            <tr id="descimg">
                <td class="right">商品详情图 :</td>
                <td class="left">
                    <input name="illustrations" id="illustrations" type="file" multiple=true
                           onchange="PreviewImage(this)"/>
                    <label id="label8"></label>
                </td>
            </tr>
        </table>
        <input style="margin-left:auto;margin-right:auto; width: 960px;height: 50px" type="submit" value="添加"
               id="submit"/>
        <s:property value="message"/>
    </form>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
<script defer="defer">
    $(function () {
        var inputTag = $('.color-attr');
        if ($.trim($(inputTag[0]).val()).length === 0) {
            $(inputTag[0]).one('focus', function () {
                var data2 = prompt("输入颜色", "");
                $(inputTag[0]).val(data2);
                $(inputTag[0]).attr('readonly', 'readonly');
            });
        }
        $.ajax({
            url: "queryBrand.do",
            dataType: "JSON",
            success: function (result) {
                $.each(result.brands, function (i, item) {
                    $('#brand').append($('<option value="' + item.brandId + '">' + item.brandName + '</option>'));
                });
            }
        });

        $.ajax({
            url: "queryCrowd.do",
            dataType: "JSON",
            success: function (result) {
                $.each(result, function (i, item) {
                    var optgroup = $('<optgroup label="' + item.crowdName + '">');
                    $.each(item.data, function (j, _item) {
                        $('<option value="' + _item.id + '">' + _item.name + '</option>').appendTo(optgroup);
                    });
                    $('#type').append(optgroup);
                });
            }
        });
        /*$("#otherColor").bind("click", function () {
         if (this.checked) {
         $("[name = colorArray]:checkbox").attr("disabled", true);
         $("[name = colorArray]:checkbox").removeAttr("checked");
         $('#colors').removeAttr("disabled");
         } else {
         $("[name = colorArray]:checkbox").attr("disabled", false);
         $('#colors').attr("disabled", true);
         $('#colors').val('');
         }

         });

         $("#otherSize").bind("click", function () {
         if (this.checked) {
         $("[name = sizeArray]:checkbox").attr("disabled", true);
         $("[name = sizeArray]:checkbox").removeAttr("checked");
         $('#sizes').removeAttr("disabled");
         } else {
         $("[name = sizeArray]:checkbox").attr("disabled", false);
         $('#sizes').attr("disabled", true);
         $('#sizes').val('');
         }

         });*/
    });
</script>
