<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>商品列表</title>
    <link rel="stylesheet" href="css/base.css">
    <link rel="stylesheet" href="css/classify.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/dropload.css">
    <style type="text/css">
        .lists{
            width: 100%;
            background-color: red;
        }
    </style>
</head>
<body>
<div class="content">
    <ul class="lists"></ul>
</div>
<div class="footer">
</div>
<!-- jQuery1.7以上 或者 Zepto 二选一，不要同时都引用 -->
<script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath }/wx/js/dropload.min.js"></script>
<script defer="defer">
    // 页数
    var index = 1;
    var totalPage = 1;
    var typeId = localStorage.getItem('typeId');
    var currentSize = 0;
    var totalSize = 0;
    $(function () {
        // dropload
        $('.content').dropload({
            scrollArea: window,
            loadUpFn: function (me) {
                // 重置页数，重新获取loadDownFn的数据
                index = 1;
                loadData(me, true);
            },
            loadDownFn: function (me) {
                loadData(me, false);
            },
            threshold: 100
        });
    });
    function loadData(me, refresh) {
        $.ajax({
            type: 'GET',
            url: 'productList.do',
            data: {
                "where_clause": "clothestypeid=" + typeId + " and visible=1",
                "order_clause": "market_date DESC",
                "offset": index
            },
            dataType: 'json',
            success: function (result) {
                // 拼接HTML
                var tag = '';
                totalPage = result.pageCount;
                var data = result.dataSet;
                totalSize = result.totalSize;
                saveGoods(data);
                var arrLen = data.length;
                if (refresh) {
                    $('.lists').html('');
                    currentSize = 0;
                }
                if (currentSize < totalSize) {
                    for (var i = 0; i < arrLen; i++) {
                        var imgs = data[i].imgpath.split(",")[0];
                        tag += "<li class='producList' onclick='goodsInfo(" + data[i].id + ")'><div class='item'><div class='pic'><img src='/goodsimg/" + imgs + "' /></div><p>" + data[i].goodName + "</p><strong>￥" + data[i].price + "</strong><div></li>";
                    }
                    $('.lists').append(tag);
                    currentSize += arrLen;
                }
                if (totalPage > index) {
                    index++;
                } else {
                    // 锁定
                    me.lock();
                    // 无数据
                    me.noData();
                }
                me.resetload();
            },
            error: function (xhr, type) {
                me.noData();
                // 即使加载出错，也得重置
                me.resetload();
            }
        });
    }
</script>
</body>
</html>