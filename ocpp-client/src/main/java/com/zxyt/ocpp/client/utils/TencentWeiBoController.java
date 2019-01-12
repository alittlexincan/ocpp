package com.zxyt.ocpp.client.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @ClassName: TencentWeboController 
 * @Description: 腾讯微博控制层
 * @author JiangXincan 
 * @date 2016年1月6日 上午10:40:52 
 * @version 1.0
 */
@Controller
public class TencentWeiBoController {



	private static String client_id;

	private static String client_secret;

	private static String redirect_uri;

	private static String response_type = "code";

	private static String code_url;

	private static String state = "ocpp";


	private static String grant_type ="code";

	private static String token_url = "https://graph.qq.com/oauth2.0/authorize";


	private static String open_url;

	private static String weiboChannelId;

	private static String weiboAreaId;



	/**
	 * 获取腾讯code
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/getcode")
	@ResponseBody
	public String getCode(@RequestParam Map<String, Object> map){
		String response_type = this.response_type;
		String client_id	= map.get("appId").toString();
		String client_secret = map.get("appKey").toString();
		String redirect_uri = this.redirect_uri;
		String state = this.state;
		
		String codeUrl = this.code_url+"?response_type="+response_type+
				"&client_id="+client_id+
				"&client_secret="+client_secret+
				"&redirect_uri="+URLEncoder.encode(redirect_uri)+
				"&state="+state;
		
		return codeUrl;
	}

	/**
	 * 发送信息
	 * @param map
	 * @return
	 */
	public static Integer authorization(Map<String, Object> map){
		// 获取token路径
		String tokenUrl = getTokenURL(map);
		// 获取token
		JSONObject token = httpRequestSSL(tokenUrl,"GET",null,1);
		if(token==null){
			return 1; // 获取token失败
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accessToken", token.getString("access_token"));
		param.put("refreshToken", token.getString("refresh_token"));
		param.put("expiresIn", token.getString("expires_in"));
		param.put("code", map.get("code"));
		param.put("keyId", map.get("keyId"));
		// 获取openId路径
		String openIdUrl = getOpenIdURL(param);
		// 获取openId
		JSONObject open = httpRequestSSL(openIdUrl,"GET",null,2);
		if(open==null){
			return 2;// 获取openId
		}
		param.put("content", "QQ{^}"+map.get("appId")+"{^}"+open.getString("openid")+"{^}"+token.getString("access_token")+"{^}"+map.get("redirectUri"));

		param.put("openId", open.getString("openid"));
		
		if(!StringUtils.isEmpty(token.getString("expires_in"))) {
			int second = Integer.valueOf(token.getString("expires_in"));
		}

		return 3;
		
        
	}

	/**
	 * 获取token路径
	 * @param map
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String getTokenURL(Map<String, Object> map){

		String code = map.get("code").toString();

		String tokenUrl = token_url+"?";
				tokenUrl += "response_type="+grant_type+"&";
				tokenUrl += "client_id="+client_id+"&";
				tokenUrl += "client_secret="+client_secret+"&";
				tokenUrl += "code="+code+"&";
				tokenUrl += "redirect_uri="+URLEncoder.encode(redirect_uri)+"&state="+state;
		return tokenUrl;
	}
	
	/**
	 * 获取openId路径
	 * @param map
	 * @return
	 */
	private static String getOpenIdURL(Map<String, Object> map){
		return open_url+"?access_token="+map.get("accessToken");
	}
	
	/**
	 * 获取数据
	 * @param tokenUrl
	 * @param requestMethod
	 * @param outputStr
	 * @param num
	 * @return
	 */
	private static JSONObject httpRequestSSL(String tokenUrl, String requestMethod, String outputStr,int num) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(tokenUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			if(num==1){
				//获取accressToken，refreshToken
				jsonObject = getTokenData(buffer.toString());
			}else{
				//获取openId
				jsonObject = getOpenIdData(buffer.toString());
			}
			
		} catch (ConnectException ce) {
			ce.getLocalizedMessage();
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		return jsonObject;
	}
	/**
	 * 获取accressToken，refreshToken
	 * @param str
	 * @return
	 */
	private static JSONObject getTokenData(String str){
		if(str.contains("callback")){
			return null;
		}else{
			 str = "{'"+str.replaceAll("&", "', '").replaceAll("=", "':'")+"'}";
			return JSONObject.parseObject(str);
		}
	}

	/**
	 * 获取openId
	 * @param str
	 * @return
	 */
	private static JSONObject getOpenIdData(String str){
		if(!str.contains("openid")){
			return null;
		}else{
			str = str.substring(str.indexOf("{"), str.indexOf("}")+1);
			return JSONObject.parseObject(str);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 腾讯发微博
	private static JSONObject httpRequestSSL(String tokenUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(tokenUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			jsonObject = getContentCode(buffer.toString());
			
		} catch (ConnectException ce) {
			ce.getLocalizedMessage();
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		return jsonObject;
	}
	private static JSONObject getContentCode(String str){
		
			return JSONObject.parseObject(str);
	}
	public static void main(String[] args) {


		RestTemplate rest = new RestTemplate();

		client_id = "101535330";
		redirect_uri = "http://www.bjzxyt.cn/index.html";
		client_id	= "101535330";
		client_secret = "f68149e7da13e95ddd3172c6442dc1aa";

		Map<String, Object> map = new HashMap<>();
		map.put("code","code");
		authorization(map);



//		String tokenUrl = getTokenURL(map);




		//获取用户openId

//		String openUrl = "https://graph.qq.com/oauth2.0/me?access_token=D7CB386170CE7FABD3231DF41A7C1792";
//
//		JSONObject openId = rest.getForObject(openUrl, JSONObject.class);
//
//		System.out.println(openId);

//		String url = "https://graph.qq.com/t/add_t?";
//		String param =
//				"access_token=D7CB386170CE7FABD3231DF41A7C1792"
//				+ "&oauth_consumer_key=101535330"
//				+ "&openid=6DF071D370325F75AE54B4ADD686CC4D"
//				+ "&format=json"
//				+ "&content=测试腾讯微博发送";
//		JSONObject json = httpRequestSSL(url,"POST",param);
//
////		JSONObject json = rest.postForObject(url + param, "POST",JSONObject.class);
//
//		Integer ret = json.getInteger("ret");
//		if(ret==0){
//			System.out.println("微博发送成功");
//		}else{
//			System.out.println("微博发送失败");
//		}
//		System.out.println(json);
	}
}
