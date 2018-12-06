package com.zxyt.ocpp.publish.service.impl.record;

import com.alibaba.fastjson.JSONObject;
import com.xincan.utils.file.FileUtil;
import com.xincan.utils.ftp.FTPConfig;
import com.xincan.utils.ftp.FTPUtil;
import com.xincan.utils.xml.XMLUtil;
import com.zxyt.ocpp.publish.mapper.publish.ICallBackMapper;
import com.zxyt.ocpp.publish.service.record.IRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Slf4j
@Service("recordService")
public class RecordServiceImpl implements IRecordService {

    // ftp 路径
    @Value("${ftp.url}")
    private String url;

    // ftp 端口号
    @Value("${ftp.port}")
    private int port;

    // 上传文件存放位置
    @Value("${ftp.path}")
    private String path;

    // 登录名称
    @Value("${ftp.login.name}")
    private String loginName;

    // 登录密码
    @Value("${ftp.login.password}")
    private String loginPassword;

    // 下载文件存放位置
    @Value("${ftp.download.file}")
    private String downloadFile;

    // 上传文件本地预存放位置
    @Value("${ftp.upload.file}")
    private String uploadFile;

    @Autowired
    private ICallBackMapper callBackMapper;

    /**
     * 国突对接（ftp方式）
     * @param json
     * @return
     */
    @Override
    @Async
    public void record(JSONObject json) {
        JSONObject result = new JSONObject();
        // 1：生成CAP协议文件,保存到本地指定文件夹下
        File fileXML = XMLUtil.setRecordXML(json,this.uploadFile);
        if(fileXML==null) {
            log.error("CAP协议文件生成失败！");
            result = callBackMsg(json.getString("id"), "国突对接：CAP协议文件生成失败");
        }else {
            log.info("CAP协议文件生成成功！文件路径为：【" + fileXML.getPath() + fileXML.getName() + "】");
            // 2：将要打包文件集合组装：获取文件集合路径，可以多文件打包
            File file = new File(fileXML.getPath());
            List<File> list = new ArrayList<>();
            list.add(file);
            // 3：打包文件
            File zipFile = FileUtil.getZipFile(list, this.uploadFile, fileXML.getName().substring(0, fileXML.getName().lastIndexOf(".")) + ".zip");
            if (zipFile == null) {
                log.error("CAP协议文件打包失败！");
                result = callBackMsg(json.getString("id"), "国突对接：CAP协议文件打包失败");
            }else {
                log.info("CAP协议文件打包成功！文件路径为：【" + zipFile.getPath() + zipFile.getName() + "】");
                // 3：FTP上传：配置基础信息；登录；文件开始上传；最后关闭
                FTPConfig config = new FTPConfig(this.url, this.port, this.path, this.loginName, this.loginPassword);
                boolean isLogin = FTPUtil.login(config);
                if (!isLogin) {
                    log.error("FTP登录失败！【登录地址：" + this.url + "、登录端口：" + this.port + "、登录名称：" + this.loginName + "、登录密码：" + this.loginPassword + "、文件夹：" + this.path + "】");
                    result = callBackMsg(json.getString("id"), "国突对接：FTP登录失败！【登录地址：" + this.url + "、登录端口：" + this.port + "、登录名称：" + this.loginName + "、登录密码：" + this.loginPassword + "、文件夹：" + this.path + "】");
                }else {
                    log.info("FTP登录成功！【登录地址：" + this.url + "、登录端口：" + this.port + "、登录名称：" + this.loginName + "、登录密码：" + this.loginPassword + "、文件夹：" + this.path + "】");
                    boolean isUpload = FTPUtil.uploadFile(zipFile.getPath(), zipFile.getName());
                    FTPUtil.close();
                    if (!isUpload) {
                        log.info("CAP协议文件FTP上传失败！");
                        result = callBackMsg(json.getString("id"), "国突对接：CAP协议文件FTP上传失败");
                    }else {
                        log.info("CAP协议文件FTP上传成功！文件路径为：【" + zipFile.getPath() + zipFile.getName() + "】");
                        result.put("messageId", json.getString("id"));
                        result.put("channelCode", "COUNTRY");
                        result.put("total", 1);
                        result.put("success", 1);
                        result.put("fail", 0);
                        result.put("work","国突对接：CAP协议文件FTP上传成功！文件路径为：【" + zipFile.getPath() + zipFile.getName() + "】");
                    }
                }
                // 4: 关闭FTP
                FTPUtil.close();
            }
        }

        // 插入回执状态主表信息
        this.callBackMapper.insertMainMsg(result);

    }

    /**
     * 失败信息
     * @param id
     * @param msg
     * @return
     */
    private JSONObject callBackMsg(String id, String msg){
        JSONObject result = new JSONObject();
        result.put("messageId", id);
        result.put("channelCode", "COUNTRY");
        result.put("total", 1);
        result.put("success", 0);
        result.put("fail", 1);
        result.put("work",msg);
        return result;
    }
}
