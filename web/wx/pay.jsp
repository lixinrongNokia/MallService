<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>爱内秀-订单支付</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/shop.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/device.min.js"></script>
    <style type="text/css">
        #check_PaymentCode {
            display: none;
        }

        #header header {
            background-color: #0D96CD;
        }

        #header header h1 {
            color: white;
        }
    </style>
</head>
<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="regConfirm()">返回</div>
            <h1>收银台</h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content">
        <div class="shopOk">
            <h1>订单支付</h1>
            <h3>订单号：<span id="order_sn"></span></h3>
            <h3>应付金额:<span id="total"></span></h3>
            <h3>支付状态:<span id="status">准备支付</span></h3>
            <h3>订单状态:<span id="order">待付款</span></h3>
            <div class="okBtn" onClick="pay1()" id="wxpay">微信支付</div>
            <div class="okBtn" onClick="pay2()" id="alipay">支付宝</div>
            <div class="okBtn" onClick="pay3()" id="inApp">余额支付</div>
        </div>
    </div>
</div>
<!--content结束-->
<!--footer开始-->
<div id="footer">
</div>
<!--footer结束 -->
</div>
<div id="check_PaymentCode" title="密码校验">
    <input name="paymentCode" type="password" id="paymentCode" placeholder="账户支付密码"/>
</div>
</body>
<script>
    var openid = localStorage.getItem('openid');
    var appUser = localStorage.getItem("appUser");
    var wxuser = localStorage.getItem('wxuser');
    var totalPrice = localStorage.getItem('totalPrice');
    var orderId = localStorage.getItem('orderId');
    var href = location.href.split('#')[0];
    var user = appUser ? $.parseJSON(appUser) : $.parseJSON(wxuser);
    var choosewxPayType = 1;//检测微信支付环境:1:H5支付；2:公众号支付
    $(function () {
        if (device.android() || device.ios()) {
            if (openid) {
                choosewxPayType = 2;
                if (!user.onbind) {
                    $('#inApp').hide();
                }
                $('#alipay').hide();
                $.ajax({
                    url: "https://res.wx.qq.com/open/js/jweixin-1.2.0.js",
                    dataType: "script",
                    success: function () {
                        $.getJSON("wxJsSDKInit.do", {"url": "" + href + ""}, function (result) {
                            if (result != null) {
                                //初始化接口环境
                                wx.config({
                                    debug: false,
                                    appId: result.appid,
                                    timestamp: result.timestamp,
                                    nonceStr: result.noncestr,
                                    signature: result.signature,
                                    jsApiList: ['chooseWXPay']
                                });
                            }
                        });
                    }
                });
            }
        } else {
            $('#wxpay').hide();
        }
        $("#order_sn").text(orderId);
        $("#total").text('￥' + totalPrice);
    });

    /*微信支付*/
    function WXPlay(payProperty) {
        $.ajax({
            url: "wxPayPrepayGate.do",
            data: {"payProperty": "" + payProperty + "", "requestCode": 6},
            type: "POST",
            dataType: "json",
            success: function (msg) {
                if (6 === msg.requestCode) {
                    if ('SUCCESS' == msg.result_code) {
                        var property = msg.data;
                        //发起微信支付
                        wx.chooseWXPay({
                            timestamp: property.timeStamp,
                            nonceStr: property.nonceStr,
                            package: property.package,
                            signType: property.signType,
                            paySign: property.paySign,
                            success: function (res) {
                                openwin('pay_success');
                            }, fail: function (res) {
                                toast('支付失败');
                            }, cancel: function (res) {
                                toast('取消支付');
                            }
                        });
                    } else {
                        toast(msg.data.return_msg);
                    }
                }
            }, complete: function () {
                layer.closeAll();
            }
        });
    }

    function pay1() {
        layer.open({type: 2});
        if (choosewxPayType === 1) {
            var payProperty = '<xml><appid><![CDATA[wx82a6291f4ce0547e]]></appid><body><![CDATA[iliker-buy]]></body><out_trade_no><![CDATA[' + orderId + ']]></out_trade_no><total_fee><![CDATA[' + parseInt(parseFloat(totalPrice) * 100) + ']]></total_fee><trade_type><![CDATA[MWEB]]></trade_type><scene_info><![CDATA[{"h5_info": {"type": "h5_info","wap_url": "http://iliker.cn","wap_name": "iliker_mall"}}]]></scene_info></xml>';
            wxH5Pay(payProperty);
        } else {
            var payProperty = '<xml><appid><![CDATA[wx8cb131acdcd8b85d]]></appid><body><![CDATA[iliker-buy]]></body><out_trade_no><![CDATA[' + orderId + ']]></out_trade_no><total_fee><![CDATA[' + parseInt(parseFloat(totalPrice) * 100) + ']]></total_fee><trade_type><![CDATA[JSAPI]]></trade_type><openid><![CDATA[' + openid + ']]></openid></xml>';
            WXPlay(payProperty);
        }
    }

    function wxH5Pay(payProperty) {
        $.ajax({
            url: "wxPayPrepayGate.do",
            data: {"payProperty": "" + payProperty + "", "requestCode": 6},
            type: "POST",
            dataType: "json",
            success: function (msg) {
                if ('SUCCESS' === msg.result_code) {
                    //发起微信支付
                    $.ajax({
                        url: msg.data.mweb_url,
                        type: "GET",
                        success: function () {
                            toast('跳转到微信app');
                        }, error: function () {
                            toast('没有安装微信app');
                        }
                    });
                } else {
                    toast(msg.data.return_msg);
                }
            }, complete: function () {
                layer.closeAll();
            }
        });
    }

    function pay3() {
        var phone = user.phone;
        $.ajax({
            type: "POST",
            url: "showAccount_getWallet.do",
            data: {"phone": "" + phone + ""},
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    var balance = parseInt(result.balance * 100);
                    var payment = parseInt(parseFloat(totalPrice) * 100);
                    if (balance === 0 || balance < payment) {
                        layer.open({
                            content: '你的账户余额不足本次付款!'
                            , btn: ['充值', '不要']
                            , yes: function (index) {
                                openwin('account');
                                layer.close(index);
                            }
                        });
                    } else {
                        layer.open({
                            content: '你账户余额￥' + result.balance + '元,现在付款?'
                            , btn: ['支付', '不要']
                            , yes: function (index) {
                                vilidataPayCode();
                                layer.close(index);
                            }
                        });
                    }
                }
            }
        });
    }

    function vilidataPayCode() {
        //验证支付密码
        $('#paymentCode').val('');
        var setPaymentCode_dialog = $('#check_PaymentCode');
        setPaymentCode_dialog.dialog({
            modal: true,
            buttons: {
                '设置': function () {
                    openwin('account');
                },
                '确定': function () {
                    inAppPay();
                }
            }
        });
    }

    function inAppPay() {
        var payment_code = $('#paymentCode').val();
        var property = {};
        if ($.trim(payment_code) == '') {
            toast('请输入支付密码');
            return;
        }
        property.out_trade_no = orderId;
        property.payAmount = parseFloat(totalPrice);
        property.uid = user.uID;
        property.requestCode = 2;
        property.payment_code = encode64(payment_code);
        $.ajax({
            url: "balancePay.do",
            data: {"payProperty": JSON.stringify(property)},
            dataType: "json",
            type: "POST",
            success: function (result) {
                if (result.result_code === 'SUCCESS' && result.requestCode === 2) {
                    shortToast('支付成功');
                    setTimeout(function () {
                        openwin('user');
                    }, 500)
                } else {
                    toast('支付失败');
                }
            }
        });
    }

    /*支付宝支付*/
    function pay2() {
        aliPay(orderId, totalPrice);
    }

    function aliPay(orderId, amount) {
        $.ajax({
            url: "aliPayForJS.do",
            data: {"out_trade_no": orderId, "total_amount": amount},
            type: "POST",
            dataType: "text",
            success: function (result) {
                $("#footer").append(result);
            }
        });
    }

    function regConfirm() {
        if (confirm('放弃付款了吗?')) {
            openwin('user');
        }
    }

</script>
</html>