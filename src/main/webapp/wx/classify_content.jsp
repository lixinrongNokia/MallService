<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta charset="utf-8">
    <title>商品分类</title>
    <link rel="stylesheet" href="css/ectouch.css">
</head>
<body>
<div id="myDiv">
    <aside id="aside">
        <div class="menu-left" id="sidebar">
            <ul>
            </ul>
        </div>
    </aside>
    <%--<section class="menu-right padding-all j-content">
        <h5>鞋/箱包</h5>
        <ul>
            <li class="w-3"><a href="#"></a> <img src="images/tp.png"><span>时尚女鞋</span></li>
        </ul>
    </section>--%>
</div>
<script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
<script type="text/javascript">
    $(function ($) {
        $.ajax({
            sync: false,
            url: "queryCrowd.do",
            dataType: "JSON",
            success: function (result) {
                parsCrowd(result);
            }
        });

    });

    function parsCrowd(data) {
        $.each(data, function (i, item) {
            var array = item.data;
            if (i == 0) {
                $('<li class="active" onclick="changer(this)">' + item.crowdName + '</li>').appendTo($('#sidebar ul'));
                var sectionNode = $('<section class="menu-right padding-all j-content"></section>');
                $('<h5>' + item.crowdName + '</h5>').appendTo(sectionNode);
                var ulNode = $('<ul></ul>');
                $.each(array, function (j, item2) {
                    var liNode = $('<li class="w-3" onclick=localStorage.typeId=' + item2.id + ';openwin(\'productList\');></li>');
                    $('<a href="javascript:"></a>').appendTo(liNode);
                    $('<img src="/img/' + item2.typeimg + '">').appendTo(liNode);
                    $('<span>' + item2.name + '</span>').appendTo(liNode);
                    liNode.appendTo(ulNode);
                });
                ulNode.appendTo(sectionNode);
                $('#myDiv').append(sectionNode);
            } else {
                $('<li onclick="changer(this)">' + item.crowdName + '</li>').appendTo($('#sidebar ul'));
                var sectionNode = $('<section class="menu-right padding-all j-content" style="display:none"></section>');
                $('<h5>' + item.crowdName + '</h5>').appendTo(sectionNode);
                var ulNode = $('<ul></ul>');
                $.each(array, function (j, item2) {
                    var liNode = $('<li class="w-3" onclick=localStorage.typeId=' + item2.id + ';openwin(\'productList\');></li>');
                    $('<a href="javascript:"></a>').appendTo(liNode);
                    $('<img onclick="loadClothestypes(' + item2.id + ')" src="/img/' + item2.typeimg + '">').appendTo(liNode);
                    $('<span>' + item2.name + '</span>').appendTo(liNode);
                    liNode.appendTo(ulNode);
                });
                ulNode.appendTo(sectionNode);
                $('#myDiv').append(sectionNode);
            }
        });
    }

    function changer(li) {
        $(li).addClass('active').siblings('li').removeClass('active');
        var index = $(li).index();
        $('.j-content').eq(index).show().siblings('.j-content').hide();
    }
</script>
</body>
</html>