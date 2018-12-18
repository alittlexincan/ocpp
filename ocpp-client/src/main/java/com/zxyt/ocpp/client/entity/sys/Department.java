package com.zxyt.ocpp.client.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Copyright (C), 2015-2018
 * FileName: Organization
 * Author:   LiWeidong
 * Date:     2018-4-30 10:44
 * Description: 机构实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@ApiModel(value = "Department",description = "部门信息")
@Getter
@Setter
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 64)
    private String id;

    @Column(name = "name",length = 50)
    private String departmentName;

    @Column(name = "code",length = 50)
    private String code;






}
