
layui.config({
    base: '/client/layuiadmin/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    productTemplate: 'productTemplate' //如果 mymod.js 是在根目录，也可以不用设定别名
    ,mod1: 'modules' //相对于上述 base 目录的子目录
    ,zTree: 'zTree'
});

layui.use(["table","form","laytpl","layer","zTree","productTemplate","element"], function() {

    let table = layui.table			// 引用layui表格
        , form = layui.form			// 引用layui表单
        , laytpl = layui.laytpl		// 引用layui模板引擎
        , layer = layui.layer		// 引用layui弹出层
        , zTree = layui.zTree
        , productTemplate = layui.productTemplate
        , $ = layui.$   			// 引用layui的jquery
        , element = layui.element
        , employee = layui.sessionData("ocpp").employee; // 当前登录用户信息
    /**
     * 全局参数
     * @type {{editor}}
     */
    let active = {
        /**
         * 初始化ueditor编辑器
         */
        editor: UE.getEditor('editor')
        /**
         * ueditor工具类
         */
        ,editorUtils: UE.dom.domUtils
        /**
         * 渠道对应受众树加载
         * @param option
         */
        ,"channelToUserGroup": function (option) {
            zTree.async({
                id: '#group_'+option.channelId,
                setting: {
                    async:{
                        enable:true,
                        url: '/client/tree/user/group/count',
                        autoParam:["id"],
                        otherParam: { "areaId":option.areaId, "channelId":option.channelId},
                        dataType:"json",
                        dataFilter:function (treeId, parentNode, responseData) {
                            if(responseData!=null){
                                for(var i = 0; i<responseData.length; i++){
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
        ,"setGroupUsers":function (result) {
            result.channels.forEach(function (channel) {
                // 追加预警内容tab选项卡
                element.tabAdd('warn-tab', {
                    title: channel.channelName
                    ,content: "<div class='ztree' id='group_" + channel.channelId + "'></div>" //支持传入html
                    ,id: channel.channelId
                });
                // 删除tab页提示信息
                element.tabDelete("warn-tab","choose-tab");
                // 默认展开第一个tab页
                element.tabChange('warn-tab',result.channels[0].channelId);
                // 动态添加渠道地区对应的受众（根据地区和渠道查询）
                active.channelToUserGroup({
                    areaId: function () {
                        var id = "";
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
         * 渠道单击事件
         * 判断active选中样式是否存在
         * 如果存在：已经选中，否则没有选中
         * @param obj
         */
        ,"channelOneClick": function (obj) {
            // 获取渠道id和渠道名称
            var channelId = $(obj).data("id"), channelName = $(obj).data("title");
            // 判断渠道是否选中
            if ($(obj).hasClass("active")) {
                // 获取选中渠道
                var param = {
                    "organizationId": employee.organizationId
                    /**
                     * 获取选中渠道
                     */
                    ,"channels": [{channelId:channelId, channelName:channelName}]
                    /**
                     * 获取选中地区
                     */
                    ,"areas":  [{areaId:employee.areaId, areaName:employee.areaName}]
                };
                 active.setGroupUsers(param);
            }else {
                // 取消渠道勾选时，同时删除对应的tab页渠道
                element.tabDelete("warn-tab", channelId);
            }
        }
        /**
         * 预警内容没有时提示，通常是点击取消渠道全选和，地区没有勾选时回填提示信息
         * @param param
         * @returns {string}
         */
        ,"defaultWarnMsg": function (param) {
            var html = "<div class='warn-content-skip'>"+param.msg+"</div>";
            // 追加预警内容tab选项卡
            element.tabAdd('warn-tab', {
                title: param.title
                ,content: html //支持传入html
                ,id: param.id
            });
            // 默认展开第一个tab页
            element.tabChange('warn-tab', param.id);
            element.render();
        }

    };
    /**
     * 初始化加载渠道
     */
    let initChannelList = function(){
        $.ajax({
            async:true
            ,type: "POST"
            ,data: {type: 0}
            ,url: "/client/channel/list"
            ,dataType: 'json'
            ,success: function(json){
                if(json.code == 200 && json.data != null){
                    let html ="";
                    json.data.forEach(function (currentValue, index, arr) {
                        if(currentValue.name=="邮件" || currentValue.name=="传真" || currentValue.name=="网站"){
                            html += "<div class='imgbox' data-id='"+currentValue.id+"' data-title='"+currentValue.name+"' data-channel='"+currentValue.name+"' data-code='"+currentValue.code+"' >";
                            html += "   <img src='/client/"+currentValue.icon+"' alt='"+currentValue.name+"' />";
                            html += "<span>"+currentValue.name+"</span>";
                            html += "</div>";
                        }
                    });
                    $(".channel-list").empty().append(html);
                }
            }
        });
    };

    /**
     * 获取当前时间
     */
    let dateTime = function(){
        var now = new Date();
        var date = new Date(now.getTime());
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        month = month < 10 ? "0"+month : month;
        day = day < 10 ? "0" + day : day;
        hour = hour < 10 ? "0" + hour : hour;
        minute = minute < 10 ? "0" + minute : minute;
        second = second < 10 ? "0" + second : second;
        return year + '年' + month + '月' + day  + '日' + hour + '时' + minute + '分';
    };
    /**
     * 渠道全选、反选
     */
    $(".channel-option").on("click", "div > span", function(element) {
        let text = $(this).text(),
            event = $(this).data("event");
        if(text == '全选'){
            // 给所有渠道添加选中样式
            $("." + event + " .imgbox").addClass("active");
            // 获取选中渠道
            let param = {
                /**
                 * 获取选中渠道
                 */
                "channels": function () {
                    var cId = [];
                    $("." + event + " .imgbox").each(function () {
                        cId.push({
                            channelId:$(this).data("id"),
                            channelName: $(this).data("title")
                        });
                    });
                    return cId;
                }()
                /**
                 * 获取选中地区
                 */
                ,"areas": [{areaId:employee.areaId, areaName:employee.areaName}]
            };
            //清除tab页所有内容
             $(".warn-tab .warn-tab-title, .warn-tab .warn-tab-content").empty();
            // 拼接预警内容和受众
            active.setGroupUsers(param);
        }else{
            // 1：取消渠道勾选
            $("." + event + " .imgbox").removeClass("active");
            // 2：清空内容回填提示信息
            $(".warn-card-content-list").empty().append("<div class='layui-col-xs12 layui-col-md12 warn-content-skip'>请选择业务类型</div>");
            // 3：清除tab页所有内容
            $(".warn-tab .warn-tab-title, .warn-tab .warn-tab-content").empty();
            // 拼回默认提示
            active.defaultWarnMsg({id:'choose-tab',title:'温馨提示',msg:'请选择渠道'});
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
     * 初始化Ueditor模板
     * @param isAppendTo
     */
    function setContent(isAppendTo) {
        active.editor.setContent(productTemplate.init(), isAppendTo);
    }

    /**
     * 设置内容
     */
    $("#btns").on("click","#setContent", isAppendTo => {
        active.editor.setContent(productTemplate.getDecisionServiceProductTemplate(null), isAppendTo);
        return false;
    });

    /**
     * 选择模板类型
     */
    form.on('select(type)',  function(data,isAppendTo) {
        var option={dateTime:dateTime(),group:employee.organizationName};
        var flag=data.value;
        if(flag==0) {
            active.editor.setContent(productTemplate.getDecisionServiceProductTemplate(option), isAppendTo);
        }else if(flag==1){
            active.editor.setContent(productTemplate.getForecastProductTemplate(option), isAppendTo);
        }else if(flag==2){
            active.editor.setContent(productTemplate.getWarnProductTemplate(option), isAppendTo);
        }else if(flag==3){
            active.editor.setContent(productTemplate.getDecisionServiceProductTemplate(option), isAppendTo);
        }else if(flag==4){
            active.editor.setContent(productTemplate.getWarnProductTemplate(option), isAppendTo);
        }else if(flag==5){
            active.editor.setContent(productTemplate.getForecastProductTemplate(option), isAppendTo);
        }
        // active.editor.setEnabled();//使其可以编辑
    });

    /**
     * 创建产品
     */
    $(".submit").on("click","#createProduct", () => {
        var type = $("#type option:selected").val();
        var title=$("#title").val();
        var text = active.editor.getContent();
        var group = {};
        if(type=="" || type==null){
            layer.msg('请选择模板', {time: 2000});
            return false;
        }
        if(title==""|| title==null ){
            layer.msg('请填写产品名称', {time: 2000});
            return false;
        }
        if($(".channel-list .imgbox.active").length == 0){
            layer.msg("请选择渠道", {time: 2000});
            return false;
        }

        layer.confirm('确定发布？', function(){
            $(".channel-list .imgbox.active").each(function () {
                var channelId = $(this).data("id");
                var channelName = $(this).data("channel");
                if(channelName=="邮件") {
                    zTree.getZTree("group_" + channelId).getCheckedNodes(true).forEach(function (item) {
                        if (channelName == "邮件") {
                            group = {
                                userGroupId: item.id,
                                userGroupName: item.name
                            };
                        }
                    });
                }
            });
            let param = {
                group:group.userGroupId,
                type:type,
                title:title,
                html: "<html><body>" + text + "</body></html>"
            };
            $.ajax({
                async: true,
                url: "/client/ueditor/getWord",
                data: param,
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if(data.code=="success"){
                        layer.msg('发布成功', {time: 2000});
                    }else{
                        layer.msg('发布失败', {time: 2000});
                    }
                }
            })
        });
    });

    /**
     * 初始化监听
     */
    UE.getEditor('editor').addListener( 'ready', function() {
        this.setHeight(400);
        this.initialFrameHeight='400px';//设置编辑器高度
    });

     initChannelList();      // 初始化加载渠道

});