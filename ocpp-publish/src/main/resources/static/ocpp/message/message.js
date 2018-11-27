layui.config({
    base: '/static/layuiadmin/modules/'
}).extend({
    zTree: 'zTree'
    ,selectTree: 'selectTree'
    ,disaster: 'disaster'
    ,ajaxFileUpload: 'ajaxFileUpload'
    ,time:'time'
});

layui.use(['table','form','laydate','element','laytpl','layer','zTree','selectTree','disaster', 'ajaxFileUpload','time'], function(){
    let table = layui.table			// 引用layui表格
        ,form = layui.form			// 引用layui表单
        ,laytpl = layui.laytpl		// 引用layui模板引擎
        ,layer = layui.layer		// 引用layui弹出层
        ,$ = layui.$   			// 引用layui的jquery
        ,element = layui.element
        ,laydate = layui.laydate
        ,zTree = layui.zTree
        ,selectTree = layui.selectTree
        ,disaster = layui.disaster
        ,ajaxFileUpload = layui.ajaxFileUpload
        ,time=layui.time
        ,employee = layui.sessionData("ocpp").employee; // 当前登录用户信息


    /**
     * 自定义验证规则
     */
    form.verify({
        disasterId: function(value){
            if(value.length == 0) {
                $("#addDisasterId .addDisasterIdShow, #updateDisasterId .updateDisasterIdShow").css("border-color","red");
                return '请选择灾种';
            }
        }
    });


    let active = {
        /**
         * 初始化ueditor编辑器
         */
        editor: UE.getEditor("message")
        /**
         * ueditor工具类
         */
        ,editorUtils: UE.dom.domUtils

        /**
         * 渠道对应受众树加载
         * @param option
         */
        ,channelToUserGroup: option => {
            zTree.async({
                id: '#group_'+option.channelId,
                setting: {
                    async:{
                        enable:true,
                        url: '/tree/user/group/count',
                        autoParam:["id"],
                        otherParam: { "areaId":option.areaId, "organizationId": option.organizationId, "channelId":option.channelId},
                        dataType:"json",
                        dataFilter:function (treeId, parentNode, responseData) {
                            if(responseData!=null){
                                for(let i = 0; i<responseData.length; i++){
                                    responseData[i].checked = true;
                                }
                            }
                            return responseData;
                        }
                    },
                    check: {
                        enable: true,
                        chkboxType: {"Y":"", "N": ""},
                        chkStyle:"checkbox"
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback:{
                        onClick:null,
                        onCheck:null
                    }
                }
            });
        }


        /**
         * 动态加载渠道、地区对应的受众
         * @param result
         */
        ,appendGroupUsers: result => {
            result.channels.forEach(function (channel) {
                // 追加预警内容tab选项卡
                element.tabAdd('ocpp-tab', {
                    title: channel.channelName
                    ,content: "<div class='ztree' id='group_" + channel.channelId + "'></div>" //支持传入html
                    ,id: channel.channelId
                });
                // 删除tab页提示信息
                element.tabDelete("ocpp-tab","choose-tab");
                // 默认展开第一个tab页
                element.tabChange('ocpp-tab',result.channels[0].channelId);
                // 动态添加渠道地区对应的受众（根据地区和渠道查询）
                active.channelToUserGroup({
                    areaId: function () {
                        let id = "";
                        for (let item of result.areas) {
                            id += "," + item.areaId;
                        }
                        return id.substring(1);
                    }(),
                    organizationId: result.organizationId,
                    channelId: channel.channelId
                });
            });
        }


        /**
         * 预警内容没有时提示，通常是点击取消渠道全选和，地区没有勾选时回填提示信息
         * @param param
         * @returns {string}
         */
        ,defaultWarnMsg: param => {
            let html = "";
            // html += "<div class='layui-row layui-col-space5'>";
            // html += "	<div class='layui-col-xs9 layui-col-md9'>";
            // html += "		<div class='layui-card warn-card-content'>";
            // html += "			<div class='layui-card-header'><span>&nbsp;&nbsp;<i class='layui-icon warn-card-hader-icon'>&#xe618;</i>预警编辑</span></div>";
            // html += "			<div  class='layui-card-body warn-card-content-list'>";
            // html += "				<div class='layui-row layui-col-space5'>";
            // html += "					<div class='layui-col-xs12 layui-col-md12 warn-content-skip'>"+param.msg+"</div>";
            // html += "				</div>";
            // html += "			</div>";
            // html += "		</div>";
            // html += "	</div>";
            // html += "	<div class='layui-col-xs3 layui-col-md3'>";
            // html += "		<div class='layui-card warn-card-content'>";
            // html += "			<div class='layui-card-header'><span>&nbsp;&nbsp;<i class='layui-icon layui-icon-tree warn-card-hader-icon'></i>受众群组</span></div>";
            // html += "			<div  class='layui-card-body warn-card-content-list'><div class='warn-content-skip'>"+param.msg+"</div></div>";
            // html += "		</div>";
            // html += "	</div>";
            // html += "</div>";


            html += "<div class='layui-card ocpp-card-content'>";
            html += "   <div class='ocpp-content-skip'>asdfasdf</div>";
            html += "</div>";

            // 追加预警内容tab选项卡
            element.tabAdd('ocpp-tab', {
                title: param.title
                ,content: html //支持传入html
                ,id: param.id
            });
            // 默认展开第一个tab页
            element.tabChange('ocpp-tab', 'choose-tab');
            element.render();
        }

        /**
         * 渠道单击事件
         * 判断active选中样式是否存在
         * 如果存在：已经选中，否则没有选中
         * @param obj
         */
        ,channelOneClick: obj => {
            let channelId = $(obj).data("id"), channelName = $(obj).data("title");
            if($(obj).hasClass("active")){
                // 获取选中渠道
                let param = {
                    /**
                     * 获取选中渠道
                     */
                    channels: [{channelId:channelId, channelName: channelName}]
                    /**
                     * 获取选中地区
                     */
                    ,areas: function () {
                        let area = [];
                        initAreaTree.getCheckedNodes().forEach(function (item) {
                            area.push({
                                areaId:item.id,
                                areaName: item.name
                            });
                        });
                        return area;
                    }()
                    ,organizationId: employee.organizationId
                };
                // 删除tab id 为choose-tab的table页
                element.tabDelete("ocpp-tab", "choose-tab");
                // 清空tab页所有内容
                // 追加选中渠道的tab页
                active.appendGroupUsers(param);
            }else{
                element.tabDelete("ocpp-tab", channelId);
            }
        }
        /**
         * 地区选择
         */
        ,areaCheck: (event, treeId, treeNode) => {

            // 判断至少选中一个地区
            let areaTree = zTree.getZTree(treeId);
            let nodes = areaTree.getCheckedNodes(true);
            if(nodes.length == 0){
                initAreaTree.checkNode(treeNode, true, true);
                layer.msg("请至少选中一个地区", {time: 2000});
                return false;
            }

        }
    };

    /**
     * 初始化加载地区树
     */
    let initAreaTree =  zTree.async({
        id: "#areaTree",
        setting: {
            async:{
                enable:true,
                url: "/tree/area",
                autoParam:["id"],
                dataType:"json",
                dataFilter:(treeId, parentNode, responseData) => {
                    if (responseData) {
                        responseData.forEach(node => {
                            if(node.id == employee.areaId || node.pId == employee.areaId){
                                node.checked = true;
                            }
                        });
                    }
                    return responseData;
                }
            },
            check: {
                enable: true,
                chkboxType: {"Y":"", "N": ""},
                chkStyle:"checkbox"
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback:{
                onClick:null,
                onCheck:active.areaCheck
            }
        }
    });

    /**
     * 初始化加载渠道
     */
    let initChannelList = function(){
        $.ajax({
            async:true
            ,type: "POST"
            ,data: {type: 0}
            ,url: "/channel/list"
            ,dataType: 'json'
            ,success: function(json){
                if(json.code == 200 && json.data != null){
                    let html ="";
                    json.data.forEach(function (currentValue, index, arr) {
                        html += "<div class='"+(currentValue.status == 1 ? "imgbox" : "imgbox-gray")+"' data-id='"+currentValue.id+"' data-title='"+currentValue.name+"' data-channel='"+currentValue.name+"' data-code='"+currentValue.code+"' >";
                        html += "   <img class='"+(currentValue.status == 0 ? "gray" : "")+"' src='"+currentValue.icon+"' title='"+(currentValue.status == 0 ? currentValue.name+"渠道未部署" : currentValue.name)+"' />";
                        html += "<span>"+currentValue.name+"</span>";
                        html += "</div>";
                    });
                    $(".channel-list").empty().append(html);
                }
            }
        });
    };

    /**
     * 渠道全选、反选
     */
    $(".channel-option").on("click", "div > span", function(element) {

        if(text == '全选'){
            // 给所有渠道添加选中样式
            $("." + event + " .imgbox").addClass("active");
            // 获取选中渠道
            let param = {
                /**
                 * 获取选中渠道
                 */
                "channelId": function () {
                    var cId = [];
                    $("." + event + " .imgbox").each(function () {
                        cId.push($(this).data("id"));
                    });
                    return cId;
                }()
                /**
                 * 获取选中地区
                 */
                ,"area": function () {
                    var area = [];
                    initAreaTree.getCheckedNodes().forEach(function (item) {
                        area.push({
                            areaId: item.id,
                            areaName: item.name
                        });
                    });
                    return area;
                }()
            };
            // 清除tab页所有内容
            $(".ocpp-tab .ocpp-tab-title, .ocpp-tab .ocpp-tab-content").empty();
            active.appendGroupUsers(param);
        }else{
            $("." + event + " .imgbox").removeClass("active");
            // 清除tab页所有内容
            $(".ocpp-tab .ocpp-tab-title, .warn-tab .ocpp-tab-content").empty();

        }
    });
    /**
     * 渠道点击单选、取消选择
     */
    $(".channel-list").on("click", ".imgbox", function(element) {

        // 追加或删除样式
        if($(this).hasClass("active")){
            if($(".channel-list .imgbox.active").length == 1){
                layer.msg("请至少选择一个渠道", {time: 2000});
                return false;
            }
            $(this).removeClass("active");
        }else{
            $(this).addClass("active");
        }
        // 调用渠道点击事件业务
        active.channelOneClick($(this));
    });



    /**
     * tab选项卡删除监听事件
     */
    element.on('tabDelete(ocpp-tab)', function(data){
        // 获取当前删除的tab title中的渠道id
        let channelId = $(this).parent().attr("lay-id");
        if(channelId != undefined) {
            // 判断当前是否是最后一个渠道，如果是则提示至少选中一个渠道
            if ($(".channel-list .imgbox.active").length == 1) {
                let result = {
                    channelId: [channelId],
                    area: []
                };
                // 循环获取当前地区树选中的地区
                initAreaTree.getCheckedNodes(true).forEach(function (item) {
                    result.area.push({
                        areaId: item.id,
                        areaName: item.name
                    });
                });
                //active.setWarnContent(result);
                layer.msg("请至少选中一个渠道", {time: 2000});
                return false;
            }

            // 删除渠道对应的样式
            $(".channel-list .imgbox.active[data-id='" + channelId + "']").removeClass("active");

        }
    });


    /**
     * tab选项卡前按钮移动操作
     */
    let tabIndex = 0;
    $(".ocpp-tab").on('click','.ocpp-tab-prev',function () {
        // title可视化宽度
        let width = $(".ocpp-tab-title").width();
        // 获取可视化区域li的个数，四舍五入
        let move = Math.round(width/95);
        if(tabIndex > 0){
            tabIndex--;
            let moveWidth = move * 95 * tabIndex;
            $(".ocpp-tab-title > li:nth-child(1)").css({"margin-left": -moveWidth});
        }
    });
    /**
     * tab选项卡后按钮移动操作
     */
    $(".ocpp-tab").on('click','.ocpp-tab-next',function () {
        // title可视化宽度
        let width = $(".ocpp-tab-title").width();
        // 获取可视化区域li的个数，四舍五入
        let move = Math.round(width/95);
        // li的总个数
        let count = $(".ocpp-tab-title > li").length;
        if(move * (tabIndex + 1) < count){
            tabIndex++;
            let moveWidth = move * 95 * tabIndex;
            $(".ocpp-tab-title > li:nth-child(1)").css({"margin-left": -moveWidth});
        }
    });

    /**
     * 监听预警提交事件
     */
    form.on("submit(submit)", function(data){
        // 数据提交到后台，通用方法
        let param = data.field;

        param.advice = "您好：" + param.title + "请您处理";     // 流程意见


        // 渠道处理
        param.channel = function(){
            let channel = [];
            $(".channel-list .imgbox.active").each(function () {
                channel.push({
                    channelId: $(this).data("id"),
                    channelName: $(this).data("title"),
                    channelCode: $(this).data("code")
                });
            });
            return JSON.stringify(channel).replace(/\"/g,"'");
        }();

        // 地区处理
        param.area = function(){
            let area = [];
            initAreaTree.getCheckedNodes(true).forEach(function (item) {
                area.push({
                    areaId: item.id,
                    areaName: item.name,
                    areaCode: item.code
                });
            });
            return JSON.stringify(area).replace(/\"/g,"'");
        }();

        // 群组处理
        param.group = function(){
            let group = {};
            $(".channel-list .imgbox.active").each(function () {
                var channelId = $(this).data("id")
                    ,channelGroup = [];
                zTree.getZTree("group_"+channelId).getCheckedNodes(true).forEach(function (item) {
                    channelGroup.push({
                        userGroupId: item.id,
                        userGroupName: item.name
                    });
                });
                group[channelId] = channelGroup;
            });
            return JSON.stringify(group).replace(/\"/g,"'");
        }();



        // 数据提交
        // ajaxFileUpload.render({
        //     async: true
        //     ,url : "/warn/edit/insert"
        //     ,type: "POST"
        //     ,param : param//需要传递的数据 json格式
        //     ,files : files()
        //     ,dataType: 'json'
        // },function (json) {
        //     if(json.code == 200){
        //         // 弹出提示信息，2s后自动关闭
        //         layer.msg(json.msg, {time: 2000},function(){
        //             location.reload();
        //         });
        //     }
        // });

    });

    /**
     * 初始化监听
     */
    active.editor.addListener( "ready", function() {
        let height = $(".ocpp-content").height()
            ,barHeight =  $("#edui1_toolbarbox").height()
            ,contentHeight = height - barHeight - 30;
        this.setHeight(contentHeight);
    });

    initChannelList();      // 初始化加载渠道
});