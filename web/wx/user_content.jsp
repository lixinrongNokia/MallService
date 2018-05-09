<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>爱内秀-我的账户</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/user.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style>
        .unmsgcount {
            width: 1rem;
            height: 1rem;
            background: #f00;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
            -webkit-border-radius: 2rem;
            font-size: 1rem;
            text-align: center;
            line-height: 1rem;
            overflow: hidden;
            color: #fff;
            position: absolute;
            right: 2.5rem;
            bottom: 1rem;
            z-index: 99
        }

        .yuE, .telc {
            display: none;
        }

        #onbind, #unmsg, #myOrder, #myCollection, #addressManager, #contacts, #customerservice, #invite_friends {
            display: none;
        }
    </style>
</head>

<body class="um-vp">
<div class="top">
    <img id="headImg" src="${pageContext.request.contextPath }/wx/images/avatar_def.png" onClick="openwin('userinfo');">用户昵称：
    <p>
        <strong>
				<span id="user_name">
				</span>
        </strong>
    </p>
    <div class="yuE telc" onclick="openwin('account')">
        <p>我的账户</p>
        <%--<p>账户余额</p>
        <p><strong><span id="user_money">0.00</span></strong>
        </p>--%>
    </div>
</div>
<ul class="list">
    <li id="onbind" onclick=localStorage.callAddress='user';openwin('bind_platform')>
        <img src="${pageContext.request.contextPath }/wx/images/pin.png">绑定手机号
    </li>
    <li id="myOrder" onclick="openwin('dingdan');">
        <img src="${pageContext.request.contextPath }/wx/css/res-apple/dingdan.png">我的订单
    </li>
    <li id="myCollection" onclick="openwin('shoucang');">
        <img src="${pageContext.request.contextPath }/wx/css/res-apple/shoucang.png">我的收藏
    </li>
    <li id="addressManager" onclick="openwin('addressList');">
        <img src="${pageContext.request.contextPath }/wx/css/res-apple/dizhi.png">地址管理
    </li>
    <li id="contacts" onclick="openwin('myFriend');">
        <img src="${pageContext.request.contextPath }/wx/images/friends.png"><span>人脉</span>
    </li>
    <li id="invite_friends" onclick="openwin('invite_friends');">
        <img src="${pageContext.request.contextPath }/wx/images/common_forward_normal.png"><span>邀请好友</span>
    </li>
    <li id="customerservice"
        onclick=localStorage.touid="alq85115612@163.com";localStorage.toNick="爱内秀";openwin("session")>
        <img src="${pageContext.request.contextPath }/wx/images/customerservice.png"><span>客服</span>
    </li>
    <li id="unmsg" onclick="openwin('sessionList');">
        <img src="${pageContext.request.contextPath }/wx/images/messages.png"><span>消息</span>
        <div class="unmsgcount" style="display:none"></div>
    </li>
</ul>
</body>
</html>
<script defer="defer">
    var sdk = null;
    var count = 0;
    var msgs = [];
    $(function () {
        var openid = localStorage.getItem('openid');
        var appUser = localStorage.getItem("appUser");
        var wxuser = localStorage.getItem('wxuser');
        //判断是否为微信环境
        if (openid) {
            //显示微信用户
            if (wxuser) {
                var user = $.parseJSON(wxuser);
                checkBind(user);
                initUserData(user);
            } else {
                getWXUserData(openid, function (wxObject) {
                    checkBind(wxObject);
                    initUserData(wxObject);
                });
            }
        } else {
            //显示iliker用户
            var user = $.parseJSON(appUser);
            if (user.headImg.length > 0) {
                user.headImg = '/share/' + user.headImg;
            }
            initUserData(user);
            showMenu();
            if (!is_weixin()) {
                $("#unmsg").show();
                $('#addressManager').show();
                $('#customerservice').show();
                $.ajax({
                    url: "https://g.alicdn.com/aliww/h5.imsdk/2.1.5/scripts/yw/wsdk.js",
                    dataType: "script",
                    success: function () {
                        initIM(user);
                    }
                });
            }
        }
    });

    function is_weixin() {
        var ua = navigator.userAgent.toLowerCase();
        return 'micromessenger' == ua.match(/MicroMessenger/i);
    }

    function checkBind(userInfo) {
        var onbind = userInfo.onbind;
        if (!userInfo.onbind) {
            $('#onbind').show();
        } else {
            showMenu();
        }
    }

    function showMenu() {
        $('#myOrder').show();
        $('#myCollection').show();
        $('#contacts').show();
        $('#invite_friends').show();
        $('.yuE.telc').show();
    }
    /*加载阿里openIM模块*/
    function initIM(userInfo) {
//        window.__WSDK__POSTMESSAGE__DEBUG__ = true;
        sdk = new WSDK();
        sdk.Base.login({
            uid: userInfo.phone,
            appkey: '23403396',
            credential: userInfo.password,
            timeout: 5000,
            success: function (data) {
                getUnreadMsgCount();
            },
            error: function (error) {
                console.log('login fail', error);
            }
        });
    }
    function getUnreadMsgCount() {
        sdk.Base.getUnreadMsgCount({
            success: function (data) {
                var list = data.data;
                var i = 0;
                list.forEach(function (item) {
                    var info = {};
                    info.touid = item.contact;
                    info.msgcount = item.msgCount;
                    msgs[i] = info;
                    count += item.msgCount;
                    i++;
                });
                if (msgs.length > 0) {
                    localStorage.setItem('unreadmsg', JSON.stringify(msgs));
                }
                if (count > 0) {
                    $('.unmsgcount').show().html(count);
                }
            },
            error: function (error) {
                console.log('获取未读消息的条数失败', error);
            }
        });
    }
    function initUserData(userInfo) {
        var headImg = userInfo.headImg;
        if (headImg && headImg != '') {
            $('#headImg').attr('src', headImg);
        }
        $('#user_name').html(userInfo.nickName);
    }

</script>