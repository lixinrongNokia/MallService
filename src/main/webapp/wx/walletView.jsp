<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>爱内秀-账户</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <style type="text/css">
        #account_info .account-money {
            /*float: left;*/
        }

        #balance {
            color: #e19511;
        }

        .ai-button-list {
            width: 100%;
        }

        .ai-button-list .ai-button {
            text-align: center;
            width: 48%;
            float: left;
            background-color: #F4F4F4;
            padding: 25px 0 25px 0;
            margin-top: 25px;
            border-radius: 5px;
        }

        .ai-button-list .cur {
            text-align: center;
            width: 48%;
            float: left;
            background-color: #3a9bc3;
            padding: 25px 0 25px 0;
            margin-top: 25px;
            border-radius: 5px;
        }

        .ai-button:active {
            background-color: #1e82af;
        }

        #dialog-message, #dialog-transfer, #dialog-recharge {
            display: none;
            size: 12px;
        }

        .keeplist {
            width: 100%;
            text-align: center;
            size: 12px;
        }

        .keeplist tr {
            background-image: url("images/layout_bg.png");
        }

        #spendinghead {
            background-color: #0d86bd;
        }

        .input-style {
            border-color: #878787;
            border-style: solid;
            border-top-width: 0px;
            border-right-width: 0px;
            border-bottom-width: 1px;
            border-left-width: 0px
        }

        #balance2 {
            color: #d86517;
        }

        #transferForm input {
            color: #4e4f50;
            size: 8px;
            Font-style: italic;
        }

        .msg {
            color: red;
        }
    </style>
</head>
<body>
<!-- 账户信息 [[ -->
<div id="account_info" class="account-info clearfix">
    <div class="account-money">
        <div>账户余额</div>
        <div id="balance_box"><span id="balance">...</span>元<span
                class="frozen i-hide"></span>
        </div>
    </div>
    <div>
        <ul class="ai-button-list">
            <li class="ai-button"><span>充 值</span></li>
            <li class="ai-button"><span>提 现</span></li>
            <li class="ai-button">最近收入</li>
            <li class="ai-button">最近支出</li>
        </ul>
        <div id="inflate">

        </div>
    </div>

    <div id="dialog-message" title="重要提示">
        <p>
            <span>到账金额&nbsp;=&nbsp;提取金额&nbsp;-&nbsp;手续费</span>
        </p>
        <p>
            支付宝费率0.6%
        </p>
    </div>
    <div id="dialog-transfer" title="转账到支付宝">
        <span>当前余额:</span><span id="balance2"></span><br/>
        <span>收款账号名:</span>
        <input id="accountName" placeholder="手机号或邮箱" class="input-style"/>
        <span class="msg" id="span1"></span>
        <span>真实姓名:</span><br/>
        <input id="realName" type="text" placeholder="账号对应的姓名" class="input-style"/>
        <span class="msg" id="span2"></span>
        <span>转账金额:</span><br/>
        <input id="transferAmount" autocomplete="off" type="number" placeholder="100以上" class="input-style"/>
        <span class="msg" id="span3"></span>
        <span>支付密码:</span><br/>
        <input id="payment_code" type="password" class="input-style"/>
        <span class="msg" id="span4"></span>
    </div>

    <div id="dialog-recharge" title="账户充值">
        <span>充值金额:</span><br/>
        <input id="rechargeAmount" type="number" placeholder="任意金额" class="input-style"/>
        <span class="msg" id="msg"></span>
    </div>
    <div id="footer">
    </div>
</div>
</body>
</html>
<script defer="defer">
    var transfer = {};
    var currentid = -1;
    var appUser = localStorage.getItem("appUser");
    var wxuser = localStorage.getItem('wxuser');
    var openid = localStorage.getItem('openid');
    var href = location.href.split('#')[0];
    var payobject = {};
    var balance = 0;
    $(function () {
        if (wxuser) {
            payobject.btnVal = '微信支付';
        } else {
            payobject.btnVal = '支付宝支付';
        }
        if (wxuser) {
            $.ajax({
                url: "https://res.wx.qq.com/open/js/jweixin-1.1.0.js",
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
        $.ajax({
            type: "POST",
            url: "showAccount_getWallet.do",
            data: {"phone": "" + phone + ""},
            success: function (result) {
                if (result.success) {
                    balance = result.balance;
                    $('#balance').html(balance);
                }
            },
            dataType: "json"
        });

        $('.ai-button-list li').click(function () {
            var li = $(this);
            li.addClass('cur').siblings('.ai-button').removeClass('cur');
            var selectid = li.index();
            switch (selectid) {
                case 0:
                    dialog_alert_recharge();
                    break;
                case 1:
                    dialog_alert();
                    break;
                case 2:
                    if (currentid == selectid) {
                        return;
                    }
                    getIncome();
                    break;
                case 3:
                    if (currentid == selectid) {
                        return;
                    }
                    getSpending();
                    break;
            }
            currentid = selectid;
            $('#inflate').html('');
        });

    });

    function pay1(orderId, totalPrice) {
        layer.open({type: 2});
        var payProperty = '<xml><appid><![CDATA[wxe44abd08bc92b509]]></appid><body><![CDATA[iliker-buy]]></body><out_trade_no><![CDATA[' + orderId + ']]></out_trade_no><total_fee><![CDATA[' + parseInt(parseFloat(totalPrice) * 100) + ']]></total_fee><mch_id><![CDATA[1436752702]]></mch_id><trade_type><![CDATA[JSAPI]]></trade_type><openid><![CDATA[' + openid + ']]></openid></xml>';
        $.ajax({
            url: "wxPayPrepayGate.do",
            data: {"payProperty": "" + payProperty + "", "requestCode": 6},
            type: "POST",
            dataType: "text",
            success: function (result) {
                layer.closeAll();
                var msg = $.parseJSON(result);
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
                } else {
                    toast('系统有点小问题');
                }
            }, error: function () {
                toast('出错');
            }
        });
    }

    function getIncome() {
        $.getJSON("showAccount_getIncomes.do", {
            "phone": "" + phone + "",
            "offset": 1
        }, function (result) {
            if (result.success) {
                var array = result.data.dataSet;
                var tag = '<table class="keeplist"><tr id="spendinghead"><td>时间</td> <td>金额</td><td>来源</td></tr>';
                for (var i = 0; i < array.length; i++) {
                    tag += '<tr><td>' + array[i].incomeTime + '</td><td>' + array[i].incomeAmount + '</td><td>' + array[i].incomeDESC + '</td></tr>';
                }
                if (result.data.pageCount > 1) {
                 tag += '<tr><th colspan="3"><a href="#" <!--onclick="openwin(\'bookKeep\')"-->查看更多</a></th></tr>';
                 }
                tag += '</table>';
                $('#inflate').html(tag);
            } else {
                $('#inflate').html('<span>没有记录</span>');
            }
        });
    }

    function getSpending() {
        $.getJSON("showAccount_getSpendings.do", {
            "phone": "" + phone + "",
            "offset": 1
        }, function (result) {
            if (result.success) {
                var array = result.data.dataSet;
                var tag = '<table class="keeplist"><tr id="spendinghead"><td>记录时间</td> <td>消费金额</td><td>消费名目</td></tr>';
                for (var i = 0; i < array.length; i++) {
                    tag += '<tr><td>' + array[i].spendTime + '</td><td>' + array[i].spendAmount + '</td><td>' + array[i].spendDESC + '</td></tr>';
                }
                if (result.data.pageCount > 1) {
                    tag += '<tr><th colspan="3"><a href="#" onclick="openwin(\'bookKeep\')">查看更多</a></th></tr>';
                }
                tag += '</table>';
                $('#inflate').html(tag);
            } else {
                $('#inflate').html('<span>没有记录</span>');
            }
        });
    }

    var transfer_dialog;

    function dialog_alert() {
        $('#balance2').html('￥' + balance);
        $('#phone').val(phone);
        if (wxuser) {
            toast('目前支持支付宝转账提现');
        } else {
            $('#dialog-message').dialog({
                modal: true,
                buttons: {
                    '知道了': function () {
                        $(this).dialog("close");
                        transfer_dialog = $('#dialog-transfer');
                        transfer_dialog.dialog({
                            modal: true,
                            buttons: {
                                '提交': function () {
                                    if (submitPost()) {
                                        submitTransfer();
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    function dialog_alert_recharge() {
        transfer_dialog = $('#dialog-recharge');
        if (appUser) {
            transfer_dialog.dialog({
                modal: true,
                buttons: {
                    '支付宝支付': function () {
                        addRecharge(function (result) {
                            if (result.success) {
                                $(transfer_dialog).dialog("close");
                                pay2(result.out_trade_no, result.total_amount);
                            }
                        });
                    }
                }
            });
        } else {
            transfer_dialog.dialog({
                modal: true,
                buttons: {
                    '微信支付': function () {
                        addRecharge(function (result) {
                            if (result.success) {
                                $(transfer_dialog).dialog("close");
                                pay1(result.out_trade_no, result.total_amount);
                            }
                        });
                    }
                }
            });
        }

    }

    /*支付宝支付*/
    function pay2(out_trade_no, total_amount) {
        $.ajax({
            url: "aliPayForJS.do",
            data: {"out_trade_no": out_trade_no, "total_amount": total_amount},
            type: "POST",
            dataType: "text",
            success: function (result) {
                $("#footer").append(result);
            }
        });
    }

    function addRecharge(callback) {
        var rechargeAmount = $('#rechargeAmount').val();
        if (rechargeAmount.length == 0) {
            toast('请填写充值金额');
            return;
        }

        if (rechargeAmount != parseInt(rechargeAmount)) {
            toast('充值金额只能是正整数');
            return;
        }
        if (rechargeAmount.length > 4) {
            toast('充值金额太大');
            return;
        }
        $.ajax({
            type: "POST",
            url: "addRechargeOrder.do",
            data: {
                "rechargeableorder.fromPhone": "" + phone + "",
                "rechargeableorder.taggetPhone": "" + phone + "",
                "rechargeableorder.amount": "" + rechargeAmount + ""
            },
            dataType: "json",
            success: function (result) {
                callback(result);
            }
        });
    }

    function submitTransfer() {
        $.ajax({
            type: "POST",
            url: "addTransfer.do",
            dataType: "JSON",
            data: {
                "transfer.phone": "" + phone + "",
                "transfer.account": "" + transfer.account + "",
                "transfer.realname": "" + transfer.realName + "",
                "transfer.amount": transfer.amount,
                "transfer.note": "{\"note\":\"账户提现\"}",
                "device": "h5",
                "payment_code": "" + transfer.payment_code + ""
            },
            success: function (result) {
                if (result.success) {
                    transfer_dialog.dialog('close');
                    window.location.reload();
                    alert('操作成功');
                } else {
                    alert(result.msg);
                }
            }
        });
    }

    function regAccount() {
        var accountName = $('#accountName').val();
        transfer.account = accountName;
        return checkPhone(accountName) || checkEmail(accountName);
    }

    function regRealName() {
        var realName = $('#realName').val();
        transfer.realName = realName;
        return ischinese(realName);
    }

    function regAmount() {
        var transferAmount = $('#transferAmount').val();
        transfer.amount = transferAmount;
        return transferAmount >= 100 && transferAmount <= balance;
    }

    function regPayment_code() {
        var payment_code = $('#payment_code').val();
        var len = payment_code.length;
        if (len > 0 && len <= 16) {
            transfer.payment_code = encode64(payment_code);
            return true;
        }
        return false;
    }

    function submitPost() {
        $('#span1').html('');
        $('#span2').html('');
        $('#span3').html('');
        $('#span4').html('');
        if (!regAccount()) {
            $('#span1').html('账户名不正确');
            return false;
        }
        if (!regRealName()) {
            $('#span2').html('姓名不正确');
            return false;
        }
        if (!regAmount()) {
            $('#span3').html('金额不足或不能为空');
            return false;
        }
        if (!regPayment_code()) {
            $('#span4').html('支付密码不能为空');
            return false;
        }
        return true;
    }

</script>
