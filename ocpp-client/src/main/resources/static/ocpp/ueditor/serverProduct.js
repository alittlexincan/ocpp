
layui.config({
    base: '/client/layuiadmin/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    selectTree: 'selectTree' //如果 mymod.js 是在根目录，也可以不用设定别名
    ,mod1: 'modules' //相对于上述 base 目录的子目录
});

layui.use(["table","form","laytpl","layer","selectTree"], function(){
    let table = layui.table			// 引用layui表格
        ,form = layui.form			// 引用layui表单
        ,laytpl = layui.laytpl		// 引用layui模板引擎
        ,layer = layui.layer		// 引用layui弹出层
        ,selectTree = layui.selectTree
        ,$ = layui.$;   			// 引用layui的jquery

    /**
     * 格式化模板
     * @param d
     * @returns {string}
     */
    let typeFormat = function(d){
        if(d.type == 0) return "短期预报";
        if(d.type == 1) return "中期预报";
        if(d.type == 2) return "长期预报";
        if(d.type == 3) return "气象专题专报";
        if(d.type == 4) return "重大气象专题专报";
        if(d.type == 5) return "气象预警专题专报";
    };


    /**
     * 数据提交到后台（通用发方法）
     * @param option
     */
    let submitServer = function(option){
        $.ajax({
            async:true
            ,type: option.type
            ,data: option.param
            ,url: option.url
            ,dataType: 'json'
            ,success: function(json){
                if(option.index != null) layer.close(option.index);
                if(json.code == 200){
                    // 刷新列表
                    reloadTable();
                }
                // 弹出提示信息，2s后自动关闭
                layer.msg(json.msg, {time: 2000});
            }
        });
    };

    /**
     * 加载表格
     */
    table.render({
        id: 'table'
        ,elem: '#table'
        ,url:'/client/serverProduct/select'
        ,page:true
        ,even: true
        ,height: 'full-165'
        ,limits:[10,20,50,100]
        ,cols: [[
             // {type: 'checkbox'}
            {type: 'numbers', title: '编号'}
            ,{field: 'type', title: '模板类型', sort: true, templet:typeFormat}
            ,{field: 'title', title: '标题',sort: true,edit: true}
            ,{field: 'path', title: '文件路径',sort: true}
            ,{field: 'createTime', title: '创建时间', sort: true}
            ,{title: '操&nbsp;&nbsp;作', width: 170, align:'center', toolbar: '#btnGroupOption'}
        ]]
    });

    /**
     * 修改后重新刷新列表，curr: 1重新从第 1 页开始
     */
    let reloadTable = function (param) {
        table.reload('table', {
            page: {
                curr: 1
            },
            where: { //设定异步数据接口的额外参数，任意设
                type: param == undefined ? '' : param.type
                ,title: param == undefined ? '' : param.title
            }
        });
    };

    /**
     * 统一按钮操作对象
     * @type {{addBtn: 添加信息, deleteBtn: 批量删除信息, deleteOption: 删除单个信息, updateOption: 修改信息}}
     */
    let active = {
        /**
         * 下载产品
         * @param obj
         */
        'downOption': function (obj) {
        let param = obj.data;
            window.location.href="/client/serverProduct/downFile?path="+ encodeURI(encodeURI(obj.data.path));
        }
        /**
         * 列表中：删除选中信息
         * @param obj
         */
        ,'deleteOption': function (obj) {
            layer.confirm('确定删除？', function(index){
                obj.del();
                // 数据提交到后台，通用方法
                submitServer({
                    index: index
                    ,param: null
                    ,type: 'DELETE'
                    ,url: '/client/serverProduct/delete/' + obj.data.id,
                });
            });
        }
    }

    /**
     * 监听头部搜索
     */
    form.on('submit(search)', function(data){
        reloadTable(data.field);
    });

    /**
     * 监听列表中按钮事件
     */
    table.on('tool(table)', function(obj){
        active[obj.event] ? active[obj.event].call(this, obj) : '';
     if(obj.event === 'edit'){
            layer.alert('编辑行：<br>'+ JSON.stringify(data))
        }
    });

    /**
     * 监控表头工具条按钮事件
     */
    $('.tableBar .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });



});