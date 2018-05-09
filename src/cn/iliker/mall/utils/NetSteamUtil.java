package cn.iliker.mall.utils;

import cn.iliker.mall.entity.RegisterBean;
import cn.iliker.mall.entity.wxinfo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NetSteamUtil {
    public static String readInputSteam(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String lines;
        StringBuilder resposeBuffer = new StringBuilder("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            resposeBuffer.append(lines);
        }
        reader.close();
        return resposeBuffer.toString();
    }

    public static boolean isMobileNO(String mobiles) {
        if (mobiles != null && !mobiles.isEmpty()) {
            Pattern p = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }
        return false;
    }

    /**
     * 匹配邮箱
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        String str = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 匹配以字母开头的字母数字下划线组合或汉字昵称
     */
    public static boolean StringFilter(String str) {
        return str != null && str.matches("(^[a-zA-Z]\\w{3,9})|([\\u4E00-\\u9FA5]{2,10})");
    }

    /**
     * 匹配以字母开头的字母数字下划线组合不能是全字母6-16位密码
     */
    public static boolean pwdFilter(String str) {
        return str != null && str.matches("^[a-zA-Z](?![a-zA-Z]+$)\\w{5,15}");
    }

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
     *
     * @param lon1 第一点的经度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的经度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位km
     */

    public static double GetDistance(double lon1, double lat1, double lon2,
                                     double lat2) {
        double EARTH_RADIUS = 6371;// 赤道半径(单位千米
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        // s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static Result paseResult(String backVal) {
        Result result = new Result();
        JSONObject jsonObject = JSON.parseObject(backVal);
        result.setErrmsg(jsonObject.getString("errmsg"));
        result.setErrcode(jsonObject.getIntValue("errcode"));
        return result;
    }

    public static boolean validateReg(RegisterBean registerBean, JSONObject msg) {
        if (registerBean == null) {
            msg.put("success", false);
            msg.put("msg", "验证错误!");
            return false;
        }
        if (!registerBean.getCode().equals(registerBean.getSmscode())) {
            msg.put("success", false);
            msg.put("msg", "验证码不正确!");
            return false;
        }
        if (registerBean.getBackPhone() == null || !registerBean.getBackPhone().equals(registerBean.getPhoneNum())) {
            msg.put("success", false);
            msg.put("msg", "手机号码不一致!");
            return false;
        }
        if (registerBean.getPassword() == null || registerBean.getPassword().length() != 16) {
            msg.put("success", false);
            msg.put("msg", "加密密码串要16位");
            return false;
        }
        if ((System.currentTimeMillis() - registerBean.getTime()) / 1000 > 120) {
            msg.put("success", false);
            msg.put("msg", "验证码已过期!");
            return false;
        }
        return true;
    }
}