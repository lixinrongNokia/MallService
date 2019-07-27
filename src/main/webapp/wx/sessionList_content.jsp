<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>爱内秀-最近会话</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <style type="text/css">
        ul li {
            line-height: 1.5rem;
            background-color: #F4F4F4;
            margin-top: 8px;
        }

        .unsum {
            color: red;
        }

    </style>

</head>
<body>
<div>
    <ul id="sessionlist">
    </ul>
</div>
</body>
<script>
</script>
</html>
<script async="async">
    var sdk = null;
    var unreadmsg = [];
    if (localStorage.getItem("unreadmsg")) {
        unreadmsg = jQuery.parseJSON(localStorage.getItem("unreadmsg"));
    }
    $(function () {
        var user = $.parseJSON(localStorage.getItem("appUser"));
        $.ajax({
            url: "https://g.alicdn.com/aliww/h5.imsdk/2.1.5/scripts/yw/wsdk.js",
            dataType: "script",
            success: function () {
                initIM(user);
            }
        });
    });

    function initIM(user) {
        window.__WSDK__POSTMESSAGE__DEBUG__ = true;
        sdk = new WSDK();
        sdk.Base.login({
            uid: user.phone,
            appkey: '23403396',
            credential: user.password,
            timeout: 5000,
            success: function (data) {
                getSessionList();
            },
            error: function (error) {
                // {code: 1002, resultText: 'TIMEOUT'}
                console.log('login fail', error);
            }
        });
    }

    function getSessionList() {
        sdk.Base.getRecentContact({
            count: 10,
            success: function (data) {
                data = data.data;
                var list = data.cnts;
                var tag = '';
                var i = 0;
                list.forEach(function (item) {
                    var uid = item.uid;
                    var msgstr = '';
                    if (unreadmsg.length > 0) {
                        var msginfo = unreadmsg[i];
                        if (msginfo.touid === uid) {
                            msgstr = '<span class="unsum">' + msginfo.msgcount + '</span>条未读消息';
                        }
                    }
//                    uid = uid.substring(uid.lastIndexOf('v') + 1, uid.length);
                    uid = sdk.Base.getNick(uid);//官方提供去除用户长id的前缀，即前8位
                    if (item.nickname != null) {
                        tag += '<li onclick=localStorage.touid="' + uid + '";localStorage.toAvatar="' + item.avator + '";localStorage.toNick="' + item.nickname + '";onReaded("' + uid + '");openwin("session");><span>' + item.nickname + '</span><p>' + msgstr + '</p></li>';
                    }
                });
                if (unreadmsg.length > 0) {
                    localStorage.removeItem('unreadmsg');
                }
                jQuery('#sessionlist').append(tag);

            },
            error: function (error) {
                console.log('获取最近联系人及最后一条消息内容失败', error);
                jQuery('#sessionlist').append('<li>没有最近消息</li>');
            }
        });
    }

    function onReaded(touid) {
        try {
            sdk.Chat.setReadState({
                touid: '' + touid + '',
                timestamp: Math.floor((+new Date()) / 1000),
                success: function (data) {
                    console.log('设置已读成功', data);
                },
                error: function (error) {
                    console.log('设置已读失败', error);
                }
            });
        } catch (e) {
            console.log(e);
        }
    }
</script>