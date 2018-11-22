
layui.config({
    base: '/static/layuiadmin/modules/' //假设这是你存放拓展模块的根目录
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
     * 自定义验证规则
     */
    form.verify({
        level: function (value) {
            if(value.length == 0) return '请选择地区级别';
        }
        ,pId: function (value) {

           if($(".pId").hasClass("layui-hide") == false){
               if(value.length == 0) {
                   $("#addPId .addPIdShow, #updatePId .updatePIdShow").css("border-color","red");
                   return '请选择上级地区';
               }
           }
        }
        ,areaName: function(value){
            if(value.length == 0) return '请输入地区名称';
            if(value.length > 20) return '地区名称长度不能超过20位';

        }
        ,code: function (value) {
            if(value.length == 0) return '请输入地区编码';
            if(!(value >= 10000000000000 && value <= 99999999999999)) return '地区编码范围值为[10000000000000, 99999999999999]';
        }
    });

    /**
     * 统一按钮操作对象
     * @type {{addBtn: 添加信息, deleteBtn: 批量删除信息, deleteOption: 删除单个信息, updateOption: 修改信息}}
     */
    let active = {

    };

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

});