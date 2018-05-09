<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>爱内秀</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/camera.css"/>
    <script type='text/javascript' src='${pageContext.request.contextPath }/wx/js/jquery.min.js'></script>
    <script type='text/javascript'
            src='${pageContext.request.contextPath }/wx/js/jquery.mobile.customized.min.js'></script>
    <script type='text/javascript' src='${pageContext.request.contextPath }/wx/js/jquery.easing.1.3.js'></script>
    <script type='text/javascript' src='${pageContext.request.contextPath }/wx/js/camera.min.js'></script>
    <script type='text/javascript' src='${pageContext.request.contextPath }/wx/js/layer.js'></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>

    <style type="text/css">
        .contentli {
            clear: both;
        }

    </style>
</head>
<body>

<div class="camera_wrap" id="camera_wrap">
    <div class="womenstore" data-thumb="${pageContext.request.contextPath }/wx/images/slides/womenstore.jpg"
         data-src="${pageContext.request.contextPath }/wx/images/slides/womenstore.jpg">
        <div class="camera_caption fadeFromBottom">
            艾拉奇女装店<em></em>
        </div>
    </div>
    <div class="manstore" data-thumb="${pageContext.request.contextPath }/wx/images/slides/manstore.jpg"
         data-src="${pageContext.request.contextPath }/wx/images/slides/manstore.jpg">
        <div class="camera_caption fadeFromBottom">
            艾拉奇男装店<em></em>
        </div>
    </div>
    <div class="preferential" data-thumb="${pageContext.request.contextPath }/wx/images/slides/preferential.jpg"
         data-src="${pageContext.request.contextPath }/wx/images/slides/preferential.jpg">
        <div class="camera_caption fadeFromBottom">
            男装内裤<em></em>
        </div>
    </div>
</div>
<!-- #camera_wrap -->
<!-- .fluid_container -->
<div class="contentli">
    <h2><img src="${pageContext.request.contextPath }/wx/css/res-apple/jingpin.png">精品推荐</h2>
    <ul class="list" id="jptj"></ul>
    <h2><img src="${pageContext.request.contextPath }/wx/css/res-apple/rexiao.png">热销排行</h2>
    <ul class="list" id="rxph"></ul>
    <h2><img src="${pageContext.request.contextPath }/wx/css/res-apple/new.png"></h2>
    <ul class="list" id="xpss"></ul>
</div>
</body>
</html>
<script defer="defer">
    jQuery(function () {
        jQuery('.womenstore').click(function () {
            openwin('classify');
        });
        jQuery('#camera_wrap').camera({
            thumbnails: true
        });
        jQuery.getJSON("recommended.do?property=price", function (result) {
                var price_goods = jQuery.parseJSON(result);
                adapter(price_goods, '#jptj');
                saveGoods(price_goods);
            }
        );
        jQuery.getJSON("recommended.do?property=sales", function (result) {
                var price_goods = jQuery.parseJSON(result);
                adapter(price_goods, '#rxph');
            }
        );
        jQuery.getJSON("recommended.do?property=marketDate", function (result) {
                var price_goods = jQuery.parseJSON(result);
                adapter(price_goods, '#xpss');
            }
        );
    });

    function adapter(goods, id) {
        var lis = "";
        for (var i = 0; i < goods.length; i++) {
            lis += "<li onclick='goodsInfo(" + goods[i].id + ")'><img src='/goodsimg/" + goods[i].imgpath.split("#")[0] + "' />￥" + goods[i].price + "元</li>";
        }
        jQuery(id).append(lis);
    }
</script>