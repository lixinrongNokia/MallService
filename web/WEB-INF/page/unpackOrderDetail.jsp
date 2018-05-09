<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>爱内秀-订单详情</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link href="../../wx/css/orderdetail.css" rel="stylesheet"/>
    <script type='text/javascript' src='${pageContext.request.contextPath }/js/jquery-1.12.4.min.js'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        #gotoPay, #cancelOrderBtn {
            display: none;
        }
    </style>
</head>

<body id="body">
<a name="top"></a>
<header>
    <div class="coupon-header">
        <a href="javascript:" id="goBack" onclick="responseBack()"><span class="coupon-header-arrow"></span></a>
        <span class="coupon-header-font">订单详情</span>
    </div>
</header>
<div class="common-wrapper pad55">
    <!--订单信息栏-->
    <div class="m step1 bdr-new">
        <div class="order-info-box bdb-1px">
            <div class="mt  cf">
                <div class="floatL">订单号:<span class="s1-num">49460909306</span></div>
                <div class="rightRedF"></div>
            </div>
        </div>
        <!--取消、退款进度栏-->
        <!--物流信息栏-->

    </div>
    <!--用户地址栏-->
    <div class="step2 bdr-new">
        <div class="m step2-in ">
            <div class="mt">
                <div class="s2-name"><i></i></div>
                <div class="s2-phone"><i></i></div>
            </div>
            <div id="receveraddr"></div>
        </div>
        <b class="s2-borderT"></b>
        <b class="s2-borderB"></b>
    </div>

    <!--商品列表-->
    <div class="m step3 bdr-new">
        <div class="mt cf">
            <span class="jd-icon"></span><span class="shop-name">爱内秀</span>
        </div>
        <div class="mc">
            <div class="step3-more" onclick="loadmore()">
                <strong>还有<i>1</i>件</strong><span class="s3-down"></span>
            </div>
        </div>
    </div>
    <!-- 联系客服 -->
    <div class="step4-contact bdb-1px" id="dongdong">
        <a class="contact-a" id="contactCustom" onclick=localStorage.touid="alq85115612@163.com";localStorage.toNick="爱内秀";openwin("session")>
            <i></i>联系客服
        </a>
    </div>
    <!-- 联系客服 -->

    <!--支付方式  配送信息  发票信息 -->
    <div class="m step4 bdr-new">
        <div class="mt bdb-1px cf">
            <h2 class="invoice-left">支付方式</h2><span id="paymethod" class="invoice-right"></span>
        </div>
        <div class="mc">
            <div class="send01 bdb-1px change-p">
                <div class="distribe cf">
                    <h3 class="invoice-left">配送信息</h3><span id="postmethod" class="invoice-right"></span>
                </div>
                <div class="s4-con">
                    <div class="s4-l">
                        <p>配送日期:付款两天内</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="mc change-p">
            <div class="distribe cf">
                <h3 class="invoice-left">发票信息</h3>
                <span class="invoice-right">电子发票
															</span>
            </div>
            <div class="s4-con">
                <div class="s4-l">
                    <p>发票抬头:个人</p>
                    <p>发票内容:明细</p>
                </div>
            </div>
        </div>
    </div>
    <!--label展示-->
    <div class="step5 bdr-new">
        <div class="s5-item-w bdb-1px">
            <div class="s-item size15">
                <div class="sitem-l">
                    商品总额
                </div>
                <div id="totalPrice" class="sitem-r">

                </div>
            </div>
            <div class="s-item">
                <div class="sitem-l">
                    <span>+运费</span>

                </div>
                <div class="sitem-r" id="deliverFee">
                    &yen;0.00
                </div>
            </div>
        </div>
        <div class="s5-sum">
            <div>实付款:<span id="payTotal"></span></div>
            <p id="orderDate"></p>
        </div>
    </div>
    <!--底部按钮 根据状态判断按钮显示个数   具体按钮是不是显示服务端下发字段判断-->
    <div class="btn-bar" id="btnBar">
        <div class="bb-info" id="bbInfo">
            <div class="bottom-but"><a id="gotoPay"
                                       href="#" onclick="openwin('pay')" class="bb-btn1-red">去支付</a></div>
            <div class="bottom-but"><a href="javascript:" class="bb-btn1-box" id="cancelOrderBtn">
                <div class="bb-btn1">取消订单</div>
            </a></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var orderInfo;
    var allowsPays = ['等待付款', '待审核', '已审核', '等待配货', '等待发货'];
    var href = window.location.search;
    $(function () {
        $('#cancelOrderBtn').click(function () {
            layer.open({
                content: orderInfo.paymentstate?'您确定要取消已付款订单？':'您确定要取消未付款订单?'
                ,btn: ['确定', '放弃']
                ,yes: function(index){
                    $.ajax({
                        url: 'userCancelOrder.do',
                        data: {"tOrder.id":orderInfo.id},
                        dataType: 'json',
                        success: function (result) {
                            if(result.success){
                                toast('取消成功');
                                setTimeout(function () {
                                    window.location.reload();
                                }, 2000);
                            }else {
                                toast(result.msg);
                            }
                        }
                    });
                    layer.close(index);
                }
            });

        });
        var code = GetQueryString('code');
        if (code) {
            var openid = localStorage.getItem('openid');
            if (openid === null) {
                getJSToten(code);
            }
        }
        $.getJSON("orderDetailById.do" + href, function (result) {
            if (result.success) {
                orderInfo = result.orderInfo;
                localStorage.orderId = orderInfo.orderid;
                localStorage.totalPrice = orderInfo.toalprice;
                if (orderInfo.postmethod == '门店自提') {
                    $('.s2-name').html('<i></i>' + orderInfo.storeInfo.storeName);
                    $('.s2-phone').html('<i></i>' + orderInfo.storeInfo.tell);
                    $('#receveraddr').html(orderInfo.storeInfo.address);
                } else {
                    $('.s2-name').html('<i></i>' + orderInfo.recevername);
                    $('.s2-phone').html('<i></i>' + orderInfo.recevertel);
                    $('#receveraddr').html(orderInfo.receveraddr);
                }
                $('.s1-num').html(orderInfo.orderid);
                $('.rightRedF').html(orderInfo.paymentstate ? '已支付' : '未支付');
                var tag = '';
                $.each(orderInfo.orderItem, function (i, item) {
                    tag += '<a href="javascript:" onclick="goodsInfo(' + item.goodsid + ')" class="a-link"><div class="s-item bdt-1px"> <div class="pdiv"> <div class="sitem-l"> <div class="sl-img-box"> <div class="sl-img"><img src="/goodsimg/' + item.imgpath + '"></div></div></div><div class="sitem-m"><p class="sitem-m-txt"><span>' + item.goodname + '</span></p><p class="s3-num">x' + item.orderamount + '</p></div><div class="sitem-r">销售价:&yen;' + item.productprice + '</div></div></div></a>';
                });
                $('.step3-more').parent().prepend(tag);
                $('#paymethod').html(orderInfo.paymethod);
                $('#postmethod').html(orderInfo.postmethod);
                $('#totalPrice').html('&yen;' + orderInfo.goodsTotalPrice);
                $('#deliverFee').html('&yen;' + orderInfo.deliverFee);
                $('#payTotal').html('&yen;' + orderInfo.toalprice);
                $('#orderDate').html('下单时间:' + orderInfo.orderdate);
                if (orderInfo.orderstate === '等待付款') {
                    $('#gotoPay').css('display', 'block');
                }
                if ($.inArray(orderInfo.orderstate, allowsPays) > -1) {
                    $('#cancelOrderBtn').css('display', 'block');
                }

                var items = $('.step3 .s-item');
                var len = items.length;
                if (len > 3) {
                    $('.step3-more').css('display', 'block');
                    $('.step3-more').find('strong').html('还有<i>' + parseInt(len - 3) + '</i>件');
                }
                for (var i = 0; i < 3; i++) {
                    $(items[i]).css('display', 'block');
                }
            }
        });
    });

    var show = false;

    function loadmore() {
        var items = $('.step3 .s-item');
        var len = items.length;
        if (!show) {
            show = true;
            for (var i = 3; i < len; i++) {
                $(items[i]).css('display', 'block');
            }
            $('.step3-more').find('strong').html('收起');
        } else {
            for (var i = 3; i < len; i++) {
                $(items[i]).css('display', 'none');
            }
            $('.step3-more').find('strong').html('还有<i>' + parseInt(len - 3) + '</i>件');
        }
        event.cancelBubble = true;
    }
</script>
</body>
</html>