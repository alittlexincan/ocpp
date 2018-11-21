package com.zxyt.ocpp.publish.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.result.ResultObject;
import com.zxyt.ocpp.publish.config.common.result.ResultResponse;
import com.zxyt.ocpp.publish.entity.sys.UserGroup;
import com.zxyt.ocpp.publish.service.sys.IUserGroupService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 群组管理控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"群组管理"}, description = "UserGroupController")
@RestController
@RequestMapping("/group")
public class UserGroupController {

    @Autowired
    private IUserGroupService userGroupService;

    @ApiOperation(value="添加群组信息",httpMethod="POST",notes="根据参数列表添加群组信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name="name",value="群组名称",required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="pId", value="所属群组", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="area_id",value="地区ID", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="organization_id",value="机构ID", required = true, dataType = "String",paramType = "query")
    })
    @PostMapping("/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        JSONObject json = new JSONObject(map);
        UserGroup group = JSON.parseObject(json.toJSONString(), new TypeReference<UserGroup>() {});
        int num = this.userGroupService.insert(group);
        if(num>0){
            return ResultResponse.make(200,"添加群组成功",group);
        }
        return ResultResponse.make(500,"添加群组失败",null);
    }

    @ApiOperation(value="修改群组信息",httpMethod="POST", notes="根据群组ID，修改参数群组信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name="id",value="群组ID", dataType = "String", required = true,paramType = "query"),
        @ApiImplicitParam(name="name",value="群组名称", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="pId", value="群组ID", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="area_id",value="地区ID", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="organization_id",value="机构ID", dataType = "String",paramType = "query")
    })
    @PostMapping("/update")
    public ResultObject<Object> update(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        JSONObject json = new JSONObject(map);
        UserGroup group = JSON.parseObject(json.toJSONString(), new TypeReference<UserGroup>() {});
        int num = this.userGroupService.update(group);
        if(num>0){
            return ResultResponse.make(200,"修改群组成功");
        }
        return ResultResponse.make(500,"修改群组失败");
    }

    @ApiOperation(value="删除群组信息",httpMethod = "DELETE", notes="根据url的群组id来删除群组信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "群组ID", required = true, dataType = "String", paramType="path")
    })
    @DeleteMapping("/delete/{id}")
    public ResultObject<Object> deleteGroupById(@PathVariable(value = "id") String id) {
        Integer num = this.userGroupService.deleteById(id);
        if(num>0){
          return  ResultResponse.make(200,"删除群组成功");
        }
        return ResultResponse.make(500,"删除群组失败");
    }

    @ApiOperation(value="批量删除群组信息",httpMethod = "POST", notes="根据一批群组ID来删除群组信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "群组ID", required = true, dataType = "String", paramType="query")
    })
    @PostMapping("/delete")
    public ResultObject<Object> deleteBatch(@RequestParam(value = "id") String id) {
        Integer num = this.userGroupService.deleteByIds(id);
        if(num>0){
            return  ResultResponse.make(200,"删除群组成功");
        }
        return ResultResponse.make(500,"删除群组失败");
    }

    @ApiOperation(value="查询群组信息",httpMethod = "POST", notes="根据url的群组ID来查询群组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "群组ID", required = true, dataType = "String", paramType="path")
    })
    @PostMapping("/select/{id}")
    public ResultObject<Object> selectById(@PathVariable(value = "id") String id) {
        return ResultResponse.ok(this.userGroupService.selectById(id));
    }

    @ApiOperation(value = "查询群组信息列表", httpMethod = "GET", notes = "根据查询条件分页查询所有群组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="群组ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="群组名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="群组ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="area_id",value="地区ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organization_id",value="机构ID", dataType = "String",paramType = "query")
    })
    @GetMapping("/select")
    public ResultObject<Object> selectAll(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        PageInfo<UserGroup> pageInfo = this.userGroupService.selectAll(map);
        return ResultResponse.page(pageInfo.getTotal(), pageInfo.getList());
    }

}
