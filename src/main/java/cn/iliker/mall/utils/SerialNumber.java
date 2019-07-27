package cn.iliker.mall.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SerialNumber {
    private static final int MAX_VALUE = 9999;
    private static final String FORMAT = "yyMMdd";
    private static final Format DF = new SimpleDateFormat(FORMAT);
    private static final byte[] lock = new byte[0];
    private String prefix = null;
    private Date date = null;
    private int number = 1;
    private final static Map<String, SerialNumber> map = new HashMap<>();

    private SerialNumber(String prefix, Date date) {
        this.prefix = prefix;
        this.date = date;
    }

    public static SerialNumber newInstance(String prefix) {
        Date date = new Date();
        return newInstance(prefix, date);
    }

    public static SerialNumber newInstance(String prefix, Date date) {
        SerialNumber o;
        synchronized (lock) {
            String key = getKey(prefix, date);
            if (map.containsKey(key)) {
                o = map.get(key);
                int number = o.getNumber();
                if (number < MAX_VALUE) {
                    o.setNumber(number + 1);
                } else {
                    o.setNumber(1);
                }

            } else {
                o = new SerialNumber(prefix, date);
                map.put(key, o);
            }
        }
        return o;
    }


    private static String getKey(String prefix, Date date) {
        return prefix + format(date);
    }

    private static String format(Date date) {
        return DF.format(date);
    }

    public String toString() {
        return prefix + format(date) + String.format("%04d", number);
    }

    private void setNumber(int number) {
        this.number = number;
    }

    private int getNumber() {
        return number;
    }
}
