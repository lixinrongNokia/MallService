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
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <style type="text/css">
        .pay-pw .mt {
            padding-left: 15px;
            color: #5a5a5a;
        }

        .pay-pw .mc {
            padding: 0 10px;
        }

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

        .pay-pw .com-ul .wth98 {
            width: 96%;
        }

        .prompt span {
            display: block;
            padding: 4px 0;
            line-height: 16px;
        }

        .type-line {
            line-height: 25px;
            margin: 10px 0;
            word-wrap: break-word;
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

        .header-btn {
            position: relative;
        }

        .fr {
            float: right;
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

        .w {
            margin: auto;
            padding: 0 6px;
        }

        div, span, a, header {
            -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
        }

    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
</head>
<body>
<a name="top"></a>
<header>
    <div class="header w">
        <div class="header-btn fr">
            <a href="javascript:" onclick="openwin('user')"><img src="images/home.png"
                                                                 style="margin:2px 0;padding-top:2px;"></a>
            &nbsp;&nbsp;
        </div>
    </div>
</header>
<div class="blank"></div>
<div class="mt">修改原始信息</div>
<div class="mc">
    <div class="set-pw">
        <div class="step-pw">
            <!--[D]当前步骤时各自在fir,sec,third 类旁加class on -->
            <ul>
                <li>
                    <span class="fir">1</span><br>
                </li>
                <li><span class="bar"></span></li>
                <li><span class="sec on">2</span></li>
            </ul>
        </div>
        <!--[D]当前步骤时在加class curr -->
        <ul class="lst-sec">
            <li><span>验证身份</span></li>
            <li><span></span></li>
            <li><span class="curr">编辑</span></li>
        </ul>
    </div>
    <input name="verifyType" value="mobile" id="verifyType" type="hidden">
    <form id="frm_resetPassword"
          action="#" method="post">
        <ul class="com-ul">
            <li class="last">
                <input id="uid" name="uid" type="hidden">
                <p class="type-line">
                    <label>重置登录密码：</label>
                </p>
                <p>
                    <input name="password" id="password" class="common-input wth98" placeholder="请输入密码"
                           tip="密码由6-20位字符组成，包含至少两种以上的字母、数字或者半角字符，区分大小写。" notblank="notBlank" valuemissingtxt="密码不能为空"
                           errorlabel="pwdError" type="password">
                </p>
                <span id="strength_tip" style="display:none;font-size:.95em;color:#67738a">密码等级:<span
                        id="strength"></span></span>
                <p></p>
                <p id="pwdError" class="paypw-err"></p>
                <p class="type-line">
                    <label>重新输入一遍：</label>
                </p>
                <p>
                    <input name="checkPassword" id="checkPassword" class="common-input wth98" placeholder="请再次输入密码"
                           notblank="notBlank" valuemissingtxt="密码不能为空" errorlabel="confirmError" matchfor="password"
                           matchfortxt="两次输入密码不一致" type="password">
                </p>
                <p></p>

                <p class="type-line">
                    <label>修改昵称：</label>
                </p>
                <p>
                    <input name="nickName" id="nickName" class="common-input wth98">
                </p>
                <p></p>
                <p id="confirmError" class="paypw-err"></p>
                <!--<p><a  id="btnResetPayPassword"   href="#" class="sub_btn">提交</a></p>"^(![\u4e00-\u9fa5])^(?!\d*$)^(?![a-zA-Z]*$).{6,20}$"-->
                <button type="button" class="sub_btn" id="btnResetPassword">提交</button>
            </li>
        </ul>
    </form>
</div>
</body>
<script>
</script>
</html>
<script>
    $('#nickName').val(localStorage.regActivate_nickName);
    function regActivateInput() {
        var checkPassword = $('#checkPassword').val();
        var password = $('#password').val();
        var nickName = $('#nickName').val();
        if ($.trim(password).length < 6) {
            toast('密码不能为空');
            return false;
        }
        if ($.trim(checkPassword).length < 6) {
            toast('确认密码不能为空');
            return false;
        }
        if (checkPassword !== password) {
            toast('两密码必须一致');
            return false;
        }
        if ($.trim(nickName).length === 0) {
            toast('昵称不能为空');
            return false;
        }
        $('#password').val(encode64(password))
        return true;
    }

    $('#btnResetPassword').click(function () {
        if (regActivateInput()) {
            var password = $('#password').val();
            var nickName = $('#nickName').val();
            $.ajax({
                type: "POST",
                url: "activateUser.do",
                dataType: "json",
                data: {"password": password, "nickName": nickName, "uid": localStorage.regActivate_uid},
                success: function (result) {
                    if (result.success) {
                        toast('账户激活成功，请登录');
                        setTimeout(function () {
                            openwin('user');
                        }, 500);
                    } else {
                        toast(result.msg);
                    }
                }
            });
        }
    })
</script>