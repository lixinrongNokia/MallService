package cn.iliker.mall.utils;

import java.io.File;

public final class UrlTools {

    public static final File GOODSDIR;//商品图片目录
    public static final File BRANDDIR;//品牌图标目录
    public static final File THEMEDIR;//活动主题海报目录

    public static final File SHAREDIR;//用户分享图片目录

    public static final File SOUNDDIR;//音频文件目录
    public static final File APPLOAD;//app安装包目录
    public static final File HEADIMG;//用户头像目录
    public static final File STOREIMG;//门店图片目录
    public static final File VALIDATION;//认证图片目录
    public static final File STOCKDIR;//商品样式图片目录

    static {
//        String homeDir = System.getProperty("user.home");
        //centos路径:/www/WebSource
        File file = new File("E:/WebSource");//windows系统目录
        if (!file.exists())
            file.mkdirs();
        String rootUrl = file.getAbsolutePath();
        // 创建子文件夹
        GOODSDIR = new File(rootUrl, "img/goodsimg");
        BRANDDIR = new File(rootUrl, "img/brandimg");
        THEMEDIR = new File(rootUrl, "img/theme");
        SHAREDIR = new File(rootUrl, "share");
        SOUNDDIR = new File(rootUrl, "sound");
        APPLOAD = new File(rootUrl, "appPack");
        VALIDATION = new File(rootUrl, "validation");
        HEADIMG = new File(rootUrl, "head");
        STOREIMG = new File(rootUrl, "storeIcon");
        STOCKDIR = new File(rootUrl, "stockImg");
        if (!GOODSDIR.exists()) {
            GOODSDIR.mkdirs();
        }
        if (!BRANDDIR.exists()) {
            BRANDDIR.mkdirs();
        }
        if (!VALIDATION.exists()) {
            VALIDATION.mkdirs();
        }
        if (!THEMEDIR.exists()) {
            THEMEDIR.mkdirs();
        }
        if (!SHAREDIR.exists()) {
            SHAREDIR.mkdirs();
        }
        if (!SOUNDDIR.exists()) {
            SOUNDDIR.mkdirs();
        }
        if (!APPLOAD.exists()) {
            APPLOAD.mkdirs();
        }
        if (!STOREIMG.exists()) {
            STOREIMG.mkdirs();
        }
        if (!STOCKDIR.exists()) {
            STOCKDIR.mkdirs();
        }
    }
}
