package com.pku.system.util;

public class Constant {
    //设备管理常量
    public static String OPEN = "open";
    public static String CLOSE = "close";
    public static String OPENALL = "open_all";
    public static String CLOSEALL = "close_all";
    public static String DETECTONLINE = "detect_online";

    //推拉流常量
    public static String STARTPULL = "start_pull";
    public static String STOPTPULL = "stop_pull";
    public static String STARTPUSH = "start_push";
    public static String STOPTPUSH= "stop_push";
    public static String STARTPUSHBROADCAST = "start_push_broadcast";
    public static String STOPTPUSHBROADCAST= "stop_push_broadcast";

    public static String FAILSTOPPUSH = "停止推流失败";
    public static String FAILSTOPPUSHBROADCAST = "停止广播失败";
    public static String FAILSTOPPULL = "停止拉流失败";

    public static String STREAMADDRESSPUBLISH = "rtmp://media.xinshenxuetang.com/publish/";
    public static String STREAMADDRESSLIVE = "rtmp://media.xinshenxuetang.com/live/";

    public static long MESSAGETIMEOUT = 120;
    public static long MESSAGETIMEOUTBROADCAST = 120*1000;
}
