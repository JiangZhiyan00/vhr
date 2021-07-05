package com.jiangzhiyan.vhr.utils;

import java.util.regex.Pattern;

/**
 * 邮箱校验工具类
 */
public class EmailUtil {
    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isValidEmail("zhangsan123@qq.com"));
    }
}
