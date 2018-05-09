<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <style type="text/css">
        body, html, #allmap {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin: 0;
        }
    </style>
    <title>门店列表</title>
</head>
<body>
<div id="allmap"></div>
</body>
</html>
<script defer="defer">
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
        map.enableScrollWheelZoom();
        map.centerAndZoom(point, 12);
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (r) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                /*var mk = new BMap.Marker(r.point);
                map.addOverlay(mk);
                map.panTo(r.point);*/
                var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
                var mk = new BMap.Marker(r.point,{icon:myIcon});  // 创建标注
                map.addOverlay(mk);
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
        width : 250,     // 信息窗口宽度
        height: 120,     // 信息窗口高度
        title : "门店信息" , // 信息窗口标题
        enableMessage:true//设置允许信息窗发送短息
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
                    var marker = new BMap.Marker(new BMap.Point(stores[i].longitude, stores[i].latitude));  // 创建标注
                    var content = '店名:'+stores[i].storeName+'<br/>地址:'+stores[i].address;
                    map.addOverlay(marker);               // 将标注添加到地图中
                    addClickHandler(content,marker);
                }
            }
        });
    }

    function addClickHandler(content,marker){
        marker.addEventListener("click",function(e){
            openInfo(content,e)}
        );
    }
    function openInfo(content,e){
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow,point); //开启信息窗口
    }
</script>