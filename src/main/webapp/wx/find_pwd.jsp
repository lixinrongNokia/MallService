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
            <div>
                <div class="input-container">
                    <label class="lable-name" for="username">账号</label>
                    <input id="username" type="text" placeholder="手机号" value=""
                           autocomplete="off">
                    <i class="icon icon-clear"></i>
                </div>
                <div class="input-container">
                    <input id="imgVerify" type="text" placeholder="请输入验证码"
                           maxlength="6">
                    <i class="icon icon-clear"></i>
                    <span class="img-code"><img id="imgCode" src="createimgcode_createcode.do"
                                                alt=""></span>
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
    var phone;
    var imgVerify;
    $(function () {
        $('#imgCode').click(function () {
            $(this).attr('src', 'createimgcode_createcode.do?time=' + new Date())
        });

        $('#sureBtn').click(function () {
            if (validate()) {
                $.ajax({
                    url: "findPwdByPhone.do",
                    data: {"phone": "" + phone + "", "nonce": "" + imgVerify + ""},
                    dataType: "json",
                    type: "POST",
                    success: function (result) {
                        if (result.success) {
                            sessionStorage.setItem("nickName", result.nickName);
                            sessionStorage.setItem("phone", result.phone);
                            openwin('find_pwd_second');
                        } else {
                            toast(result.msg);
                        }
                    }
                });
            }
        });
    });

    function validate() {
        phone = $('#username').val();
        imgVerify = $('#imgVerify').val();
        if (!checkPhone(phone)) {
            toast('手机号不正确');
            return false;
        }
        if (imgVerify.length === 0) {
            toast('验证码不正确');
            return false;
        }
        return true;
    }

</script>