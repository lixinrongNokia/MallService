<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="iliker" uri="http://iliker.cn/MallService" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/onlink.js"></script>
<div id="navbar">
    <div class="userMenu">
        <ul id="ul">
            <li>
                <a href="main.do">管理平台首页</a>
            </li>
            <li>
                <iliker:permission module="buyer" privilege="view">
                    <a href="allUser.do">用户管理</a>
                </iliker:permission>
            </li>
            <li>
                <iliker:permission module="employee" privilege="view">
                    <a href="permissionManage.do">系统管理</a>
                </iliker:permission>
            </li>
            <li>
                <a href="viewAPPManager.do">客户端更新</a>
            </li>
            <li>
                <iliker:permission module="product" privilege="view">
                    <a href="showGoods.do">货品管理</a>
                </iliker:permission>
            </li>
            <li>
                <iliker:permission module="order" privilege="view">
                    <a href="allorder.do">订单管理</a>
                </iliker:permission>
            </li>
            <li>
                <iliker:permission module="community" privilege="view">
                    <a href="action_showShare.do?node=0">分享管理</a>
                </iliker:permission>
            </li>
            <li><a href="pushmsg.do">广播消息</a></li>
            <li><a href="themeActivity.do">主题活动</a></li>
            <li>
                <iliker:permission module="order" privilege="refundOrder">
                    <a href="show_transfer_panel.do">财务转账</a>
                </iliker:permission>
            </li>
            <li><a href="javascript:void(0)" onclick="logOut()">注销</a></li>
        </ul>
    </div>
    你好!${user.nickname }
</div>
