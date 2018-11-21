package com.zxyt.ocpp.publish.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.result.ResultObject;
import com.zxyt.ocpp.publish.config.common.result.ResultResponse;
import com.zxyt.ocpp.publish.entity.sys.User;
import com.zxyt.ocpp.publish.service.sys.IUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: JiangXincan
 * @Description: 受众管理控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"受众管理"}, description = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value="添加受众信息",httpMethod="POST",notes="根据参数列表添加受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="受众名称",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="终端号码",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="userGroupId", value="群组ID", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="channelId", value="渠道ID", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="areaId",value="地区ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organizationId",value="机构ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="job",value="受众职务", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="duties",value="受众职责", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="leader",value="分管领导", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="age",value="受众年龄", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sex",value="受众性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="address",value="详细地址", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="longitude",value="地区经度", dataType = "Double",paramType = "query"),
            @ApiImplicitParam(name="latitude",value="地区纬度", dataType = "Double",paramType = "query"),
            @ApiImplicitParam(name="altitude",value="海拔高度", dataType = "Double",paramType = "query")
    })

    @PostMapping("/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        String uuidUser = UUID.randomUUID().toString().replace("-", "");
        String uuidJob = UUID.randomUUID().toString().replace("-", "");
        map.put("userId",uuidUser);
        map.put("userJobId",uuidJob);
        JSONObject json = new JSONObject(map);
        User user = JSON.parseObject(json.toJSONString(), new TypeReference<User>() {});
        int num = this.userService.insertUser(map);
        int numJob = this.userService.insertUserJob(map);
        if(num>0 && numJob>0){
            return ResultResponse.make(200,"添加受众成功",user);
        }
        return ResultResponse.make(500,"添加受众失败",null);
    }

    @ApiOperation(value="修改受众信息",httpMethod="POST", notes="根据受众ID，修改参数受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="受众ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="受众名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="终端号码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="userGroupId", value="群组ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="channelId", value="渠道ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="areaId",value="地区ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organizationId",value="机构ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="job",value="受众职务", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="duties",value="受众职责", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="leader",value="分管领导", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="age",value="受众年龄", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sex",value="受众性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="address",value="详细地址", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="longitude",value="地区经度", dataType = "Double",paramType = "query"),
            @ApiImplicitParam(name="latitude",value="地区纬度", dataType = "Double",paramType = "query"),
            @ApiImplicitParam(name="altitude",value="海拔高度", dataType = "Double",paramType = "query")
    })
    @PostMapping("/update")
    public ResultObject<Object> update(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        JSONObject json = new JSONObject(map);
        User user = JSON.parseObject(json.toJSONString(), new TypeReference<User>() {});
//        int num = this.userService.update(user);//修改主表
        String uuidJob = UUID.randomUUID().toString().replace("-", "");
        map.put("userJobId",uuidJob);
        map.put("userId",map.get("id").toString());
        int numJob = this.userService.insertUserJob(map);//插入子表
        int num = this.userService.updateUser(map);//修改主表

        if(num>0 && numJob>0){
            return ResultResponse.make(200,"修改受众成功");
        }
        return ResultResponse.make(500,"修改受众失败");
    }

    @ApiOperation(value="删除受众信息",httpMethod = "DELETE", notes="根据url的受众ID来删除受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "受众ID", required = true, dataType = "String", paramType="path")
    })
    @DeleteMapping("/delete/{id}")
    public ResultObject<Object> deleteById(@PathVariable(value = "id") String id) {

        int jobNum = this.userService.deleteUserJobById(id);
        int num = this.userService.deleteById(id);
        if(num>0 && jobNum>0){
            return  ResultResponse.make(200,"删除受众成功");
        }
        return ResultResponse.make(500,"删除受众失败");
    }

    @ApiOperation(value="批量删除受众信息",httpMethod = "POST", notes="根据一批受众ID来删除受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "受众ID", required = true, dataType = "String", paramType="query")
    })
    @PostMapping("/delete")
    public ResultObject<Object> deleteBatch(@RequestParam(value = "id") String id) {
        int jobNum = this.userService.deleteUserJobByIds(id);
        int  num = this.userService.deleteByIds(id);
        if(num>0 && jobNum>0){
            return  ResultResponse.make(200,"删除受众成功");
        }
        return ResultResponse.make(500,"删除受众失败");
    }

    @ApiOperation(value="查询受众信息",httpMethod = "POST", notes="根据url的受众ID来查询受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "受众ID", required = true, dataType = "String", paramType="path")
    })
    @PostMapping("/select/{id}")
    public ResultObject<Object> selectById(@PathVariable(value = "id") String id) {
        return ResultResponse.ok(this.userService.selectById(id));
    }

    @ApiOperation(value = "查询受众信息列表", httpMethod = "GET", notes = "根据查询条件分页查询所有受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="受众ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="受众名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="终端号码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="userGroupId", value="群组ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="channelId", value="渠道ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="areaId",value="地区ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organizationId",value="机构ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="job",value="受众职务", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="duties",value="受众职责", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="leader",value="分管领导", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="age",value="受众年龄", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sex",value="受众性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="address",value="详细地址", dataType = "String",paramType = "query")
    })
    @GetMapping("/select")
    public ResultObject<Object> selectAll(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        PageInfo<User> pageInfo = this.userService.selectAll(map);
        return ResultResponse.page(pageInfo.getTotal(), pageInfo.getList());
    }

    @ApiOperation(value = "查询受众信息", httpMethod = "GET", notes = "根据查询条件查询所有受众信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="areaId", value="地区ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="organizationId", value="机构ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="channelName", value="渠道名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="受众类型（1：责任人员；2：基层防御人员）", dataType = "Integer",paramType = "query")
    })
    @GetMapping("/list")
    public ResultObject<Object> selectList(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        List<User> users = this.userService.selectList(map);
        if(users.size() > 0){
            return  ResultResponse.make(200,"查询受众成功", users);
        }
        return ResultResponse.make(500,"查询受众失败", null);
    }

    @PostMapping("/userDetails")
    public JSONObject userDetails( @RequestParam Map<String,Object> map) {
        JSONObject json=this.userService.userDetails(map);
        System.out.println(json);
        return json;
    }
}
