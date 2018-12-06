package com.zxyt.ocpp.client.entity.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Message
 * @Description 一键发布信息关联地区和渠道实体类
 * @Author Xincan
 * @Version 1.0
 **/
@ApiModel(value = "MessageAreaChannel",description = "一键发布信息关联地区和渠道")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

}
