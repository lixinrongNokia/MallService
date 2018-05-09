<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>爱内秀-用户</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <style type="text/css">
        .com-ul {
            background: none repeat scroll 0 0 #FFFDF7;
            border: 1px solid #CDC2B0;
            border-radius: 5px;
            font-size: 1em;
            margin: 15px 0 0;
        }

        .com-ul li {
            border-bottom: 1px solid #DED6C9;
            border-top: medium none;
            overflow: hidden;
            padding: 10px;
            position: relative;
        }

        .com-ul li.last {
            border-bottom: none;
            padding: 15px 10px;
        }

        .set-pw {
            padding-top: 15px;
        }

        .set-pw ul {
            display: table;
            height: 30px;
            width: 100%;
        }

        .set-pw ul li {
            display: table-cell;
        }

        .set-pw ul li:nth-child(2n-1) {
            width: 26px;
        }

        .set-pw ul li .fir, .set-pw ul li .sec, .set-pw ul li .third {
            display: inline-block;
            width: 26px;
            height: 26px;
            border: 1px solid #c6c2bd;
            background-color: #c6c2bd;
            border-radius: 15px;
            text-align: center;
            line-height: 25px;
            box-shadow: 0 1px 0 #fff;
            box-shadow: inset 0 1px 1px #a8a5a1;
            color: #fff;
        }

        .set-pw ul li .on {
            background: -webkit-gradient(linear, left top, left bottom, from(#f7f3ec), to(#ddc9a8));
            background: -moz-linear-gradient(top, #f7f3ec, #ddc9a8);
            box-shadow: inset 0 1px 0 #fff;
            color: #222;
            text-shadow: 1px 1px 1px #fff;
        }

        .set-pw ul li .bar {
            position: relative;
            display: block;
            top: -2px;
            height: 6px;
            background-color: #c6c2bd;
            box-shadow: 0 1px 0 #fff;
            border-top: 1px solid #a19d99;
            box-shadow: inset 0 1px 0 #bab6b1;
        }

        .set-pw .lst-sec li {
            color: #a7a39e;
        }

        .set-pw .lst-sec li:nth-child(2n-1) {
            width: 96px;
            text-align: center;
        }

        .set-pw .lst-sec li .curr {
            color: #000;
        }

        .set-pw .step-pw {
            padding: 0 30px;
        }

        .pay-pw .com-ul .common-input {
            width: 95%;
            padding: 6px;
            vertical-align: top;
        }

        .btn-type {
            display: inline-block;
            background: -webkit-gradient(linear, left top, left bottom, from(#fef6e9), to(#f9d8a2));
            background: -moz-linear-gradient(top, #fef6e9, #f9d8a2);
            color: #3c3c3c;
            border: 1px solid #BAAC9D;
            font-weight: normal;
            text-shadow: 1px 0 0 #fff;
            border: none;
            padding: 0 10px;
            height: 30px;
            margin-left: 10px;
            line-height: 30px;
            font-size: 14px;
            cursor: pointer;
            text-align: center;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            text-shadow: 1px 0 0 #fff;
            border-bottom: 1px solid #bcb0a3;
            box-shadow: inset 1px 1px #fff, inset 1px -1px 1px #fbe7c4;
            text-shadow: 1px 1px #fff;
        }

        .tbn-type-mglf0 {
            margin-left: 0;
        }

        .prompt span {
            display: block;
            padding: 4px 0;
            line-height: 16px;
        }

        .pay-pw .common-input {
            width: 96%;
            padding: 6px;
            vertical-align: top;
        }

        .paypw-err {
            padding-top: 5px;
            color: #db2929;
            font-size: .95em;
            line-height: 17px;
        }

        .pop-txt a {
            color: #e5d22e;
            text-decoration: underline;
        }

        .paypw-codesdtip {
            font-size: .95em;
        }

        .sub_btn {
            display: block;
            background: -webkit-gradient(linear, left top, left bottom, from(#ed3131), to(#990a0a));
            background: -moz-linear-gradient(top, #ed3131, #990a0a);
            background-color: rgba(0, 0, 0, 0);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed3131', endColorstr='#990a0a');
            background-color: red;
            border: none;
            border-bottom-width: medium;
            border-bottom-style: none;
            border-bottom-color: currentcolor;
            width: 90%;
            margin: 10px auto 0;
            height: 40px;
            line-height: 40px;
            color: #fff;
            font-size: 1em;
            font-weight: bold;
            cursor: pointer;
            text-align: center;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            text-shadow: 1px 0 0 #000;
            border-bottom: 1px solid #bcb0a3;
        }

        .pay-pw .com-ul .common-input {
            width: 95%;
            padding: 6px;
            vertical-align: top;
        }

        .pay-pw .common-input {
            width: 96%;
            padding: 6px;
            vertical-align: top;
        }

        .common-input {
            font-size: 1em;
            background-color: #F4F4F4;
            border: 1px solid #a4a4a4;
            padding-left: 8px;
            padding-top: 3px;
            height: 18px;
            line-height: 18px;
            -moz-box-shadow: 0 1px 3px #c8c8c8 inset;
            -webkit-box-shadow: 0 1px 3px #c8c8c8 inset;
            box-shadow: 0 1px 3px #c8c8c8 inset;
            vertical-align: text-top;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
        }

        body, input, textarea, select, button, table {
            font-size: 16px;
            line-height: 1.25em;
        }

    </style>
</head>

<body>
<div id="main" class="box-ver box">
    <div class="mc">
        <div class="set-pw">
            <div class="step-pw">
                <!--[D]当前步骤时各自在fir,sec,third 类旁加class on -->
                <ul>
                    <li>
                        <span class="fir on">1</span><br>
                    </li>
                    <li><span class="bar"></span></li>
                    <li><span class="sec">2</span></li>
                </ul>
            </div>
            <!--[D]当前步骤时在加class curr -->
            <ul class="lst-sec">
                <li><span class="curr">验证身份</span></li>
                <li><span></span></li>
                <li><span>编辑</span></li>
            </ul>
        </div>
        <ul class="com-ul">
            <li class="last">
                <p class="pd-h10">请输入手机号：</p>
                <p>
                    <%--<select style="width:97%;height:30px" id="selectValidateType">
                        <option selected="" value="mobile">已验证手机</option>
                    </select>--%>
                    <input class="common-input" name="mobile" id="mobile">
                </p>
                <!--<p class="pd-h10">用户昵称：高石花道</p> -->
                <%--<p class="pd-h10">已验证手机:<span id="mobileno"></span></p>
                <input name="mobile" id="mobile" value="xxx" type="hidden">
                <input name="type" id="type" value="2" type="hidden">--%>
                <p></p>
                <p class="paypw-codesdtip" id="notify" style="display:none">
                    短信已经发送到您的手机，如在<strong><span style="color:red" id="second"></span></strong>秒之内还没有 收到短信验证码，请重新获取验证码
                </p>
                <p class="paypw-codesdtip" id="notify_e" style="display:none;color:red">
                    短信验证码发送失败，请在<strong><span style="color:black" id="second_e"></span></strong>秒之后重新获取短信验证码
                </p>
                <p id="mobileError" class="paypw-err"></p>
                <p>
                    <input value="获取短信校验码" id="btnCodesend" class="btn-type tbn-type-mglf0" type="button">
                </p>
                <form id="frm_validateCode"
                      action=""
                      method="post">
                    <p class="pd-h10">请填写短信校验码：</p>
                    <p><input class="common-input" name="code" id="smscode" placeholder="请输入验证码" errorlabel="codeError"
                              notblank="notBlank" valuemissingtxt="请输入验证码" type="text"></p>
                    <p id="codeError" class="paypw-err"></p>
                    <p id="errorTip" class="paypw-err"></p>
                    <input name="fp" value="" id="fp" type="hidden">
                    <input name="eid" value="" id="eid" type="hidden">
                    <input name="verifyType" value="mobile" id="verifyType" type="hidden">
                    <input id="btnValidateCode" class="sub_btn" value="下一步" type="button">
                </form>
            </li>
        </ul>
    </div>
</div>
</body>
<script defer="defer">
    var sid;
    var t;
    var s = 120;
    var phone;
    var code;
    var outtime;
    var backPhone;
    $(function () {
        $('#second').text(s);
        sid = GetQueryString('sid');
        /*if (sid) {
         $.getJSON("decodeSID2Phone.do", {"phone": sid}, function (result) {
         phone = result.mobileno;
         var reg = /(\d{3})\d{4}(\d{4})/;
         var tel = phone.replace(reg, "$1****$2");
         $('#mobileno').text(tel);
         })
         }*/
        $('#btnValidateCode').click(function () {
            if (typeof(code) === 'undefined') {
                toast('获取短信校验码');
                return;
            }
            var smscode = $('#smscode').val();
            var len = $.trim(smscode).length;
            if (len >= 4) {
                var registerBean = {};
                registerBean.smscode = smscode;
                registerBean.backPhone = backPhone;
                registerBean.phoneNum = $('#mobile').val();
                registerBean.code = code;
                registerBean.time = outtime;
                $.ajax({
                    type: "POST",
                    url: "regActivate.do",
                    dataType: 'JSON',
                    data: {
                        "registerBean": "" + JSON.stringify(registerBean) + ""
                    },
                    success: function (result) {
                        if (result.success) {
                            localStorage.setItem('regActivate_nickName', result.nickName);
                            localStorage.setItem('regActivate_uid', result.uid);
                            openwin('modify_userinfo2');
                        } else {
                            toast(result.msg);
                        }
                    }
                });
            } else {
                toast('请填写验证码');
            }
        });
        $('#btnCodesend').click(function () {
            phone = $('#mobile').val();
            if (!checkPhone(phone)) {
                toast('请正确输入手机号码');
                return;
            }
            $.ajax({
                url: "regphone.do",
                dataType: 'JSON',
                data: {
                    "phoneNum": phone
                },
                success: function (result) {
                    if (result.success) {
                        $('#notify').css('display', 'block');
                        $('#btnCodesend').attr("disabled", "disabled");
                        $('#mobile').attr('readonly', 'readonly');
                        code = result.code;
                        outtime = result.time;
                        backPhone = result.backPhone;
                        t = setInterval(changeSecond, 1000);
                    } else {
                        toast(result.msg);
                    }
                }
            });
        });

        function changeSecond() {
            s--;
            $('#second').text(s);
            if (s == 0) {
                s = 120;
                $('#second').text(s);
                $('#notify').css('display', 'none');
                $('#btnCodesend').removeAttr('disabled');
                $('#mobile').removeAttr('readonly');
                clearInterval(t);
            }
        }
    });
</script>
</html>