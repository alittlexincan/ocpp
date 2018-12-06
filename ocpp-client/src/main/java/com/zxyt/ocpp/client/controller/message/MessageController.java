package com.zxyt.ocpp.client.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.zxyt.ocpp.client.config.common.result.ResultObject;
import com.zxyt.ocpp.client.config.common.result.ResultResponse;
import com.zxyt.ocpp.client.service.message.IMessageService;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: JiangXincan
 * @Description: 一键发布控制层
 * @Date: Created in 18:04 2018-4-18
 * @Modified By:
 */
@Api(tags = {"一键发布"}, description = "MessageController")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;


    @ApiOperation(value = "获取文件信息", httpMethod = "GET", notes = "根据FTP类型下载FTP上最新文件下载到本地制定路径，然后读取文件内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name="channelCode",value="渠道类型", dataType = "String",paramType = "query")
    })
    @GetMapping("/select/file")
    public ResultObject<Object> selectFTPFileInfo(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Subject subject = SecurityUtils.getSubject();
        JSONObject json = (JSONObject) subject.getSession().getAttribute("employee");
        map.put("areaId", json.getString("areaId"));
        map.put("organizationId", json.getString("organizationId"));
        JSONObject result = this.messageService.selectFTPFileInfo(map);
        if(result==null || result.getInteger("code") == 200){
            String msg = result.getString("msg");
            result.remove("msg");
            result.remove("code");
            return ResultResponse.make(200, msg ,result);
        }
        return ResultResponse.make(500,result.getString("msg"),null);
    }

    @ApiOperation(value = "添加一键发布信息", httpMethod = "POST", notes = "根据参数添加一键发布信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="title",value="发布标题", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="type",value="发布类型", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="record",value="国突对接（0：否，1：是）", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="content",value="发布内容", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="areas",value="影响地区集合", dataType = "List<Map>",paramType = "query"),
            @ApiImplicitParam(name="channels",value="发布渠道集合", dataType = "List<Map>",paramType = "query"),
            @ApiImplicitParam(name="groups",value="发布群组集合", dataType = "List<Map>",paramType = "query")
    })
    @PostMapping(value = "/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String, Object> map) {
        JSONObject result = this.messageService.insert(map);
        if(result.getInteger("code") == 200){

            // 调用分发接口

            return ResultResponse.make(200,result.getString("msg"), result);
        }
        return ResultResponse.make(500,result.getString("msg"),null);
    }

}
