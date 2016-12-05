package cn.asens.connector.http;

/**
 * Created by asens on 2016/11/26.
 */
public class Constants {
    public static final String Package="cn.asens.connector.http";
    public static String WEB_ROOT=Thread.currentThread().getContextClassLoader().getResource("").toString().replace("file:/","");
}
