package com.aurora.common.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Nick
 * @create: 2022-04-02 10:43
 **/
public class StringUtil extends StringUtils {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+\\.?[0-9]*");

    /**
     * 是否为数字
     *
     * @param str : str
     * @return boolean
     * @author Nick
     * @date 2022/04/02
    */
    public static boolean isNumberStr(String str) {
        Matcher isNum = NUMBER_PATTERN.matcher(str);
        return isNum.matches();
    }
}
