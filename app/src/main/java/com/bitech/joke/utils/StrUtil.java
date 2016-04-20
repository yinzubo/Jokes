package com.bitech.joke.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p>
 * Created on 2016/4/20 10:37.
 *
 * @author Lucy
 */
public class StrUtil {

    /**
     * 将<br/>转换成/n
     */
    public static String brToText(String text) {

        String result = "";

        if (text != null) {
            result = text.replace("<br/>", "\n");
        }
        return result;
    }

    /**
     * 去除字符串中的标签以及转义字符
     */
    public static String subHtmlToText(String html) {

        String result = "";

        if (html != null) {
            result = html.replaceAll("</?[^>]+>", "");// 去除标签
            result = result.replace("&nbsp", "");// 空格
            result = result.replace(" ", "");// 空格
            result = result.replace("	", "");// 空格

            result = result.replace(".", "");
            result = result.replace("\"", "'");
            result = result.replace("'", "‘");
            result = result.replace(";", "");
            result = result.replace("\\s*|\t|\r|\n", "");// 去除空格，回车，换行符，制表符
            result = result.replace("<p>", "");
            result = result.replace("</p>", "");
            result = result.replace("</br>", "\r\n");
        }

        return result;
    }

    /**
     * @return 判断电话格式是否正确
     */
    public static boolean isTel(String tel) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[01236789]))\\d{8}$");
        Matcher matcher = pattern.matcher(tel);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * @return 判断是邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
