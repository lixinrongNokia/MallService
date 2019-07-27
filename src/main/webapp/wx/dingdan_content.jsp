<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-我的订单</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
            -webkit-text-size-adjust: none;
        }

        body {
            background-color: #f5f5f5;
            font-size: 1.2em;
        }

        .content {
            padding-bottom: 40px;
            background-color: #fff;
        }

        @-webkit-keyframes opacity {
            0% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }

        @keyframes opacity {
            0% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }

        .footer a:before {
            content: '';
            position: absolute;
            left: 0;
            top: 10px;
            width: 1px;
            height: 20px;
            background-color: #ccc;
        }

        .footer a:first-child:before {
            display: none;
        }

        .content .lists {
            width: 100%;
            height: auto;
            font-size: 1rem;
        }

        .content .lists li {
            height: 25%;
            margin: 8px;
            background-color: #e3e3e3;
        }

        .content .lists li span {
            font-size: 1rem;
            margin: 16px;
        }

        .content .lists li img {
            -webkit-box-sizing: border-box;
            width: 6.25rem;
            height: 6.25rem;
            border: 2px solid #fff
        }

        .content .lists li strong {
            color: red;
        }

        .content .lists li input {
            background-color: #cc0000;
            color: white;
            border-radius: 5px;
            float: right;
        }

        .tab {
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            height: 44px;
            line-height: 44px;
            border-bottom: 1px solid #ff3c3c;
            background-color: #eee;
        }

        .tab .item {
            display: block;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            width: 100%;
            font-size: 14px;
            text-align: center;
            color: #333;
            text-decoration: none;
        }

        .tab .cur {
            height: 42px;
            border-bottom: 2px solid #ff3c3c;
            color: #ff3c3c;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/dropload.css">
</head>
<body>
<div class="tab">
    <a href="javascript:" class="item cur">待付款</a>
    <a href="javascript:" class="item">待收货</a>
    <a href="javascript:" class="item">确认收货</a>
    <a href="javascript:" class="item">已取消</a>
</div>
<div class="content">
    <ul class="lists"></ul>
    <ul class="lists"></ul>
    <ul class="lists"></ul>
    <ul class="lists"></ul>
</div>
<!-- jQuery1.7以上 或者 Zepto 二选一，不要同时都引用 -->
<script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath }/wx/js/dropload.min.js"></script>
<script defer="defer">
    var appUser = $.parseJSON(localStorage.getItem("appUser"));
    var wxuser = $.parseJSON(localStorage.getItem("wxuser"));
    var phone;
    if (appUser != null) {
        phone = appUser.phone;
    } else {
        phone = wxuser.phone;
    }
    var item0 = {
        index: 1,
        queryCondition: {attr0: 'orderstate', val0: '等待付款'},
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    var item1 = {
        index: 1,
        queryCondition: {attr0: 'paymentstate', val0: 1},
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    var item2 = {
        index: 1,
        queryCondition: {attr0: 'orderstate', val0: '已收货'},
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    var item3 = {
        index: 1,
        queryCondition: {attr0: 'orderstate', val0: '已取消'},
        tabLoadEnd: false,
        totalPage: 1,
        totalSize: 0,
        currentSize: 0
    };
    $(function () {
        var itemIndex = 0;
        // tab
        $('.tab .item').on('click', function () {
            var alink = $(this);
            var selectId = alink.index();
            if (itemIndex === selectId) {
                return;
            }
            itemIndex = selectId;
            alink.addClass('cur').siblings('.item').removeClass('cur');
            $('.lists').eq(itemIndex).show().siblings('.lists').hide();
            // 如果选中菜单一
            switch (itemIndex) {
                case 0:
                    selectClass(item0);
                    break;
                case 1:
                    selectClass(item1);
                    break;
                case 2:
                    selectClass(item2);
                    break;
                case 3:
                    selectClass(item3);
                    break;
            }
            // 重置
            dropload.resetload();
        });

        function selectClass(object) {
            if (object.tabLoadEnd) {
                // 锁定
                dropload.lock('down');
                dropload.noData();
            } else {
                // 解锁
                dropload.unlock();
                dropload.noData(false);
            }
        }

        // dropload
        var dropload = $('.content').dropload({
            scrollArea: window,
            loadUpFn: function (me) {
                switch (itemIndex) {
                    case 0:
                        item0.index = 1;
                        item0.tabLoadEnd = false;
                        loadDate(itemIndex, item0, me, true);
                        break;
                    case 1:
                        item1.index = 1;
                        item1.tabLoadEnd = false;
                        loadDate(itemIndex, item1, me, true);
                        break;
                    case 2:
                        item2.index = 1;
                        item2.tabLoadEnd = false;
                        loadDate(itemIndex, item2, me, true);
                        break;
                    case 3:
                        item3.index = 1;
                        item3.tabLoadEnd = false;
                        loadDate(itemIndex, item3, me, true);
                        break;
                }
            },
            loadDownFn: function (me) {
                switch (itemIndex) {
                    case 0:
                        loadDate(itemIndex, item0, me, false);
                        break;
                    case 1:
                        loadDate(itemIndex, item1, me, false);
                        break;
                    case 2:
                        loadDate(itemIndex, item2, me, false);
                        break;
                    case 3:
                        loadDate(itemIndex, item3, me, false);
                        break;
                }
            },
            threshold: 100
        });
    });

    function loadDate(itemIndex, object, me, refresh) {
        $.ajax({
            type: 'GET',
            url: 'loadOrderByPhone.do',
            data: {
                "propertyName": "phone",
                "queryVal": phone,
                "propertyName2": object.queryCondition.attr0,
                "queryVal2": object.queryCondition.val0,
                "offset": object.index
            },
            dataType: 'json',
            success: function (result) {
                var tag = '';
                object.totalPage = result.pageCount;
                object.totalSize = result.totalSize;
                var data = result.dataSet;
                var arrLen = data.length;
                if (refresh) {
                    $('.lists').eq(itemIndex).html('');
                    object.currentSize = 0;
                }
                if (object.currentSize < object.totalSize) {
                    for (var i = 0; i < arrLen; i++) {
                        var imgs = data[i].imgpath.split(",");
                        tag += '<li onclick="orderDetail(' + data[i].id + ')"><p><span>' + data[i].orderstate + '</span><span>' + data[i].orderdate + '</span></p>';
                        for (images in imgs) {
                            tag += '<img src="/goodsimg/' + imgs[images] + '" alt="goods">';
                        }
                        tag += '<br/>数量:<span>' + data[i].orderamount + '</span><strong>价格:￥' + data[i].toalprice + '</strong>';
                        if (data[i].orderstate === '等待付款') {
                            tag += '<input type="button" onclick=localStorage.orderId="' + data[i].orderid + '";localStorage.totalPrice="' + data[i].toalprice + '";openwin(\'pay\') value="去付款"/>'
                        }
                        tag += '</li>';
                    }
                    $('.lists').eq(itemIndex).append(tag);
                    object.currentSize += arrLen;
                }
                if (object.totalPage > object.index) {
                    object.index++;
                } else {
                    // 数据加载完
                    object.tabLoadEnd = true;
                    // 锁定
                    me.lock();
                    // 无数据
                    me.noData();
                }
                // 每次数据加载完，必须重置
                me.resetload();
            },
            error: function (xhr, type) {
                // 即使加载出错，也得重置
                me.noData();
                me.resetload();
            }
        });
    }

</script>
</body>
</html>