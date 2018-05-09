<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>爱内秀-绑定</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <style type="text/css">
        * {
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
            outline: 0;
        }

        body {
            color: #333;
            font-size: 12px;
        }

        body {
            font-size: 14px;
            font-family: -apple-system, Helvetica, sans-serif;
            line-height: 1.5;
            color: #666;
            -webkit-text-size-adjust: 100% !important;
        }

        .account_section {
            background: #fff;
            position: relative;
            margin: -1px 0 15px;
            overflow: hidden;
        }

        .account_remind {
            padding: 15px 10px;
            text-align: center;
        }

        .account_remind .account_remind_icon {
            display: block;
            width: 50px;
            height: 50px;
            margin: 0 auto 10px;
            background-repeat: no-repeat;
            background-size: 50px auto;
        }

        .account_remind .account_remind_icon.type_warning {
            background-image: url('images/exister.png');
        }

        em, i {
            font-style: normal;
        }

        .account_form {
            position: relative;
            padding: 0 10px;
            border: 1px;
        }

        .account_form .label {
            margin: 15px 0;
            font-size: 14px;
            color: #333;
            line-height: 1;
        }

        .account_form p {
            margin: 10px 0;
        }

        .account_form .input input {
            display: block;
            width: 100%;
            height: 45px;
            line-height: 1;
            font-size: 14px;
        }

        .account_form .input input {
            border: none;
            background: none;
            -webkit-appearance: none;
            appearance: none;
            border-radius: 0;
        }

        button, h1, h2, h3, h4, h5, h6, input, select {
            font-size: 100%;
            font-family: inherit;
        }

        blockquote, body, button, dd, dl, dt, fieldset, form, h1, h2, h3, h4, h5, h6, hr, input, legend, li, ol, p, pre, td, textarea, th, ul {
            margin: 0;
            padding: 0;
            vertical-align: baseline;
        }

        .account_form .input.input_code {
            padding-right: 155px;
        }

        .account_form div.input {
            margin: 15px 0;
        }

        ol, ul {
            list-style: none;
        }

        .account_form .input {
            position: relative;
            padding: 0 45px 0 10px;
            background-color: #fff;
        }

        .mod_btns .mod_btn {
            display: block;
            box-sizing: border-box;
            width: 100px;
            height: 44px;
            line-height: 42px;
            text-align: center;
            font-size: 14px;
            border-radius: 2px;
            position: relative;
        }

        .mod_btns .mod_btn ~ .mod_btn {
            margin-left: 10px;
        }

        .mod_btns .mod_btn, .mod_btns .mod_btn.bg_4 {
            border-color: #ddd;
            color: #333;
        }

        .mod_btns .mod_btn {
            background: #f3f3f3;
            color: #fff;
        }

        .mod_btns .mod_btn {
            width: 100%;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            flex: 1;
        }

        a, a:visited {
            text-decoration: none;
            color: #333;
        }

        .account_form .input.input_code .btn {
            background: #3985ff;
            font-size: 14px;
            text-align: center;
            line-height: 35px;
            border-radius: 2px;
            color: #fff;
        }

        .account_form .input.input_code .btn, .account_form .input.input_code .img {
            position: absolute;
            right: 5px;
            top: 5px;
            width: 105px;
            height: 35px;
        }

        a, a:visited {
            text-decoration: none;
            color: #333;
        }

        .mod_btns {
            overflow: hidden;
            margin: 15px 10px;
        }

        .mod_btns {
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
        }

        .mod_btns .mod_btn.bg_2, .mod_btns .mod_btn.mod_btn_bg1 {
            background: #3884ff;
            color: #fff;
        }

        .footer {
            margin: 45px 0 60px !important
        }

        .footer {
            margin-bottom: 40px
        }

        .footer .logo {
            height: 30px;
            width: 86px;
            margin: 15px auto 0;
            background: url('${pageContext.request.contextPath }/wx/img/logo.png');
            background-size: cover;
            -webkit-background-size: cover
        }

        .footer .info {
            font-size: 12px;
            text-align: center;
            color: #666
        }

        .redBack {
            border: 2px solid red;
        }
    </style>
</head>
<body>
<div class="account_section account_remind">
    <i class="account_remind_icon type_warning"></i>
    <p class="account_remind_text" id="registerTip">
        <sapn id="phone"></sapn>
        手机号已注册爱内秀账号
    </p>
</div>
<div class="account_section">
    <div class="account_form">
        <p class="label">请输入登录密码：</p>
        <div class="input input_password">
            <input type="password" placeholder="请输入登录密码" id="psw">
        </div>
        <p class="label">请输入验证码：</p>
        <div class="input input_code">
            <input type="text" placeholder="请输入图片验证码" id="validateCodeInput">
            <div class="img"><img id="validateCode" data-tag="validate" src="createimgcode_createcode.do">
            </div>
        </div>
        <div class="mod_btns">
            <a href="javascript:void(0);" class="mod_btn bg_2" id="sure">确认</a>
            <a href="javascript:void(0);" class="mod_btn" id="cancel">取消</a>
        </div>
        <ul class="account_links">
            <li>
                <a href="javascript:" onclick=localStorage.callAddress='exister_bind';openwin('find_pwd')>找回密码</a>
            </li>
        </ul>
    </div>
</div>
<div class="footer">
    <div class="logo"></div>
    <div class="info">页面内容由爱内秀提供。</div>
</div>
</body>
</html>
<script>
    var callAddress = localStorage.getItem('callAddress');
    var wxuser = $.parseJSON(localStorage.getItem('wxuser'));
    $(function () {
        $('#sure').click(function () {
            var pwd = $('#psw').val();
            var code = $('#validateCodeInput').val();
            $('.input.input_password').removeClass('redBack');
            $('.input.input_code').removeClass('redBack');
            if (pwd == '') {
                $('#psw').focus();
                $('.input.input_password').addClass('redBack');
                return;
            }

            if (code == '') {
                $('#validateCodeInput').focus();
                $('.input.input_code').addClass('redBack');
                return;
            }
            $.ajax({
                url: "existerUserBind.do",
                data: {
                    "password": "" + encode64(pwd) + "",
                    "nonce": "" + code + "",
                    "openid": "" + localStorage.openid + "",
                    "uid": sessionStorage.bindUid,
                    "unionID": wxuser.unionID ? "" + wxuser.unionID + "" : ""
                },
                type: "POST",
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        localStorage.setItem('wxuser', JSON.stringify(result.wxuser));
                        toast(result.msg);
                        setTimeout(function () {
                            openwin(callAddress);
                        }, 500);
                    } else {
                        toast(result.msg);
                    }
                },
                error: function () {
                    toast('姿势不正确');
                }
            });
        });

        $('#validateCode').click(function () {
            $(this).attr('src', 'createimgcode_createcode.do?time=' + new Date())
        });
    });
</script>