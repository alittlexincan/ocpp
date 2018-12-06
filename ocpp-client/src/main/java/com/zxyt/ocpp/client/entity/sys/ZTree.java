package com.zxyt.ocpp.client.entity.sys;

import io.swagger.annotations.ApiModel;

import javax.persistence.Id;

/**
 * Copyright (C), 2015-2018
 * FileName: ZTree
 * Author:   JiangXincan
 * Date:     2018-4-30 10:44
 * Description: ZTree实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@ApiModel(value = "ZTree",description = "地区树信息")
public class ZTree {

    @Id
    private String id;

    private String name;

    private String code;

    private String pId;

    private int level;  // 级别

    private String channelId;

    private String areaId;

    private String organizationId;

    private Boolean open;

    private int type;

    private int disasterColor;

    private int disasterLevel;

    private String areaName;

    private String organizationName;

    private int isConfig;

    private int isStrategy;

    private String img;

    private int count;  // 子集个数

    private String url;

    private int turn; // 排序

    public ZTree() {}

    public ZTree(String id, String name, String code, String pId, int level, String channelId, String areaId, String organizationId, Boolean open, int type, int disasterColor, int disasterLevel, String areaName, String organizationName, int isConfig, int isStrategy, String img, int count, String url, int turn) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.pId = pId;
        this.level = level;
        this.channelId = channelId;
        this.areaId = areaId;
        this.organizationId = organizationId;
        this.open = open;
        this.type = type;
        this.disasterColor = disasterColor;
        this.disasterLevel = disasterLevel;
        this.areaName = areaName;
        this.organizationName = organizationName;
        this.isConfig = isConfig;
        this.isStrategy = isStrategy;
        this.img = img;
        this.count = count;
        this.url = url;
        this.turn = turn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDisasterColor() {
        return disasterColor;
    }

    public void setDisasterColor(int disasterColor) {
        this.disasterColor = disasterColor;
    }

    public int getDisasterLevel() {
        return disasterLevel;
    }

    public void setDisasterLevel(int disasterLevel) {
        this.disasterLevel = disasterLevel;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(int isConfig) {
        this.isConfig = isConfig;
    }

    public int getIsStrategy() {
        return isStrategy;
    }

    public void setIsStrategy(int isStrategy) {
        this.isStrategy = isStrategy;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
