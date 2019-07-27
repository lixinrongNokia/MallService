<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <title>微信JS-SDK Demo</title>
    <script type='text/javascript' src='${pageContext.request.contextPath }/wx/js/jquery.min.js'></script>
</head>
<body>
<div class="wxapi_container">
    <div class="wxapi_index_container">
        <ul class="label_box lbox_close wxapi_index_list">
            <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-share">分享接口</a></li>
            <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-image">图像接口</a></li>
            <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-voice">音频接口</a></li>
            <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-location">地理位置接口</a></li>
            <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-scan">微信扫一扫接口</a></li>
            <li class="label_item wxapi_index_item"><a class="label_inner" href="#menu-pay">微信支付接口</a></li>
        </ul>
    </div>
    <div class="lbox_close wxapi_form">
        <h3 id="menu-share">分享接口</h3>
        <span class="desc">获取“分享到朋友圈”按钮点击状态及自定义分享内容接口</span>
        <button class="btn btn_primary" id="onMenuShareTimeline">onMenuShareTimeline</button>
        <span class="desc">获取“分享给朋友”按钮点击状态及自定义分享内容接口</span>
        <button class="btn btn_primary" id="onMenuShareAppMessage">onMenuShareAppMessage</button>
        <span class="desc">获取“分享到QQ”按钮点击状态及自定义分享内容接口</span>
        <button class="btn btn_primary" id="onMenuShareQQ">onMenuShareQQ</button>
        <span class="desc">获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口</span>
        <button class="btn btn_primary" id="onMenuShareWeibo">onMenuShareWeibo</button>
        <span class="desc">获取“分享到QZone”按钮点击状态及自定义分享内容接口</span>
        <button class="btn btn_primary" id="onMenuShareQZone">onMenuShareQZone</button>

        <h3 id="menu-image">图像接口</h3>
        <span class="desc">拍照或从手机相册中选图接口</span>
        <button class="btn btn_primary" id="chooseImage">chooseImage</button>
        <span class="desc">预览图片接口</span>
        <button class="btn btn_primary" id="previewImage">previewImage</button>
        <span class="desc">上传图片接口</span>
        <button class="btn btn_primary" id="uploadImage" onclick="onLoadImg()">uploadImage</button>
        <span class="desc">下载图片接口</span>
        <button class="btn btn_primary" id="downloadImage">downloadImage</button>

        <h3 id="menu-voice">音频接口</h3>
        <span class="desc">开始录音接口</span>
        <button class="btn btn_primary" id="startRecord">startRecord</button>
        <span class="desc">停止录音接口</span>
        <button class="btn btn_primary" id="stopRecord">stopRecord</button>
        <span class="desc">播放语音接口</span>
        <button class="btn btn_primary" id="playVoice">playVoice</button>
        <span class="desc">暂停播放接口</span>
        <button class="btn btn_primary" id="pauseVoice">pauseVoice</button>
        <span class="desc">停止播放接口</span>
        <button class="btn btn_primary" id="stopVoice">stopVoice</button>
        <span class="desc">上传语音接口</span>
        <button class="btn btn_primary" id="uploadVoice">uploadVoice</button>
        <span class="desc">下载语音接口</span>
        <button class="btn btn_primary" id="downloadVoice">downloadVoice</button>

        <h3 id="menu-location">地理位置接口</h3>
        <span class="desc">使用微信内置地图查看位置接口</span>
        <button class="btn btn_primary" id="openLocation">openLocation</button>
        <span class="desc">获取地理位置接口</span>
        <button class="btn btn_primary" id="getLocation">getLocation</button>

        <h3 id="menu-scan">微信扫一扫</h3>
        <span class="desc">调起微信扫一扫接口</span>
        <button class="btn btn_primary" id="scanQRCode1" onclick="getQRCodeResult()">scanQRCode(直接返回结果)</button>

        <h3 id="menu-pay">微信支付接口</h3>
        <span class="desc">发起一个微信支付请求</span>
        <button class="btn btn_primary" id="chooseWXPay">chooseWXPay</button>
    </div>
</div>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    var href = location.href.split('#')[0];
    jQuery(function () {
        jQuery.getJSON("/MallService/wxJsSDKInit.do", {"url": "" + href + ""}, function (result) {
            if (result != null) {
                wx.config({
                    debug: true,
                    appId: result.appid,
                    timestamp: result.timestamp,
                    nonceStr: result.noncestr,
                    signature: result.signature,
                    jsApiList: ['chooseWXPay']
                });
            }
        });
    });

    function getQRCodeResult() {
        wx.scanQRCode({
            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                //ilikerAppOrderID
                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                var ilikerAppOrderID = jQuery.parseJSON(result).ilikerAppOrderID;
                if (ilikerAppOrderID != null) {
                    alert('你的订单号是' + ilikerAppOrderID);
                }
            }
        });
    }
    //拍照或从手机相册中选图接口
    function onLoadImg() {
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                //上传图片接口
                wx.uploadImage({
                    localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        var media_id = res.serverId; // 返回图片的服务器端ID
                        var phone = localStorage.getItem('phone');
                        jQuery.getJSON("/MallService/chooseImage.do", {
                            "media_id": media_id,
                            "phone": phone
                        }, function (result) {
                            if (result.success) {
                                alert('更改头像成功!');
                            }
                        });
                    }
                });
            }
        });
    }
</script>
</html>
