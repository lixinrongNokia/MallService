var root = "/MallService";
var edshopurl = "/wx/";
var registerBean = {};
window.indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
var db;
var versionCode = 2;
var dbName = 'iliker.db';
jQuery(function () {
    loadDB();
    if (typeof(db) !== "undefined") {
        getshop_num(db);
    }
});

function toast(msg) {
    layer.open({
        content: msg
        , skin: 'msg'
        , time: 2 //2秒后自动关闭
    });
}

function shortToast(msg) {
    layer.open({
        content: msg
        , skin: 'msg'
        , time: 500
    });
}

function loadDB() {
    if (!window.indexedDB) {
        toast("你的浏览器不支持IndexedDB");
    } else {
        var request = window.indexedDB.open(dbName, versionCode);
        request.onerror = function (event) {
            console.log("打开DB失败", event);
        };
        request.onupgradeneeded = function (event) {
            db = event.target.result;
            if (!db.objectStoreNames.contains('goodses')) {
                db.createObjectStore("goodses", {keyPath: "id"});
            }
            if (!db.objectStoreNames.contains('cart')) {
                db.createObjectStore("cart", {keyPath: "id"});
            }
        };
        request.onsuccess = function (event) {
            db = event.target.result;
        };

    }
}

/*缓存商品到indexedDB*/
function saveGoods(goodsArray) {
    if (db != null) {
        var transaction = db.transaction(["goodses"], "readwrite");
        var store = transaction.objectStore("goodses");
        for (var i = 0; i < goodsArray.length; i++) {
            store.add(goodsArray[i]);
        }
    }
}

function getGoodsById(goodId, callback) {
    if (db != null) {
        var transaction = db.transaction(["goodses"], "readonly");
        var store = transaction.objectStore("goodses");
        var request = store.get(parseInt(goodId));
        request.onsuccess = function (e) {
            var goods = e.target.result;
            if (goods != null) {
                callback(goods);
            } else {
                getOnLineGoodsById(goodId, callback);
            }
        };
    } else {
        getOnLineGoodsById(goodId, callback);
    }
}

function getOnLineGoodsById(goodId, callback) {
    jQuery.getJSON("getGoodsById.do", {"goodid": goodId}, function (result) {
        if (result.success) {
            saveGoods([result.goods]);//缓存新商品
            callback(result.goods);
        }
    });
}

function getshop_num() {
    var sum = 0;
    if (db != null) {
        var transaction = db.transaction(["cart"], "readonly");
        var store = transaction.objectStore("cart");
        var cursor = store.openCursor();
        cursor.onsuccess = function (e) {
            var res = e.target.result;
            if (res != null) {
                var cartItem = res.value;
                sum += cartItem.itemCount;
                res.continue();
            } else {
                if (sum !== 0) {
                    if (sum > 10) {
                        sum = '10+';
                    }
                    jQuery(".shop_num").show().html(sum);
                } else {
                    jQuery(".shop_num").hide().html("");
                }
            }
        };
    }
}

/*页面跳转*/
function openwin(view) {
    location.href = edshopurl + view + ".jsp";
    event.cancelBubble = true;//禁止点击事件上传父元素
}

function regAccount(link) {
    var wxuser = localStorage.getItem('wxuser');
    var appUser = localStorage.getItem('appUser');
    var openid = localStorage.getItem('openid');
    if (openid) {
        if (wxuser) {
            openwin(link);
        } else {
            getWXUserData(openid, function (wxuserObject) {
                if (wxuserObject) {
                    openwin(link);
                }
            });
        }
    } else if (appUser) {
        openwin(link);
    } else {
        localStorage.callAddress = 'shop';
        openwin('login');
    }
}

/*商品详情页面*/
function goodsInfo(id) {
    window.location.href = edshopurl + "product.jsp?goodid=" + id;
}

function getnumber(operators, inut) {
    var num = parseInt($(inut).val());
    switch (operators) {
        case '+':
            num = num + 1;
            break;
        case '-':
            if (num > 1) {
                num = num - 1;
            }
            break;
    }
    $(inut).val(num);
}

//用户登陆
function login() {
    var phone = $('#phone').val();
    var password = $('#password').val();
    if (!checkPhone(phone) || $.trim(password) < 6) {
        toast('请输入手机号与密码');
        return;
    }
    password = encode64(password);//加密
    jQuery.ajax({
        url: "userLogin.do",
        data: {"logtext": "" + phone + "", "password": "" + password + "", "device": "h5"},
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result.success) {
                localStorage.setItem("appUser", JSON.stringify(result.userInfo));
                toast('登陆成功');
                setTimeout(function () {
                    var callAdd = localStorage.getItem('callAddress');
                    if (callAdd && typeof(callAdd) !== "undefined") {
                        openwin(callAdd);
                    } else {
                        history.go(-1);
                    }
                }, 500);
            } else {
                toast('登陆错误');
            }
        },
        error: function (errorMsg) {
            console.log('请求错误');
        }
    });
}

/*请求微信access_token*/
function getJSToten(code) {
    $.ajax({
        //请求微信access_token获取opendid
        url: "getServerProperty.do",
        data: {"code": "" + code + ""},
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.success) {
                localStorage.setItem('openid', result.openid);
            }
        }
    });
}

/*拉取微信用户信息*/
function getWXUserData(openid, callback) {
    $.ajax({
        url: "getWXUserData.do",
        data: {"openid": "" + openid + ""},
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result.success) {
                localStorage.setItem('wxuser', JSON.stringify(result.wxuser));
                if ('superiornum' in result) {
                    localStorage.setItem('superiornum', result.superiornum);
                }
                callback(result.wxuser);
            }
        }
    });
}

function userexit() {
    localStorage.removeItem('appUser');
    localStorage.removeItem('wxuser');
    localStorage.removeItem('orderContactInfo');
    openwin('login');
}

//计算订单总价格
function orderAccounting() {
    var money = 0.00;
    var transaction = db.transaction(["cart"], "readonly");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res != null) {
            var goods = res.value;
            money += goods.price * goods.itemCount;
            res.continue();
        } else
            jQuery("#total").html("￥" + money);
    };
}

//删除购物项
function delCart_item(tag) {
    var cid = jQuery(tag).next().val();
    var numVal = jQuery(tag).parent().children(".num").children("input").val();
    var itemCount = parseInt(numVal);
    var transaction = db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.delete(parseInt(cid));
    request.onsuccess = function (e) {
        orderAccounting();
        location.href = edshopurl + "shop.jsp";
    };
}

//点击返回处理
function responseBack() {
    var href = document.referrer;
    if (href.match('iliker.cn') == null) {
        window.location.href = edshopurl + "index.jsp";
    } else {
        history.go(-1);
    }
}

/*添加到购物车*/
function add2Cart(newCartItem, callback) {
    if (typeof(db) !== "undefined") {
        loadDB();
    }
    var transaction = db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.get(newCartItem.id);
    request.onsuccess = function (event) {
        var cartItem = event.target.result;
        if (cartItem == null) {
            var request = store.add(newCartItem);
            request.onsuccess = function (e) {
                callback(true);
            };
        } else {
            cartItem.color = newCartItem.color;
            cartItem.size = newCartItem.size;
            cartItem.itemCount += newCartItem.itemCount;
            var request = store.put(cartItem);
            request.onsuccess = function (e) {
                callback(true);
            }
        }
    };
}

//添加商品到购物项
function addCart_Item(goodsId, goodsName, price, imgpath, input, isaddCart) {
    var color = $("#colorAttr").val();
    var size = $("#sizeAttr").val();
    if (color == "" || size == "") {
        toast('请选中颜色与尺寸');
        return;
    }
    var count = parseInt($(input).val());
    var newCartItem = {
        id: goodsId,
        goodsName: goodsName,
        price: price,
        imgpath: imgpath,
        color: color,
        size: size,
        itemCount: count
    };
    add2Cart(newCartItem, function (result) {
        if (result) {
            if (isaddCart) {
                shortToast('已加入购物车');
                setTimeout(function () {
                    layer.closeAll();
                }, 500);
            } else {
                openwin('shopWrite');
            }
        }
    });
}

//增减购物项
function changeOrderCount(tag, operator) {
    var cid = jQuery(tag).parent().parent().children('input:last-child').val();
    var inputTag = jQuery(tag).parent().children('input');
    var numVal = parseInt(inputTag.val());
    var transaction = db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.get(parseInt(cid));
    request.onsuccess = function (event) {
        var cartItem = event.target.result;
        var itemCount = cartItem.itemCount;
        switch (operator) {
            case '+':
                itemCount++;
                break;
            case '-':
                if (numVal > 1) {
                    itemCount--;
                }
                break;
        }
        cartItem.itemCount = itemCount;
        store.put(cartItem);
        inputTag.val(itemCount);
        orderAccounting();
    };
}

//获取购物车所有商品数据
function queryCartAll() {
    var cartlist = jQuery("#cartlist");
    var transaction = db.transaction(["cart"], "readonly");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    var totalPrice = 0.00;
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res != null) {
            var goods = res.value;
            jQuery("#pay").show();
            var lis = '<li><div class="goods"><img src="/goodsimg/' + goods.imgpath + '" /><p class="name">' + goods.goodsName + '</p></div><div class="num"><div class="left numBtn" onclick="changeOrderCount(this,\'-\')">-</div><input class="left" readonly type="number" value="' + goods.itemCount + '" /><div class="left numBtn" onclick="changeOrderCount(this,\'+\')">+</div><span>￥' + goods.price + '</span></div><div class="del" onclick="delCart_item(this)"></div><input type="hidden" value="' + goods.id + '"/></li>';
            cartlist.append(lis);
            totalPrice += goods.itemCount * goods.price;
            res.continue();
        } else {
            jQuery("#total").html("￥" + totalPrice);
            if (jQuery("#cartlist").children().length == 0) {
                cartlist.append('<li><div class="goods"><span>空空如也</span></div></li>');
                jQuery("#pay").hide();
            }
        }
    };
}

function checkCart(goodsId, callback) {
    var transaction = db.transaction(["cart"], "readwrite");
    var store = transaction.objectStore("cart");
    var request = store.get(goodsId);
    request.onsuccess = function (event) {
        var cartItem = event.target.result;
        if (cartItem) {
            callback(true);

        } else {
            callback(false);
        }
    };
}

function confirmOrder() {
    var cartlist = jQuery("#cartlist");
    var money = 0.0;
    var orderamount = 0;
    var deliverFee = 0.0;
    $("#pay").show();
    var transaction = db.transaction(["cart"], "readonly");
    cartlist.append('<li><div></div><div><span>商品列表</span></div><div></div><div></div></li>');
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        var lis;
        if (res) {
            var goods = res.value;
            lis = '<li><div><img src="/goodsimg/' + goods.imgpath + '"></div><div><p>' + goods.goodsName + '</p><p><span>' + goods.color + '</span><span>' + goods.size + '</span></p></div><div>' + goods.price + '</div><div><strong>' + goods.itemCount + '</strong></div></li>';
            cartlist.append(lis);
            money += goods.price * goods.itemCount;
            orderamount += goods.itemCount;
            res.continue();
        } else {
            if (money < 150) {
                deliverFee = 10;
            }
        }
        $("#total").text(money + deliverFee);
        $("#goodsTotalPrice").text(money);
        $("#deliverFee").text(deliverFee);
        $("#orderamount").val(orderamount);
    };
}

function ask() {
    if (confirm('是否清空购物车？')) {
        delCartAll();
        window.location.reload();
    }
}

//清空购物车
function delCartAll() {
    var transaction = db.transaction(["cart"], "readwrite");
    var cart = transaction.objectStore("cart");
    cart.clear();
}

//用于模板内容输出到页面指定控件上。
function setHtml(id, html) {
    if ("string" == typeof(id)) {
        var ele = document.getElementById(id);
        if (ele != null) {
            ele.innerHTML = html == null ? "" : html;
        }
    } else if (id != null) {
        id.innerHTML = html == null ? "" : html;
    }
}

//获取配送信息购物车使用
function getaddlist1(uid, callback) {
    $.getJSON("loadDeliverInfo.do?uid=" + uid, function (result) {
        callback(result);
    });
}

//保存地址
function saveaddress() {
    var appUser = localStorage.getItem('appUser');
    var wxuser = localStorage.getItem('wxuser');
    var user = appUser ? $.parseJSON(appUser) : $.parseJSON(wxuser);
    var province = getValue('#province');
    var city = getValue('#city');
    var county = getValue('#county');
    var mobile = getValue('#mobile');
    var ADD = getValue('#address');
    var consignee = getValue('#consignee');
    if ('选择省份' == province || '选择城市' == city || '选择区域' == county || '' == $.trim(ADD) || '' == $.trim(consignee) || '' == $.trim(mobile)) {
        toast('收货信息不完整');
        return;
    }
    $.ajax({
        type: "POST",
        url: "editDeliverInfo.do",
        dataType: "json",
        data: {
            "shippingaddress.address": province + city + county + ADD,
            "shippingaddress.consigneeName": consignee,
            "shippingaddress.phone": mobile,
            "shippingaddress.userinfo.uid": user.uID
        },
        success: function (result) {
            if (result.success) {
                toast('添加成功');
                setTimeout(function () {
                    openwin(localStorage.callAddress)
                }, 2000)
            } else {
                toast('添加失败');
            }
        }
    });
}

//获取指定控件的内容
function getValue(id) {
    return $(id).val();
}

//删除地址
function deladdress() {
    var addid = localStorage.getItem('addid');
    var url = httpUrl + "address-3-" + addid;
    $.getJSON(url, function (data) {
            toast('提示', '地址删除成功!');
            setTimeout(openwins('addressList'), 5000);

        }, "json",//返回类型为json
        function (err) {
            if (err.status == '-1') str = '无网络，连接失败^_^！';
        },
        "GET",//以POST方式提交
        null,//提交的参数键值对对象
        false);//是否缓存数据
}

//提交购物车所有商品数据  ///donoing
function submitCartAll(callback) {
    var array = [];
    var index = 0;
    var result = '[';
    var transaction = db.transaction(["cart"], "readonly");
    var store = transaction.objectStore("cart");
    var cursor = store.openCursor();
    cursor.onsuccess = function (e) {
        var res = e.target.result;
        if (res != null) {
            var cartItem = res.value;
            array[index] = "{\"goods\":{\"id\":" + cartItem.id + "},\"productPrice\":" + cartItem.price + ",\"orderamount\":" + cartItem.itemCount + ",\"color\":\"" + cartItem.color + "\",\"size\":\"" + cartItem.size + "\"}";
            index++;
            res.continue();
        } else {
            result += array.toString() + ']';
            callback(result);
        }
    };
}

function submitOrder() {
    var orderContactInfo = localStorage.getItem('orderContactInfo');
    /*private Double goodsTotalPrice = 0.00;//商品总金额
     private Double deliverFee = 0.00;//配送费
     private Double toalprice;//订单总金额*/

    /* $("#total").html("￥" + (money + deliverFee));
     $("#goodsTotalPrice").html("￥" + money);
     $("#deliverFee").html("￥" + deliverFee);*/

    if (orderContactInfo == null) {
        toast('请确认收货地址');
        return;
    }
    var orderInfo = $.parseJSON(orderContactInfo);
    orderInfo.goodsTotalPrice = parseFloat($("#goodsTotalPrice").text());
    orderInfo.deliverFee = parseFloat($("#deliverFee").text());
    orderInfo.toalprice = parseFloat($("#total").text());
    orderInfo.orderamount = parseInt($("#orderamount").val());
    submitCartAll(function (result) {
        var array = $.parseJSON(result);
        if (array.length > 0) {
            orderInfo.orderdetails = array;
            jQuery.ajax({
                type: "POST",
                url: "addOrder.do",
                data: {"orderInfo": JSON.stringify(orderInfo)},
                dataType: "JSON",
                success: function (result) {
                    if ('SUCCESS' === result.result_code) {
                        delCartAll();
                        localStorage.setItem('orderId', result.data);
                        localStorage.setItem('totalPrice', result.totalPrice);
                        openwin('pay');
                    } else {
                        alert('系统有点小问题');
                    }
                }
            });
        }
    });
}

function orderDetail(orderid) {
    window.location.href = edshopurl + "orderDetail.jsp?id=" + orderid;
}

var time = 120;
var t;
var getsmscode = function () {
    var phone = jQuery("#phoneNum").val();
    if (!validatemobile()) {
        return;
    }

    jQuery.ajax({
        url: "regphone.do",
        dataType: 'JSON',
        data: {
            "phoneNum": phone
        },
        success: function (result) {
            if (result.success) {
                jQuery("#code").val(result.code);
                jQuery("#backPhone").val(result.backPhone);
                jQuery("#time").val(result.time);
                jQuery("#getSMSCodeBtn").unbind("click", getsmscode);
                t = setInterval(method, 1000);
            }
            if (typeof(layer) == "undefined") {
                alert(result.msg);
            } else {
                toast(result.msg);
            }
        }
    });

    var method = function () {
        time--;
        if ($("#getSMSCodeBtn").is('a')) {
            jQuery("#getSMSCodeBtn").text("" + time + "s");
        } else {
            jQuery("#getSMSCodeBtn").val("" + time + "s");
        }
        if (time == 0) {
            clearInterval(t);
            time = 120;
            if ($("#getSMSCodeBtn").is('a')) {
                jQuery("#getSMSCodeBtn").text("获取验证码");
            } else {
                jQuery("#getSMSCodeBtn").val("获取验证码");
            }
            jQuery("#getSMSCodeBtn").bind("click", getsmscode);
        }
    }
};

/**
 * @return {null}
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
    return null;
}

function validatemobile() {
    var mobile = jQuery("#phoneNum").val();
    if (!checkPhone(mobile)) {
        toast('是手机号吗?');
        return false;
    }
    registerBean.phoneNum = mobile;
    return true;
}

function regsmscode() {
    var smscode = jQuery("#smscode").val();
    if (smscode == "") {
        alert("请填写验证码");
        return false;
    }
    registerBean.smscode = smscode;
    return true;
}

function regNickname() {
    var nickname = jQuery("#nickname").val();
    if (nickname == "") {
        alert("请填写昵称");
        return false;
    }
    var re = /^[\u4e00-\u9fa5A-Za-z0-9]+$/;
    if (!re.test(nickname)) {
        alert("名称中含有特殊字符!");
        return false;
    }
    registerBean.nickname = nickname;
    return true;
}

function regPwd() {
    var pwd = jQuery("#password").val();
    if (pwd == "" || pwd.length < 6) {
        alert("请创建至少6位的密码");
        return false;
    }
    registerBean.password = encode64(pwd);
    return true;
}

function reguser() {
    var superiornum = localStorage.getItem('superiornum');
    registerBean.backPhone = $("#backPhone").val();
    registerBean.code = $("#code").val();
    registerBean.time = $("#time").val();
    if (validatemobile() && regsmscode() && regNickname() && regPwd()) {
        if (superiornum != null) {
            registerBean.superiornum = superiornum;
        }
        $.ajax({
            type: "POST",
            url: "webuserregister.do",
            dataType: 'JSON',
            data: {
                "registerBean": JSON.stringify(registerBean)
            },
            success: function (result) {
                toast(result.msg);
                if (result.success) {
                    setTimeout(function () {
                        openwin('login');
                    }, 2000);
                }
            }
        });
    }
}

/*验证邮箱*/
function checkEmail(str) {
    var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    return str != '' && re.test(str);
}

/*验证手机号*/
function checkPhone(str) {
    var myreg = /^(((13[0-9])|(15[0-9])|17[0135678]|(18[0-9]))+\d{8})$/;
    return $.trim(str).length === 11 && myreg.test(str);
}

/*校验中文*/
function ischinese(str) {
    var re = /[^u4e00-u9fa5]/;
    return str != '' && re.test(str);
}

function loadClassify() {
    jQuery.getJSON("loadClassify.do", function (result) {
        if (result.success) {
            jQuery.each(result.data, function (i, item) {
                jQuery("#sort_ul").append('<li onclick=localStorage.crowdId="' + item.id + '";openwin(\'classify2\')>' + item.name + '</li>');
            });
        }
    });
}

function loadClothestypes(crowdId) {
    jQuery.getJSON("loadClothestypes.do", {"crowdid": crowdId}, function (result) {
        if (result.success) {
            jQuery.each(result.data, function (i, item) {
                jQuery("#sort_ul").append('<li onclick=localStorage.typeId="' + item.id + '";openwin(\'productList\')>' + item.name + '</li>');
            });
        }
    });
}

changeTwoDecimal_f = function (floatvar) {
    var f_x = parseFloat(floatvar);
    if (isNaN(f_x)) {
        alert('function:changeTwoDecimal->parameter error');
        return false;
    }
    var f_x = Math.round(f_x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}