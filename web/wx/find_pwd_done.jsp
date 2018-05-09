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
            text-align: center;
            margin-right: 1rem;
        }

        .acc-input {
            width: 100%;
            height: 100%;
            box-sizing: border-box;
        }

        a {
            color: blue;
            text-decoration: none;
        }

        .set-notice {
            padding: .05rem 0 .08rem;
            color: #848689;
            font-size: .14rem;
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
                <p class="set-text">请设置新的登录密码</p>
                <div class="input-container set-input">
                    <input id="passwd" class="acc-input password J_ping" type="password" placeholder="6-20位新密码"
                           minlength="6" maxlength="20" autocomplete="off" onkeyup="CtoH(this);">
                    <i class="icon icon-clear" style="display: none;"></i>
                </div>
                <p class="set-notice">备注：请将密码设置为6-20位，并且由字母，数字和符号两种以上组合</p>
                <div class="account_link"><a href="javascript:" id="sureBtn" class="btn J_ping">完成</a></div>
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
    var phone = sessionStorage.getItem("phone");
    $(function () {
        $('#sureBtn').click(function () {
            var passwd = $('#passwd').val();
            if (passwd.length < 6) {
                toast('密码不能为空且6位以上');
                return;
            }
            $.ajax({
                url: "findPwdByUpdate.do",
                data: {
                    "phone": phone, "password": encode64(passwd), "device": "h5"
                },
                type: "POST",
                dataType: 'json',
                success: function (result) {
                    if (result.success) {
                        toast(result.msg);
                        setTimeout(back, 3000);
                    } else {
                        toast(result.msg);
                    }
                }
            });
        });
    });
    function back() {
        openwin(localStorage.callAddress);
    }
</script>