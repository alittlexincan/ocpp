package com.zxyt.ocpp.client.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.client.config.common.result.ResultObject;
import com.zxyt.ocpp.client.config.common.result.ResultResponse;
import com.zxyt.ocpp.client.entity.sys.Department;
import com.zxyt.ocpp.client.service.sys.IDepartmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: LiWeidong
 * @Description: 部门管理控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"部门管理"}, description = "DepartmentController")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;


    @ApiOperation(value="添加部门信息",httpMethod="POST",notes="根据参数列表添加部门信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name="departmentName",value="部门名称",required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="code",value="部门编码",required = true, dataType = "String",paramType = "query")
    })
    @PostMapping("/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        JSONObject json = new JSONObject(map);
        Department department = JSON.parseObject(json.toJSONString(), new TypeReference<Department>() {});
        int num = this.departmentService.insert(department);
        if(num>0){
            return ResultResponse.make(200,"添加部门成功",department);
        }
        return ResultResponse.make(500,"添加部门失败",null);
    }

    @ApiOperation(value="修改机构信息",httpMethod="POST", notes="根据机构ID，修改参数列表机构信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name="id",value="机构ID", dataType = "String", required = true,paramType = "query"),
        @ApiImplicitParam(name="organizationName",value="机构名称", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="code",value="机构编码", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="pId",value="上级机构", dataType = "String",paramType = "query"),
        @ApiImplicitParam(name="areaId",value="所属地区", dataType = "String",paramType = "query")
    })
    @PostMapping("/update")
    public ResultObject<Object> update(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        JSONObject json = new JSONObject(map);
        Department department = JSON.parseObject(json.toJSONString(), new TypeReference<Department>() {});
        int num = this.departmentService.update(department);
        if(num>0){
            return ResultResponse.make(200,"修改部门成功");
        }
        return ResultResponse.make(500,"修改部门失败");
    }

    @ApiOperation(value="删除部门信息",httpMethod = "DELETE", notes="根据url的部门id来删除部门信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "String", paramType="path")
    })
    @DeleteMapping("/delete/{id}")
    public ResultObject<Object> deleteById(@PathVariable(value = "id") String id) {
        Integer num = this.departmentService.deleteById(id);
        if(num>0){
          return  ResultResponse.make(200,"删除部门成功");
        }
        return ResultResponse.make(500,"删除部门失败");
    }

    @ApiOperation(value="批量删除部门详细信息",httpMethod = "POST", notes="根据一批部门id来删除部门详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "String", paramType="query")
    })
    @PostMapping("/delete")
    public ResultObject<Object> deleteBatch(@RequestParam(value = "id") String id) {
        Integer num = this.departmentService.deleteByIds(id);
        if(num>0){
            return  ResultResponse.make(200,"删除部门成功");
        }
        return ResultResponse.make(500,"删除部门失败");
    }

    @ApiOperation(value="查询部门详细信息",httpMethod = "GET", notes="根据url的部门id来查询部门详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "String", paramType="path")
    })
    @GetMapping("/select/{id}")
    public ResultObject<Object> selectById(@PathVariable(value = "id") String id) {
        return ResultResponse.ok(this.departmentService.selectById(id));
    }

    @ApiOperation(value = "查询部门信息列表", httpMethod = "GET", notes = "根据查询条件分页查询所有部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="部门ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="departmentName",value="部门名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code",value="部门编码", dataType = "String",paramType = "query")
    })
    @GetMapping("/select")
    public ResultObject<Object> selectAll(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        PageInfo<Department> pageInfo = this.departmentService.selectAll(map);
        return ResultResponse.page(pageInfo.getTotal(), pageInfo.getList());
    }


}
