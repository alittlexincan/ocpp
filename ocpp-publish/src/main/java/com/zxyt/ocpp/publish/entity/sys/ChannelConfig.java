package com.zxyt.ocpp.publish.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright (C), 2015-2018
 * FileName: Area
 * Author:   JiangXincan
 * Date:     2018-4-30 10:44
 * Description: 渠道配置实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@ApiModel(value = "ChannelConfig",description = "渠道配置信息")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "channel_config")
public class ChannelConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 64)
    private String id;

    @Column(name = "channel_code",length = 50)
    private String channelCode;

    @Column(name = "content",length = 50)
    private String content;

    @Column(name = "area_id",length = 64)
    private String areaId;

    @Column(name = "organization_id",length = 64)
    private String organizationId;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;
}
