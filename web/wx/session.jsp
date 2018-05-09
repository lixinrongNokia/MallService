<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>爱内秀-聊天窗口</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="https://g.alicdn.com/aliww/??h5.imsdk/2.1.5/scripts/yw/wsdk.js,h5.openim.kit/0.4.0/scripts/kit.js"
            charset="utf-8"></script>
    <style>
        #toNick {
            size: 6px;
        }
    </style>
</head>
<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="history.go(-1)">返回</div>
            <!--<div class="headerRight" ontouchstart="zy_touch('backDown')" onClick="delorderall()">清空</div>-->
            <span id="toNick"></span>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer"></div>
    <!--footer结束 -->
</div>
</body>
</html>
<script>
    var user = $.parseJSON(localStorage.getItem("appUser"));
    var touid = localStorage.getItem('touid');
    var toAvatar = localStorage.getItem("toAvatar");
    var toNick = localStorage.getItem("toNick");
    WKIT.init({
        uid: user.phone,// 需要登录的用户id
        appkey: '23403396',//appkey
        credential: user.password,//需要登录的用户密码
        touid: touid,// 需要聊天的用户id
        avatar: 'http://iliker.cn/share/' + user.headImg,//登录用户的头像
        toAvatar: toAvatar,
        sendBtn: true,     //是否显示发送按钮 默认值: false
        titleBar: false,
        container: document.getElementById('content'),
        hideLoginSuccess: true,
        onAudioReceived: function (content) {
            return '<audio src="' + content + '"  controls="controls"></audio>';
        },
        onLoginSuccess: function () {
            //登录成功后的回调
            console.log('登陆成功');

        },
        onLoginError: function () {
            //登录失败后的回调
            console.log('登陆失败');
        }
    });
    jQuery('#toNick').html(toNick);
</script>
