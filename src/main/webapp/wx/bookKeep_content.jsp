<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>支出记录</title>
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

        .footer {
            position: fixed;
            left: 0;
            bottom: 0;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            width: 100%;
            height: 40px;
            font-size: 1rem;
        }

        .footer a {
            position: relative;
            display: block;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            line-height: 40px;
            text-align: center;
            color: #0e0e0e;
            background-color: #e65300;
            text-decoration: none;
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

        .lists {
            width: 100%;
            height: auto;
            font-size: 1rem;
        }

        .lists li {
            padding: 20px;
            height: 25%;
            margin: 8px;
            background-image: url("images/layout_bg.png");
        }

        .lists li span {
            font-size: 1rem;
            margin: 16px;
        }

        .lists li img {
            -webkit-box-sizing: border-box;
            width: 6.25rem;
            height: 6.25rem;
            border: 2px solid #fff
        }

        .lists li strong {
            color: red;
        }

        .lists li input {
            background-color: #cc0000;
            color: white;
            border-radius: 5px;
            float: right;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/dropload.css">
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
<script>
    var currentSize = 0;
    var totalSize = 0;
    var index = 1;
    var totalPage = 1;
    var user = $.parseJSON(localStorage.getItem('appUser'));
    var phone = user.phone;
    jQuery(function () {
        jQuery('.content').dropload({
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
        jQuery.ajax({
            type: 'GET',
            url: 'showAccount_getSpendings.do',
            data: {
                "phone": phone,
                "offset": index
            },
            dataType: 'json',
            success: function (result) {
                var tag = '';
                totalPage = result.data.pageCount;
                var array = result.data.dataSet;
                totalSize = result.data.totalSize;
                var arrLen = array.length;
                if (refresh) {
                    jQuery('.lists').html('');
                    currentSize = 0;
                }
                if (currentSize < totalSize) {
                    for (var i = 0; i < arrLen; i++) {
                        tag += '<li>' + array[i].spendTime + '&nbsp;' + array[i].spendAmount + '&nbsp;' + array[i].spendDESC + '</li>';
                    }
                    jQuery('.lists').append(tag);
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
                me.resetload();
            }
        });
    }
</script>
</body>
</html>