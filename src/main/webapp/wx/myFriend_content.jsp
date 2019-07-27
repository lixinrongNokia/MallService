<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-部门</title>
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

        .content {
            background-color: #fff;
        }

        .content .item {
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-align: center;
            -webkit-box-align: center;
            box-align: center;
            -webkit-align-items: center;
            align-items: center;
            padding: 3.125%;
            border-bottom: 1px solid #ddd;
            color: #333;
            text-decoration: none;
        }

        .content .item img {
            display: block;
            width: 40px;
            height: 40px;
            border: 1px solid #ddd;
        }

        .content .item h3 {
            display: block;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            width: 100%;
            max-height: 40px;
            overflow: hidden;
            line-height: 20px;
            margin: 0 10px;
            font-size: 1.2rem;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/dropload.css">
</head>
<body>
<div class="tab">
    <a href="javascript:" class="item cur">直属部门</a>
    <a href="javascript:" class="item">间属部门</a>
</div>
<div class="content">
    <ul class="lists"></ul>
    <ul class="lists"></ul>
</div>
<!-- jQuery1.7以上 或者 Zepto 二选一，不要同时都引用 -->
<script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath }/wx/js/dropload.min.js"></script>
<script>
    var appUser = $.parseJSON(localStorage.getItem("appUser"));
    var wxuser = $.parseJSON(localStorage.getItem("wxuser"));
    var phone;
    if (appUser != null) {
        phone = appUser.phone;
    } else {
        phone = wxuser.phone;
    }
    var item0 = {index: 1, whereId: 1, tabLoadEnd: false, totalPage: 1, totalSize: 0, currentSize: 0};
    var item1 = {index: 1, whereId: 2, tabLoadEnd: false, totalPage: 1, totalSize: 0, currentSize: 0};
//    var item3 = {index: 1, whereId: 3, tabLoadEnd: false, totalPage: 1, totalSize: 0, currentSize: 0};
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
            if (itemIndex === 0) {
                // 如果数据没有加载完
                if (item0.tabLoadEnd) {
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                } else {
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }
                // 如果选中菜单二
            } else if (itemIndex === 1) {
                if (item1.tabLoadEnd) {
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                } else {
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }
            }/*else{
                if (item3.tabLoadEnd) {
                    // 锁定
                    dropload.lock('down');
                    dropload.noData();
                } else {
                    // 解锁
                    dropload.unlock();
                    dropload.noData(false);
                }
            }*/
            // 重置
            dropload.resetload();
        });

        // dropload
        var dropload = $('.content').dropload({
            scrollArea: window,
            loadUpFn: function (me) {
                if (itemIndex === 0) {
                    item0.index = 1;
                    item0.tabLoadEnd = false;
                    loadDate(itemIndex, item0, me, true);
                } else if (itemIndex === 1) {
                    item1.index = 1;
                    item1.tabLoadEnd = false;
                    loadDate(itemIndex, item1, me, true);
                }/*else{
                    item2.index = 1;
                    item2.tabLoadEnd = false;
                    loadDate(itemIndex, item2, me, true);
                }*/
            },
            loadDownFn: function (me) {
                // 加载菜单一的数据
                if (itemIndex === 0) {
                    loadDate(itemIndex, item0, me, false);
                    // 加载菜单二的数据
                } else if (itemIndex === 1) {
                    loadDate(itemIndex, item1, me, false);
                }/*else {
                    loadDate(itemIndex, item2, me, false);
                }*/
            },
            threshold: 100
        });
    });

    function loadDate(itemIndex, object, me, refresh) {
        $.ajax({
            type: 'GET',
            url: 'loadChild.do',
            data: {
                "whereId": object.whereId,
                "superiornum": "" + phone + "",
                "offset": object.index
            },
            dataType: 'json',
            success: function (result) {
                var tag = '';
                object.totalPage = result.pageCount;
                object.totalSize = result.totalSize;
                var data = result.dataSet;
                var len = data.length;
                if (refresh) {
                    $('.lists').eq(itemIndex).html('');
                    object.currentSize = 0;
                }
                if (object.currentSize < object.totalSize) {
                    for (var i = 0; i < len; i++) {
                        var headimg = data[i].headimg === '' ? 'avatar_def.png' : data[i].headimg;
                        tag += '<li onclick=localStorage.touid="' + data[i].phone + '";localStorage.toAvatar="' + data[i].headimg + '";localStorage.toNick="' + data[i].nickname + '";wxuser==null?openwin("session"):"";><img src="/share/' + headimg + '"/>\<span>' + data[i].nickname + '</span></li>';
                    }
                    $('.lists').eq(itemIndex).append(tag);
                    object.currentSize += len;
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