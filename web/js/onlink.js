function logOut() {
    var isexit = window.confirm("你确定要退出系统吗？");
    if (isexit) {
        window.location = "logout.do";
    }
}

var input = $('<input id="search_value" type="text" placeholder="请输入商品编号"/>');
var newSelect = $('<select id="search_value" style="width:100px;"></select>');
$('<option selected="selected" value="0">--请选择--</option>').appendTo(newSelect);
$(function () {
    $('#search_where').after(newSelect);
});
var isRemove = false;

function change_property(select) {
    newSelect.empty();
    $('<option selected="selected" value="0">--请选择--</option>').appendTo(newSelect);
    var url = $(select).val();
    switch (url) {
        case 0:
            break;
        case 'goods.brand.brandId':
            if (isRemove) {
                $(input).remove();
                $(select).after(newSelect);
            }
            $.ajax({
                url: "queryBrand.do",
                dataType: "JSON",
                success: function (result) {
                    parsBrand(newSelect, result);
                }
            });
            break;
        case 'goods.clothestype.id':
            if (isRemove) {
                $(input).remove();
                $(select).after(newSelect);
            }
            $.ajax({
                url: "queryCrowd.do",
                dataType: "JSON",
                success: function (result) {
                    parsCrowd(newSelect, result);
                }
            });
            break;
        case 'goods.price':
            if (isRemove) {
                $(input).remove();
                $(select).after(newSelect);
            }
            $('<option value="0.1-50">0.1-50</option>').appendTo(newSelect);
            $('<option value="50-100">50-100</option>').appendTo(newSelect);
            $('<option value="100-200">100-200</option>').appendTo(newSelect);
            $('<option value="200-500">200-500</option>').appendTo(newSelect);
            $('<option value="500-1000">500-1000</option>').appendTo(newSelect);
            $('<option value="1000-2000">1000-2000</option>').appendTo(newSelect);
            break;
        case 'goods.goodCode':
            isRemove = true;
            $(newSelect).remove();
            $(select).after(input);
            break;
    }
}

/*添加颜色*/
function addStock_item(button) {
    var inputTag = $('.color-attr');
    var frestColor = $(inputTag[0]).val();
    if ($.trim(frestColor).length === 0) {
        alert('输入颜色');
        return;
    }
    var data = prompt("输入颜色", "");
    if ($.trim(data).length > 0) {
        var existerAt = false;
        $.each(inputTag, function (i, item) {
            var atColor = $(item).val();
            if ($.trim(atColor) === $.trim(data)) {
                existerAt = true;
                return;
            }
        });
        if (!existerAt) {
            var index_ = $('.stocks > li').length - 1;
            var newRow = $('<li class="stock_li"><p>颜色:<input class="color-attr" value="' + data + '" readonly="readonly" type="text" placeholder="颜色" name="goods.stockInfoSet.makeNew[' + index_ + '].color" size="5" maxlength="5">样式:<input type="file" name="stockImg" onchange="PreviewImage(this)"/></p><ul class="stockItem"><li>规格:<input class="size-attr" placeholder="尺寸" type="text" name="goods.stockInfoSet.makeNew[' + index_ + '].stockItems.makeNew[0].size" size="5" maxlength="5">库存:<input class="stock-count-attr" type="number" min="0" name="goods.stockInfoSet.makeNew[' + index_ + '].stockItems.makeNew[0].stockCount" size="5" maxlength="5"></li></ul><input onclick="addStock_cell(this)" type="button" value="添加规格"/><input type="button" value="删除颜色" onclick="delStock_item(this)"></li>');
            $(button).parent().before(newRow);
        } else {
            alert('你输入了相同的颜色');
        }
    }
}

/*添加尺寸库存*/
function addStock_cell(button) {
    var cells = $(button).parent().find('.stockItem > li');
    var index_ = $(button).parents('.stock_li').index();
    var newCell = $('<li>规格:<input class="size-attr" placeholder="尺寸" type="text" name="goods.stockInfoSet.makeNew[' + index_ + '].stockItems.makeNew[' + cells.length + '].size" size="5" maxlength="5">库存:<input class="stock-count-attr" type="number" min="0" name="goods.stockInfoSet.makeNew[' + index_ + '].stockItems.makeNew[' + cells.length + '].stockCount" size="5" maxlength="5"><input type="button" value="删除规格" onclick="delStock_cell(this)"></li>');
    $(button).parents('.stock_li').find('.stockItem').append(newCell);
}

/*移除尺寸库存*/
function delStock_cell(button) {
    var index_ = $(button).parents('.stock_li').index();
    $(button).parent().remove();
    var lii = $('.stocks > li')[index_];
    var cells = $(lii).find('.stockItem > li');
    for (var i = 1; i < cells.length; i++) {
        $(cells[i]).find('.size-attr').attr('name', 'goods.stockInfoSet.makeNew[' + index_ + '].stockItems.makeNew[' + i + '].size');
        $(cells[i]).find('.stock-count-attr').attr('name', 'goods.stockInfoSet.makeNew[' + index_ + '].stockItems.makeNew[' + i + '].stockCount');
    }
}

/*移除颜色*/
function delStock_item(button) {
    $(button).parents('.stock_li').remove();
    var lis = $('.stocks > li');
    for (var i = 1; i < lis.length - 1; i++) {
        var stocks = lis[i];
        var cells = $(stocks).find('.stockItem > li');
        $(stocks).find('.color-attr').attr('name', 'stockInfoSet.makeNew[' + i + '].color');
        $.each(cells, function (j, item) {
            $(item).find('.size-attr').attr('name', 'stockInfoSet.makeNew[' + i + '].stockItems.makeNew[' + j + '].size');
            $(item).find('.stock-count-attr').attr('name', 'stockInfoSet.makeNew[' + i + '].stockItems.makeNew[' + j + '].stockCount');
        });
    }
}

/*解析品牌*/
function parsBrand(select, result) {
    $.each(result.brands, function (i, item) {
        $('<option value="' + item.brandId + '">' + item.brandName + '</option>').appendTo(select);
    });
}

/*解析类别*/
function parsCrowd(select, result) {
    $.each(result, function (i, item) {
        var optgroup = $('<optgroup label="' + item.crowdName + '">');
        $.each(item.data, function (j, _item) {
            $('<option value="' + _item.id + '">' + _item.name + '</option>').appendTo(optgroup);
        });
        select.append(optgroup);
    });
}

function selectVal2(crowdid) {
    var typeselect = $("#type");
    typeselect.find("option").remove();
    $.ajax({
        url: "querytype.do",
        data: {
            "crowdid": crowdid
        },
        dataType: "JSON",
        success: function (result) {
            var data = $.parseJSON(result);
            $.each(data, function (i, item) {
                typeselect.append("<option value='" + item.id + "'>"
                    + item.name + "</option>");

            });
        }
    });
}

function regcheck() {
    var uname = $("#uname").val();
    var pwd = $("#pwd").val();
    var imgcode = $("#imgcode").val();
    $("#span1").text("");
    $("#span2").text("");
    if (uname.length == 0) {
        $("#span1").text("用户名不能为空！");
        return false;
    }
    if (pwd.length == 0) {
        $("#span2").text("密码不能为空！");
        return false;
    }
    if (imgcode.length == 0) {
        $("#span1").text("校验码不能为空！");
        return false;
    }
    return true;
}

function findAtTime(val) {
    window.location = "action_showShare.do?node=" + val;
}

function changeAll() { // 定义函数checked
    var fullcbo = document.getElementById("allChecked");
    var allcbo = document.getElementsByName("checkboxs");
    var checktext = document.getElementById("checktext");

    checktext.innerHTML = '';
    if (fullcbo.checked == true) {
        checktext.innerHTML = '反选';
        for (var i = 0; i < allcbo.length; i++) {
            allcbo[i].checked = true;
        }
    } else {
        checktext.innerHTML = '全选';
        for (var i = 0; i < allcbo.length; i++) {
            allcbo[i].checked = false;
        }
    }
}

function delShare(shareId) {
    var ischecked = true;
    var allcbo = $("input[name='checkboxs']");
    for (var i = 0; i < allcbo.length; i++) {
        if (allcbo[i].checked == true) {
            ischecked = false;
            var b = window.confirm("这条记录下的所有评论也会删除！");
            if (b) {
                window.location = "delshare.do?shareid=" + shareId;
            }
        }
    }

    if (ischecked) {
        alert("请勾选后操作");
    }
}

// 校验商品编号输入是否只由字母数字下划线组成
function regGoodCode() {
    if ($("#label1").text() != "") {
        return false;
    }
    $("#label1").text('');
    var goodCode = $("#goodCode").val();
    var regu = "^[0-9a-zA-Z\_\-]+$";
    var re = new RegExp(regu);
    if (re.test(goodCode)) {
        return true;
    }
    $("#label1").append("字母数字组合");
    return false;

}

// 校验商品名称输入是否只由字母数字汉字组成
function regGoodName() {
    $("#label2").text('');
    var goodName = $("#goodName").val();
    var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
    var re = new RegExp(regu);
    if (re.test(goodName)) {
        return true;
    }
    $("#label2").append("不能有空格");
    return false;

}

// 校验商品描述输入是否只由字母数字汉字组成
function regGoodDesc() {
    $("#label4").text('');
    var goodName = $("#goodDesc").val();
    /*var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
     var re = new RegExp(regu);*/
    if (goodName.trim() == "") {
        $("#label4").append("商品描述不能为空");
        return false;
    }
    return true;
}

// 校验商品类别无选择
function regType() {
    $("#label3").text('');
    var persontype = $("#persontype").val();
    if (persontype == -1) {
        return false;
    }
    var persontype = $("#type").val();
    if (persontype == -1) {
        $("#label3").append("不能为空");
        return false;
    }
    return true;
}

// 校验商品价格输入是否只由正小数或正整数组成
function ckProPrice() {
    $("#label5").text('');
    // 判断商品价格
    var goodPrice = $("#goodPrice").val();
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if (goodPrice == "") {
        $("#label5").append("不能为空");
        return false;
    } else {
        if (!reg.test(goodPrice)) {
            $("#label5").append("输入正整数或正小数,小数点2位");
            return false;
        } else {
            return true;
        }
    }
}

// 校验商品图片无选择
function regImg() {
    if ($.trim($('#goodimg0').val()).length === 0) {
        alert("请选择封面图");
        return false;
    }
    return true;
}

/*function regColors() {
 var ischecked = $("[name = colorArray]:checkbox").is(':checked');
 var colors = $('#colors').val();
 if (ischecked || (colors != null && colors.length > 0)) {
 return true;
 }
 alert('请输入颜色');
 return false;
 }*/

/*function regSizes() {
 var ischecked = $("[name = sizeArray]:checkbox").is(':checked');
 var sizes = $('#sizes').val();
 if (ischecked || (sizes != null && sizes.length > 0)) {
 return true;
 }
 alert('请输入尺寸');
 return false;
 }*/

function regLaunch() {
    return regBrand() && regGoodCode() && regGoodName() && regType() && regGoodDesc() && ckProPrice() && regImg();
}

/*
 function regStock() {
 var stock = $('#stock').val();
 if (stock.length == 0) {
 alert('选择输入库存');
 return false;
 }
 if (stock != parseInt(stock)) {
 alert('库存数量为整数');
 return false;
 }

 return true;
 }*/

function regBrand() {
    if ($('#brand').val() == 0) {
        alert('选择品牌');
        return false;
    }
    return true;
}

function exists(val) {
    if (val != "") {
        $("#label1").text('');
        $.ajax({
            url: "regGoodCode.do",
            data: {
                "goodCode": val
            },
            dataType: 'text',
            success: function (result) {
                if (result != null)
                    $("#label1").text(result);
            }
        });
    }
}

/* 按页码跳转 */
function regVal(totalSize) {
    var reg = /^[1-9]\d*$/;
    var sendVal = $("#sendVal").val();
    if (!reg.test(sendVal)) {
        alert('请输入正整数');
        return;
    }
    if (sendVal > totalSize) {
        alert('没有这么多页码');
        return;
    }
    window.location = "action_showShare.do?offset=" + sendVal;
}

/* 按页码跳转 */
function regVal2(totalSize) {
    var reg = /^[1-9]\d*$/;
    var sendVal = $("#sendVal").val();

    if (!reg.test(sendVal)) {
        alert('请输入正整数');
        return;
    }
    if (sendVal > totalSize) {
        alert('没有这么多页码');
        return;
    }
    window.location = "showGoods.do?offset=" + sendVal;
}

function delGood(goodid) {
    var asOk = window.confirm("请谨慎操作！是否删除？");
    if (asOk) {
        window.location = "delGoods.do?goodid=" + goodid;
    }
}

/*校验搜索商品*/
function reg() {
    var goodCode = $("#goodCode").val();
    var goodPrice = $("#goodPrice").val();
    var regu = "^[0-9a-zA-Z\_]+$";
    var re = new RegExp(regu);
    if (goodCode != "" && !re.test(goodCode)) {
        return false;
    }
    // 判断商品价格
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if (goodPrice != "" && !reg.test(goodPrice)) {
        return false;
    }
    return true;
}

// 推送对象
var bool = true;
var bool2 = true;

function getpushObject(val) {
    if (!bool) {
        bool = true;
        $("#pushobject").children("select:last-child").remove();
    }
    if (!bool2) {
        bool2 = true;
        $("#pushobject").children("input").remove();
    }
    if (val == "city") {
        bool = false;
        var city;
        city = ["湖南", "广东", "河北", "安徽"];// 测试用，正常环境从数据库取标签
        var select = document.createElement("select");
        select.setAttribute("id", "cityName");
        select.setAttribute("name", "cityName");
        for (var i in city) {
            select.options[i] = new Option(city[i], city[i]);
        }
        $("#pushToWho").after(select);
    } else {
        if (val == "person") {
            bool2 = false;
            var personinput = document.createElement("input");
            personinput.setAttribute("id", "channelId");
            personinput.setAttribute("name", "channelId");
            $("#pushToWho").after(personinput);
        }
    }
}

function regpush() {
    if ($("#cityName").val() == "") {
        alert("组不能为空！");
        return false;
    }
    if ($("#channelId").val() == "") {
        alert("设备通讯号不能为空！");
        return false;
    }
    if ($("#title").val() == "") {
        alert("标题不能为空！");
        return false;
    }
    if ($("#title").val() == "") {
        alert("标题不能为空！");
        return false;
    }
    return true;
}

var val;

function listenerChange() {
    val = $("#propertyName").val();
    var input = $("#queryVal");
    switch (val) {
        case "tradeNo":
        case "orderid":
            input.attr("disabled", false);
            break;
        case "all":
        case "waitpayment":
        case "waitconfirm":
        case "waitdeliver":
        case "cancel":
        case "delivered":
        case "received":
            input.val("");
            input.attr("disabled", true);
            break;
    }
}

function regcheckinput() {
    if (val == "trade_no" || val == "orderid") {
        var inputval = $("#queryVal").val();
        if (inputval == "") {
            alert("请输入查询编号");
            return false;
        }
        var regu = "^[0-9a-zA-Z]+$";
        var re = new RegExp(regu);
        if (!re.test(inputval)) {
            alert("不支持中文");
            return false;
        }
    }
    return true;
}

function regAccount() {
    var method = $("#method").val();
    var uname = $("#uname").val().trim();
    var nameSpan = $("#nameSpan");
    var pwdSpan = $("#pwdSpan");
    $(nameSpan).text();
    $(pwdSpan).text();
    var pwd = $("#password").val().trim();
    var pwd1 = $("#regpassword").val().trim();
    if (uname === "" || uname.length === 0) {
        $(nameSpan).text('账户名不能为空');
        return false;
    }
    if ("add" === method) {
        if (pwd === "" || pwd.length < 6) {
            $(pwdSpan).text('密码不能小于6位');
            return false;
        }
        if (pwd1 !== pwd) {
            $(pwdSpan).text('密码不一致');
            return false;
        }
        $("#password").val(encode64(pwd));
        $("#regpassword").val(encode64(pwd1));
    }
    if ("edit" === method) {
        if (pwd !== "" || pwd1 !== "") {
            if (pwd.length < 6 || pwd1.length < 6) {
                $(pwdSpan).text('密码长度不能小于6位');
                return false;
            }
            if (pwd1 !== pwd) {
                $(pwdSpan).text('密码不一致');
                return false;
            }
            $("#password").val(encode64(pwd));
            $("#regpassword").val(encode64(pwd1));
        }
    }
    return true;
}

function PreviewImage(imgFile) {
    var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/i;
    if (!pattern.test(imgFile.value)) {
        alert("系统仅支持jpg/jpeg/png/gif/bmp格式的照片！");
        imgFile.value = "";
        imgFile.focus();
    }
    else {
        var filename = imgFile.value;
        filename = filename.substring(filename.lastIndexOf('\\') + 1);
        var firstnode = $(imgFile).parent("td").find("input").eq(0);
        var path;
        if (document.all)//IE
        {
            imgFile.select();
            path = document.selection.createRange().text;
            document.getElementById("imgPreview").innerHTML = "";
            document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + path + "\")";//使用滤镜效果
        }
        else//FF
        {
            path = URL.createObjectURL(imgFile.files[0]);
            $(imgFile).attr('src', path);
        }
        firstnode.val(filename);
    }
}

function delimg(itemname) {
    var count = $("input[name='markname']").length;
    if (count > 1) {
        var i = window.confirm("你要删除吗?");
        $(itemname).parent("td").parent("tr");
        if (i)
            $(itemname).parent("td").parent("tr").remove();
    } else {
        alert("再删就没有了,可以修改");
    }
}


function regUpdate() {
    return regGoodName() && regGoodDesc() && regType() && ckProPrice();
}

function themeRegInput() {
    var themeName = $("#themeName").val();
    var introduction = $("#introduction").val();
    var themeURL = $("#themeURL").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    if (themeName == "") {
        alert("活动标题不能为空");
        return false;
    }
    if (introduction == "") {
        alert("活动简介不能为空");
        return false;
    }
    if (themeURL == "") {
        alert("活动彩页不能为空");
        return false;
    }
    if (startTime == "" || endTime == "") {
        alert("开始结束日期不能为空");
        return false;
    }
    if (!checkEndTime(startTime, endTime)) {
        alert("结束日期不能小于开始时间");
        return false;
    }
    return true;
}

function checkEndTime(startTime, endTime) {
    var start = new Date(startTime.replace("-", "/").replace("-", "/"));
    var end = new Date(endTime.replace("-", "/").replace("-", "/"));
    return end > start;
}