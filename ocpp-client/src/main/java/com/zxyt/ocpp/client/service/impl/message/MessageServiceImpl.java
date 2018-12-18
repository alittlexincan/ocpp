package com.zxyt.ocpp.client.service.impl.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xincan.utils.doc.ReaderFileUtil;
import com.xincan.utils.ftp.FTPConfig;
import com.xincan.utils.ftp.FTPUtil;
import com.zxyt.ocpp.client.config.common.universal.AbstractService;
import com.zxyt.ocpp.client.entity.message.Message;
import com.zxyt.ocpp.client.entity.message.MessageAreaChannel;
import com.zxyt.ocpp.client.entity.message.MessageFile;
import com.zxyt.ocpp.client.entity.message.MessageUser;
import com.zxyt.ocpp.client.entity.sys.ChannelConfig;
import com.zxyt.ocpp.client.mapper.message.IMessageAreaChannelMapper;
import com.zxyt.ocpp.client.mapper.message.IMessageMapper;
import com.zxyt.ocpp.client.mapper.message.IMessageUserMapper;
import com.zxyt.ocpp.client.mapper.sys.IChannelConfigMapper;
import com.zxyt.ocpp.client.service.message.IMessageService;
import com.zxyt.ocpp.client.utils.MessageTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: JiangXincan
 * @Description:
 * @Date: Created in 16:29 2018-4-18
 * @Modified By:
 */
@Slf4j
@Service("messageService")
public class MessageServiceImpl extends AbstractService<Message> implements IMessageService {

    /**
     * 注入渠道配置数据接口层
     */
    @Autowired
    private IChannelConfigMapper channelConfigMapper;

    /**
     * 注入一键发布消息数据接口层
     */
    @Autowired
    private IMessageMapper messageMapper;

    /**
     * 注入一键发布消息关联地区和渠道数据接口层
     */
    @Autowired
    private IMessageAreaChannelMapper messageAreaChannelMapper;

    /**
     * 注入一键发布消息接收受众数据接口层
     */
    @Autowired
    private IMessageUserMapper messageUserMapper;


    /**
     * 添加预警相关信息
     *  1：添加一键发布基本信息
     *  2：添加一键发布内容信息
     *  3：添加一键发布群组信息
     *  4：添加一键发布流程信息
     *  5：添加一键发布上传文件信息
     * @param map
     * @return
     */
    @Override
    @Transactional
    public JSONObject insert(Map<String, Object> map,MultipartFile[] files) {

        Subject subject = SecurityUtils.getSubject();
        JSONObject employee = (JSONObject) subject.getSession().getAttribute("employee");
        map.put("employeeId",employee.getString("employeeId"));
        map.put("employeeName",employee.getString("employeeName"));
        map.put("areaId", employee.getString("areaId"));
        map.put("areaName", employee.getString("areaName"));
        map.put("organizationId",employee.getString("organizationId"));
        map.put("organizationName",employee.getString("organizationName"));
        map.put("organizationCode",map.get("organizationCode"));

        // 转换接收信息
        JSONObject json = new JSONObject(map);

        // 获取渠道（将字符串转换为JSONArray数组对象）同key值数据覆盖
        JSONArray channel =  JSONArray.parseArray(json.getString("channels"));
        json.put("channels", channel);

        // 获取地区（将字符串转换为JSONArray数组对象）同key值数据覆盖
        JSONArray area = JSONArray.parseArray(json.getString("areas"));
        json.put("areas",area);

        // 获取群组（将字符串转换为JSONArray数组对象）同key值数据覆盖
        JSONObject group = JSONObject.parseObject(json.getString("groups"));
        json.put("groups",group);

        Message message = addMessage(json);             // 1：添加一键发布基本信息
        String messageId = message.getId();            // 2：预警基础信息ID
        addMessageAreaBindChannel(json, messageId);    // 3：添加一键发布内容信息
        addMessageUser(json, messageId);               // 4：添加一键发布群组信息

        getMessageUserInfo(json, messageId);

        json.put("id", messageId);
        //添加文件列表
        addMessageFile(message,files);
        if(!StringUtils.isEmpty(messageId)){
            json.put("code", 200);
            json.put("msg","一键发布成功");
            return json;
        }
        json.put("code", 500);
        json.put("msg","一键发布失败");
        return json;
    }

    /**
     * 1：添加一键发布基本信息
     * @param json          接收信息
     * @return
     */
    private Message addMessage(JSONObject json){
        Message message = new Message();
        message.setTitle(json.getString("title"));
        message.setType(json.getInteger("type"));
        message.setAreaId(json.getString("areaId"));
        message.setAreaName(json.getString("areaName"));
        message.setOrganizationId(json.getString("organizationId"));
        message.setOrganizationName(json.getString("organizationName"));
        message.setContent(json.getString("content"));
        message.setRecord(json.getInteger("record"));
        this.messageMapper.insertSelective(message);
        return  message;
    }

    /**
     * 2：添加一键发布内容信息
     * @param json          接收信息
     * @param messageId    一件发布信息ID
     * @return
     */
    private int addMessageAreaBindChannel(JSONObject json, String messageId){
        // 存储对象
        List<MessageAreaChannel> list = new ArrayList<>();
        // 获取渠道
        JSONArray channels =  json.getJSONArray("channels");
        // 获取地区
        JSONArray areas =  json.getJSONArray("areas");
        // 循环获取渠道
        for(int i = 0; i<channels.size(); i++){
            JSONObject channel = channels.getJSONObject(i);
            String channelId = channel.getString("channelId");
            // 循环获取地区
            for(int j = 0; j<areas.size(); j++){
                JSONObject area = areas.getJSONObject(j);
                String areaId = area.getString("areaId");
                // 组装预警内容
                MessageAreaChannel messageAreaChannel = new MessageAreaChannel();
                messageAreaChannel.setMessageId(messageId);
                messageAreaChannel.setChannelId(channelId);
                messageAreaChannel.setAreaId(areaId);
                list.add(messageAreaChannel);
            }
        }
        return this.messageAreaChannelMapper.insertBatch(list);
    }

    /**
     * 3：添加一键发布群组信息
     * @param json          接收信息
     * @param messageId    一键发布信息ID
     * @return
     */
    private int addMessageUser(JSONObject json, String messageId){
        // 存储对象
        List<MessageUser> list = new ArrayList<>();
        // 获取渠道
        JSONArray channels =  json.getJSONArray("channels");
        // 获取群组
        JSONObject group =  json.getJSONObject("groups");
        // 循环获取渠道
        for(int i = 0; i<channels.size(); i++){
            JSONObject channel = channels.getJSONObject(i);
            JSONArray groupArray = group.getJSONArray(channel.getString("channelId"));
            for(int j = 0; j<groupArray.size(); j++){
                JSONObject userGroup = groupArray.getJSONObject(j);
                String userGroupName = userGroup.getString("userGroupName");
                if(userGroupName.indexOf("(") > -1){
                    userGroupName = userGroupName.substring(0,userGroupName.indexOf("("));
                }
                MessageUser messageUser = new MessageUser();
                messageUser.setMessageId(messageId);
                messageUser.setChannelId(channel.getString("channelId"));
                messageUser.setUserGroupId(userGroup.getString("userGroupId"));
                messageUser.setUserGroupName(userGroupName);
                list.add(messageUser);
            }
        }
        return this.messageUserMapper.insertBatch(list);
    }


    /**
     * 根据id获取预警发布对象信息
     * @param result
     * @param messageId
     * @return
     */
    private void getMessageUserInfo(JSONObject result, String messageId){
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        List<MessageUser> list = this.messageUserMapper.selectByMessageId(map);
        if(list.size() > 0){
            // 组装渠道下的群组，一个渠道可能对应多个群组
            JSONObject group = new JSONObject();
            // 组装渠道下的用户，一个群组可能对应多个用户
            JSONObject user = new JSONObject();
            // 渠道去重
            Map<String, List<MessageUser>> groupList = list.stream().collect(Collectors.groupingBy(MessageUser::getChannelId));
            list.forEach(weu -> {
                // 群组过滤不必要字段
                JSONArray groupArray = new JSONArray();
                // 渠道对应的群组去重
                groupList.get(weu.getChannelId()).stream().collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getChannelId() + ";" + o.getUserGroupId()))),
                        ArrayList::new
                )).forEach( gr -> {
                    JSONObject g = new JSONObject();
                    g.put("userGroupId", gr.getUserGroupId());
                    g.put("userGroupName", gr.getUserGroupName());
                    groupArray.add(g);
                });
                // 用户过滤不必要字段
                JSONArray userGroupArray = new JSONArray();
                // 组装群组下的受众
                list.stream()
                        .filter(p -> p.getUserCode() != null && p.getUserGroupId().equals(weu.getUserGroupId()))
                        .collect(Collectors.toList())
                        .forEach(ug -> {
                            JSONObject g = new JSONObject();
                            g.put("userName", ug.getUserName());
                            g.put("userCode", ug.getUserCode());
                            g.put("channelCode", ug.getChannelCode());
                            g.put("longitude", ug.getLongitude());
                            g.put("latitude", ug.getLatitude());
                            g.put("altitude", ug.getAltitude());
                            userGroupArray.add(g);
                        });
                // 在当前渠道下追加群组
                group.put(weu.getChannelId(), groupArray);
                // 当前群组下追加受众用户
                user.put(weu.getUserGroupId(), userGroupArray);
            });
            result.put("users", user);
            result.put("groups", group);
        }
    }


    /**
     * 查询并下载FTP文件数据并进行读取
     * @param map
     * @return
     */
    @Override
    public JSONObject  selectFTPFileInfo(Map<String, Object> map){

        // 1：获取FTP配置文件
        JSONObject result = new JSONObject();
        List<ChannelConfig> channelConfigs = this.channelConfigMapper.findAll(map);
        if(channelConfigs.size() ==0){
            result.put("code", 500);
            result.put("msg", "请配置系统FTP!");
            return result;
        }

        // 2：将配置文件信息转换成java对象
        ChannelConfig channelConfig = channelConfigs.get(0);
        FTPConfig ftpConfig = JSON.parseObject(channelConfig.getContent(), new TypeReference<FTPConfig>() {});

        // 3:FTP文件处理
        JSONObject ftp = executeFTP(ftpConfig);
        if(ftp.getInteger("code") == 500) return ftp;

        // 4：获取处理后的文件路径和文件名称
        String localFilePath = ftp.getString("localFilePath"),
                localFileName = ftp.getString("localFileName");

        // 5：读取文件内容
        result = ReaderFileUtil.read(localFilePath);

        // 6：匹配文件对应的业务类型
        result.put("type", MessageTypeUtil.getType(localFileName));
        result.put("fileName",localFileName.substring(0, localFileName.lastIndexOf(".")));
        return result;
    }


    /**
     * FTP数据获取处理
     * @param ftpConfig
     * @return
     */
    private JSONObject executeFTP(FTPConfig ftpConfig){
        // 返回结果对象
        JSONObject result = new JSONObject();
        // 1：登录FTP
        boolean bool = FTPUtil.login(ftpConfig);
        if(!bool){
            result.put("code", 500);
            result.put("msg", "FTP登录失败，请检查配置");
            log.error("FTP登录失败，请检查配置");
            return result;
        }
        // 2：获取FTP最新文件 fileName, fileCreateTime
        JSONObject newFTPFile = FTPUtil.getNewFile();

        // 3: 判断最新文件是否存在，如果不存在则直接返回
        if(newFTPFile.getInteger("code") == 500) return newFTPFile;

        // 4: 获取FTP文件名称，组装本地文件路径
        String newFTPFileName = newFTPFile.getString("fileName"),
                localFilePath =  ftpConfig.getLocalPath() + "/" + newFTPFileName;

        // 5：判断本地文件是否存在，如果存在则直接返回，否则下载读取
        File localFile = new File(localFilePath);
        if(localFile.exists()){
            result.put("code", 200);
            result.put("msg", "文件获取成功");
            result.put("localFileName", newFTPFileName);
            result.put("localFilePath", localFilePath);
            log.info("文件获取成功{}", result.toJSONString());
            return result;
        }

        // 6：下载FTP最新文件到指定路径下
        boolean downBool = FTPUtil.downloadFile(localFilePath, newFTPFileName);
        if(!downBool){
            result.put("code", 500);
            result.put("msg", "FTP文件获取失败，请检查FTP目录下是否有文件");
            log.error("FTP文件获取失败，请检查FTP目录下是否有文件");
            return result;
        }
        // 7：关闭FTP
        FTPUtil.close();

        result.put("code", 200);
        result.put("msg", "文件获取成功");
        result.put("localFileName", newFTPFileName);
        result.put("localFilePath", localFilePath);
        log.info("文件获取成功{}", result.toJSONString());
        return result;
    }


    /**
     * 获取FTP信息
     * @return
     */
    public FTPConfig getFTPConfig(){
        ChannelConfig channelConfig = this.channelConfigMapper.findConfigByCode();
        FTPConfig ftpConfig = new FTPConfig();
        // 全局赋值
        JSONObject cc = JSONObject.parseObject(channelConfig.getContent());
        ftpConfig.setHost(cc.getString("host"));
        ftpConfig.setPort(Integer.parseInt(cc.getString("port")));
        ftpConfig.setUser(cc.getString("user"));
        ftpConfig.setPassword(cc.getString("password"));
        return ftpConfig;
    }

    /**
     * 1：添加一键发布基本信息(上传的文件)
     * @param
     * @return
     */
    public void addMessageFile(Message message,MultipartFile[] files){
        MessageFile messageFile = new MessageFile();
        messageFile.setMessageId(message.getId());
        messageFile.setName(message.getTitle());
        String url = "";
        String fileSize = "";
        for (MultipartFile file : files) {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            url = url + fileName + ",";
            fileSize = fileSize + file.getSize()+",";
        }
        messageFile.setUrl(url);
        messageFile.setCreateTime(message.getCreateTime());
        messageFile.setSize(fileSize);
        messageMapper.insertMessageFile(messageFile);
    }

}
