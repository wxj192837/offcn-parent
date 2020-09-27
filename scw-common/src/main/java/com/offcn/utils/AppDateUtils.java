package com.offcn.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class AppDateUtils {
    public static   String getFormatTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
    public static   String getFormatTime(String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }
    public static   String getFormatTime(String pattern,Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
