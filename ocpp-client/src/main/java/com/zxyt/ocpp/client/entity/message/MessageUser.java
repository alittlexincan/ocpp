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
 * @Description 一键发布接收用户信息实体类
 * @Author Xincan
 * @Version 1.0
 **/
@ApiModel(value = "MessageUser",description = "一键发布接收用户信息")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "message_user")
public class MessageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 64)
    private String id;

    @Column(name = "message_id",length = 64)
    private String messageId;

    @Column(name = "channel_id",length = 2)
    private String channelId;

    @Column(name = "user_group_id",length = 64)
    private String userGroupId;

    @Column(name = "user_group_name",length = 100)
    private String userGroupName;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    private String userName;

    private String userCode;

    private Double longitude;

    private Double latitude;

    private Double altitude;

    private String channelName;

    private String channelCode;
}
