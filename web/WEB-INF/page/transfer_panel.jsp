<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <style type="text/css">
        body, div, ul, li {
            margin: 0 auto;
            padding: 0;
        }

        body {
            font: 12px "宋体";
            text-align: center;
        }

        a:link {
            color: #00F;
            text-decoration: none;
        }

        a:visited {
            color: #00F;
            text-decoration: none;
        }

        a:hover {
            color: #c00;
            text-decoration: underline;
        }

        ul {
            list-style: none;
        }

        .main {
            clear: both;
            padding: 8px;
            text-align: center;
        }

        /*第一种形式*/
        #tabs0 {
            height: 540px;
            width: 100%;
            border: 1px solid #cbcbcb;
            background-color: #f2f6fb;
        }

        .menu0 {
            width: 400px;
        }

        .menu0 li {
            display: block;
            float: left;
            padding: 4px 0;
            width: 100px;
            text-align: center;
            cursor: pointer;
            background: #FFFFff;
        }

        .menu0 li.hover {
            background: #f2f6fb;
        }

        #main0 ul {
            display: none;
        }

        #main0 ul.block {
            display: block;
        }
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script>
        $(function(){
            window.open('${pageContext.request.contextPath}/pageLoad_showApply.do', 'applyList', '');
        });
        function setTab(m, n) {
            var tli = document.getElementById("menu" + m).getElementsByTagName("li");
            var mli = document.getElementById("main" + m).getElementsByTagName("ul");
            for (var i = 0; i < tli.length; i++) {
                tli[i].className = i == n ? "hover" : "";
                mli[i].style.display = i == n ? "block" : "none";
            }
            switch (n) {
                case 0:
                    window.open('${pageContext.request.contextPath}/pageLoad_showApply.do', 'applyList', '');
                    break;
                case 1:
                    window.open('${pageContext.request.contextPath}/pageLoad_successApply.do', 'successApply', '');
                    break;
            }
        }
    </script>
</head>
<body>
<div id="header" class="wrap">
    <div id="logo">爱内秀后台</div>
    <%@ include file="header.jsp" %>
</div>
<div id="content" class="wrap" style="height: 540px">
    <div id="tabs0">
        <ul class="menu0" id="menu0">
            <li onclick="setTab(0,0)" class="hover">申请列表</li>
            <li onclick="setTab(0,1)">成功列表</li>
        </ul>
        <div class="main" id="main0">
            <ul class="block">
                <li>
                    <iframe name="applyList" style="width:100%;height: 480px"></iframe>
                </li>
            </ul>
            <ul>
                <li>
                    <iframe name="successApply" style="width:100%;height: 480px"></iframe>
                </li>
            </ul>
        </div>
    </div>
</div>
<div id="footer" class="wrap">东莞艾芬儿 &copy; 版权所有</div>
</body>
</html>
