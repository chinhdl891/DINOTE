package com.example.dinote.utils;

import java.util.Calendar;

public class Constant {
    public final static String  MAIN_FRAGMENT = "main_fragment" ;
    public final static String  CREATE_DINOTE_FRAGMENT = "create_fragment" ;
    public final static String  DRAW_FRAGMENT = "draw_fragment";
    public final static String DETAIL_FRAGMENT = "detail_fragment";
    public final static String DETAIL_FRAGMENT_LOVE = "detail_fragment_love";
    public final static String  EXPORT_FRAGMENT = "export_fragment" ;
    public final static String  LOCK_FRAGMENT = "lock_fragment" ;
    public final static String  REMIND_FRAGMENT = "remind_fragment" ;
    public final static String  THEME_FRAGMENT = "theme_fragment" ;
    public final static String  TAG_FRAGMENT = "tag_fragment" ;
    public final static String  FAVORITE_FRAGMENT = "favorite_fragment" ;
    public final static String MOTION_FUN = "motion_fun";
    public final static String MOTION_HATE = "motion_hate";
    public final static String MOTION_COOL = "motion_cool";
    public final static String MOTION_HAPPY = "motion_happy";
    public final static String MOTION_WOW = "motion_wow";
    public final static String MOTION_WORRIED = "motion_worried";
    public final static String MOTION_INTERESTING = "motion_interesting";
    public final static String MOTION_CUTE = "motion_cute";
    public final static String SEND_DATA_OBJ_DINOTE = "send_obj_dinote";
    public final static int PERMISSION_WRITE_EXTERNAL_STORAGE = 101;
    public final static String THEME_SHARE_PER = "theme_int";
    public static final int REQUEST_MOTION = 101;
    public static final String TIME_REMIND = "time_default";
    public static final String RE_CREATE_ALARM = "re_create_alarm" ;
    public static final String TIME_REMIND_DEFAULT = "long_time_default";
    public static final String SEARCH_FRAGMENT = "search_fragment" ;
    public static final String KEY_SEARCH = "key_search";
    public static final String RESULT_SEARCH_FRAGMENT = "result_search_fragment";
    public static final String DETAIL_FRAGMENT_SEARCH = "detail_fragment_search" ;
    public static final String FIRST_INSTALL = "the_first_install" ;
    public static final int LIMIT_SEARCH = 50;


    public static long defaultCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
