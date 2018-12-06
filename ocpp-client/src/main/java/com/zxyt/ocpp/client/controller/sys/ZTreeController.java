package com.zxyt.ocpp.client.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.client.entity.sys.ZTree;
import com.zxyt.ocpp.client.service.sys.IZTreeService;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: ZTree树管理控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"ZTree树管理"}, description = "ZTreeController")
@RestController
@RequestMapping("/tree")
public class ZTreeController {

    @Autowired
    private IZTreeService zTreeService;


    @ApiOperation(value = "查询菜单树", httpMethod = "POST", notes = "根据查询条件查询菜单树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="菜单ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="菜单名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="菜单编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId",value="上级菜单", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="level",value="菜单级别", dataType = "Integer",paramType = "query")
    })
    @PostMapping("/menu")
    public List<ZTree> getMenuTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Subject subject = SecurityUtils.getSubject();
        JSONObject json = (JSONObject) subject.getSession().getAttribute("employee");
        map.put("areaId", json.getString("areaId"));
        map.put("organizationId", json.getString("organizationId"));
        return this.zTreeService.getMenuTree(map);
    }


    @ApiOperation(value = "查询地区树", httpMethod = "POST", notes = "根据查询条件查询地区树信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name="id",value="地区ID", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="areaName",value="地区名称", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="code",value="地区编码", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="pId",value="上级地区", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="level",value="地区级别", dataType = "Integer",paramType = "query")
    })
    @PostMapping("/area")
    public List<ZTree> getAreaTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        return this.zTreeService.getAreaTree(map);
    }

    @ApiOperation(value = "查询机构树", httpMethod = "POST", notes = "根据查询条件查询机构树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="机构ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organizationName",value="机构名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="机构编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId",value="上级机构", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="areaId",value="所属地区", dataType = "String",paramType = "query")
    })
    @PostMapping("/organization")
    public List<ZTree> getOrganizationTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        return this.zTreeService.getOrganizationTree(map);
    }

    @ApiOperation(value = "查询灾种树", httpMethod = "POST", notes = "根据查询条件查询灾种树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="灾种ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="灾种名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="灾种编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId",value="上级灾种", dataType = "String",paramType = "query")
    })
    @PostMapping("/disaster")
    public List<ZTree> getDisasterTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        return this.zTreeService.getDisasterTree(map);
    }

    @ApiOperation(value = "查询灾种级别树", httpMethod = "POST", notes = "根据查询条件查询灾种树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="灾种ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="灾种名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="灾种编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId",value="上级灾种", dataType = "String",paramType = "query")
    })
    @PostMapping("/disaster/level")
    public List<ZTree> getDisasterLevelTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        return this.zTreeService.getDisasterLevelTree(map);
    }

    @ApiOperation(value = "查询群组树", httpMethod = "POST", notes = "根据查询条件查询群组树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="群组ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="群组名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId",value="上级群组", dataType = "String",paramType = "query")
    })
    @PostMapping("/user/group")
    public List<ZTree> getUserGroupTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        return this.zTreeService.getUserGroupTree(map);
    }

    @ApiOperation(value = "查询用户组对用受众个数树", httpMethod = "POST", notes = "根据查询条件查询群组对应用户个数树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="群组ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="群组名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId",value="上级群组", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="channelId",value="渠道ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="areaId",value="地区ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organizationId",value="机构ID", dataType = "String",paramType = "query")
    })
    @PostMapping("/user/group/count")
    public List<ZTree> getUserGroupCountTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {

        return this.zTreeService.getUserGroupCountTree(map);
    }

    @ApiOperation(value = "查询机构群组树", httpMethod = "POST", notes = "根据查询条件查询机构群组树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="organizationId",value="机构ID", dataType = "String",paramType = "query")
    })
    @PostMapping("/organization/group")
    public List<ZTree> getOrganizationUserGroupTree(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Subject subject = SecurityUtils.getSubject();
        JSONObject json = (JSONObject) subject.getSession().getAttribute("employee");
        map.put("areaId", json.getString("areaId"));
        return this.zTreeService.getOrganizationUserGroupTree(map);
    }

}
