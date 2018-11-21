package com.zxyt.ocpp.publish.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.result.ResultObject;
import com.zxyt.ocpp.publish.config.common.result.ResultResponse;
import com.zxyt.ocpp.publish.entity.sys.Disaster;
import com.zxyt.ocpp.publish.service.sys.IDisasterService;
import com.zxyt.ocpp.publish.utils.UploadFileUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 灾种管理控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"灾种管理"}, description = "DisasterController")
@RestController
@RequestMapping("/disaster")
public class DisasterController {

    /**
     * 获取上传的文件夹，具体路径参考application.properties中的配置
     */
    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * 灾种图标上传文件夹
     */
    @Value("${web.disaster-path}")
    private String disaster;

    @Autowired
    private IDisasterService disasterService;

    @ApiOperation(value="添加灾种信息",httpMethod="POST",notes="根据参数列表添加灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="灾种名称",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code", value="灾种编码", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="icon",value="灾种图标",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属灾种", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="灾种类型", required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="isConfig",value="灾种配置", required = true,dataType = "Integer",paramType = "query")
    })
    @PostMapping("/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String,Object> map, @RequestParam("addFile") MultipartFile file ){


        // 文件开始上传
        String fileUrl = UploadFileUtil.upload(file, uploadPath, disaster);
        // 上传成功走后台添加数据
        if(fileUrl!=null){
            // 添加渠道数据
            map.put("icon", fileUrl);
        }
        JSONObject json = new JSONObject(map);
        Disaster disaster = JSON.parseObject(json.toJSONString(), new TypeReference<Disaster>() {});
        int num = this.disasterService.insert(disaster);
        if(num>0){
            return ResultResponse.make(200,"添加灾种成功",disaster);
        }
        return ResultResponse.make(500,"添加灾种失败",null);
    }

    @ApiOperation(value="修改灾种信息",httpMethod="POST", notes="根据灾种ID，修改参数列表灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="灾种ID", dataType = "String", required = true,paramType = "query"),
            @ApiImplicitParam(name="name",value="灾种名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code", value="灾种编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="icon",value="灾种图标", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属灾种", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="灾种类型", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="isConfig",value="灾种配置", dataType = "Integer",paramType = "query")
    })
    @PostMapping("/update")
    public ResultObject<Object> update(@ApiParam(hidden = true) @RequestParam Map<String,Object> map, @RequestParam("updateFile") MultipartFile file ){
        if(file.isEmpty()){
            map.put("icon", "/" + this.disaster + "/" + map.get("icon"));
        }else {
            // 文件开始上传
            String fileUrl = UploadFileUtil.upload(file, uploadPath, disaster);
            // 上传成功走后台添加数据
            if(fileUrl!=null){
                // 修改渠道数据
                map.put("icon", fileUrl);
            }
        }

        JSONObject json = new JSONObject(map);
        Disaster disaster = JSON.parseObject(json.toJSONString(), new TypeReference<Disaster>() {});
        int num = this.disasterService.update(disaster);
        if(num>0){
            return ResultResponse.make(200,"修改灾种成功");
        }
        return ResultResponse.make(500,"修改渠灾种失败");
    }

    @ApiOperation(value="修改策略配置标识",httpMethod="POST", notes="根据灾种ID，策略标识修改灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="灾种ID", dataType = "String", required = true,paramType = "query"),
            @ApiImplicitParam(name="isStrategy",value="策略配置", required = true, dataType = "Integer",paramType = "query")
    })
    @PostMapping("/update/strategy")
    public ResultObject<Object> updateStrategyById(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        System.out.println(map);
        int num = this.disasterService.updateStrategyById(map);
        if(num>0){
            return ResultResponse.make(200,"修改策略配置成功",null);
        }
        return ResultResponse.make(500,"修改策略配置失败",null);
    }

    @ApiOperation(value="删除灾种信息",httpMethod = "DELETE", notes="根据url的灾种id来删除灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "渠道手段ID", required = true, dataType = "String", paramType="path")
    })
    @DeleteMapping("/delete/{id}")
    public ResultObject<Object> deleteById(@PathVariable(value = "id") String id) {
        Integer num = this.disasterService.deleteById(id);
        if(num>0){
          return  ResultResponse.make(200,"删除灾种成功");
        }
        return ResultResponse.make(500,"删除灾种失败");
    }

    @ApiOperation(value="批量删除灾种信息",httpMethod = "POST", notes="根据一批灾种id来删除灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "灾种ID", required = true, dataType = "String", paramType="query")
    })
    @PostMapping("/delete")
    public ResultObject<Object> deleteBatch(@RequestParam(value = "id") String id) {
        Integer num = this.disasterService.deleteByIds(id);
        if(num>0){
            return  ResultResponse.make(200,"删除灾种成功");
        }
        return ResultResponse.make(500,"删除灾种失败");
    }

    @ApiOperation(value="根据id查询灾种信息",httpMethod = "POST", notes="根据url的灾种id来查询灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "灾种ID", required = true, dataType = "String", paramType="path")
    })
    @PostMapping("/select/{id}")
    public ResultObject<Object> selectById(@PathVariable(value = "id") String id) {
        return ResultResponse.ok(this.disasterService.selectById(id));
    }

    @ApiOperation(value = "查询灾种信息列表", httpMethod = "GET", notes = "根据查询条件分页查询所有灾种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="渠道手段ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="灾种名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="code", value="灾种编码", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="icon",value="灾种图标", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属灾种", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="灾种类型", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="isConfig",value="灾种配置", dataType = "Integer",paramType = "query")
    })
    @GetMapping("/select")
    public ResultObject<Object> selectAll(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        PageInfo<Disaster> pageInfo = this.disasterService.selectAll(map);
        return ResultResponse.page(pageInfo.getTotal(), pageInfo.getList());
    }

}
