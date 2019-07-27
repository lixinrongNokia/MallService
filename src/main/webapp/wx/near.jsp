<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/wx/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        body, html {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin-top: 0;
            font-family: "微软雅黑", serif;
        }

        #header {
            width: 100%;
            height: 10%;
        }

        #allmap {
            width: 100%;
            height: 90%;
            overflow: hidden;
            margin-top: 0;
            padding-top: 0;
            font-family: "微软雅黑", serif;
        }
    </style>
    <title>附近门店-列表</title>
</head>
<body>
<div id="header">
    <header>
        <h1>附近门店</h1>
        <div class="back" onclick="responseBack()">返回</div>
    </header>
</div>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
    var lat;
    var lng;
    var map;
    jQuery(function () {
        var script = document.createElement("script");
        script.src = "http://api.map.baidu.com/api?v=2.0&ak=utHziKnXChDF1PORBNB1WyCKDCmLFq13&callback=initialize";//此为v2.0版本的引用方式
        document.body.appendChild(script);
    });
    // 百度地图API功能
    function initialize() {
        console.log("初始化进行。。");
        map = new BMap.Map("allmap");
        var point = new BMap.Point(116.331398, 39.897445);
        var top_right_navigation = new BMap.NavigationControl({
            anchor: BMAP_ANCHOR_TOP_RIGHT,
            type: BMAP_NAVIGATION_CONTROL_SMALL
        });
        map.enableScrollWheelZoom();
        map.addControl(top_right_navigation);
        map.centerAndZoom(point, 12);
        var geolocationControl = new BMap.GeolocationControl({
            anchor:BMAP_ANCHOR_TOP_LEFT
        });
        geolocationControl.addEventListener("locationSuccess", function (e) {
            // 定位成功事件
            var address = '';
            address += e.addressComponent.province;
            address += e.addressComponent.city;
            address += e.addressComponent.district;
            address += e.addressComponent.street;
            address += e.addressComponent.streetNumber;
            console.log(address);
        });
        map.addControl(geolocationControl);
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (r) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                map.panTo(r.point);
                lng = r.point.lng;
                lat = r.point.lat;
                getNearStores();
            }
            else {
                console.log('failed' + this.getStatus());
            }
        }, {enableHighAccuracy: true});
    }
    var opts = {
        width: 250,     // 信息窗口宽度
        height: 150,     // 信息窗口高度
        title: "门店信息", // 信息窗口标题
        enableMessage: true//设置允许信息窗发送短息
    };
    function getNearStores() {
        jQuery.getJSON("${pageContext.request.contextPath }/getNearStore.do", {
            "mylat": "" + lat + "",
            "mylng": "" + lng + ""
        }, function (result) {
            if (result.success) {
                var stores = result.stores;
                // data += 'coord:' + stores[i].latitude + ',' + stores[i].longitude + ';coordtype:3;title:' + stores[i].storeName + ';addr:' + stores[i].address + '';
                // 向地图添加标注
                for (var i = 0; i < stores.length; i++) {
                    var img=stores[i].faceIcon.split("#")[0];
                    var marker = new BMap.Marker(new BMap.Point(stores[i].longitude, stores[i].latitude));  // 创建标注
                    var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
                            '<img src="/storeIcon/'+img+'" alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:100px;margin-left:3px;"/>' +
                            '地址：'+stores[i].address+'<br/>电话：'+stores[i].tell+'<br/>简介：'+stores[i].storeName+'' +
                            '</div>';
                    map.addOverlay(marker);               // 将标注添加到地图中
                    addClickHandler(content, marker);
                }
            }
        });
    }

    function addClickHandler(content, marker) {
        marker.addEventListener("click", function (e) {
                    openInfo(content, e)
                }
        );
    }
    function openInfo(content, e) {
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
    }
</script>