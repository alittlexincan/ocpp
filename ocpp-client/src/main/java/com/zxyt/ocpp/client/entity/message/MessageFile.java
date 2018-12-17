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
 * @ClassName MessageFile
 * @Description 一键发布消息文件实体类
 * @Author Liweidong
 * @Version 1.0
 **/
@ApiModel(value = "MessageFile",description = "一键发布文件信息")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "message_file")
public class MessageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 64)
    private String id;

    @Column(name = "message_id",length = 64)
    private String messageId;

    @Column(name = "name",length = 100)
    private String name;

    @Column(name = "size",length = 50)
    private String size;

    @Column(name = "url",length = 200)
    private String url;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

}
