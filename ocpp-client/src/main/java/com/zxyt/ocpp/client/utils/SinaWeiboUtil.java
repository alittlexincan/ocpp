package com.zxyt.ocpp.client.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SinaWeiboUtil
 * @Description TODO
 * @Author Xincan
 * @Version 1.0
 **/
public class SinaWeiboUtil {



    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        String appId = "3948489948";
        String appKey = "b5f5188665ec31642e9e79774a5374de";
        String grant_type = "authorization_code";
        String redirect_uri = "http://www.bjzxyt.cn";

//        String sinaUrl = "https://api.weibo.com/oauth2/authorize?client_id=" + appId + "&redirect_uri=" + redirect_uri + "&response_type=code";
//        System.out.println(sinaUrl);

        // 获取codeurl


        // 获取tokenurl
//        String url = "https://api.weibo.com/oauth2/access_token?grant_type=authorization_code&client_id=3948489948&client_secret=b5f5188665ec31642e9e79774a5374de&code=1e8f4808a047e59daa42f4a48319b559&redirect_uri=http://www.bjzxyt.cn";
//        JSONObject result = restTemplate.postForObject(url, "POST", JSONObject.class);
//        System.out.println(result);
//       String token = result.getString("access_token");
//

        try {
            Map<String, Object> map = new HashMap<>();
            map.put(URLEncoder.encode("access_token","UTF-8"), URLEncoder.encode("2.00Wey8HGkb9N_E1a39a4a5c60zKNXV","UTF-8"));
            map.put(URLEncoder.encode("status","UTF-8"),URLEncoder.encode("该条微博由JAVA程序发送，目的测试其微博相关api，并无实际用途。http://www.bjzxyt.cn", "UTF-8"));
            System.out.println(map);

            JSONObject res = restTemplate.postForObject("https://api.weibo.com/2/statuses/update.json", HttpMethod.POST, JSONObject.class, map);
            System.out.println(res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }





}
