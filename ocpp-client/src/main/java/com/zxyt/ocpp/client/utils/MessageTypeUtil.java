package com.zxyt.ocpp.client.utils;

/**
 *
 * @ClassName MessageTypeUtil
 * @Author Xincan
 * @Version 1.0
 **/
public class MessageTypeUtil {

    /**
     * 判断文件对应的业务类型类型
     * @param str
     * @return
     */
    public static int getType(String str){
        if(str.indexOf("短期预报") != -1) return 0;
        if(str.indexOf("中期预报") != -1) return 1;
        if(str.indexOf("长期预报") != -1) return 2;
        if(str.indexOf("气象专题专报") != -1) return 3;
        if(str.indexOf("重大气象专题专报") != -1) return 4;
        if(str.indexOf("其他") != -1) return 5;
        return 5;
    }

}
