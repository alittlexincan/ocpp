package com.zxyt.ocpp.publish.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.zxyt.ocpp.publish.config.common.result.ResultObject;
import com.zxyt.ocpp.publish.config.common.result.ResultResponse;
import com.zxyt.ocpp.publish.entity.sys.Channel;
import com.zxyt.ocpp.publish.service.sys.IChannelService;
import com.zxyt.ocpp.publish.utils.UploadFileUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 渠道手段管理控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"渠道手段管理"}, description = "ChannelController")
@RestController
@RequestMapping("/channel")
public class ChannelController {

    /**
     * 获取上传的文件夹，具体路径参考application.properties中的配置
     */
    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * 渠道图标上传文件夹
     */
    @Value("${web.channel-path}")
    private String channel;

    @Autowired
    private IChannelService channelService;

    @ApiOperation(value="添加渠道手段信息",httpMethod="POST",notes="根据参数列表添加渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="渠道手段名称",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="icon",value="渠道手段图标",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属渠道", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段类型", required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段状态", required = true,dataType = "Integer",paramType = "query")
    })
    @PostMapping("/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String,Object> map, @ApiParam(hidden = true) @RequestParam("addFile") MultipartFile file){
        JSONObject json = new JSONObject(map);
        // 文件开始上传
        String fileUrl = UploadFileUtil.upload(file, uploadPath, channel);
        if(fileUrl!=null){
            // 添加渠道数据
            map.put("icon", fileUrl);
        }
        Channel channel = JSON.parseObject(json.toJSONString(), new TypeReference<Channel>() {});
        int num = this.channelService.insert(channel);
        if(num>0){
            return ResultResponse.make(200,"添加渠道手段成功",channel);
        }
        return ResultResponse.make(500,"添加渠道手段失败",null);
    }

    @ApiOperation(value="修改渠道手段信息",httpMethod="POST", notes="根据渠道手段ID，修改参数列表渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="渠道手段ID", dataType = "String", required = true,paramType = "query"),
            @ApiImplicitParam(name="name",value="渠道手段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="icon",value="渠道手段图标", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属渠道", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段类型", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段状态", required = true,dataType = "Integer",paramType = "query")
    })
    @PostMapping("/update")
    public ResultObject<Object> update(@ApiParam(hidden = true) @RequestParam Map<String,Object> map, @ApiParam(hidden = true) @RequestParam("updateFile") MultipartFile file){
        JSONObject json = new JSONObject(map);

        if(file.isEmpty()){
            json.put("icon", "/" + this.channel + "/" + map.get("icon"));
        }else {
            // 文件开始上传
            String fileUrl = UploadFileUtil.upload(file, uploadPath, channel);
            // 上传成功走后台添加数据
            if(fileUrl!=null){
                // 修改渠道数据
                json.put("icon", fileUrl);
            }
        }

        Channel channel = JSON.parseObject(json.toJSONString(), new TypeReference<Channel>() {});
        int num = this.channelService.update(channel);
        if(num>0){
            return ResultResponse.make(200,"修改渠道手段成功");
        }
        return ResultResponse.make(500,"修改渠道手段失败");
    }

    @ApiOperation(value="修改渠道是否启用禁用",httpMethod="POST", notes="根据渠道手段ID，修改参数列表渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="渠道手段ID", dataType = "String", required = true,paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段状态", required = true,dataType = "Integer",paramType = "query")
    })
    @PostMapping("/update/status")
    public ResultObject<Object> updateStatus(@ApiParam(hidden = true) @RequestParam Map<String, Object> map){
        String msg = Integer.parseInt(map.get("status").toString()) == 0 ? "禁用" : "启用";
        int num = this.channelService.updateStatus(map);
        if(num>0){
            return ResultResponse.make(200,"渠道"+msg+"成功");
        }
        return ResultResponse.make(500,"修改"+msg+"失败");
    }

    @ApiOperation(value="删除渠道手段信息",httpMethod = "DELETE", notes="根据url的渠道手段id来删除渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "渠道手段ID", required = true, dataType = "String", paramType="path")
    })
    @DeleteMapping("/delete/{id}")
    public ResultObject<Object> deleteById(@PathVariable(value = "id") String id) {
        Integer num = this.channelService.deleteById(id);
        if(num>0){
          return  ResultResponse.make(200,"删除渠道手段成功");
        }
        return ResultResponse.make(500,"删除渠道手段失败");
    }

    @ApiOperation(value="批量删除渠道手段信息",httpMethod = "POST", notes="根据一批用户id来删除渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "渠道手段ID", required = true, dataType = "String", paramType="query")
    })
    @PostMapping("/delete")
    public ResultObject<Object> deleteBatch(@RequestParam(value = "id") String id) {
        Integer num = this.channelService.deleteByIds(id);
        if(num>0){
            return  ResultResponse.make(200,"删除渠道手段成功");
        }
        return ResultResponse.make(500,"删除渠道手段失败");
    }

    @ApiOperation(value="根据id查询渠道手段信息",httpMethod = "POST", notes="根据url的地区id来查询渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "渠道手段ID", required = true, dataType = "String", paramType="path")
    })
    @PostMapping("/select/{id}")
    public ResultObject<Object> selectById(@PathVariable(value = "id") String id) {
        return ResultResponse.ok(this.channelService.selectById(id));
    }

    @ApiOperation(value = "查询渠道手段信息列表", httpMethod = "GET", notes = "根据查询条件分页查询所有渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="渠道手段ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="渠道手段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="icon",value="渠道手段图标", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属渠道",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段类型", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段状态", required = true,dataType = "Integer",paramType = "query")
    })
    @GetMapping("/select")
    public ResultObject<Object> selectAll(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        PageInfo<Channel> pageInfo = this.channelService.selectAll(map);
        return ResultResponse.page(pageInfo.getTotal(), pageInfo.getList());
    }

    @ApiOperation(value = "查询渠道手段信息", httpMethod = "POST", notes = "根据查询条件查询所有渠道手段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="渠道手段ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="渠道手段名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pId", value="所属渠道",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段类型", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="type",value="渠道手段状态", required = true,dataType = "Integer",paramType = "query")
    })
    @PostMapping("/list")
    public ResultObject<Object> selectList(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        return ResultResponse.ok(this.channelService.selectByParam(map));
    }

}
