
layui.config({
    base: '/static/layuiadmin/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    selectTree: 'selectTree' //如果 mymod.js 是在根目录，也可以不用设定别名
    ,mod1: 'modules' //相对于上述 base 目录的子目录
});

layui.use(["table","element","form","laytpl","layer","selectTree"], function(){
    let table = layui.table			// 引用layui表格
        ,form = layui.form			// 引用layui表单
        ,laytpl = layui.laytpl		// 引用layui模板引擎
        ,layer = layui.layer		// 引用layui弹出层
        ,selectTree = layui.selectTree
        ,element = layui.element
        ,$ = layui.$;   			// 引用layui的jquery

    /**
     * 自定义验证规则
     */
    form.verify({
        host: value => {
            if(value.length == 0) return '请输入FTP连接地址如：192.168.1.133';
        }
        ,port: value => {
            if(value.length == 0) return '请输入FTP连接端口如：21';
        }
        ,user: value => {
            if(value.length == 0) return '请输入FTP所在电脑登录用户';
        }
        ,password: value => {
            if(value.length == 0) return '请输入FTP所在电脑登录密码';
        }
        ,path: value => {
            if(value.length == 0) return '请输入FTP映射目录';
        }

    });

    /**
     * 统一按钮操作对象
     * @type
     */
    let active = {

        /**
         * 统一请求后台，获取数据
         * @param param
         * @param callback
         */
        getData: (param, callback) => {
            $.ajax({
                async:true
                ,type: param.type
                ,data: param.data
                ,url: param.url
                ,dataType: 'JSON'
                ,success: function(json){
                    callback(json);
                }
            });
        }

        /**
         * 初始化加载渠道配置信息
         */
        ,initChannelInfo: () => {
            active.getData({
                type: "POST",
                data: {type:"MESSAGE_FTP"},
                url: "/channel/config/select/type"
            }, result => {
                if(result.code == 200){
                    let data = result.data[0].content;
                    form.val("form-filter", $.parseJSON(data));
                }
            });
        }
    };

    /**
     * 触发表单按钮点击事件后，立刻监听form表单提交，向后台传参
     */
    form.on("submit(submitBtn)", function(data){
        data.field.channelCode = "MESSAGE_FTP";
        active.getData({
            type: "POST",
            data: data.field ,
            url: "/channel/config/insert"
        }, result => {
            layer.msg(result.msg, {time: 2000});
        });
    });

    /**
     * 监听列表中按钮事件
     */
    table.on('tool(table)', function(obj){
        active[obj.event] ? active[obj.event].call(this, obj) : '';
    });

    /**
     * 监控表头工具条按钮事件
     */
    $('.tableBar .layui-btn').on('click', function(){
        let type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    // 初始化加载配置渠道信息
    active.initChannelInfo();
});