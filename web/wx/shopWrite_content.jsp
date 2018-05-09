<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>爱内秀-确认订单</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/shop.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        .onhave {
            background-color: red;
            color: #fff;
        }

        .unhave {
            background-color: white;
            color: #7E7E7E;
        }
    </style>
</head>
<body class="um-vp">
<div class="write">
    <h1><img src="${pageContext.request.contextPath }/wx/css/res-apple/dizhi.png">确认收货地址</h1>
    <div class="choose address">
        <p id="name">请选择收货地址</p>
        <p id="address"></p>
    </div>
    <h1><img src="${pageContext.request.contextPath }/wx/css/res-apple/dingdan.png">确认订单信息</h1>
    <ul class="dingDan" id="cartlist">
    </ul>
    <div id="couponsChoose" class="choose address coupons">
        <p id="coupons">卡券<span class="onhave" id="prepaidcard"></span></p>
    </div>
</div>
</body>
<script defer="defer">
    var href = location.href.split('#')[0];
    var wxuser = localStorage.getItem('wxuser');
    var appUser = localStorage.getItem('appUser');
    var user = appUser ? $.parseJSON(appUser) : $.parseJSON(wxuser);
    jQuery(function () {
        if (wxuser) {
            $.ajax({
                url: "https://res.wx.qq.com/open/js/jweixin-1.1.0.js",
                dataType: "script",
                success: function () {
                    jQuery.getJSON("wxJsSDKInit.do", {"url": "" + href + ""}, function (result) {
                        if (result != null) {
                            //初始化接口环境
                            wx.config({
                                debug: false,
                                appId: result.appid,
                                timestamp: result.timestamp,
                                nonceStr: result.noncestr,
                                signature: result.signature,
                                jsApiList: ['editAddress', 'getLatestAddress', 'openAddress']
                            });
                        }
                    });
                }
            });
        }
        var orderContactInfo = localStorage.getItem('orderContactInfo');
        if (orderContactInfo) {
            orderContactInfo = $.parseJSON(orderContactInfo);
            setHtml('name', orderContactInfo.recevername + '&nbsp;&nbsp;' + orderContactInfo.recevertel);
            setHtml('address', orderContactInfo.receveraddr);
        }
        if (appUser || user.onbind) {
            loadCouponsByPhone(user.phone);
        }


        $('.choose.address:first').click(function () {
            if (wxuser) {
                //拉取微信用户地址
                var user = $.parseJSON(wxuser);
                wx.openAddress({
                    success: function (res) {
                        if (user.onbind) {
                            var orderContactInfo = '{\"phone\":\"' + user.phone + '\",\"recevername\":\"' + res.userName + '\",\"recevertel\":\"' + res.telNumber + '\",\"receveraddr\":\"' + res.provinceName + '' + res.cityName + '' + res.countryName + '' + res.detailInfo + '\"}';
                            localStorage.setItem('orderContactInfo', orderContactInfo);
                        }
                        setHtml('name', res.userName + '&nbsp;&nbsp;' + res.telNumber);
                        setHtml('address', res.provinceName + '' + res.cityName + '' + res.countryName + '' + res.detailInfo);
                    },
                    cancel: function () {
                        if (localStorage.orderContactInfo == null) {
                            toast('用户取消拉取地址');
                        }
                    },
                    fail: function () {
                        if (!user.onbind) {
                            bind_platform();
                        } else {
                            openwin('addressList1');
                        }
                    }
                });
            } else {
                openwin('addressList1');
            }
        });

        $('.btn.right').click(function () {
            if (wxuser) {
                var user = $.parseJSON(wxuser);
                if (!user.onbind) {
                    bind_platform();
                } else {
                    submitOrder();
                }
            } else {
                submitOrder();
            }
        });
        $('#couponsChoose').bind('click', chooseCoupons);
        confirmOrder();

    });
    function loadCouponsByPhone(phone) {
        $.ajax({
            type: "POST",
            url: "findCouponsByPhone.do",
            data: {"phone": "" + phone + "", "offset": 1},
            dataType: 'json',
            success: function (result) {
                $('#prepaidcard').text(result.totalSize + '张可用');
            }, error: function () {
                $('#couponsChoose').unbind('click', chooseCoupons);
                $('#prepaidcard').text('不可用');
                $('#coupons span').removeClass();
                $('#coupons span').attr('class', 'unhave');
            }
        });
    }
    function chooseCoupons() {
        //选择优惠券

    }

    function bind_platform() {
        //底部对话框
        layer.open({
            content: '绑定手机号到爱内秀更有优惠'
            , btn: ['绑定', '不要']
            , skin: 'footer'
            , yes: function (index) {
                localStorage.callAddress = 'shopWrite';
                openwin('bind_platform');
            }
        });
    }
</script>
</html>