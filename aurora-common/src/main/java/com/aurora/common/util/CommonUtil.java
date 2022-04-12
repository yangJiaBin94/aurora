package com.aurora.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author: Nick
 * @create: 2022-03-25 14:38
 **/
public class CommonUtil {

    /**
     * 获取编号
     *
     * @param head 前缀
     * @return String
     */
    public static String getNo(String head) {

        return head + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);

    }

    /**
     * 随机生成指定长度
     *
     * @param length length
     * @return String
     */
    public static String getCharAndNumber(int length) {
        Random random = new Random();
        StringBuilder valSb = new StringBuilder();
        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        int charLength = charStr.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return valSb.toString();
    }

    public static <T> List<T> transferDateList(Object data, Class<T> tClass) {
        return JSONArray.parseArray(JSONObject.toJSONString(data), tClass);
    }

    public static <T> T transferDate(Object data, Class<T> tClass) {
        return JSONObject.parseObject(JSONObject.toJSONString(data), tClass);
    }

}
