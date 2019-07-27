package cn.iliker.mall.control;

import static cn.iliker.mall.utils.AliPayUtil.readBuffer;
import static cn.iliker.mall.utils.DateParseUtils.data2String;
import static cn.iliker.mall.utils.NetSteamUtil.paseResult;
import static cn.iliker.mall.utils.NetSteamUtil.validateReg;
import static cn.iliker.mall.utils.wxpay.XMLUtil.toXML;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.config.WXPlatformConf;
import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.JSWXToken;
import cn.iliker.mall.entity.RegisterBean;
import cn.iliker.mall.entity.Shippingaddress;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.Userinfo;
import cn.iliker.mall.entity.WXTicket;
import cn.iliker.mall.entity.WXToken;
import cn.iliker.mall.entity.WXUser;
import cn.iliker.mall.entity.Wallet;
import cn.iliker.mall.entity.wxinfo.Result;
import cn.iliker.mall.service.IDeliverInfoSvc;
import cn.iliker.mall.service.ITOrderSvc;
import cn.iliker.mall.service.IUserinfoSvc;
import cn.iliker.mall.service.IWXPaperService;
import cn.iliker.mall.service.IWXUserService;
import cn.iliker.mall.utils.MD5Util;
import cn.iliker.mall.utils.NetSteamUtil;
import cn.iliker.mall.utils.UrlTools;
import cn.iliker.mall.utils.wxpay.ConstantUtil;
import cn.iliker.mall.utils.wxpay.XMLUtil;

//微信公众平台与h5相关
public class WXServiceAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private IUserinfoSvc userinfoSvc;
    private IDeliverInfoSvc deliverInfoSvc;
    private IWXPaperService wxTokenService;
    private ITOrderSvc tordersvc;
    private URL menuUrl;

    private String signature;//微信服务端加密字符串
    private String timestamp;
    private String nonce;//随机字符串
    private String echostr;
    private String token = "weixin";//本地令牌
    private SortedMap<String, String> parameters = new TreeMap<>();
    private String openid;
    private String registerBean;
    private int uid;
    private String unionID = "";
    private IWXUserService iwxUserService;
    private Map<String, Object> wxMsg = new HashMap<>();
    private double latitude;
    private double longitude;
    private String gid;
    private CloseableHttpClient client = HttpClients.createDefault();
    private String shareContent;
    private Shippingaddress shippingaddress;

    public String getGid() {
        return gid;
    }

    private int requestCode;

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public Shippingaddress getShippingaddress() {
        return shippingaddress;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public void setShippingaddress(Shippingaddress shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    public void setTordersvc(ITOrderSvc tordersvc) {
        this.tordersvc = tordersvc;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public void setRegisterBean(String registerBean) {
        this.registerBean = registerBean;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setMenuUrl(URL menuUrl) {
        this.menuUrl = menuUrl;
    }

    public void setIwxUserService(IWXUserService iwxUserService) {
        this.iwxUserService = iwxUserService;
    }

    public void setWxTokenService(IWXPaperService wxTokenService) {
        this.wxTokenService = wxTokenService;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setUserinfoSvc(IUserinfoSvc userinfoSvc) {
        this.userinfoSvc = userinfoSvc;
    }

    public void setDeliverInfoSvc(IDeliverInfoSvc deliverInfoSvc) {
        this.deliverInfoSvc = deliverInfoSvc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    private String getAppid() {
        if ("app".equals(device)) {
            return ConstantUtil.APP_ID;
        }
        return WXPlatformConf.APPID;
    }

    private String getSecret() {
        if ("app".equals(device)) {
            return ConstantUtil.APP_SECRET;
        }
        return WXPlatformConf.SECRET;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

    public String weChatSign() throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        if (echostr == null) {
            StringBuilder inputString = readBuffer(request);
            if (inputString.length() > 0) {
                try {
                    parameters = XMLUtil.doXMLParse(inputString.toString());
                    String MsgType = parameters.get("MsgType");
                    switch (MsgType) {
                        case "text":
                            //文本消息
                            wxMsg.put("ToUserName", parameters.get("FromUserName"));
                            wxMsg.put("FromUserName", parameters.get("ToUserName"));
                            wxMsg.put("CreateTime", System.currentTimeMillis());
                            wxMsg.put("MsgType", "transfer_customer_service");
                            wxMsg.put("Content", parameters.get("Content"));
                            writer.print(toXML(wxMsg));
                            writer.flush();
                            writer.close();
                            wxMsg.clear();
                            break;
                        case "image":
                            //图片消息
                            break;
                        case "voice":
                            //语音消息
                            break;
                        case "video":
                            //视频消息
                            break;
                        case "shortvideo":
                            //小视频消息
                            break;
                        case "location":
                            //地理位置消息
                            break;
                        case "link":
                            //链接消息
                            break;
                        case "event":
                            //事件推送
                            String Event = parameters.get("Event");
                            switch (Event) {
                                case "subscribe":
                                    //1. 用户未关注时，进行关注后的事件推送
                                    String eventKey = parameters.get("EventKey");
                                    String superiornum = cutOutSuperiorNum(eventKey);
                                    openid = parameters.get("FromUserName");
                                    JSWXToken token = wxTokenService.getJSWXTokenByOpenId(openid);
                                    if (token == null) {
                                        token = new JSWXToken(openid, superiornum);
                                        wxTokenService.saveJSWXToken(token);
                                    }
                                    String kk = "<xml>" +
                                            "<ToUserName><![CDATA[" + parameters.get("FromUserName") + "]]></ToUserName>" +
                                            "<FromUserName><![CDATA[" + parameters.get("ToUserName") + "]]></FromUserName>" +
                                            "<CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
                                            "<MsgType><![CDATA[news]]></MsgType>" +
                                            "<ArticleCount>1</ArticleCount>" +
                                            "<Articles>" +
                                            "<item>" +
                                            "<Title><![CDATA[终于等到您！]]></Title>" +
                                            "<Description><![CDATA[意想不到的惊喜等着你！]]></Description>" +
                                            "<PicUrl><![CDATA[http://iliker.cn/share/weixi.jpg]]></PicUrl>" +
                                            "<Url><![CDATA[http://zfl.blt-dt.com/index.php?g=Wap&m=Guo&a=index&do=false&token=jydljj1492679394&bookname=ocfRbwXqyvP-Eus4C5G0rRE0Ryqswqlcms1493359780]]></Url>" +
                                            "</item>" +
                                            "</Articles>" +
                                            "</xml> ";
                                    writer.print(kk);
                                    writer.flush();
                                    writer.close();
                                    wxMsg.clear();
                                    break;
                                case "SCAN":
                                    //2. 用户已关注时的事件推送
                                    break;
                                case "LOCATION":
                                    //上报地理位置事件
                                    setLatitude(Double.parseDouble(parameters.get("Latitude")));
                                    setLongitude(Double.parseDouble(parameters.get("Longitude")));
                                    break;
                                case "CLICK":
                                    //点击菜单拉取消息时的事件推送

                                    break;
                                case "VIEW":
                                    //点击菜单跳转链接时的事件推送
                                    /*openid = parameters.get("FromUserName");
                                    WXUser wxUser = iwxUserService.getWXUser(openid);
                                    if (wxUser == null) {
                                        wxUser = new WXUser(openid);
                                        iwxUserService.saveWXUser(wxUser);
                                    }*/
                                    break;
                                case "scancode_waitmsg":
                                    String scanresult = parameters.get("ScanResult");
                                    try {
                                        JSONObject jsonObject = JSON.parseObject(scanresult);
                                        if (jsonObject.containsKey("ilikerAppOrderID")) {
                                            int orderID = jsonObject.getIntValue("ilikerAppOrderID");
                                            TOrder orderinfo = tordersvc.findById(orderID);
                                            if (orderinfo != null) {
                                                String content = "<xml>" +
                                                        "<ToUserName><![CDATA[" + parameters.get("FromUserName") + "]]></ToUserName>" +
                                                        "<FromUserName><![CDATA[" + parameters.get("ToUserName") + "]]></FromUserName>" +
                                                        "<CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
                                                        "<MsgType><![CDATA[news]]></MsgType>" +
                                                        "<ArticleCount>1</ArticleCount>" +
                                                        "<Articles>" +
                                                        "<item>" +
                                                        "<Title><![CDATA[订单信息]]></Title>" +
                                                        "<Description><![CDATA[订单状态:" + orderinfo.getOrderstate() + ";订单总金:" + orderinfo.getToalprice() + "]]></Description>" +
                                                        "<PicUrl><![CDATA[http://iliker.cn/share/weixi.jpg]]></PicUrl>" +
                                                        "<Url><![CDATA[https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8cb131acdcd8b85d&redirect_uri=http://iliker.cn/wx/orderDetail.jsp?id=" + orderID + "&response_type=code&scope=snsapi_userinfo#wechat_redirect]]></Url>" +
                                                        "</item>" +
                                                        "</Articles>" +
                                                        "</xml> ";
                                                writer.print(content);
                                                writer.flush();
                                                writer.close();
                                            } else {
                                                wxMsg.put("ToUserName", parameters.get("FromUserName"));
                                                wxMsg.put("FromUserName", parameters.get("ToUserName"));
                                                wxMsg.put("CreateTime", System.currentTimeMillis());
                                                wxMsg.put("MsgType", "text");
                                                wxMsg.put("Content", "查询不到对应的订单");
                                                writer.print(toXML(wxMsg));
                                                writer.flush();
                                                writer.close();
                                            }
                                        }
                                    } catch (Exception e) {
                                        wxMsg.put("ToUserName", parameters.get("FromUserName"));
                                        wxMsg.put("FromUserName", parameters.get("ToUserName"));
                                        wxMsg.put("CreateTime", System.currentTimeMillis());
                                        wxMsg.put("MsgType", "text");
                                        wxMsg.put("Content", "目前只接受自提订单扫描");
                                        writer.print(toXML(wxMsg));
                                        writer.flush();
                                        writer.close();
                                    }
                                    break;
                            }
                            break;
                    }
                    return NONE;
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
            writer.print("error");
            writer.flush();
            writer.close();
            return NONE;
        } else {
            //接入端口验证
            List<String> list = new ArrayList<>();
            list.add(token);
            list.add(timestamp);
            list.add(nonce);
            Collections.sort(list);
            String signStr = "";
            for (String str : list) {
                signStr += str;
            }
            if (signature.equals(SHA1(signStr))) {
                writer.print(echostr);
                writer.flush();
                writer.close();
            }
            writer.print("error");
            writer.flush();
            writer.close();
            return NONE;
        }
    }

    private String cutOutSuperiorNum(String eventKey) {
        if (!"".equals(eventKey)) {
            return eventKey.split("_")[1];
        }
        return null;
    }

    private static String SHA1(String decript) {
        if (decript != null && !decript.isEmpty()) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                digest.update(decript.getBytes());
                byte messageDigest[] = digest.digest();
                // Create Hex String
                StringBuilder hexString = new StringBuilder();
                // 字节数组转换为 十六进制 数
                for (byte aMessageDigest : messageDigest) {
                    String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                    if (shaHex.length() < 2) {
                        hexString.append(0);
                    }
                    hexString.append(shaHex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String logtext = "";
    private String password = "";
    private String device = "";//默认登陆设备 app,h5


    public void setDevice(String device) {
        this.device = device;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogtext(String logtext) {
        this.logtext = logtext;
    }

    public String userLogin() throws IOException {
        msg.clear();
        if (password == null || "".equals(password)) {
            msg.put("success", false);
            return ERROR;
        }
        String loginType = "phone";//默认以电话号码登陆
        if (!NetSteamUtil.isMobileNO(logtext)) {
            loginType = "nickname";
        }
        if (("h5").equals(device)) {
            password = MD5Util.getMD5Str(new String(MD5Util.decode(password)));
        }
        Userinfo userinfo = userinfoSvc.login(loginType, logtext, password);
        if (userinfo != null && userinfo.getEnable()) {
            JSONObject userJson = parsJsonObject(userinfo);
            WXUser wxUser = iwxUserService.getWXUserByUID(userinfo.getUid());
            if (wxUser != null) {
                userJson.put("onbind", true);
            }
            msg.put("userInfo", userJson);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String faceLogin() {
        msg.clear();
        if (gid != null) {
            Userinfo userinfo = userinfoSvc.faceLogin(gid);
            if (userinfo != null) {
                JSONObject userJson = parsJsonObject(userinfo);
                msg.put("userInfo", userJson);
                msg.put("success", true);
                return SUCCESS;
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    private JSONObject parsJsonObject(Userinfo userinfo) {
        JSONObject userJson = new JSONObject();
        userJson.put("uID", userinfo.getUid());
        userJson.put("nickName", userinfo.getNickname());
        userJson.put("password", userinfo.getPassword());
        userJson.put("email", userinfo.getEmail());
        userJson.put("phone", userinfo.getPhone());
        userJson.put("sex", userinfo.getSex());
        userJson.put("headImg", userinfo.getHeadimg());
        userJson.put("superiornum", userinfo.getParent() == null ? "" : userinfo.getParent().getPhone());
        userJson.put("signature", userinfo.getSignature());
        userJson.put("birthday", userinfo.getBirthday());
        userJson.put("registered", data2String(userinfo.getRegistered()));
        userJson.put("level", userinfo.getLevel());
        userJson.put("gid", userinfo.getGid());
        userJson.put("guestNO", userinfo.getGuestNO());
        return userJson;
    }

    public String wxAccess() {
        return SUCCESS;
    }

    public String loadDeliverInfos() {
        msg.clear();
        List<Shippingaddress> list = deliverInfoSvc.findByProperty("userinfo.uid", uid);
        if (!list.isEmpty()) {
            JSONArray jsonArray = new JSONArray();
            for (Shippingaddress address : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("consigneeName", address.getConsigneeName());
                jsonObject.put("phone", address.getPhone());
                jsonObject.put("address", address.getAddress());
                jsonObject.put("id", address.getId());
                jsonArray.add(jsonObject);
            }
            msg.put("data", jsonArray);
            if (requestCode > 0) {
                msg.put("requestCode", requestCode);
                msg.put("result_code", "SUCCESS");
            }
            return SUCCESS;
        }
        return NONE;
    }

    //封装json数据
    private JSONObject msg = new JSONObject();

    public JSONObject getMsg() {
        return msg;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setParameters(SortedMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public String editDeliverInfo() {
        msg.clear();
        if (shippingaddress != null) {
            try {
                deliverInfoSvc.attachDirty(shippingaddress);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String wxJsSDKInit() {
        msg.clear();
        String jsapi_ticket = getJsapi_ticket();
        if (jsapi_ticket != null) {
            SecureRandom random = new SecureRandom();
            StringBuilder builder = new StringBuilder();
            String noncestr = new BigInteger(32, random).toString(8);
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            builder.append("jsapi_ticket=").append(jsapi_ticket)
                    .append("&noncestr=").append(noncestr)
                    .append("&timestamp=").append(timestamp)
                    .append("&url=").append(url);
            String signature = SHA1(builder.toString());
            msg.put("noncestr", noncestr);
            msg.put("timestamp", timestamp);
            msg.put("appid", getAppid());
            msg.put("signature", signature);
            return SUCCESS;
        }
        return NONE;
    }

    /*创建自定义菜单*/
    public String createMenu() throws IOException {
        msg.clear();
        if (menuUrl != null) {
            String token = getToken();
            HttpPost method = new HttpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token);
            FileEntity fileEntity = new FileEntity(new File(menuUrl.getPath()));
            method.setEntity(fileEntity);
            try {
                CloseableHttpResponse response = client.execute(method);
                if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                    Result result = paseResult(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                    msg.put("errcode", result.getErrcode());
                    msg.put("errmsg", result.getErrmsg());
                    return SUCCESS;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                client.close();
            }
        }
        return NONE;
    }

    /*删除自定义菜单*/
    public String delMenu() throws IOException {
        msg.clear();
        String token = getToken();
        HttpGet method = new HttpGet("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token);
        try {
            CloseableHttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                Result result = paseResult(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                msg.put("errcode", result.getErrcode());
                msg.put("errmsg", result.getErrmsg());
                return SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        return NONE;
    }

    private String getToken() {
        WXToken token = wxTokenService.getToken(1);
        long newTime = System.currentTimeMillis() / 1000;
        long oldTime = token.getCreate_date().getTime() / 1000;
        if ((newTime - oldTime) >= token.getExpires_in()) {
            HttpGet method = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + getAppid() + "&secret=" + getSecret() + "");
            try {
                CloseableHttpResponse response = client.execute(method);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                    if (jsonObject.containsKey("access_token")) {
                        token.setAccess_token(jsonObject.getString("access_token"));
                        token.setExpires_in(jsonObject.getInteger("expires_in"));
                        token.setCreate_date(new Date());
                        wxTokenService.updateToken(token);
                        return token.getAccess_token();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token.getAccess_token();
    }

    private String getJsapi_ticket() {
        WXTicket ticket = wxTokenService.getTicket(1);
        long newTime = System.currentTimeMillis() / 1000;
        long oldTime = ticket.getCreate_date().getTime() / 1000;
        if ((newTime - oldTime) >= ticket.getExpires_in()) {
            String token = getToken();
            HttpGet method = new HttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi");
            try {
                CloseableHttpResponse response = client.execute(method);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                    if (jsonObject.containsKey("ticket")) {
                        ticket.setTicket(jsonObject.getString("ticket"));
                        ticket.setExpires_in(jsonObject.getInteger("expires_in"));
                        ticket.setCreate_date(new Date());
                        wxTokenService.updateTicket(ticket);
                        return ticket.getTicket();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ticket.getTicket();
    }

    private String media_id;

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    //请求微信调用选取图片接口
    public String chooseImage() throws IOException {
        msg.clear();
        String token = getToken();
        HttpGet httpGet = new HttpGet("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + token + "&media_id=" + media_id + "");
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
                FileOutputStream fos = new FileOutputStream(new File(UrlTools.SHAREDIR, "xxx.jpg"));//图片命名已电话号
                byte[] buf = new byte[8096];
                int size;
                while ((size = bis.read(buf)) != -1)
                    fos.write(buf, 0, size);
                fos.close();
                bis.close();
                msg.put("success", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        msg.put("success", false);
        return ERROR;
    }

    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getServerProperty() throws IOException {
        msg.clear();
        HttpGet method = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + getAppid() + "&secret=" + getSecret() + "&code=" + code + "&grant_type=authorization_code");
        try {
            CloseableHttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                if (jsonObject.containsKey("access_token")) {
                    String openid = jsonObject.getString("openid");
                    JSWXToken token = wxTokenService.getJSWXTokenByOpenId(openid);
                    if (token == null) {
                        token = JSON.parseObject(jsonObject.toJSONString(), JSWXToken.class);
                        token.setCreate_date(new Date());
                    } else {
                        token.setAccess_token(jsonObject.getString("access_token"));
                        token.setCreate_date(new Date());
                        token.setRefresh_token(jsonObject.getString("refresh_token"));
                        token.setExpires_in(jsonObject.getIntValue("expires_in"));
                    }
                    wxTokenService.saveJSWXToken(token);
                    msg.put("openid", jsonObject.getString("openid"));
                    if (jsonObject.containsKey("access_token")) {
                        msg.put("unionid", jsonObject.getString("unionid"));
                    }
                    msg.put("success", true);
                    return SUCCESS;

                }
            }
        } finally {
            client.close();
        }
        msg.put("success", false);
        return ERROR;
    }

    /*拉取用户信息*/
    public String getWXUserData() throws IOException {
        msg.clear();
        JSWXToken jswxToken = wxTokenService.getJSWXTokenByOpenId(openid);
        if (jswxToken != null) {
            WXUser wxUser = iwxUserService.getWXUserByUnionid(jswxToken.getUnionid());
            if (wxUser != null) {
                //绑定过爱内秀
                Userinfo userinfo = wxUser.getBindWXID().getUserinfo();
                JSONObject userJson = parsJsonObject(userinfo);
                String headImg = userJson.getString("headImg");
                if (headImg != null && !headImg.isEmpty()) {
                    userJson.replace("headImg", "http://iliker.cn/share/" + headImg);
                }
                userJson.put("onbind", true);
                msg.put("wxuser", userJson);
                msg.put("success", true);
                return SUCCESS;
            } else {
                //没有绑定爱内秀
                long newTime = System.currentTimeMillis() / 1000;
                long oldTime = jswxToken.getCreate_date().getTime() / 1000;
                if ((newTime - oldTime) >= jswxToken.getExpires_in()) {
                    HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + getAppid() + "&grant_type=refresh_token&refresh_token=" + jswxToken.getRefresh_token());
                    CloseableHttpResponse response = client.execute(httpGet);
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                        if (jsonObject.containsKey("access_token")) {
                            jswxToken.setAccess_token(jsonObject.getString("access_token"));
                            jswxToken.setExpires_in(jsonObject.getIntValue("expires_in"));
                            jswxToken.setRefresh_token(jsonObject.getString("refresh_token"));
                            jswxToken.setCreate_date(new Date());
                            jswxToken.setOpenid(jsonObject.getString("openid"));
                            jswxToken.setScope(jsonObject.getString("scope"));
                            if (wxTokenService.saveJSWXToken(jswxToken)) {
                                if (pushWXUserData(jswxToken.getAccess_token(), jswxToken.getOpenid())) {
                                    if (jswxToken.getSuperiornum() != null) {
                                        msg.put("superiornum", jswxToken.getSuperiornum());
                                    }
                                    return SUCCESS;
                                }
                            }
                        }
                    }
                } else {
                    if (pushWXUserData(jswxToken.getAccess_token(), jswxToken.getOpenid())) {
                        if (jswxToken.getSuperiornum() != null) {
                            msg.put("superiornum", jswxToken.getSuperiornum());
                        }
                        return SUCCESS;
                    }
                }

            }
        }
        return ERROR;
    }

    /*拉取微信用户信息*/
    private boolean pushWXUserData(String access_token, String openid) {
        HttpGet method = new HttpGet("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN");
        try {
            CloseableHttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                if (!jsonObject.containsKey("errcode")) {
                    JSONObject wxuser = new JSONObject();
                    wxuser.put("nickName", jsonObject.getString("nickname"));
                    wxuser.put("sex", jsonObject.getInteger("sex"));
                    wxuser.put("city", jsonObject.getString("city"));
                    wxuser.put("province", jsonObject.getString("province"));
                    wxuser.put("country", jsonObject.getString("country"));
                    wxuser.put("headImg", jsonObject.getString("headimgurl"));
                    wxuser.put("unionid", jsonObject.getString("unionid"));
                    wxuser.put("openid", jsonObject.getString("openid"));
                    wxuser.put("onbind", false);//是否绑定iliker账户
                    msg.put("success", true);
                    msg.put("wxuser", wxuser);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return false;
    }

    public String existerUserBind() {
        msg.clear();
        String checkCode = (String) ActionContext.getContext().getSession().get("checkCode");
        if (!nonce.equals(checkCode)) {
            msg.put("success", false);
            msg.put("msg", "验证码不正确");
            return ERROR;
        }
        ActionContext.getContext().getSession().remove("checkCode");
        Userinfo userinfo = userinfoSvc.findById(uid);
        if (!userinfo.getPassword().equals(MD5Util.getMD5Str(new String(MD5Util.decode(password))))) {
            msg.put("success", false);
            msg.put("msg", "密码不正确");
            return ERROR;
        }
        try {
            JSWXToken jswxToken = wxTokenService.getJSWXTokenByOpenId(openid);
            WXUser foundUser = iwxUserService.getWXUserByUnionid(jswxToken.getUnionid());
            if (foundUser != null) {
                iwxUserService.delWXUser(foundUser);
            }
            foundUser = new WXUser(jswxToken.getUnionid(), new Userinfo(uid));
            iwxUserService.saveWXUser(foundUser);
            JSONObject userJson = parsJsonObject(userinfo);
            String headImg = userJson.getString("headImg");
            if (headImg != null && !headImg.isEmpty()) {
                userJson.replace("headImg", "http://iliker.cn/share/" + headImg);
            }
            userJson.put("onbind", true);
            msg.put("wxuser", userJson);
            msg.put("msg", "绑定成功");
            msg.put("success", true);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.put("success", false);
        msg.put("msg", "绑定失败");
        return ERROR;
    }

    public String regUserBind() {
        msg.clear();
        RegisterBean bean = JSON.parseObject(registerBean, RegisterBean.class);
        if (validateReg(bean, msg)) {
            Userinfo userinfo = userinfoSvc.findByNickName(bean.getNickname());
            if (userinfo != null) {
                msg.put("success", false);
                msg.put("msg", "昵称被人使用!");
                return ERROR;
            }
            userinfo = new Userinfo();
            userinfo.setNickname(bean.getNickname());
            userinfo.setRegistered(new Date());
            userinfo.setPassword(MD5Util.getMD5Str(new String(MD5Util.decode(bean.getPassword()))));
            userinfo.setPhone(bean.getPhoneNum());
            userinfo.setEnable(true);
            String superiornum = bean.getSuperiornum();
            if (superiornum == null || superiornum.isEmpty()) {
                superiornum = "18680602795";
            }
            Wallet wallet = new Wallet(userinfo.getPhone(), userinfo);
            IntegralAccount integralAccount = new IntegralAccount(userinfo.getPhone(), userinfo);
            GiveAccount giveAccount = new GiveAccount(userinfo.getPhone(), userinfo);
            userinfo.setIntegralAccount(integralAccount);
            userinfo.setGiveAccount(giveAccount);
            userinfo.setWallet(wallet);
            Userinfo userinfo1 = userinfoSvc.findByPhone(superiornum);
            if (userinfo1 != null) {
                userinfo.setParent(userinfo1);
            }
            try {
                JSWXToken jswxToken = wxTokenService.getJSWXTokenByOpenId(openid);
                iwxUserService.bindWXToAPP(new WXUser(jswxToken.getUnionid(), userinfo), userinfo);
                JSONObject userJson = parsJsonObject(userinfo);
                userJson.put("onbind", true);
                msg.put("wxuser", userJson);
                msg.put("msg", "绑定成功");
                msg.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                msg.put("success", false);
                msg.put("msg", "绑定不成功");
            }
        }
        return ERROR;
    }

    public String appBindWX() {
        msg.clear();
        if (registerBean != null && !registerBean.isEmpty()) {
            RegisterBean bean = JSON.parseObject(registerBean, RegisterBean.class);
            if (validateData(bean)) {
                Userinfo userinfo = userinfoSvc.findByNickName(bean.getNickname());
                if (userinfo != null) {
                    msg.put("success", false);
                    msg.put("msg", "昵称被人使用!");
                    return ERROR;
                }
                userinfo = new Userinfo();
                userinfo.setNickname(bean.getNickname());
                userinfo.setRegistered(new Date());
                userinfo.setPassword(bean.getPassword());
                userinfo.setPhone(bean.getPhoneNum());
                userinfo.setEnable(true);
                Wallet wallet = new Wallet(userinfo.getPhone(), userinfo);
                IntegralAccount integralAccount = new IntegralAccount(userinfo.getPhone(), userinfo);
                GiveAccount giveAccount = new GiveAccount(userinfo.getPhone(), userinfo);
                userinfo.setIntegralAccount(integralAccount);
                userinfo.setGiveAccount(giveAccount);
                userinfo.setWallet(wallet);
                String superiornum = bean.getSuperiornum();
                if (superiornum == null || superiornum.isEmpty()) {
                    superiornum = "18680602795";
                }
                Userinfo userinfo1 = userinfoSvc.findByPhone(superiornum);
                if (userinfo1 != null) {
                    userinfo.setParent(userinfo1);
                    userinfo.setSuperiornum(superiornum);
                }
                try {
                    iwxUserService.bindWXToAPP(new WXUser(bean.getUnionID(), userinfo), userinfo);
                    JSONObject userJson = parsJsonObject(userinfo);
                    userJson.put("onbind", true);
                    msg.put("wxuser", userJson);
                    msg.put("msg", "绑定成功");
                    msg.put("success", true);
                    return SUCCESS;
                } catch (Exception e) {
                    msg.put("success", false);
                    msg.put("msg", "绑定不成功");
                    return ERROR;
                }
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    private boolean validateData(RegisterBean bean) {
        if (bean.getPhoneNum() == null || !NetSteamUtil.isMobileNO(bean.getPhoneNum())) {
            msg.put("success", false);
            msg.put("msg", "手机号格式不规范!");
            return false;
        }
        if (bean.getNickname() == null || !NetSteamUtil.StringFilter(bean.getNickname())) {
            msg.put("success", false);
            msg.put("msg", "昵称不合规范!");
            return false;
        }
        if (bean.getPassword() == null) {
            msg.put("success", false);
            msg.put("msg", "密码不能为空!");
            return false;
        }
        return true;
    }

    public String showqrcode() {
        msg.clear();
        HttpPost method = new HttpPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + getToken());
        String content = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + shareContent + "\"}}}";
        try {
            StringEntity entity = new StringEntity(content, "UTF-8");
            method.addHeader("Content-type", "application/json; charset=utf-8");
            method.setHeader("Accept", "application/json");
            method.setEntity(entity);
            CloseableHttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                msg = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                if (msg.containsKey("ticket")) {
                    String ticket = msg.getString("ticket");
                    msg.replace("ticket", URLEncoder.encode(ticket, "utf-8"));
                    return SUCCESS;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String existsAPPBindWX() {
        msg.clear();
        HttpGet method = new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + getAppid() + "&secret=" + getSecret() + "&code=" + code + "&grant_type=authorization_code");
        try {
            CloseableHttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                if (jsonObject.containsKey("access_token")) {
                    JSWXToken token = wxTokenService.getJSWXTokenByOpenId(jsonObject.getString("openid"));
                    if (token == null) {
                        token = JSON.parseObject(jsonObject.toJSONString(), JSWXToken.class);
                        token.setCreate_date(new Date());
                    } else {
                        token.setAccess_token(jsonObject.getString("access_token"));
                        token.setCreate_date(new Date());
                        token.setRefresh_token(jsonObject.getString("refresh_token"));
                        token.setExpires_in(jsonObject.getIntValue("expires_in"));
                    }
                    wxTokenService.saveJSWXToken(token);
                    WXUser foundUser = iwxUserService.getWXUserByUnionid(token.getUnionid());
                    if (foundUser != null) {
                        iwxUserService.delWXUser(foundUser);
                    }
                    foundUser = new WXUser(token.getUnionid(), new Userinfo(uid));
                    iwxUserService.saveWXUser(foundUser);
                    msg.put("success", true);
                    return SUCCESS;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.put("success", false);
        return ERROR;
    }

    public String unBindWX() {
        WXUser foundUser = iwxUserService.getWXUserByUID(uid);
        if (foundUser != null) {
            try {
                iwxUserService.delWXUser(foundUser);
                msg.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                msg.put("success", false);
                return ERROR;
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*添加客服*/
    public String addKF() throws IOException {
        //{"errcode":65400,"errmsg":"please enable new custom service, or wait for a while if you have enabled hint: [gAADLA09731551]"}
        //https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN
        HttpPost method = new HttpPost("https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + getToken());
        StringEntity entity = new StringEntity("{\"kf_account\":\"LXRDWX\",\"nickname\":\"高石花道\"}", "UTF-8");
        method.addHeader("Content-type", "application/json; charset=utf-8");
        method.setHeader("Accept", "application/json");
        method.setEntity(entity);
        try {
            CloseableHttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                Result result = paseResult(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                msg.put("errcode", result.getErrcode());
                msg.put("errmsg", result.getErrmsg());
                return SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        return NONE;
    }
}
