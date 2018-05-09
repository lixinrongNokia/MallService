<%--suppress ALL --%>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>爱内秀-设置</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <style>
        body {
            background-color: #FFFFff;
        }

       /* .infoPhone, .infoNickName {
            display: none;
        }

        .txtEnable, .txtDisable {
            display: none;
        }*/

        .person {
            background-color: #CCCCCC;
            clear: both;
        }

        .person > ul > li {
            color: #0066cc;
            border: 1px dashed #747474;
        }

        .person > ul > li>div {
            float: right;
        }

        .secure {
            background-color: #CCCCCC;
        }

        #dialog_setPaymentCode {
            display: none;
        }

        #msg, #msg1 {
            color: red;
            size: 12px;
            display: none;
        }
    </style>
</head>
<body>

<div class="title clearfix">
    <div class="label">基本信息</div>
</div>
<div class="person">
    <ul>
        <li class="desc clearfix">
            <span>真实姓名</span>
            <div class="info">
                <span class="info-name">李新荣</span>
                <span class="info-id-card"></span>
            </div>
        </li>
        <li class="infoPhone">
            <span class="label">手机号码</span>
            <div>  <!-- 已绑定手机的用户 -->
                <div class="info">
                    <span></span>
                </div>
                <div class="opt">
                    <a href="javascript:void(0)" data-type="change"></a>
                </div>
            </div>
        </li>

        <li class="infoNickName">
            <span class="label">昵称</span>
            <div>
                <div class="info">
                    <span class="nickName"></span>
                </div>
                <div class="opt">
                    <a href="javascript:void(0)" data-type="change">修改</a>
                </div>
            </div>
        </li>
        <li class="desc clearfix">
            <span class="label">电子邮箱</span> <!-- 已添加邮箱的用户 -->
            <div>
                <div class="info">
                    <span class="email"></span>
                </div>
                <div class="opt">
                    <a href="#">修改</a>
                </div>
            </div>
            <div>
                <a href="#">激活</a><span
                    class="vertical-split"></span>
                <a href="javascript:void(0)">未添加</a>
            </div>
        </li>
    </ul>
</div>

<!-- 安全设置未启用 ]] -->
<div class="txt">
    <span>账户安全设置</span>
</div>
<div class="secure" id="pnlSecure">
    <div class="desc clearfix">
        <div class="label">登录密码</div>
        <div class="opt">
            <a href="#">修改</a>
        </div>
    </div>
    <div class="desc clearfix">
        <div class="label">支付密码</div>
        <div class="txtEnable">
            <div class="info">
                <span class="info-state">已启用</span>
                <span class="t_content">账户资金支出或资料变更时，需先验证支付密码</span>
            </div>
            <div class="opt">
                <a href="javascript:void(0)" id="aModPaypwd">修改</a>
                <span class="vertical-split"></span>
                <a href="javascript:void(0)" id="aFindPaypwd">找回</a>
            </div>
        </div>
        <div class="txtDisable">
            <div class="info">
                <span class="info-state">未启用</span>
                <span>账户资金支出或资料变更时，需先验证支付密码</span>
            </div>
            <div class="opt">
                <a href="javascript:void(0)" id="aSetPaypwd" onclick="dialogPayWindow()">设置</a>
            </div>
        </div>
    </div>
</div>

<div id="dialog_setPaymentCode" title="设置支付密码">
    <input name="password" type="password" id="password" placeholder="账户登陆密码"/><span id="msg"></span>
    <input name="paymentCode" type="password" id="paymentCode" placeholder="新支付密码"/><span id="msg1"></span>
</div>
<div id="progressbar"></div>
</body>
</html>

<script>
    var progressbar;
    var setPaymentCode_dialog;
    jQuery(function () {
        $('.desc.clearfix:last-child').css('display','none');
        progressbar = $("#progressbar");
        jQuery.ajax({
            type: "POST",
            url: "getUserInfo.do",
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    if (result.realname == "") {
                        jQuery('.info-name').html('没填写');
                        jQuery('.js_name_auth_pop').css('display', 'none');
                    } else jQuery('.info-name').html(result.realname);
                    jQuery('.email').html(result.email);
                    var localPhone = user.phone;
                    jQuery('.infoNickName').css('display', 'block');
                    jQuery('.nickName').html(result.nickname);
                    jQuery('.infoPhone').css('display', 'block');
                    if (result.paymentCode.toString() != "") {
                        jQuery('.txtEnable').css('display', 'block');
                    } else {
                        jQuery('.txtDisable').css('display', 'block');
                    }
                }

            },
            data: {"phone": "" + phone + ""}
        });
    });


    function dialogPayWindow() {
        jQuery('#paymentCode').val('');
        setPaymentCode_dialog = jQuery('#dialog_setPaymentCode');
        setPaymentCode_dialog.dialog({
            modal: true,
            buttons: {
                '确定': function () {
                    setPayPwd();
                }
            }
        });
    }

    function setPayPwd() {
        jQuery('#msg').html('');
        jQuery('#msg1').html('');
        var paymentCode = jQuery('#paymentCode').val();
        var password = jQuery('#password').val();
        if (password.length == 0) {
            jQuery('#msg').css('display', 'block');
            jQuery('#msg').html('请输入登陆密码');
            return;
        }
        if (paymentCode.length == 0) {
            jQuery('#msg1').css('display', 'block');
            jQuery('#msg1').html('请设置支付密码');
            return;
        }
        setPaymentCode_dialog.dialog("close");
        progressbar.progressbar({
            value: false
        });
        jQuery.ajax({
            type: "post",
            dataType: "json",
            url: "changePayment_code.do",
            data: {
                "phone": "" + phone + "",
                "password": "" + encode64(password) + "",
                "payment_code": "" + encode64(paymentCode) + "",
                "device": "h5"
            },
            success: function (result) {
                jQuery(progressbar).css('display', 'none');
                jQuery('#msg').css('display', 'none');
                jQuery('#msg1').css('display', 'none');
                if (result.success) {
                    toast('设置成功');
                } else {
                    toast('密码不正确');
                }
            },
            error: function (errorMsg) {
                console.log("error");
                jQuery(progressbar).css('display', 'none');
                jQuery('#msg').css('display', 'none');
            }
        });
    }
</script>