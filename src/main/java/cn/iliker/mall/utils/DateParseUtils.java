package cn.iliker.mall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateParseUtils {
    public static String getFormatDate(String formatString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(new Date());
    }

    public static String create_Batch_no(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public static String data2String(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
