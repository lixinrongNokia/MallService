<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">

<head>
    <meta charset="utf-8">
    <title>宝贝详情</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/attr.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/wx/css/classify.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/layer.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/wx/js/openwin.js"></script>
    <script src="${pageContext.request.contextPath }/js/islider.js"></script>
    <script src="${pageContext.request.contextPath }/js/plugins/islider_desktop.js"></script>
    <style>
        body {
            background: #f5f5f5
        }

        /*.divcss5 {
            width: 100%;
            height: auto;
            max-height: 450px;
            overflow: hidden
        }

        .divcss5 img {
            width: 100%;
            -webkit-box-sizing: border-box;
        }*/
        /*#vertical-slide {
            width: 100%;
            height: 412px;
            overflow: hidden;
        }*/
        .iSlider-effect {
            width: 100%;
            height: 380px;
            max-height: 420px;
            overflow: hidden;
            position: relative;
            margin: 0 auto;
        }

        .iteminfo_buying {
            display: none;
            height: auto;
        }

        .sys_item_spec {
            height: auto;
        }

        .btnConfirm {
            width: 100%;
            position: fixed;
            bottom: 0;
            background: #ff4400;
            -webkit-border-radius: 0.25rem;
            color: #fff;
            text-align: center;
            line-height: 2.5rem
        }
    </style>
</head>

<body class="um-vp">
<!--<div class="zp">-->
<div id="vertical-slide" class="iSlider-effect"></div>
<%--<div id="dom-effect" class="iSlider-effect"></div>--%>
<!--
<div id="pagenavi"><a href="javascript:void(0);" class="active"></a><a href="javascript:void(0);"></a></div>
</div>-->

<div class="name" id="goods_name"></div>
<%--<div class="shuoMing" id="goods_brief">说明</div>--%>
<ul class="price">
    <%--<li id="shop_price"></li>--%>
    <li id="market_price"></li>
</ul>
<div class="picMore" onclick="product_detail()">
    <img src="${pageContext.request.contextPath }/wx/css/res-apple/picMore.png">查看图片详情
</div>
<%--<div style="border-style:solid;">请选择商品规格
    <input type="hidden" name="color" id="colorAttr"/>
    <input type="hidden" name="size" id="sizeAttr"/>
</div>--%>
<input type="hidden" name="color" id="colorAttr"/>
<input type="hidden" name="size" id="sizeAttr"/>
<div class="iteminfo_buying">
    <!--规格属性-->
    <div class="sys_item_spec">
        <dl class="clearfix iteminfo_parameter sys_item_specpara" data-sid="1">
            <dt>颜色</dt>
            <dd>
                <ul id="colors" class="sys_spec_text">
                </ul>
            </dd>
        </dl>
        <dl class="clearfix iteminfo_parameter sys_item_specpara" data-sid="2">
            <dt>尺码</dt>
            <dd>
                <ul id="sizes" class="sys_spec_text">
                </ul>
            </dd>
        </dl>
        <div class="shuLiang"><span>数量：</span>
            <div class="number">
                <div class="left jian"></div>
                <input value="1" id="num" readonly="readonly">
                <div class="left jia"></div>
            </div>
        </div>
    </div>
    <input type="button" id="btnConfirm" class="btnConfirm" value="确定"/>
</div>

<%--<div class="addSc" onClick="favgoods();">添加到收藏</div>
<div class="comment" onclick="openwin('comment');"><img src="css/res-apple/comment.png">查看用户评论
</div>--%>
</body>

<script>
    var appUser = localStorage.getItem("appUser");
    var wxuser = localStorage.getItem('wxuser');
    var goodsId = GetQueryString('goodid');
    var openid = localStorage.getItem('openid');
    var superiornum = GetQueryString('superiornum');
    var addCart = true;
    var illustrations;
    var goodsName;
    var price;
    var pics = [];
    var input;
    var phone;
    var picList = [];
    if (superiornum) {
        localStorage.setItem('superiornum', superiornum);
    }
    var shareUrl = 'http://iliker.cn/wx/product.jsp?goodid=' + goodsId;
    if (appUser) {
        phone = $.parseJSON(appUser).phone;
    } else if (wxuser) {
        phone = $.parseJSON(wxuser).phone;
    }
    if (phone) {
        shareUrl += '&superiornum=' + encode64(phone);
    }
    getGoodsById(goodsId, function (goods) {
        adapterData(goods);
    });

    $(function () {
        $('.btn.left').click(function () {
            addCart = true;
            layer.open({
                type: 1
                , content: $('.iteminfo_buying').html()
                , anim: 'up'
                , style: 'position:fixed; bottom:0; left:0; width: 100%; height: 250px; padding:0; border:none;'
            });
        });
        $('.btn1.right').click(function () {
            addCart = false;
            if (openid) {
                if (wxuser === null) {
                    getWXUserData(openid, function (wxuserObject) {
                        if (wxuserObject) {
                            alertChoose();
                        }
                    });
                } else {
                    alertChoose();
                }
            } else {
                if (appUser === null) {
                    openwin('login');
                } else {
                    alertChoose();
                }
            }
        });
        $(document).on("click", ".left.jian", function () {
            input = $(this).parent().find('input');
            getnumber('-', input);
        });
        $(document).on("click", ".left.jia", function () {
            input = $(this).parent().find('input');
            getnumber('+', input);
        });
        $(document).on("click", "#btnConfirm", function () {
            input = $(this).parent().find('#num');
            addCart_Item(parseInt(goodsId), goodsName, price, pics[0], input, addCart);
        });
    });

    function alertChoose() {
        checkCart(parseInt(goodsId), function (result) {
            if (result) {
                openwin('shopWrite');
            } else {
                layer.open({
                    type: 1
                    , content: $('.iteminfo_buying').html()
                    , anim: 'up'
                    , style: 'position:fixed; bottom:0; left:0; width: 100%; height: 250px; padding:0; border:none;'
                });
            }
        });
    }

    function adapterData(goods) {
        illustrations = goods.illustrations;
        pics = goods.imgpath.split("#");
        goodsName = goods.goodName;
        price = goods.price;
        jQuery("#goods_name").append(goodsName);
        jQuery("#market_price").append('价格：<strong>￥' + price + '</strong><span>满￥150包邮</span>');
        var colors = goods.colors.split("#");
        var sizes = goods.sizes.split("#");
        for (var i = 0; i < colors.length; i++) {
            jQuery("#colors").append('<li><a href="javascript:;" onclick="statusChange(this,\'color\')" title="' + colors[i] + '">' + colors[i] + '</a><i></i></li>');
        }

        for (var i = 0; i < sizes.length; i++) {
            jQuery("#sizes").append('<li><a href="javascript:;" onclick="statusChange(this,\'size\')" title="' + sizes[i] + '">' + sizes[i] + '</a><i></i></li>');
        }
        $.each(pics, function (i, item) {
            picList[i] = {
                'content': '/goodsimg/' + item,
                'width': '100%',
                'height': 380
            };
        });
        //all animation effect
        var islider = new iSlider({
            data: picList,
            dom: document.getElementById("vertical-slide"),
            duration: 3000,
            type: 'pic',
            animateType: 'flip',
            isAutoplay: true,
            isLooping: true
            // isVertical: true, 是否垂直滚动
        });
        islider.bindMouse();
        mobShare.config({
            appkey: '1e77534358d00', // appkey
            params: {
                url: shareUrl, // 分享链接
                title: goods.goodName, // 分享标题
                description: goods.goodsDesc, // 分享内容
                pic: 'http://iliker.cn/goodsimg/' + pics[0]//分享图片，使用逗号,隔开
            },

            /**
             * 分享时触发的回调函数
             * 分享是否成功，目前第三方平台并没有相关接口，因此无法知道分享结果
             * 所以此函数只会提供分享时的相关信息
             *
             * @param {String} plat 平台名称
             * @param {Object} params 实际分享的参数 { url: 链接, title: 标题, description: 内容, pic: 图片连接 }
             */
            callback: function (plat, params) {
            }

        });
    }

    function statusChange(a, attr) {
        var tag = jQuery(a).parent();
        $(tag).addClass('selected').siblings(tag).removeClass('selected');
        if ('color' == attr) {
            jQuery("#colorAttr").val(jQuery(a).text());
        } else {
            jQuery("#sizeAttr").val(jQuery(a).text());
        }
    }

    function product_detail() {
        location.href = '${pageContext.request.contextPath }/wx/product_detail.jsp?illustrations=' + illustrations;
    }
</script>

</html>