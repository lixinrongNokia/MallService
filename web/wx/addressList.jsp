<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>爱内秀-管理地址</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="icon" type="image/x-icon" href="http://iliker.cn/images/logo.png" media="screen"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/user.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        .footer {
            position: fixed;
            left: 0;
            bottom: 0;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
            width: 100%;
            height: 40px;
            font-size: 1rem;
        }

        .footer a {
            position: relative;
            display: block;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            line-height: 40px;
            text-align: center;
            color: #0e0e0e;
            background-color: #e65300;
            text-decoration: none;
        }

        .footer a:before {
            content: '';
            position: absolute;
            left: 0;
            top: 10px;
            width: 1px;
            height: 20px;
            background-color: #ccc;
        }

        .footer a:first-child:before {
            display: none;
        }
    </style>
</head>
<body>
<div id="main" class="box-ver box">
    <!--header开始-->
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <h1>管理地址</h1>
        </header>
    </div>
    <!--header结束 -->
    <!--content开始-->
    <div id="content">
        <ul class="addressList" id="addresslist">
        </ul>
    </div>
    <!--content结束-->
    <!--footer开始-->
    <div class="footer">
        <a href="addressadd.jsp" class="item">新建地址</a>
    </div>
    <!--footer结束 -->
</div>
</body>
<script>
    $(function () {
        var appUser = $.parseJSON(localStorage.getItem("appUser"));
        var uid = appUser.uID;
        getaddlist1(uid,function (result) {
            $.each (result.data,function (i,item) {
                $("#addresslist").append('<li><p>' + item.consigneeName + '&nbsp;&nbsp;' + item.phone + '</p><p>' + item.address + '</p></li>');
            });/* {
                var orderContactInfo = result[i];
                orderContactInfo.phone = appUser.phone;
                /!*var deliverInfo={};
                 deliverInfo.phone = user.phone;
                 deliverInfo.recevername = item.consigneeName;
                 deliverInfo.recevertel = item.phone;
                 deliverInfo.receveraddr = item.address;
                 $("#addresslist").append('<li onclick=localStorage.orderContactInfo=\'' + JSON.stringify(deliverInfo) + '\';openwin("shopWrite");><p>' + item.consigneeName + '&nbsp;&nbsp;' + item.phone + '</p><p>' + item.address + '</p></li>');*!/
                $("#addresslist").append('<li><p>' + orderContactInfo.recevername + '&nbsp;&nbsp;' + orderContactInfo.recevertel + '</p><p>' + orderContactInfo.receveraddr + '</p></li>');
            }*/
        });
    });
</script>
</html>