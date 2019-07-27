<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>爱内秀</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script type='text/javascript' src='${pageContext.request.contextPath }/js/jquery-1.12.4.min.js'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css">
    <script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/device.min.js"></script>
    <style type="text/css">
        .download-pannel {
            height: 50px;
            width: 100%;
            line-height: 50px;
            position: relative;
            font-size: 0;
            overflow: hidden
        }

        .download-pannel .pannel-bg {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            z-index: 4
        }

        .download-pannel .pannel-bg img {
            width: 100%;
            height: 100%
        }

        .download-pannel div[class^='download-'] {
            position: relative;
            z-index: 5;
            float: left;
            overflow: hidden
        }

        .download-pannel .download-close {
            width: 16px;
            height: 50px;
            line-height: 50px;
            display: inline-block;
            margin-left: 2.5%
        }

        .download-pannel .download-close img {
            width: 100%;
            height: auto
        }

        .download-pannel .download-logo {
            width: 35px;
            height: 50px;
            line-height: 50px;
            margin-left: 5%;
            margin-right: 2.5%
        }

        .download-pannel .download-logo img {
            width: 100%;
            height: auto
        }

        .download-pannel .download-txt {
            display: inline-block;
            width: 40%;
            height: 100%
        }

        .download-pannel .font-large {
            font-size: 15px
        }

        .download-pannel .download-action {
            display: inline-block;
            height: 100%;
            width: 31.88%;
            float: right !important;
            text-align: center;
            font-size: 13px;
            color: #fff
        }

        .download-pannel .download-content {
            font-size: 12px;
            display: block;
            position: relative
        }

        .download-pannel .download-content .content-up {
            color: white;
            font-weight: 500;
            position: absolute;
            top: -9px
        }

        .download-pannel .download-content .content-down {
            color: #999;
            position: absolute;
            top: 9px
        }

        .download-pannel em {
            font-style: normal
        }

        .download-pannel img, .download-pannel div {
            vertical-align: middle
        }
    </style>
</head>

<body>

<div id="main" class="box-ver box">
    <div id="download_banner" class="download-pannel" style="display: none;">
        <div class="pannel-bg"><img
                src="${pageContext.request.contextPath }/wx/images/download-bg.png"></div>
        <div class="download-close"><img id="download_openweb"
                                         src="${pageContext.request.contextPath }/wx/images/icon-close-banner.png"
                                         class="J_ping"
                                         report-eventid="MDownLoadFloatTop_CloseC"></div>
        <div class="download-logo"><img src="${pageContext.request.contextPath }/images/logo.png"></div>
        <div class="download-txt"><span class="download-content"><em class="content-up"
                                                                     style="font-size:13px;color:#ffffff;">打开爱内秀App购物</em><em
                class="content-down" style="font-size:13px;color:#999999;">活动期新人领50元购物券</em></span></div>
        <div class="download-action"><a id="download_openapp" href="javascript:" class="J_ping"
                                        report-eventid="MDownLoadFloatTop_OpenC"><span class="font-large"
                                                                                       style="color:#FFFFFF;">立即打开</span></a>
        </div>
    </div>
    <!--header开始-->
    <div id="header">
        <header>
            <div class="search">
                <div class="ipt">
                    <input placeholder="请输入搜索关键字" id="skey"/>
                </div>
                <div class="searchBtn" onclick="searchkey();"></div>
            </div>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content"></div>

    <!--content结束-->
    <!--footer开始-->
    <div id="footer">
        <footer>
            <ul>
                <li class="homeFouce">首页</li>
                <li class="classify" onclick="openwin('classify');">分类</li>
                <li class="shop" onclick="openwin('shop');">购物车
                    <div class="shop_num" id="shop_num" style="display:none"></div>
                </li>
                <li class="user" onclick="openwin('user');">我的</li>
                <li class="more" onclick="openwin('more');">更多</li>
            </ul>
        </footer>
    </div>
    <!--footer结束 -->
</div>
</body>
<script type="text/javascript">
    document.addEventListener("input", loadDataList, true);
    var history = [];

    function loadDataList() {
        var input = $('#skey').val();
        if (input.length > 0) {
            $.getJSON("searchObject.do", {"matching": input}, function (result) {

            });
        }
        return true;
    }

    jQuery(function () {
        if (device.mobile()) {
            if (!sessionStorage.download_pannel_close) {
                $('#download_banner').show();
            }
        }

        $('#download_openapp').click(function () {
            location.href = 'http://iliker.cn/download.html';
        });

        $('#download_openweb').click(function () {
            sessionStorage.setItem('download_pannel_close', true);
            $('#download_banner').hide();
        });
        var availableTags = [
            "ActionScript",
            "AppleScript",
            "Asp",
            "BASIC",
            "C",
            "C++",
            "Clojure",
            "COBOL",
            "ColdFusion",
            "Erlang",
            "Fortran",
            "Groovy",
            "Haskell",
            "Java",
            "JavaScript",
            "Lisp",
            "Perl",
            "PHP",
            "Python",
            "Ruby",
            "Scala",
            "Scheme"
        ];
        $("#skey").autocomplete({
            source: availableTags
        });

        /* $.ajax({
         url: "http://res.wx.qq.com/open/js/jweixin-1.0.0.js",
         dataType: "script",
         success: function () {

         }
         });*/
        var code = GetQueryString('code');
        var superiornum = GetQueryString('superiornum');
        if (superiornum) {
            localStorage.setItem('superiornum', superiornum);
        }
        var openid = localStorage.getItem('openid');
        if (code) {
            if (openid === null) {
                getJSToten(code);
            }
        } else {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == 'micromessenger') {
                if (!openid) {
                    location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8cb131acdcd8b85d&redirect_uri=http://iliker.cn/wx/index.jsp&response_type=code&scope=snsapi_userinfo#wechat_redirect';
                }
            }
        }
    });

    jQuery(function () {
        jQuery("#content").load("${pageContext.request.contextPath }/wx/index_content.jsp");
    });
</script>
</html>