<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>爱内秀分享邀请</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/utils.js"></script>
    <style type="text/css">
        .feedback textarea {
            background-color: #FFF;
            width: 90%;
            height: 4rem;
            padding: 0.5rem 2.5%;
            margin: 0.5rem 2.5%;
            border: none;
            font-size: 0.6rem;
            line-height: 0.9rem;
        }
    </style>
</head>
<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <div class="headerRight -mob-share-open">邀请</div>
            <!--<div class="headerRight" ontouchstart="zy_touch('backDown')" onClick="delorderall()">清空</div>-->
            <!--MOB SHARE BEGIN-->
            <div class="-mob-share-ui" style="display: none">
                <ul class="-mob-share-list">
                    <li class="-mob-share-weibo"><p>新浪微博</p></li>
                    <li class="-mob-share-qzone"><p>QQ空间</p></li>
                    <li class="-mob-share-qq"><p>QQ好友</p></li>
                    <li class="-mob-share-weixin"><p>微信</p></li>
                    <li class="-mob-share-douban"><p>豆瓣</p></li>
                    <li class="-mob-share-youdao"><p>有道云笔记</p></li>
                    <li class="-mob-share-pengyou"><p>朋友网</p></li>
                </ul>
                <div class="-mob-share-close">取消</div>
            </div>
            <div class="-mob-share-ui-bg"></div>
            <script id="-mob-share"
                    src="http://f1.webshare.mob.com/code/mob-share.js?appkey=1b3052a73a680"></script>

            <!--MOB SHARE END-->
            <h1>邀请码</h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content">
        <div class="nctouch-main-layout feedback">
            <div class="nctouch-asset-info">
                <div class="container voucher"><i class="icon"></i>
                    <dl class="rule">
                    </dl>
                </div>
            </div>
            <div style="font-size: 16px; padding: 10px; margin-top:10px;text-align: center;">
                您的邀请二维码：
            </div>
            <div style="width:150px;margin:0 auto; padding-bottom:10px;text-align: center;">
                <!--<a href="javascript:;;" id="download_url" title="保存二维码">--><img id="myurl_src"
                                                                                    style="width:150px; height:150px">
                <!--</a>-->
                <div style="font-size: 14px;text-align: center;">
                    点击二维码可以保存哦
                </div>
            </div>

            <div style="font-size: 16px; text-align: center; padding: 10px; text-align: center;">
                您的邀请链接(长按下面链接复制)：
            </div>
            <textarea id="myurl" class="textarea" style="height: 2rem; font-size: 0.8rem;text-align: center" readonly="readonly"
                      onfocus="setTxt()">
            </textarea>
            <!--<a href="javascript:setTxt();" class="btn-l" id="copy_btn">复制邀请网址</a>-->
            <p style="display: none"><span id="foo">http://www.hmall365.com/wap/tmpl/member/register.html#!2</span></p>
            <button style="display: none"> class="btn-l" data-clipboard-action="copy" data-clipboard-target="#foo"
                id="copy_btn">
                复制邀请网址
            </button>
        </div>
    </div>
    <!--content结束-->
    <!--footer开始-->
    <div id="footer"></div>
    <!--footer结束 -->
</div>
</body>
</html>
<script defer="defer">
    var appUser = $.parseJSON(localStorage.getItem("appUser"));
    var wxuser = $.parseJSON(localStorage.getItem("wxuser"));
    var phone = appUser ? appUser.phone : wxuser.phone;
    var shareContent = encode64(phone);
    var shareUrl = 'http://iliker.cn/wx/index.jsp?superiornum=' + shareContent;
    var imgUrl;
    var href = location.href.split('#')[0];
    $(function () {
        if (wxuser) {
            $.ajax({
                url: "showqrcode.do",
                timeout: 10000,
                data: {"shareContent": "" + shareContent + ""},
                type: "POST",
                dataType: "json",
                success: function (result) {
                    $('#myurl_src').attr('src', 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=' + result.ticket);
                    imgUrl = 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=' + result.ticket;
                    $('#myurl').text(shareUrl);
                }
            });
            $.ajax({
                url: "https://res.wx.qq.com/open/js/jweixin-1.1.0.js",
                dataType: "script",
                success: function () {
                    $.getJSON("wxJsSDKInit.do", {"url": "" + href + ""}, function (result) {
                        if (result != null) {
                            //初始化接口环境
                            wx.config({
                                debug: false,
                                appId: result.appid,
                                timestamp: result.timestamp,
                                nonceStr: result.noncestr,
                                signature: result.signature,
                                jsApiList: ['onMenuShareTimeline',
                                    'onMenuShareAppMessage',
                                    'onMenuShareQQ',
                                    'onMenuShareWeibo',
                                    'onMenuShareQZone', 'hideMenuItems']
                            });

                            wx.ready(function () {
                                /*wx.checkJsApi({
                                 jsApiList: ['onMenuShareTimeline',
                                 'onMenuShareAppMessage',
                                 'onMenuShareQQ',
                                 'onMenuShareWeibo',
                                 'onMenuShareQZone'],
                                 success: function(res) {
                                 toast(res);
                                 }
                                 });*/
                                /* wx.showMenuItems({
                                 menuList: ['menuItem:share:appMessage', 'menuItem:share:timeline', 'menuItem:share:qq', 'menuItem:share:weiboApp', 'menuItem:favorite', 'menuItem:share:QZone'] // 要显示的菜单项，所有menu项见附录3
                                 });*/
                                wx.hideMenuItems({
                                    menuList: ['menuItem:copyUrl']
                                });
                                var _title = '邀请好友',
                                    _link = 'http://iliker.cn/wx/index.jsp?superiornum=' + shareContent,
                                    _desc = '美丽养成舒适生活，关爱身边断翼天使！',
                                    _img = 'http://iliker.cn/images/logo.png';
                                wx.onMenuShareTimeline({
                                    title: _title, // 分享标题
                                    desc: _desc, // 分享描述
                                    link: _link, // 分享链接
                                    imgUrl: _img, // 分享图标
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                    }, trigger: function () {
                                        //监听Menu中的按钮点击时触发的方法
                                    }, fail: function () {
                                        //接口调用失败时执行的回调函数
                                    }, complete: function () {
                                        //接口调用完成时执行的回调函数，无论成功或失败都会执行
                                    }
                                });

                                wx.onMenuShareAppMessage({
                                    title: _title, // 分享标题
                                    desc: _desc, // 分享描述
                                    link: _link, // 分享链接
                                    imgUrl: _img, // 分享图标
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                    }
                                });

                                wx.onMenuShareQQ({
                                    title: _title, // 分享标题
                                    desc: _desc, // 分享描述
                                    link: _link, // 分享链接
                                    imgUrl: _img, // 分享图标
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                    }
                                });
                                wx.onMenuShareWeibo({
                                    title: _title, // 分享标题
                                    desc: _desc, // 分享描述
                                    link: _link, // 分享链接
                                    imgUrl: _img, // 分享图标
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                    }
                                });

                                wx.onMenuShareQZone({
                                    title: _title, // 分享标题
                                    desc: _desc, // 分享描述
                                    link: _link, // 分享链接
                                    imgUrl: _img, // 分享图标
                                    success: function () {
                                        // 用户确认分享后执行的回调函数
                                    },
                                    cancel: function () {
                                        // 用户取消分享后执行的回调函数
                                    }
                                });
                            });
                        }
                    });
                }
            });
        } else {
            $('#myurl_src').attr('src', 'createimgcode_createQRCode.do?QRCodeContent=' + shareContent);
            imgUrl = 'http://iliker.cn/createimgcode_createQRCode.do?QRCodeContent = ' + shareContent;
            $('#myurl').text(shareUrl);
        }

        mobShare.config({
            appkey: '1e77534358d00', // appkey
            params: {
                url: shareUrl, // 分享链接
                title: '时尚内衣圈', // 分享标题
                description: '贴心呵护关爱女性', // 分享内容
                pic: imgUrl//分享图片，使用逗号,隔开
            },

            /**
             * 分享时触发的回调函数
             * 分享是否成功，目前第三方平台并没有相关接口，因此无法知道分享结果
             * 所以此函数只会提供分享时的相关信息
             *
             * @param {String} plat 平台名称
             * @param {Object} params 实际分享的参数 { url: 链接, title: 标题, description: 内容, pic: 图片连接 }
             */
            callback: function (plat, params) {
            }

        });
    });
    function setTxt() {
        var t = $("#myurl");
        t.select();
    }
</script>