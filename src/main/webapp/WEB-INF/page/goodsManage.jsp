<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>商品管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="css/switch.css" media="screen" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
    <script type="text/javascript">
        function changePP(index, id, divided_into) {
            var tdid = "td" + index;
            var imgid = "img" + index;
            var textname = "ppval" + index;
            var img = $("#" + imgid);
            var src = img[0].src;
            var root = $("#" + tdid);
            root.empty();
            var input = $("<input name='" + textname + "' value='" + divided_into + "' type='text' size='3'/>");
            var button = $("<input type='button' value='确定'/>");
            root.append(input, button);
            input.focus();
            var onblur = function () {
                if (input.val().trim() == "") {
                    root.empty();
                    img.attr('src', src);
                    root.append(img);
                } else {
                    input.focus();
                    input.addClass("focus");
                    button.val("清除");
                }
            };
            input.change(function () {
                input.unbind("blur");
            });
            input.bind("blur", onblur);
            button.click(function () {
                var val = button.val();
                var textval = input.val().trim();
                if (textval == "") return;
                if ("清除" == val) {
                    input.val("");
                    root.empty();
                    img.attr('src', src);
                    root.append(img);
                } else if ("确定" == val) {
                    $.ajax({
                        url: "edit_divided_into.do",
                        data: {
                            "goodid": id,
                            "divided_into": textval
                        },
                        dataType: 'text',
                        success: function (result) {
                            if (result != null)
                                alert(result);
                            input.val("");
                            root.empty();
                            img.attr('src', src);
                            root.append(img);
                        }
                    });
                }
            });
        }
    </script>
    <style type="text/css">
        .searchForm {
            text-align: center;
            margin-top: 16px
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
                        tr.append("<img style='height:150px;width: 150px ' src='/goodsimg/" + item + "'/>&nbsp;");
                    }
                });

            });
        });
    });
</script>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>

<div id="content" class="wrap2">

    <div class="list bookList">
        <a href="launchgoodsPage.do" target="_blank"><span>+新增商品</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
            href="launchCrowdPage.do" target="_blank"><span>+新增类别</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
            href="launchBrandPage.do" target="_blank"><span>+新增品牌</span></a>
        <div id="goodsArray">
            <span>搜索条件</span>
            <select id="search_where" name="search_where" style="width:100px;" onchange="change_property(this)">
                <option selected="selected" value="0">--请选择--</option>
                <option value="goods.brand.brandId">品牌</option>
                <option value="goods.clothestype.id">类别</option>
                <option value="goods.price">价格范围</option>
                <option value="goods.goodCode">商品编号</option>
            </select>
            <input id="submitBtn" type="image" src="images/button_search.gif"/>
        </div>
        <s:if test="#goodList==null||#goodList.list.size==0">
            <tr>
                <td colspan="6"><h3>没有找到任何商品信息</h3></td>
            </tr>
        </s:if>
        <s:else>

            <table style="text-align: center;margin-top: 8px" width="960px"
                   height="150px" cellpadding="5px" cellspacing="1px">
                <tr>
                    <th class="orderId">品牌</th>
                    <th class="orderId">商品编号</th>
                    <th class="orderId">商品名称</th>
                    <th class="orderId">商品系列</th>
                    <th class="orderId">商品介绍</th>
                    <th class="orderId">商品价格</th>
                    <s:if test="#session.user.pricinga_power==1">
                        <th class="orderId">分佣比例</th>
                    </s:if>
                    <th class="orderId">库存</th>
                    <th class="orderId">图片展示</th>
                    <th class="orderId">操作</th>
                    <th class="orderId">活动</th>
                </tr>
                <s:iterator var="good" value="%{#goodList.list}" status="i">
                    <s:url var="flashsalePage" value="flashsalePage.do">
                        <s:param name="goodid" value="id"/>
                    </s:url>
                    <tr <s:if test="#i.index%2==0"> class="odd" </s:if>>
                        <td><s:property value="brand.BrandName"/></td>
                        <td><s:property value="goodCode"/></td>
                        <td><s:property value="goodName"/></td>
                        <td><s:property value="clothestype.crowd.name"/><br/> <s:property
                                value="clothestype.name"/></td>
                        <td><s:property value="goodsDesc"/></td>
                        <td><s:property value="price"/></td>
                        <td id="td<s:property value='#i.index'/>">
                            <img id="img<s:property value='#i.index'/>" src="images/more_feedback_icon.png" alt="分佣"
                                 onclick="changePP(${i.index},${good.id},${good.divided_into})"/>
                        </td>
                        <td><s:property value="stock"/></td>
                        <td><textarea style="resize:none" rows="6" cols="50" readonly="readonly"><s:property
                                value='imgpath'/></textarea></td>
                        <td>
                            <p><a href="findGoods.do?goodid=<s:property value="id"/>" target="_blank">修改</a></p>
                            <p><a href="javascript:void(0)"
                                  onclick='delGood(<s:property value="id"/>)'>删除</a>
                            </p>
                            <p>
                                <s:if test="visible">
                                    <a href="#" onclick="regAlert(false,<s:property value="id"/>)">下架</a>
                                </s:if>
                                <s:else>
                                    <a href="#" onclick="regAlert(true,<s:property value="id"/>)">上架</a>
                                </s:else>
                            </p>
                        </td>

                        <td>
                            <s:if test="FlashSales==null||FlashSales.isEmpty()"> <a
                                    href="${flashsalePage}">加入促销</a></s:if><s:else>
                            <a href="#">查看活动</a>
                        </s:else>
                        </td>
                    </tr>
                </s:iterator>
            </table>
            <div id="imglist"></div>
            <div class="page-spliter">
                <s:url var="url_pre" value="showGoods.do">
                    <s:param name="offset" value="offset-1"/>
                </s:url>
                <s:url var="index" value="showGoods.do">
                    <s:param name="offset" value="1"/>
                </s:url>
                <s:url var="url_next" value="showGoods.do">
                    <s:param name="offset" value="offset+1"/>
                </s:url>
                <s:url var="last" value="showGoods.do">
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
                <s:property value="totalSize"/>
                条记录&nbsp;
                跳转第<s:textfield id="sendVal" maxlength="3"/>页
                <input type="button" onclick="regVal2(<s:property value="totalPage"/>)" value="go"/>

            </div>
        </s:else>
    </div>
</div>
<div id="footer" class="wrap">东莞爱芬儿 &copy; 版权所有</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        $('#submitBtn').click(function () {
            var search_value = $('#search_value').val();
            var search_where = $('#search_where').val();
            if ((search_where == 0 && search_value == 0) || (search_where != 0 && search_value != 0)) {
                location.href = "showGoods.do?search_where=" + search_where + "&search_value=" + search_value;
            } else {
                alert('请选择查询条件');
            }
        });
    });

    function regAlert(property, goodid) {
        var _bool = confirm('我好好想想!');
        if (_bool) {
            if (property) {
                location.href = "visibleGoods.do?goods.id=" + goodid + "&goods.visible=true";
            } else {
                location.href = "visibleGoods.do?goods.id=" + goodid + "&goods.visible=false";
            }
        }
    }
</script>
