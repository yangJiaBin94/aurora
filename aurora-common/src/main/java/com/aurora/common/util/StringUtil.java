package com.aurora.common.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
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


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您要问的问题：");
        while (true){
            String str = sc.nextLine();
            //0、将汉字转换为UTF-8编码
            String string = URLEncoder.encode(str, "UTF-8");
            //1、url对象
            URL url = new URL("https://api.jisuapi.com/iqa/query?appkey=62958a3a6ef3c56d&question=" + string);
            //2、打开服务器连接，得到连接对象 conn
            URLConnection conn = url.openConnection();
            //3、获取加载数据的字节输入流 is
            InputStream is = conn.getInputStream();
            //4、将is装饰为能一次读取一行的字符输入流 br
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            //5、加载一行数据
            String text = br.readLine();
            //6、显示
            System.out.println(JSONObject.parseObject(text).getJSONObject("result").getString("content"));
            //7、释放资源
            br.close();
        }
    }

}
