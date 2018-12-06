package com.zxyt.ocpp.publish.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaoWenExcuteFile {

    /**
     * 读取范围行
     * @param sourceFile
     * @throws Exception
     */
    public static List<String> readAppointedLineNumber(File sourceFile) {
        List<String> list = new ArrayList<>();
        try{
            FileReader in = new FileReader(sourceFile);
            LineNumberReader reader = new LineNumberReader(in);
            String contentLine = "";
            JSONObject line = getTotalLines(sourceFile);
            while (contentLine != null) {
                contentLine = reader.readLine();
                if (reader.getLineNumber() >= line.getInteger("start") && reader.getLineNumber() <=  line.getInteger("end")) {
                    list.add(contentLine);
                }
            }
            reader.close();
            in.close();
            return list;
        }catch (Exception e){
            log.error("【"+sourceFile.getPath()+"】文件内容读取错误: " + e.getMessage());
        }
        return null;
    }

    /**
     * 根据文件内容，读取文件行数、将要获取的行数，到那行为止
     * @param
     * @param file
     * @return {count:0,start:0,end:0}
     */
    public static JSONObject getTotalLines(File file){
        JSONObject json = new JSONObject();
        try {
            int count = 0, start = 0;
            FileReader fileReader = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(fileReader);
            if (file.isFile() && file.exists()) {
                String lineTxt = null;
                while ((lineTxt = reader.readLine()) != null) {
                    count++;
                    if (lineTxt.indexOf("53930") > -1) {
                        start = reader.getLineNumber();
                    }
                }
                json.put("code",200);
                json.put("msg","读取成功");
                json.put("count",count);
                json.put("start", start + 1);
                json.put("end", start + 14);


            } else {
                json.put("code",404);
                json.put("msg","找不到文件");
            }
        } catch (Exception e) {
            json.put("code",404);
            json.put("msg","读取文件出错" + e.getMessage());
        }
        log.info(json.toJSONString());
        return json;
    }

    /**
     * 温度转换
     * @param number
     * @return
     */
    public static String getWDName(double number){

        if(number == 999.9){
            return "暂无数据";
        }else {
            return number + "℃";
        }

    }

    /**
     * 风速转换
     * @param number
     * @return
     */
    public static String getFSName(double number){
        String name = "";
        if(number == 0) return "≤3级";
        if(number == 1) return "3-4级";
        if(number == 2) return "4.5级";
        if(number == 3) return "5-6级";
        if(number == 4) return "6-7级";
        if(number == 5) return "7-8级";
        if(number == 6) return "8-9级";
        if(number == 7) return "9-10级";
        if(number == 8) return "10-11级";
        if(number == 9) return "11-12级";
        return name;
    }

    /**
     * 风向转换
     * @param number
     * @return
     */
    public static String getFXName(double number){
        String name = "";
        if(number == 0) return "无风向";
        if(number == 1) return "东北风";
        if(number == 2) return "东风";
        if(number == 3) return "东南风";
        if(number == 4) return "南风";
        if(number == 5) return "西南风";
        if(number == 6) return "西风";
        if(number == 7) return "西北风";
        if(number == 8) return "北风";
        if(number == 9) return "旋转/不定";
        return name;
    }

    /**
     * 天气现象转换
     * @param number
     * @return
     */
    public static String getTQName(double number){
        String name = "";
        if(number == 0) return "晴";
        if(number == 1) return "多云";
        if(number == 2) return "阴";
        if(number == 3) return "阵雨";
        if(number == 4) return "雷阵雨";
        if(number == 5) return "雷阵雨并伴有冰雹";
        if(number == 6) return "雨夹雪";
        if(number == 7) return "小雨";
        if(number == 8) return "中雨";
        if(number == 9) return "大雨";
        if(number == 10) return "暴雨";
        if(number == 11) return "大暴雨";
        if(number == 12) return "特大暴雨";
        if(number == 13) return "阵雪";
        if(number == 14) return "小雪";
        if(number == 15) return "中雪";
        if(number == 16) return "大雪";
        if(number == 17) return "暴雪";
        if(number == 18) return "雾";
        if(number == 19) return "冻雨";
        if(number == 20) return "沙尘暴";
        if(number == 21) return "小雨-中雨";
        if(number == 22) return "中雨-大雨";
        if(number == 23) return "大雨-暴雨";
        if(number == 24) return "暴雨-大暴雨";
        if(number == 25) return "大暴雨-特大暴雨";
        if(number == 26) return "小雪-中雪";
        if(number == 27) return "中雪-大雪";
        if(number == 28) return "大雪-暴雪";
        if(number == 29) return "浮尘";
        if(number == 30) return "扬沙";
        if(number == 31) return "强沙尘暴";
        return name;
    }

}
