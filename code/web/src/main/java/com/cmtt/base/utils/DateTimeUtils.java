package com.cmtt.base.utils;


import java.time.LocalDateTime;


public class DateTimeUtils {


    public static String getSocialDateDisplay(LocalDateTime createAt) {

        LocalDateTime currTime=LocalDateTime.now();

        java.time.Duration duration = java.time.Duration.between(createAt, currTime );

        System.out.println(duration.toMinutes());
        if (duration.toMinutes() < 30) {
            return "刚刚";
        }else if(duration.toMinutes() < 60){
            return duration.toMinutes() + "分钟前";
        }else if(duration.toMinutes() < (60*24)){
            return duration.toMinutes()/60 + "小时前";
        }else if(duration.toMinutes() > (60*24) && duration.toMinutes() < (60*24*2)){
            return "昨天";
        }else if(duration.toMinutes() > (60*24*2) && duration.toMinutes() < (60*24*365)){

            return createAt.getMonthValue() + "月" + createAt.getDayOfMonth() + "日";
        }
        else if(duration.toMinutes() > (60*24*365) && duration.toMinutes() < (60*24*365*2)){

            return "去年";
        }else if(duration.toMinutes() > (60*24*365*2)){

            return duration.toMinutes()/(60*24*365)+"年前";
        }
        else {
            return createAt.getMonthValue() + "月" + createAt.getDayOfMonth() + "日";
        }

    }

}
