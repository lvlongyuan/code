package com.yuangege.springboot.utils.baiduaip;

import com.yuangege.springboot.utils.baiduaip.utils.Base64Util;
import com.yuangege.springboot.utils.baiduaip.utils.FileUtil;
import com.yuangege.springboot.utils.baiduaip.utils.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 植物识别
 */
@RequestMapping("/test")
public class Plant {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */

    public static String regxChinese(String source){
        // 将上面要匹配的字符串转换成小写
        source = source.toLowerCase();
        // 匹配的字符串的正则表达式
        String regCharset = "(?<=(me\":)).*?(?=})";
        Pattern p = Pattern.compile(regCharset);//规则
        Matcher m = p.matcher(source);//按规则处理
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group());
        }
        return sb.toString();
    }


    public static String plant(String imageUrl) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
        try {
            // 本地文件路径
            //F:/桌面/毕设/springboot/src/main/java/com/yuangege/springboot/com/baidu/ai/aip/pic/h01.JPG
            String filePath = imageUrl;
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.000206f2a711ec629610d13a6a44f21d.2592000.1656036039.282335-25948618";

            String result = HttpUtil.post(url, accessToken, param);


//            String result = "{'result':[{'score':0.85609126,'name':'百子莲'},{'score':0.004069349,'name':'大花葱'},log_id':1517759390755887977}";
            // 要匹配的字符串
            String source = result;
            return regxChinese(source);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



//    public static void main(String[] args) {
//        Plant.plant("F:/桌面/毕设/springboot/src/main/java/com/yuangege/springboot/com/baidu/ai/aip/pic/h01.JPG");
//    }

}
