package cn.iliker.mall.control;

import static cn.iliker.mall.utils.NetSteamUtil.isMobileNO;
import static cn.iliker.mall.utils.Rename_PIC.renameUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.taobao.api.ApiException;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.ProtohuMan;
import cn.iliker.mall.entity.ReportShare;
import cn.iliker.mall.entity.Share;
import cn.iliker.mall.entity.Userdata;
import cn.iliker.mall.entity.Userinfo;
import cn.iliker.mall.entity.Wallet;
import cn.iliker.mall.service.IAliYunAccountSvc;
import cn.iliker.mall.service.ICommonQuerySvc;
import cn.iliker.mall.service.IReportObjSvc;
import cn.iliker.mall.service.IShareSvc;
import cn.iliker.mall.service.IUserinfoSvc;
import cn.iliker.mall.utils.EmailJob;
import cn.iliker.mall.utils.MD5Util;
import cn.iliker.mall.utils.NetSteamUtil;
import cn.iliker.mall.utils.UrlTools;

public class UserManager extends ActionSupport {
    private int uid;
    private IUserinfoSvc usersvc;
    private IAliYunAccountSvc aliYunAccountSvc;
    private EmailJob testjob;
    private JSONObject msg = new JSONObject();
    private String phone;
    private String message;
    private String payment_code;
    private String password = "";
    private String userinfo;
    private ICommonQuerySvc querySvc;
    private String superiornum;
    private int offset = 1;
    private int whereId = 1;
    private int totalPage = 1;
    private int totalSize = 1;
    private String device;
    List<String> list = new ArrayList<>();
    private String nonce;
    private String nickName;
    private File headPortrait;
    private String headPortraitFileName;
    private List<File> sharePortrait;
    private List<String> sharePortraitFileName;
    private Share share;
    private IShareSvc shareSvc;
    private IReportObjSvc reportObjSvc;
    private Userdata userdata;

    public Userdata getUserdata() {
        return userdata;
    }

    public void setUserdata(Userdata userdata) {
        this.userdata = userdata;
    }

    public void setReportObjSvc(IReportObjSvc reportObjSvc) {
        this.reportObjSvc = reportObjSvc;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public void setShareSvc(IShareSvc shareSvc) {
        this.shareSvc = shareSvc;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setWhereId(int whereId) {
        this.whereId = whereId;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setSuperiornum(String superiornum) {
        this.superiornum = superiornum;
    }

    public void setQuerySvc(ICommonQuerySvc querySvc) {
        this.querySvc = querySvc;
    }

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public void setTestjob(EmailJob testjob) {
        this.testjob = testjob;
    }

    public void setUsersvc(IUserinfoSvc usersvc) {
        this.usersvc = usersvc;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public File getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(File headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getHeadPortraitFileName() {
        return headPortraitFileName;
    }

    public void setHeadPortraitFileName(String headPortraitFileName) {
        this.headPortraitFileName = headPortraitFileName;
    }

    public List<File> getSharePortrait() {
        return sharePortrait;
    }

    public void setSharePortrait(List<File> sharePortrait) {
        this.sharePortrait = sharePortrait;
    }

    public List<String> getSharePortraitFileName() {
        return sharePortraitFileName;
    }

    public void setSharePortraitFileName(List<String> sharePortraitFileName) {
        this.sharePortraitFileName = sharePortraitFileName;
    }

    public String viewUserAll() throws Exception {
        JSONObject jsonObject = querySvc.getAll("uid,realname,nickname,email,phone,sex,headimg,birthday,superiornum,registered,level", "userinfo", null, null, "registered DESC", offset, 20);
        if (jsonObject != null) {
            List<Userinfo> list = JSON.parseArray(jsonObject.getJSONArray("dataSet").toJSONString(), Userinfo.class);
            GeneralList<Userinfo> generalList = new GeneralList<Userinfo>();
            generalList.setList(list);
            setTotalSize(jsonObject.getInteger("totalSize"));
            setTotalPage(jsonObject.getInteger("pageCount"));
            ActionContext.getContext().put("generalList", generalList);
            return SUCCESS;
        }
        return NONE;
    }

    public String userdetail() {
        Set<Userdata> set = usersvc.findByUId(uid);
        if (set != null && set.size() > 0) {
            ActionContext.getContext().put("userdata", set);
            return SUCCESS;
        }
        return ERROR;
    }

    public String autoSendEmail() {
        List<Userinfo> list = usersvc.getAllEmail();
        for (Userinfo user : list) {
            String emailaddress = user.getEmail();
            if (emailaddress != null) {
                try {
                    testjob.sendTemplateMail(user.getNickname(), user.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return NONE;
    }

    public String getUserInfoByPhone() {
        msg.clear();
        Userinfo userinfo = usersvc.findByPhone(phone);
        if (userinfo != null) {
            msg.put("email", userinfo.getEmail() == null ? "" : userinfo.getEmail());
            msg.put("birthday", userinfo.getBirthday());
            msg.put("registered", new SimpleDateFormat("yyyy-MM-dd").format(userinfo.getRegistered()));
            msg.put("nickname", userinfo.getNickname());
            msg.put("realname", userinfo.getRealname());
            msg.put("phone", userinfo.getPhone());
            msg.put("paymentCode", userinfo.getPaymentCode() == null ? "" : userinfo.getPaymentCode());
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    /*用户更新邮箱后发送邮件*/
    public String welcomeemail() {
        /*List<Userinfo> list = usersvc.getAllEmail();
        for (Userinfo user : list) {
            testjob.sendSimpleMail("尊敬的会员:" + user.getNickname() + ",你的资料已更新！", user.getEmail());
        }*/
        return NONE;
    }

    /*批量导入用户*/
    public String batchImport() throws IOException {
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        URL url = this.getClass().getClassLoader().getResource("ProtohuMan.xls");
        if (url != null) {
            InputStream is = new FileInputStream(new File(url.getPath()));
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            try {
                List<ProtohuMan> list = readXls(hssfWorkbook);
                usersvc.batchAddUser(list);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    builder.append(list.get(i).getPhone()).append(",");
                    //阿里短信通知一次最多发送的数量为200
                    if ((i + 1) % 200 == 0) {
                        builder.deleteCharAt(builder.length() - 1);
                        aliYunAccountSvc.sendSMSNotify(builder.toString());
                        builder.setLength(0);
                    }
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                    aliYunAccountSvc.sendSMSNotify(builder.toString());
                }
                writer.print("success");
                writer.close();
                return NONE;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer.print("error");
        writer.close();
        return NONE;
    }

    /*读取属性*/
    private List<ProtohuMan> readXls(HSSFWorkbook hssfWorkbook) {
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        List<ProtohuMan> list = new ArrayList<>();
        // 循环行Row因为有头表题，从1开始
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            HSSFCell cell = hssfRow.getCell(0);
            if (cell == null) {
                continue;
            }
            String cardId = cell.getStringCellValue();
            cell = hssfRow.getCell(1);
            if (cell == null) {
                continue;
            }
            String setRealName = cell.getStringCellValue();
            cell = hssfRow.getCell(5);
            if (cell == null) {
                continue;
            }
            String phoneStr = cell.getStringCellValue();
            if (!isMobileNO(phoneStr)) {
                continue;
            }
            list.add(new ProtohuMan(cardId, setRealName, phoneStr));
        }
        return list;
    }

    /*设置支付密码*/
    public String changePaymentCode() {
        msg.clear();
        Userinfo userinfo = usersvc.findByPhone(phone);
        if (userinfo != null) {
            if ("h5".equals(device)) {
                payment_code = MD5Util.getMD5Str(new String(MD5Util.decode(payment_code)));
                password = MD5Util.getMD5Str(new String(MD5Util.decode(password)));
            }
            if (userinfo.getPassword().equals(password)) {
                userinfo.setPaymentCode(payment_code);
                try {
                    usersvc.update(userinfo);
                    msg.put("success", true);
                    return SUCCESS;
                } catch (Exception e) {
                    msg.put("success", false);
                    return ERROR;
                }
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*用户注册*/
    public String userRegister() {
        Userinfo user;
        if (userinfo == null) {
            user = (Userinfo) ServletActionContext.getRequest().getSession().getAttribute("registerUser");
        } else {
            user = JSON.parseObject(userinfo, Userinfo.class);
            JSONObject jsonObject = JSON.parseObject(userinfo);
            if (jsonObject.containsKey("superiornum")) {
                Userinfo parentUser = usersvc.findByPhone(jsonObject.getString("superiornum"));
                if (parentUser != null) {
                    user.setParent(parentUser);
                }
            }
        }
        if (user != null) {
            Userinfo checkUserinfo = usersvc.findByNickName(user.getNickname());
            if (checkUserinfo != null) {
                msg.put("success", false);
                msg.put("msg", "昵称被人使用!");
                return ERROR;
            }
            user.setEnable(true);
            user.setRegistered(new Date());
            Wallet wallet = new Wallet(user.getPhone(), user);
            IntegralAccount integralAccount = new IntegralAccount(user.getPhone(), user);
            GiveAccount giveAccount = new GiveAccount(user.getPhone(), user);
            user.setIntegralAccount(integralAccount);
            user.setGiveAccount(giveAccount);
            user.setWallet(wallet);
            try {
                int uid = usersvc.save(user);
                List<String> list = new ArrayList<>(1);
                list.add(user.getPhone());
                aliYunAccountSvc.pushMessage(list, "感谢你成为爱内秀用户！");
                msg.put("success", true);
                msg.put("uid", uid);
                ServletActionContext.getRequest().getSession().removeAttribute("registerUser");
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        msg.put("msg", "注册失败!");
        return ERROR;
    }

    /*public String testRegister() {
        Userinfo user=new Userinfo("猥琐发育3","123456","15611213354", (Userinfo) usersvc.findByPhone("18680602795"));
        Wallet wallet=new Wallet(user.getPhone(),user);
        IntegralAccount integralAccount=new IntegralAccount(user.getPhone(),user);
        user.setIntegralAccount(integralAccount);
        GiveAccount giveAccount=new GiveAccount(user.getPhone(),user);
        user.setGiveAccount(giveAccount);
        user.setWallet(wallet);
        try {
            usersvc.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }*/

    public String getChild() {
        msg.clear();
        String whereStr = "superiornum=" + superiornum;
        if (whereId == 2) {
            whereStr = "superiornum in(select phone from userinfo where superiornum='" + superiornum + "')";
        }
        msg = querySvc.getAll("nickname,phone,headimg,registered,`level`", "userinfo", whereStr, null, "registered DESC", offset, 20);
        if (msg != null) {
            return SUCCESS;
        }
        return NONE;
    }

    public String sendIMToPerson() {
        msg.clear();
        list.add(phone);
        try {
            aliYunAccountSvc.pushMessage(list, message);
            msg.put("success", true);
            list.clear();
            return SUCCESS;
        } catch (ApiException e) {
            msg.put("success", false);
            return ERROR;
        }
    }

    public String bindWXCheck() {
        msg.clear();
        Userinfo userinfo = usersvc.findByPhone(phone);
        if (userinfo != null) {
            msg.put("success", true);
            msg.put("uid", userinfo.getUid());
            msg.put("msg", "手机号已被使用");
            return SUCCESS;
        }
        msg.put("phone", phone);
        msg.put("success", false);
        msg.put("msg", "手机号可以使用");
        return ERROR;
    }

    public String findPwdByPhone() {
        msg.clear();
        String checkCode = (String) ActionContext.getContext().getSession().get("checkCode");
        if (!checkCode.equals(nonce)) {
            msg.put("success", false);
            msg.put("msg", "校验码不正确");
            return ERROR;
        }
        Userinfo userinfo = usersvc.findByPhone(phone);
        if (userinfo != null) {
            msg.put("success", true);
            msg.put("nickName", userinfo.getNickname());
            msg.put("phone", userinfo.getPhone());
            return SUCCESS;
        }
        msg.put("success", false);
        msg.put("msg", "用户不存在");
        return ERROR;
    }

    public String findPwdByUpdate() {
        msg.clear();
        if (phone == null || password == null || password.length() < 6) {
            msg.put("success", false);
            msg.put("msg", "数据缺失");
            return ERROR;
        }
        Userinfo userinfo = usersvc.findByPhone(phone);
        if (userinfo != null) {
            if ("h5".equals(device)) {
                password = MD5Util.getMD5Str(new String(MD5Util.decode(password)));
            }
            userinfo.setPassword(password);
            try {
                usersvc.update(userinfo);
                msg.put("success", true);
                msg.put("msg", "密码修改成功");
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        msg.put("msg", "修改失败");
        return ERROR;
    }

    /*查找下级*/
    public String findChlid() throws IOException {
        //TODO
        return NONE;
    }

    /*激活vip用户*/
    public String activateUser() {
        msg.clear();
        Userinfo userinfo = usersvc.findById(uid);
        if (!userinfo.getNickname().equals(nickName)) {
            Userinfo existsed = usersvc.findByNickName(nickName);
            if (existsed != null) {
                msg.put("success", false);
                msg.put("msg", "昵称被占用");
                return ERROR;
            } else {
                userinfo.setNickname(nickName);
            }
        }
        userinfo.setPassword(MD5Util.getMD5Str(new String(MD5Util.decode(password))));
        userinfo.setEnable(true);
        userinfo.setRegistered(new Date());
        Wallet wallet = new Wallet(userinfo.getPhone(), userinfo);
        IntegralAccount integralAccount = new IntegralAccount(userinfo.getPhone(), userinfo);
        GiveAccount giveAccount = new GiveAccount(userinfo.getPhone(), userinfo);
        userinfo.setIntegralAccount(integralAccount);
        userinfo.setGiveAccount(giveAccount);
        userinfo.setWallet(wallet);
        try {
            usersvc.update(userinfo);
            msg.put("success", true);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.put("success", false);
        msg.put("msg", "系统繁忙，请稍后再试");
        return ERROR;
    }

    public String uploadHeadPortrait() {
        msg.clear();
        Userinfo userinfo = usersvc.findById(uid);
        if (userinfo != null) {
            if (headPortrait != null) {
                String oldHeadImg = userinfo.getHeadimg();
                String newHeadImg = renameUtil(headPortraitFileName);
                userinfo.setHeadimg(newHeadImg);
                try {
                    usersvc.update(userinfo);
                    FileUtils.copyFile(headPortrait, new File(UrlTools.SHAREDIR, newHeadImg));
                    if (oldHeadImg != null && !"".equals(oldHeadImg)) {
                        File oldFile = new File(UrlTools.SHAREDIR, oldHeadImg);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    msg.put("success", true);
                    msg.put("headImg", newHeadImg);
                    return SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    public String sharePortrait() {
        msg.clear();
        if (share != null) {
            Userinfo userinfo = usersvc.findById(share.getUserinfo().getUid());
            if (userinfo != null) {
                if (sharePortrait != null && sharePortraitFileName != null) {
                    List<String> newimgName = renameUtil(sharePortraitFileName);
                    StringBuilder buf2 = new StringBuilder();
                    for (String str : newimgName) {
                        buf2.append(str).append("#");
                    }
                    buf2.deleteCharAt(buf2.length() - 1);
                    share.setPic(buf2.toString());
                    share.setPiccount(newimgName.size());
                    share.setUserinfo(userinfo);
                    share.setNickname(userinfo.getNickname());
                    try {
                        shareSvc.save(share);
                        for (int i = 0; i < sharePortrait.size(); i++) {
                            FileUtils.copyFile(sharePortrait.get(i), new File(UrlTools.SHAREDIR, newimgName.get(i)));
                        }
                        msg.put("success", true);
                        return SUCCESS;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    private ReportShare reportShare;

    public ReportShare getReportShare() {
        return reportShare;
    }

    public void setReportShare(ReportShare reportShare) {
        this.reportShare = reportShare;
    }

    public String reportShare() {
        if (reportShare != null) {
            reportShare.setReportTime(new java.sql.Timestamp(new java.util.Date().getTime()));
            try {
                reportObjSvc.addReportShare(reportShare);
                msg.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*获取融云token*/
    public String rongYunIMTokenService() {
        msg.clear();
        Userinfo userinfo = usersvc.findById(uid);
        String rongyuntoken = null;
        if (userinfo != null) {
            if (userinfo.getRongYunToken() == null) {
                rongyuntoken = requestToken(userinfo);
                if (rongyuntoken != null) {
                    userinfo.setRongYunToken(rongyuntoken);
                    try {
                        usersvc.update(userinfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                rongyuntoken = userinfo.getRongYunToken();
            }
            if (rongyuntoken != null) {
                msg.put("token", rongyuntoken);
                msg.put("success", true);
                return SUCCESS;
            }
        }
        msg.put("success", false);
        return ERROR;
    }
    /*public String rongYunIMTokenService() {
        msg.clear();
        Userinfo userinfo = usersvc.findById(uid);//27
        String rongyuntoken = requestToken(userinfo);
        if (rongyuntoken != null) {
            userinfo.setRongYunToken(rongyuntoken);
            try {
                usersvc.update(userinfo);
                msg.put("token", rongyuntoken);
                msg.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }*/

    private String requestToken(Userinfo userinfo) {
        CloseableHttpClient client = HttpClients.createDefault();
        SecureRandom random = new SecureRandom();
        HttpPost httpPost = new HttpPost("http://api.cn.ronghub.com/user/getToken.json");
        String nonce = new BigInteger(32, random).toString(8);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        httpPost.setHeader("App-Key", "82hegw5u8dobx");
        httpPost.setHeader("Nonce", nonce);
        httpPost.setHeader("Timestamp", timestamp);
        httpPost.setHeader("Signature", sha1("PHdBPkJhqT" + nonce + timestamp));
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("userId", userinfo.getPhone()));
        params.add(new BasicNameValuePair("name", userinfo.getNickname()));
        params.add(new BasicNameValuePair("portraitUri", "http://iliker.cn/share/" + userinfo.getHeadimg()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                return jsonObject.getString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sha1(String data) {
        StringBuilder buf = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (byte bit : bits) {
                int a = bit;
                if (a < 0) a += 256;
                if (a < 16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public String getBodyByUID() {
        msg.clear();
        Userdata userdata = usersvc.findBodyByUID(uid);
        if (userdata != null) {
            int wait = measure(userdata.getHeight(), userdata.getOnchest(), userdata.getWaist(), userdata.getHip());
            Double bmi = matchBMI(userdata.getHeight(), paseWeight(userdata.getWeight()));
            String pantsSize = matchPantsSize(userdata.getHip());
            String cupSize = matchCupSize(userdata.getOnchest(), userdata.getUnderchest());
            msg.put("bmi", bmi);
            msg.put("bodyType", wait);
            msg.put("height", userdata.getHeight());
            msg.put("weight", userdata.getWeight());
            msg.put("onchest", userdata.getOnchest());
            msg.put("underchest", userdata.getUnderchest());
            msg.put("waist", userdata.getWaist());
            msg.put("hip", userdata.getHip());
            msg.put("pantsSize", pantsSize);
            msg.put("cupSize", cupSize);
            return SUCCESS;
        }
        return NONE;
    }

    private Double paseWeight(String weight) {
        if (weight != null) {
            if (weight.toLowerCase().contains("kg")) {
                return Double.parseDouble(weight.substring(0, weight.length() - 2));
            }
            return Double.parseDouble(weight);
        }
        return 0.0;
    }

    /*保存用户身型数据*/
    public String saveSideData() {
        msg.clear();
        if (userdata != null && userdata.getUserinfo() != null) {
            try {
                usersvc.saveSideData(userdata);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /**
     * 测试体型
     *
     * @param sg 身高
     * @param xw 胸围
     * @param yw 腰围
     * @param tw 臀围
     * @return 身型代码 1 梨子；2 苹果；3 草莓；4 葫芦；5 甘蔗；6 未知
     */
    private static int measure(double sg, double xw, double yw, double tw) {
        double yt = yw / tw;
        double yx = yw / xw;
        double ys = yw / sg;
        double xt = xw / tw;
        if (ys <= 0.5) {
            if (yt < 0.7) {
                return 1;
            }
            if ((yt >= 0.7) && (yt <= 0.82)) {
                if ((yx >= 0.7) && (yx <= 0.84)) {
                    return 4;
                }
                if ((yx > 0.84) && (yx <= 0.89) && (xt >= 0.9) && (xt <= 1.0)) {
                    return 3;
                }
                return 1;
            }
            if ((yt > 0.82) && (yt <= 0.91)) {
                if ((yx >= 0.7) && (yx <= 0.84)) {
                    return 4;
                }
                if ((yx > 0.84) && (yx <= 0.89) && (xt >= 0.9) && (xt <= 1.0)) {
                    return 3;
                }
                return 5;
            }
            return 6;
        }
        if (yt > 0.91) {
            return 2;
        }
        return 6;
    }


    /**
     * 计算罩杯尺码
     *
     * @param b  上胸围
     * @param bd 下胸围
     * @return 罩杯尺码
     */
    private String matchCupSize(double b, double bd) {
        String mCurrentCupName = "";
        BigDecimal bBD = new BigDecimal(b);
        BigDecimal bdBD = new BigDecimal(bd);
        double val = bBD.subtract(bdBD).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        switch ((int) (bd + 0.5)) {
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
                if (val >= 10.0 && val < 12.5) {
                    mCurrentCupName = "70A/32A";
                } else if (val >= 12.5 && val < 15) {
                    mCurrentCupName = "70B/32B";
                } else if (val >= 15 && val < 17.5) {
                    mCurrentCupName = "70c/32c";
                } else if (val >= 17.5 && val < 20) {
                    mCurrentCupName = "70D/32D";
                } else if (val >= 20) {
                    mCurrentCupName = "70E/32E";
                }
                break;
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
                if (val >= 10.0 && val < 12.5) {
                    mCurrentCupName = "75A/34A";
                } else if (val >= 12.5 && val < 15) {
                    mCurrentCupName = "75B/34B";
                } else if (val >= 15 && val < 17.5) {
                    mCurrentCupName = "75c/34c";
                } else if (val >= 17.5 && val < 20) {
                    mCurrentCupName = "75D/34D";
                } else if (val >= 20) {
                    mCurrentCupName = "75E/34E";
                }
                break;
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
                if (val >= 10.0 && val < 12.5) {
                    mCurrentCupName = "80A/36A";
                } else if (val >= 12.5 && val < 15) {
                    mCurrentCupName = "80B/36B";
                } else if (val >= 15 && val < 17.5) {
                    mCurrentCupName = "80c/36c";
                } else if (val >= 17.5 && val < 20) {
                    mCurrentCupName = "80D/36D";
                } else if (val >= 20) {
                    mCurrentCupName = "80E/36E";
                }
                break;
        }
        return mCurrentCupName;
    }


    /**
     * 计算裤码
     *
     * @param hip 臀围
     * @return 裤码
     */
    private String matchPantsSize(double hip) {
        String underpants;
        int t1 = (int) (hip + 0.5);
        if (t1 >= 80 && t1 < 85) {
            underpants = "S";
        } else if (t1 >= 85 && t1 < 90) {
            underpants = "M";
        } else if (t1 >= 90 && t1 < 95) {
            underpants = "L";
        } else if (t1 >= 95 && t1 < 100) {
            underpants = "XL";
        } else {
            underpants = "XXL";
        }
        return underpants;
    }


    /**
     * 计算身高体重比
     *
     * @param height 身高(cm)
     * @param weight 体重(kg)
     * @return bmi
     */
    private Double matchBMI(double height, double weight) {
        if (height < 150) {
            return null;
        }
        BigDecimal bda = new BigDecimal(weight);
        BigDecimal bdb = new BigDecimal(45.0);
        if (bda.compareTo(bdb) < 0) {
            return null;
        }
        Double dfbmi = weight / (Math.pow(height / 100, 2));
        BigDecimal bigbmi = new BigDecimal(dfbmi);
        return bigbmi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
