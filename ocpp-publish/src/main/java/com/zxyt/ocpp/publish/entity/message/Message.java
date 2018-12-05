package com.zxyt.ocpp.publish.entity.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Message
 * @Description 一键发布消息实体类
 * @Author Xincan
 * @Version 1.0
 **/
@ApiModel(value = "Message",description = "一键发布信息")
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 64)
    private String id;

    @Column(name = "title",length = 100)
    private String title;

    @Column(name = "type",length = 2)
    private Integer type;

    @Column(name = "area_id",length = 64)
    private String areaId;

    @Column(name = "area_name",length = 100)
    private String areaName;

    @Column(name = "organization_id",length = 64)
    private String organizationId;

    @Column(name = "organization_name",length = 100)
    private String organizationName;

    @Column(name = "content",length = 4000)
    private String content;

    @Column(name = "record",length = 1)
    private Integer record;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    public Message() {
    }

    public Message(String title, Integer type, String areaId, String areaName, String organizationId, String organizationName, String content, Integer record, Date createTime) {
        this.title = title;
        this.type = type;
        this.areaId = areaId;
        this.areaName = areaName;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.content = content;
        this.record = record;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
