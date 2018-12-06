package com.zxyt.ocpp.client.entity.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Message
 * @Description 一键发布信息关联地区和渠道实体类
 * @Author Xincan
 * @Version 1.0
 **/
@ApiModel(value = "MessageAreaChannel",description = "一键发布信息关联地区和渠道")
@Table(name = "message_area_channel")
public class MessageAreaChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 64)
    private String id;

    @Column(name = "message_id",length = 64)
    private String messageId;

    @Column(name = "channel_id",length = 64)
    private String channelId;

    @Column(name = "area_id",length = 64)
    private String areaId;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    public MessageAreaChannel() {
    }

    public MessageAreaChannel(String messageId, String channelId, String areaId, Date createTime) {
        this.messageId = messageId;
        this.channelId = channelId;
        this.areaId = areaId;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
