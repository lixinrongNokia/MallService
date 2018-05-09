<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>补全平台账户</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        .account_section {
            background: #fff;
            position: relative;
            margin: -1px 0 15px;
            overflow: hidden
        }

        .account_section:before {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ccc;
            height: 1px;
            left: 0;
            right: 0;
            top: 0
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_section:before {
                -webkit-transform: scaleY(.5);
                -webkit-transform-origin: 50% 0
            }
        }

        .account_section:after {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ccc;
            height: 1px;
            left: 0;
            right: 0;
            bottom: 0
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_section:after {
                -webkit-transform: scaleY(.5);
                -webkit-transform-origin: 50% 100%
            }
        }

        .account_section .account_item:nth-child(3):before {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ddd;
            height: 1px;
            left: 0;
            right: 0;
            top: 0;
            left: 10px;
            right: 10px
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_section .account_item:nth-child(3):before {
                -webkit-transform: scaleY(.5);
                -webkit-transform-origin: 50% 0
            }
        }

        .account_title {
            font-size: 14px;
            font-weight: 400;
            margin: 0 10px;
            padding: 12px 0;
            position: relative
        }

        .account_title:after {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ddd;
            height: 1px;
            left: 0;
            right: 0;
            bottom: 0
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_title:after {
                -webkit-transform: scaleY(.5);
                -webkit-transform-origin: 50% 100%
            }
        }

        .account_title a {
            color: #3985ff;
            float: right;
            font-size: 12px
        }

        p.account_title {
            font-size: 12px
        }

        .account_item {
            padding: 15px 10px 15px 70px;
            position: relative;
            min-height: 50px
        }

        .account_item img {
            position: absolute;
            left: 10px;
            top: 15px;
            display: block;
            width: 50px;
            height: 50px;
            border-radius: 50px
        }

        .account_item {
            list-style-type: none;
            color: #999;
            line-height: 18px
        }

        .account_item strong {
            color: #333;
            font-weight: 400
        }

        .account_item .links a {
            color: #3985ff;
            margin-right: 10px
        }

        .account_btn a {
            position: relative;
            float: left;
            display: block;
            width: 50%;
            height: 30px;
            line-height: 30px;
            text-align: center;
            border-radius: 2px;
            background: #f3f3f3;
            color: #333
        }

        .account_btn a:active {
            background: #e6e6e6
        }

        .account_btn a:after {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ddd;
            border: 1px solid #ddd;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background: none;
            border-color: #ddd;
            border-radius: 2px
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_btn a:after {
                right: -100%;
                bottom: -100%;
                -webkit-transform: scale(.5);
                -webkit-transform-origin: 0 0;
                border-radius: 4px
            }
        }

        .account_btn a:nth-child(2) {
            float: right;
            margin-right: -10px
        }

        .account_form {
            position: relative;
            padding: 0 10px
        }

        .account_form .account_hr span {
            background-color: #fff
        }

        .account_form .mod_btns {
            margin: 0
        }

        .account_form .link_registered a {
            color: #3985ff
        }

        .account_form .link_registered a:after {
            content: "";
            display: block;
            width: 4px;
            height: 4px;
            border-top: 1px solid #3985ff;
            border-left: 1px solid #3985ff;
            -webkit-transform-origin: 50%;
            transform-origin: 50%;
            -webkit-transform: rotate(135deg);
            transform: rotate(135deg);
            display: inline-block;
            vertical-align: middle;
            margin: -2px 0 0 2px
        }

        .account_form p {
            margin: 10px 0
        }

        .account_form p em {
            color: #999
        }

        .account_form .label {
            margin: 15px 0;
            font-size: 14px;
            color: #333;
            line-height: 1
        }

        .account_form .text {
            margin: -5px 0 10px;
            font-size: 12px;
            color: #999
        }

        .account_form .text.error {
            color: #e4393c
        }

        .account_form .input {
            position: relative;
            padding: 0 45px 0 10px;
            background-color: #fff
        }

        .account_form .input input {
            display: block;
            width: 100%;
            height: 45px;
            line-height: 1;
            font-size: 14px
        }

        .account_form .input input:focus + .delete {
            display: block
        }

        .account_form .input input::-webkit-input-placeholder {
            color: #ccc
        }

        .account_form .input.input_code .img img {
            display: block;
            width: 100%;
            height: 100%;
            border-radius: 2px
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_form .input.input_code .img:after {
                right: -100%;
                bottom: -100%;
                -webkit-transform: scale(.5);
                -webkit-transform-origin: 0 0;
                border-radius: 4px
            }
        }

        .account_form .input.error {
            background-color: #fcebeb
        }

        .account_form .input.error:before {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #e4393c;
            border: 1px solid #ddd;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background: none;
            border-color: #e4393c;
            z-index: 2
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_form .input.error:before {
                right: -100%;
                bottom: -100%;
                -webkit-transform: scale(.5);
                -webkit-transform-origin: 0 0
            }
        }

        .account_form .input.error input {
            color: #e4393c
        }

        .account_form div.input {
            margin: 15px 0
        }

        .account_form div.input:after {
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ddd;
            border: 1px solid #ddd;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            background: none;
            border-color: #ddd
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_form div.input:after {
                right: -100%;
                bottom: -100%;
                -webkit-transform: scale(.5);
                -webkit-transform-origin: 0 0
            }
        }

        .account_form li.input:after {
            left: 10px;
            right: 10px;
            content: "";
            position: absolute;
            z-index: 1;
            pointer-events: none;
            background-color: #ddd;
            height: 1px;
            left: 0;
            right: 0;
            top: 0
        }

        @media only screen and (-webkit-min-device-pixel-ratio: 2) {
            .account_form li.input:after {
                -webkit-transform: scaleY(.5);
                -webkit-transform-origin: 50% 0
            }
        }

        .account_form li.input:first-child:after {
            display: none
        }

        .account_checkbox a {
            color: #3985ff
        }

        .account_hr span {
            position: relative;
            z-index: 1;
            background: #e8e8ed;
            font-size: 14px;
            color: #999;
            padding: 0 10px
        }

        .account_ol li {
            margin: 0 0 5px 15px;
            color: #666;
        }

        .account_id li {
            position: relative;
            float: left;
            width: 40%;
            text-align: center;
            font-size: 10px;
            color: #999;
            min-height: 1px
        }

        .account_id img {
            display: block;
            width: 50px;
            height: 50px;
            margin: 0 auto 10px;
            border-radius: 50px
        }

        .account_id strong {
            display: block;
            font-weight: 400;
            color: #333
        }

        .account_id .arrow i {
            left: 50%;
            top: 25px;
            width: 50px;
            margin-left: -25px
        }

        .account_id .arrow i, .account_id .arrow i:after {
            position: absolute;
            display: block;
            height: 2px;
            background: #ccc
        }

        .account_id .arrow i:after {
            content: "";
            width: 12px;
            right: 0;
            bottom: 0;
            -webkit-transform: rotate(40deg);
            transform: rotate(40deg);
            -webkit-transform-origin: 100% 100%;
            transform-origin: 100% 100%
        }

        .account_list li {
            color: #999;
            line-height: 1.8em
        }

        .account_list li strong {
            font-weight: 400;
            color: #333
        }

        .account_dl dt {
            margin: 0 0 -20px;
            padding: 15px 10px;
            font-weight: 700;
            color: #333
        }

        .account_toggle .account_title:before {
            content: "";
            display: block;
            width: 10px;
            height: 10px;
            border-top: 1px solid #666;
            border-left: 1px solid #666;
            -webkit-transform-origin: 50%;
            transform-origin: 50%;
            -webkit-transform: rotate(-135deg);
            transform: rotate(-135deg);
            position: absolute;
            right: 10px;
            top: 50%;
            margin-top: -8px
        }

        .account_toggle.open .account_title:before {
            -webkit-transform: rotate(45deg);
            transform: rotate(45deg);
            margin-top: -4px
        }

        /*补全*/
        .account_title {
            font-size: 14px;
            font-weight: 400;
            margin: 0 10px;
            padding: 12px 0;
            position: relative;
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

        .account_item img {
            position: absolute;
            left: 10px;
            top: 15px;
            display: block;
            width: 50px;
            height: 50px;
            border-radius: 50px;
        }

        img {
            border: 0 none;
            vertical-align: top;
        }

        * {
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
            outline: 0;
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

        /*下一步*/
        .mod_btns .mod_btn.bg_2, .mod_btns .mod_btn.mod_btn_bg1 {
            background: #3884ff;
            color: #fff;
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
<section class="account_section">
    <div id="curPinPanel">
        <h3 class="account_title">当前登录账号</h3>
        <div class="account_item">
            <img src="#" id="headImg">
            微信账号：<strong id="name"></strong>
        </div>
    </div>
    <div class="account_form">
        <p class="label" id="headTips">请填写手机号码，以补全爱内秀账号信息：</p>
        <div class="input">
            <input type="tel" placeholder="请输入手机号" id="tel">
        </div>
        <p class="text error input_tip" style="display: none"></p>
        <p class="text">温馨提示：</p>
        <p class="text" id="tips">若您输入的手机号码已被注册，需输入相应的登录密码补全账号信息。</p>
        <p class="text">暂不支持非大陆手机号码注册或关联。</p>
        <div class="mod_btns"><a href="javascript:void(0);" class="mod_btn bg_2" id="next" manualclick="yes"
                                 ptag="7409.1.1" onclick="regbindPhone()">下一步</a></div>
    </div>
</section>
<div class="footer">
    <div class="logo"></div>
    <div class="info">页面内容由爱内秀提供。</div>
</div>
</body>
</html>
<script>
    var wxuser = $.parseJSON(localStorage.getItem('wxuser'));
    $(function () {
        $('#headImg').attr('src', wxuser.headImg);
        $('#name').html(wxuser.nickName);
    });
    //checkPhone
    function regbindPhone() {
        var phone = $('#tel').val();
        if (!checkPhone(phone)) {
            $('#tel').focus();
            $('.input').addClass('redBack')
            return;
        }
        $.ajax({
            type: "post",
            url: "bindWXCheck.do",
            dataType: "json",
            data: {"phone": "" + phone + ""},
            success: function (result) {
                if (result.success) {
                    sessionStorage.setItem('bindUid', result.uid);
                    openwin('exister_bind');
                } else {
                    sessionStorage.setItem('bindPhone', result.phone);
                    openwin('noexister_bind');
                }
            },
            error: function () {
                toast('操作无效');
            }
        });
    }
</script>
