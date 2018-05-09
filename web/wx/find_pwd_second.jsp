<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>爱内秀-找回密码</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <style type="text/css">
        .page {
            width: 100%;
            background: #f3f5f7;
            padding: 0 .2rem .2rem
        }

        .input-container {
            width: 100%;
        }

        .account_link {
            text-align: right;
            margin-right: 1rem;
        }

        a {
            color: blue;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <h1>找回密码</h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content">
        <section class="page">
            <div class="wrap findpwdPage">
                <p class="page-notice">请输入<span id="mobile"></span>收到的短信验证码</p>
                <p class="page-txt">用户名：<span id="nickName"></span></p>
                <div class="input-box">
                    <div class="input-container">
                        <input id="mesCode" class="acc-input small-input J_ping" type="text" placeholder="请输入验证码"
                               autocomplete="off">
                        <i class="icon icon-clear"></i>
                    </div>
                    <input id="getMesCode" type="button" value="获取验证码"/>
                </div>
                <div class="account_link"><a href="javascript:" id="sureBtn" class="btn">下一步</a></div>
            </div>
        </section>
    </div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer"></div>
    <!--footer结束 -->
</div>
</body>
</html>
<script defer="defer">
    var nickName = sessionStorage.getItem("nickName");
    var phone = sessionStorage.getItem("phone");
    $(function () {
        $('#nickName').text(nickName);
        var reg = /(\d{3})\d{4}(\d{4})/;
        var tel = phone.replace(reg, "$1****$2");
        $("#mobile").text(tel);
        $('#getMesCode').bind("click", getSMSCode);
        $('#sureBtn').click(function () {
            var mesCode = $('#mesCode').val();
            var code = sessionStorage.SMSCode;
            var time = sessionStorage.outTime;
            if (mesCode != code) {
                toast('验证码不正确');
                return;
            }
            var nowTime = $.now();
            if ((nowTime - time) / 1000 >= 120) {
                toast('验证码已超时');
                return;
            }
            openwin('find_pwd_done');
        });
    });


    var time = 120;
    var t;
    var getSMSCode = function () {
        $.ajax({
            url: "findPwdForSecond.do",
            dataType: 'JSON',
            data: {
                "phoneNum": phone
            },
            success: function (result) {
                if (result.success) {
                    sessionStorage.setItem('SMSCode', result.code);
                    sessionStorage.setItem('outTime', result.time);
                    $("#getMesCode").unbind("click", getSMSCode);
                    t = setInterval(method, 1000);
                }
                toast(result.msg);
            }
        });

        var method = function () {
            time--;
            $("#getMesCode").val("" + time + "s");
            if (time === 0) {
                clearInterval(t);
                time = 120;
                jQuery("#getMesCode").val("获取验证码");
                jQuery("#getMesCode").bind("click", getSMSCode);
            }
        }
    };
</script>