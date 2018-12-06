package com.zxyt.ocpp.publish.task;

import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.publish.service.wechat.IWechatService;
import com.zxyt.ocpp.publish.util.BaoWenExcuteFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 微信三天预报定时任务类
 * 1：从指定盘符读取文件信息
 * 2：调用分发平台数据接口进行推送
 */
@Slf4j
@Component
public class WechatThreeWeatherTask {

    // 微信推送三天预报数据采集路径
    @Value("${wechat.weather.url}")
    private String wechatWeatherUrl;

    // 微信推送服务标题
    @Value("${wechat.weather.title}")
    private String wechatWeatherTitle;

    // 微信推送服务类别
    @Value("${wechat.weather.type}")
    private String wechatWeatherType;

    // 注入微信服务接口
    @Autowired
    private IWechatService wechatService;



    @Scheduled(cron = "0 0 17 * * *")
//    @Scheduled(cron = "0/1 * * * * *")
    private void scheduled(){
        // 时间格式化（日志）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 时间格式化（微信）
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日HH时");

        Date date = new Date();

        log.info("===============3天实况任务开始执行" + sdf.format(date) + "===================");
        String content = getFcstData();
        if(StringUtils.isEmpty(content)){
            log.info("推送内容不能为空");
            return;
        }
        // 拼接发送信息
        JSONObject param = new JSONObject();
        param.put("title", this.wechatWeatherTitle);
        param.put("type", this.wechatWeatherType);
        param.put("time",fmt.format(date));
        param.put("content", content);

        JSONObject result = this.wechatService.pushThreeWeatherInfo(param);
        log.info(result.toJSONString());
        log.info("===============3天实况任务结束执行" + sdf.format(new Date()) + "===================");
    }

    /**
     * 获取三天预报信息
     * @return
     */
    private String getFcstData(){
        // 读取文件
        File sourceFile = new File(getFileName());

        log.info(sourceFile.getName());

        // 判断文件是否为空
        if(sourceFile == null) return null;

        // 返回结果数据集
        // 读取指定的行
        List<String> list =  BaoWenExcuteFile.readAppointedLineNumber(sourceFile);
        // 存储数据
        StringBuffer value = new StringBuffer();
        if(list.size() > 0){
            list.forEach( item -> {
                // 将含有空格的字符串转换为数组
                String[] array = item.replaceAll("\\s+", "@").split("@");
                if(array[0].equals("24")||array[0].equals("48")||array[0].equals("72")){
                    String time = array[0] + "小时"           // 小时
                            ,pnmn = BaoWenExcuteFile.getTQName(Double.valueOf(array[19]))      // 天气现象
                            ,windD = BaoWenExcuteFile.getFXName(Double.valueOf(array[20]))     // 风向
                            ,windS = BaoWenExcuteFile.getFSName(Double.valueOf(array[21]))     // 风速
                            ,minTem = BaoWenExcuteFile.getWDName(Double.valueOf(array[12]))    // 最低温度
                            ,maxTem = BaoWenExcuteFile.getWDName(Double.valueOf(array[11]));   // 最高温度
                    value.append(time + ":" + pnmn + "," + windD + windS +","+ minTem.substring(0, minTem.length()-1) + "~" + maxTem + ";\\r\\n");
                }
            });
        }
        log.info("三天预报：" + value);
        return value.toString();
    }


    /**
     * 获取文件名称
     * @return
     */
    private String getFileName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
        Date date = new Date();
        String time = sdf.format(date);
        String fileName = null;
        if (Integer.parseInt(time.substring(8, 10)) > 17) {
            fileName = this.wechatWeatherUrl + "/Z_SEVP_C_BCLZ_"+time.substring(0, 8)+"083000_P_RFFC-SPCC-"+time.substring(0, 8)+"1200-16812.TXT";
        }else{
            long period=(date.getTime() / 1000) - 60 * 60 * 24;//昨天的日期
            date.setTime(period * 1000);
            time = sdf.format(date);
            fileName = this.wechatWeatherUrl + "/Z_SEVP_C_BCLZ_"+time.substring(0, 8)+"083000_P_RFFC-SPCC-"+time.substring(0, 8)+"1200-16812.TXT";
        }
        return fileName;
    }

}
