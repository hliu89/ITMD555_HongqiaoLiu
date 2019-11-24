package com.xk.trucktrade.app;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


/**
 * 各种常量
 */
public class Constant {
    public static final String ACTION_STARTSOCKET_CLIENT = "com.xk.ACTION_STARTSOCKET_CLIENT";
    public static final String ACTION_SEND_MSG = "com.xk.ACTION_SEND_MSG";
    public static double latitude = 0;
    public static double lontitude = 0;


    public static final TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    public static final SimpleDateFormat shortDayFormat = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static final String APP_ID = "pBHqU5JhLJ4Bu2vYnOhB06P7-gzGzoHsz";
    public static final String APP_KEY = "kiKyJ3BUDry5hdlBs31HqLbl";


    public static final String IS_SIGN_UP = "is_sign_up";


    public static String[] tab_names = {"find car", "find customer", "list", "profile"};

    //以下是SharedPreferences的key
    public static final String SPKEY_CURRENTUSERPHONENUMBER = "SPKEY_CURRENTUSERPHONENUMBER";
    public static final String SPKEY_IMEI = "SPKEY_IMEI";
    public static final String SPKEY_LEANCLOUND_ID = "SPKEY_LEANCLOUND_ID";



    public static final String baseurl_address = "http://192.168.0.27:8080/truck/";//模拟器
    public static String baseurl = baseurl_address + "/servlet/";//测试阶段 baseuri在app里获取 因为考虑到真机和模拟器的不同

    public static String url_login = baseurl + "LoginServlet";
    public static String url_autologin = baseurl + "AutoLoginServlet";
    public static String url_register = baseurl + "RigisterServlet";
    public static String url_logout = baseurl + "LogoutServlet";
    public static String url_truck = baseurl + "TruckServlet";
    public static String url_trucksource = baseurl + "TruckSourceServlet";
    public static String url_upload_head = baseurl + "UploadHeadServlet";
    public static String url_update_userinfo = baseurl + "UpdateUserinfoServlet";
    public static String url_my_friend = baseurl + "FriendServlet";
    public static String url_base_headimg = baseurl_address + "imghead/";

    public static String IM_IP="104.194.103.10";

    //车的类型
    public static final int Variety_1 = 1;
    public static final int Variety_2 = 2;
    public static final int Variety_3 = 3;
    public static final int Variety_4 = 4;
    public static final int Variety_5 = 5;
    public static final int Variety_6 = 6;
    public static final int Variety_7 = 7;
    public static final int Variety_8 = 8;

    //请求的what
    public static final int request_what_login = 0;
    public static final int reuqest_what_logout = 1;
    public static final int reuqest_what_autologin = 2;
    public static final int reuqest_what_register = 3;
    public static final int reuqest_what_addtruck = 4;
    public static final int reuqest_what_queryalltruck = 5;
    public static final int reuqest_what_addtrucksource = 6;
    public static final int reuqest_what_queryalltrucksource = 7;


}
