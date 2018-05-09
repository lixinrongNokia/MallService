<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <title>支付成功</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <script src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <style type="text/css">
        div {
            position: relative;
        }

        div img {
            width: 100px;
            height: 100px;
        }

        .div11 {
            position: absolute;
            width: 100px;
            height: 100px;
            animation: myfirst 5s;
            -moz-animation: myfirst 3s; /* Firefox */
            -webkit-animation: myfirst 3s; /* Safari 和 Chrome */
            -o-animation: myfirst 3s;
            top: 0;
            background: #fff;
            left: 0;
        }

        @keyframes myfirst {
            0% {
                left: 0;
            }

            100% {
                left: 100px;
            }
        }

        @-moz-keyframes myfirst /* Firefox */
        {
            0% {
                left: 0;
            }

            100% {
                left: 100px;
            }
        }

        @-webkit-keyframes myfirst /* Safari 和 Chrome */
        {
            0% {
                left: 0;
            }

            100% {
                left: 100px;
            }
        }

        @-o-keyframes myfirst /* Opera */
        {
            0% {
                left: 0;
            }

            100% {
                left: 100px;
            }
        }
    </style>
</head>
<body>
<div align="center">
    <div id="header">
        <header>
            <div class="back" onclick="responseBack()">返回</div>
            <h1>支付结果</h1>
        </header>
    </div>
    <div>
        <img src="${pageContext.request.contextPath }/wx/images/dagou.png"/>
        <div class="div11"></div>
    </div>
    <span>支付成功</span>
</div>
</body>
</html>
