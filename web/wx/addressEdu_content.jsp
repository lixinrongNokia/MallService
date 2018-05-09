<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
    <meta charset="utf-8">
    <title>管理地址</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/user.css">
</head>
<body class="um-vp" ontouchstart>
<ul class="addressEdu">
    <li>
        <select id="province" class=" left select1" onChange="getarea(this.value,'city')">
            <option value="0">选择省份</option>
        </select>
        <select id="city" class=" left select1" onChange="getarea(this.value,'district')">
            <option>选择城市</option>
        </select>
        <select id="district" class=" left select2">
            <option>选择区域</option>

        </select>
    </li>
    <li>
        <input id="address" type="text" placeholder="请输入街道地址"/>
        <h2>街道地址</h2>
    </li>
    <li>
        <input id="consignee" type="text" placeholder="请输入收货人姓名"/>
        <h2>收货人姓名</h2>
    </li>
    <li>
        <input id="mobile" placeholder="请输入手机号码"/>
        <h2>手机号码</h2>
    </li>
    <!--  <li style="line-height:3.875rem">设为默认地址
      <div class="right" style="margin-top:1.25rem">
        <div id="autoFouce" onclick="javascript:turnon('auto'); turnoff('autoFouce')"></div>
        <div id="auto" onclick="javascript:turnoff('auto'); turnon('autoFouce')"></div>
        </div>
      </li>-->
    <li>
        <input id="zipcode" placeholder="请输入邮编"/>
        <h2>邮编</h2>
    </li>
</ul>
<input disabled class="footBtn" value="保存地址" ontouchstart="zy_touch('down1')" onClick="saveaddress(4);"/>
</body>
<script>
    zy_init();

    window.uexOnload = function (type) {
        if (!type) {
            getarea(1, "province");
            getadddetial();

        }
    }
</script>
</html>