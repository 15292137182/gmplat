/**
 * Created by liyuanquan on 2017/9/15.
 */

/**
 * @description:页面加载执行函数
 * @author:liyuanquan
 */
$(function() {
    var _components = $("body").find("components");
    // 若当前页面存在components
    if (_components.length > 0) {
        // 循环遍历当前页面components
        $.each(_components, function(index, item) {
            var _json = item.attributes["data"].value,
                param;
            // 表单模块
            if (item.attributes[":is"].value == "gmpForm") {
                param = item.attributes[":child-form-table"].value;
                // 渲染动态模板
                dynamicObj.render(_json, param);
            }
            // 表格模块
            if (item.attributes[":is"].value == "gmpTable") {
                param = item.attributes[":table-data"].value;
                // 渲染动态模板
                dynamicObj.render(_json, param);
            }
            // 查询模块
            if (item.attributes[":is"].value == "search") {}
        });
    }
});

/**
 * @description:gmp弹出框方法定义
 * @author:liyuanquan
 */
var gmpPopup = (function() {
    // 自定义索引
    var gmpPopIndex = 29891014;

    var _index;

    // 弹出div
    var dynamicDiv = function(divHtmlUrl, title, width, height, callback) {
        gmpPopIndex = gmpPopIndex + 10;
        var divIndex = layer.open({
            // layer提供了5种层类型。可传入的值有：0（信息框，默认）；1（页面层）；2（iframe层）；3（加载层）；4（tips层）。
            type: 1,
            // 弹出动画
            anim: 5,
            title: [title, 'font-size:18px;font-weight:bold;'],
            // 是否固定弹出
            fix: false,
            // 最大小化按钮
            maxmin: false,
            // 控制点击弹层外区域关闭
            shadeClose: false,
            // 窗口大小 area: ['400px', '500px'] or auto 自适应大小
            area: [width, height],
            // 是否允许拉伸
            resize: false,
            // 浏览器滚动条
            scrollbar: false,
            // 自定义index
            zIndex: gmpPopIndex,
            // 成功弹出      layero:当前layer层对象；index 当前layer层对象索引
            success: function(layero, index) {
                // 找出当前layer弹出层的div对象 作为 容器
                var div = layero.find(".layui-layer-content");
                div.attr("layerIndex", index);
                div.css("overflow", 'hidden');
                div.load(divHtmlUrl, function() {
                    // 取消父页面的焦点
                    var aaa = layero.find(
                        ".layui-layer-ico,.layui-layer-close,.layui-layer-close1");
                    aaa.focus(); //先设置弹出页的焦点
                    aaa.blur(); //再取消焦点

                    var components = $(this).find("components");

                    $.each(components, function(index, item) {
                        var _json = item.attributes["data"].value,
                            param;
                        // 表单模块
                        if (item.attributes[":is"].value == "gmpForm") {
                            param = item.attributes[":child-form-table"].value;
                            // 渲染动态模板
                            dynamicObj.render(_json, param);
                        }
                        // 表格模块
                        if (item.attributes[":is"].value == "gmpTable") {
                            param = item.attributes[":table-data"].value;
                            // 渲染动态模板
                            dynamicObj.render(_json, param);
                        }
                        // 查询模块
                        if (item.attributes[":is"].value == "search") {}

                        if (callback) {
                            callback();
                        }
                    });
                });
            }
        });

        _index = divIndex;
        return divIndex;
    };

    var throwMsg = function(msg) {
        gmpPopIndex = gmpPopIndex + 10;
        layer.open({
            type: 0,
            skin: 'layui-layer-lan',
            anim: 5,
            title: '提示',
            fix: false,
            maxmin: false,
            content: msg,
            resize: false,
            scrollbar: false,
            zIndex: gmpPopIndex,
            end: function() {}
        });
    };

    // 关闭弹层
    var Close = function () {
        layer.close(_index);
    };

    return {
        dynamicDiv: dynamicDiv,
        throwMsg: throwMsg,
        close: Close
    }
})();

// 全局变量
var GmpForm = {};
var GmpTable = {};
var GmpSearch = {};

/**
 * @description:创建gmp动态模块
 * @author:liyuanquan
 */

// 表单对象
var dynamicObj = (function() {
    // 用户配置
    function render(data, param) {
        // 获取配置参数
        var params = JSON.parse(data);
        // 创建对象
        create(params.main, params.code, param);
    };

    function create(mainId, code, compId) {
        var demoData = null;
        htmlAjax.keyValue(code, false, function(res) {
            var arr = res.resp.content.data;
            if (arr != "") {
                // 判断模板类型
                if (arr[0].funcType == "form") {
                    var form = new gmpFormObj(compId, code, arr, mainId, "", "");
                    // 创建vue实例
                    form.bulidComponent();
                    // 缓存实例化对象
                    GmpForm[compId] = form;
                }
                if (arr[0].funcType == "grid") {
                    var divIndex = -1;
                    var _table = new gmpTableObj(compId, code, arr, mainId, "", "", "", {
                        onClickRow: function(row) {
                            // console.log("click row data: " + JSON.stringify(row));
                        },
                        onEditRow: function() {},
                        onDeleteRow: function() {
                            // console.log("delete row");
                            // this.removeRow("dataset_code", "test001");
                        },
                        onCellClick: function(row) {
                            // console.log("cell click data: " + JSON.stringify(row));
                        },
                        onDbClick: function(row) {
                            // console.log("double click data: " + JSON.stringify(row));
                        },
                        onDbCellClick: function(row) {
                            // console.log("double click cell data: " + JSON.stringify(row));
                        },
                    });
                    // 创建vue实例
                    _table.bulidComponent();
                    // 缓存实例化对象
                    GmpTable[compId] = _table;
                }
                if (arr[0].funcType == "search") {}
            } else {
                // 提示错误信息
                var _error = res.resp.content.msg;
                gmpPopup.throwMsg(_error);
            }
        });
    };

    // 配置方法函数
    return {
        render: render
    }
})();

/**
 * @description:
 * @author:liyuanquan
 */

// 动态表单对象
function gmpFormObj(compId, blockId, formBlockItems, vueEl, postUrl, postParam, formUrl) {
    this.compId = compId; //父组件名字
    this.blockId = blockId; //功能块标识
    this.formBlockItems = formBlockItems; //表单块key值集合

    this.vueEl = vueEl; //vue el
    this.vueObj = null; //vue对象实例
    this.formObj = {
        disabled: {}
    }; //表单数据Key : value

    this.postUrl = postUrl //获取后端数据接口
    this.postParam = postParam //请求参数参数json
    this.formUrl = formUrl //提交接口
};

// 获取vue实例对象
gmpFormObj.prototype.getVueObj = function() {
    return this.vueObj;
};

// 父组件数据
gmpFormObj.prototype.searchSelect = function() {
    var compId = this.compId;
    var arr = this.formBlockItems;
    for (var j = 0; j < arr.length; j++) {
        var objs = arr[j].ename;
        var disa = arr[j].ename + "Disabled";
        this.formObj[objs] = '';
        this.formObj.disabled[disa] = false;
    }
    var obj = {
        props: []
    }
    obj[compId] = this.formObj;
    return obj;
};

// 构建表单组件
gmpFormObj.prototype.bulidComponent = function(exeFunction) {
    var strHtml = DynamicStitchings.Concatenation(this.formBlockItems);
    var that = this;
    var id = that.vueEl;
    var vue = new Vue({
        el: id,
        data: that.searchSelect(), //获取父组件数据
        computed: { //表单组件定义
            gmpForm() { //form输入框组件
                var template = strHtml.html;
                var props = ["childFormTable"]; //子组件参数名
                return {
                    template,
                    props
                }
            }
        },
        methods: {
            // demo(){
            //     console.log(that.getData());
            // }
            demo: exeFunction
        }
    });
    this.vueObj = vue;
};

// 获取表单数据
gmpFormObj.prototype.getData = function() {
    var arr = this.formBlockItems;
    var formObj = this.formObj;
    var data = {};
    var dataConfig = {};
    for (var i = 0; i < arr.length; i++) {
        if (formObj[arr[i].ename] instanceof Date) {
            var date = new Date(formObj[arr[i].ename]);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            if (m < 10) {
                m = "0" + m;
            }
            if (d < 10) {
                d = "0" + d;
            }
            var newDate = y + "-" + m + "-" + d;
            formObj[arr[i].ename] = newDate;
        }
        data[arr[i].ename] = formObj[arr[i].ename];
        dataConfig[(arr[i].ename) + "Disabled"] = formObj.disabled[(arr[i].ename) + "Disabled"];
    }
    return {
        data: data,
        dataConfig: dataConfig
    };
};

// 数据直接加载表单域
gmpFormObj.prototype.loadData = function(json) {
    var formObj = this.formObj;
    for (var key in json) {
        formObj[key] = json[key];
    }
};

// 获取表单域中控件的值
gmpFormObj.prototype.getItemValue = function(key) {
    return this.formObj[key];
};

// 设置表单域中控制的值
gmpFormObj.prototype.setItemValue = function(key, value) {
    this.formObj[key] = value;
};

// 获取表单域中控件的对象
gmpFormObj.prototype.getItem = function(key) {
    var selectDom = "[data=" + key + "]";
    var dom = $(selectDom).children();
    return dom;
};

// 获取 表单配置参数
gmpFormObj.prototype.getOptions = function() {
    var data = {};
    data.compId = this.compId; //父组件名字
    data.blockId = this.blockId; //功能块标识
    data.formBlockItems = this.formBlockItems; //表单块key值集合

    data.vueEl = this.vueEl; //vue el
    data.vueObj = this.vueObj; //vue对象实例
    data.formObj = this.formObj; //表单数据Key : value

    data.postUrl = this.postUrl; //获取后端数据接口
    data.postParam = this.postParam; //请求参数参数json
    data.formUrl = this.formUrl; //提交地址
    return data;
};

// 设置 表单ajax参数
gmpFormObj.prototype.setOptions = function(json) {
    this.postUrl = json.postUrl; //获取后端数据接口
    this.postParam = json.postParam; //请求参数参数json
};

// 获取表单域数据
gmpFormObj.prototype.request = function(callback) {
    var that = this;
    var data = this.postParam;
    var url = this.postUrl;
    $.ajax({
        url: url,
        type: "post",
        data: data,
        success: function(res) {
            var arr = res.resp.content.data;
            for (var j = 0; j < arr.length; j++) {
                var obj = arr[j];
                for (var k in obj) {
                    that.formObj[k] = obj[k];
                }
            }
            if (callback) {
                callback(res.resp.content.data);
            }
        }
    })
};

// 禁用 表单域控件
gmpFormObj.prototype.lock = function(keyArr) {
    for (var j = 0; j < keyArr.length; j++) {
        var disabled = keyArr[j] + "Disabled";
        this.formObj.disabled[disabled] = true;
    }
};

// 启用 表单域控件
gmpFormObj.prototype.unlock = function(keyArr) {
    for (var j = 0; j < keyArr.length; j++) {
        var disabled = keyArr[j] + "Disabled";
        this.formObj[disabled] = false;
    }
};

//清空表单域数据
gmpFormObj.prototype.clear = function() {
    var formObj = this.formObj;
    for (var key in formObj) {
        if (key != "disabled") {
            formObj[key] = '';
        }
    }
};

// 提交表单
gmpFormObj.prototype.submit = function(json, callback) {
    var that = this;
    if (json["formUrl"] == 'undefined') {
        if (that.formUrl == '') {
            gmpPopup.throwMsg("未定义提交接口");
            return
        }
        json["formUrl"] = that.formUrl;
    }
    if (json["data"] == 'undefined') {
        var datas = that.getData();
        json["data"] = datas.data;
    }
    if (json["isValidate"]) {
        //验证方法
    }
    $.ajax({
        url: json["formUrl"],
        type: "post",
        data: json["data"],
        success: function(res) {
            if (callback) {
                callback(res);
            }
        }
    })
};

/**
 *
 * @description:表格对象
 * @author:liyuanquan
 */

//动态表格对象
function gmpTableObj(compId, blockId, formBlockItems, vueEl, postUrl, queryParam, submitUrl, jsonFunction) {
    this.compId = compId; //父组件名字
    this.blockId = blockId; //功能块标识
    this.formBlockItems = formBlockItems; //表格块key值集合

    this.vueEl = vueEl; //vue el
    this.vueObj = null; //vue对象实例
    this.tableObjArr = []; //表格数组对象数据
    this.searchId = []; //表格关联的查询块
    this.queryUrl = '' //表格查询接口
    this.postUrl = postUrl //获取后端数据接口
    this.queryParam = queryParam //表格查询参数json
    this.submitUrl = submitUrl //表格提交接口
    this.isCheckData = true; //是否为提交表格选中的数据，默认为true
    this.records = [] //表格初始数据
    this.height = '' //表格高度，默认自适应
    this.pagination = true; //表格是否分页， 默认分页true
    this.columns = [] //表格初始列信息
    this.custom = [] //表格自定义列信息 ignore
    this.singleSelect = false //表格单选/多选 默认单选
    this.pageSize = '' //每页显示多少条数据，当表格需要分页时有效
    this.pageNo = ''; //当前第多少页，当表格需要分页时有效
    this.total = ''; //共多少条数据，当表格需要分页时有效

    this.rows = []; //选中行数据

    if (jsonFunction) {
        this.onClickRow = jsonFunction.onClickRow; //单击行事件
        this.onEditRow = jsonFunction.onEditRow; //编辑行事件
        this.onDeleteRow = jsonFunction.onDeleteRow; //删除行事件
        this.onDbClick = jsonFunction.onDbClick; //双击行事件
        this.onCellClick = jsonFunction.onCellClick; //单元格点击事件
        this.onDbCellClick = jsonFunction.onDbCellClick; //单元格双击事件
        this.onCheck = jsonFunction.onCheck; //表格选中事件
        this.onUnCheck = jsonFunction.onUnCheck; //表格取消选中事件
        this.onCheckAll = jsonFunction.onCheckAll; //表格全选事件
        this.onUnCheckAll = jsonFunction.onUnCheckAll; //表格移除全选事件
    }
};

//父组件数据
gmpTableObj.prototype.searchSelect = function() {
    var that = this;
    var compId = this.compId;
    var clickRowTime = null;
    var cellClickTime = null;
    //单击行事件
    this.tableObjArr["clickRow"] = function(row) {
        clearTimeout(clickRowTime);
        clickRowTime = setTimeout(function() {
            that.onClickRow(row);
        }, 300);
    }
    //双击行事件
    this.tableObjArr["dblclick"] = function(row, event) {
        clearTimeout(clickRowTime);
        that.onDbClick(row);
    }
    //单元格单击事件
    this.tableObjArr["cellClick"] = function(row, column, cell) {
        clearTimeout(cellClickTime);
        cellClickTime = setTimeout(function() {
            that.onCellClick(row, null, null, row[column.property]);
        }, 300);
    }
    //单元格双击事件
    this.tableObjArr["DbClickCell"] = function(row, column, cell) {
        clearTimeout(cellClickTime);
        that.onDbCellClick(row, null, null, row[column.property])
    }
    //获取选中记录
    this.tableObjArr["getCheckedRows"] = function(val) {
        that.rows = val;
    }
    this.tableObjArr["editRow"] = function(index, row) {
        that.onEditRow(index, row);
    }
    //删除按钮事件
    this.tableObjArr["deleteRow"] = function(index, row) {
        that.onDeleteRow(index, row);
    }
    var obj = {
        props: [],
    }
    obj[compId] = this.tableObjArr;
    return obj;
};

//构建表格组件
gmpTableObj.prototype.bulidComponent = function() {
    var strHtml = DynamicStitchings.Concatenation(this.formBlockItems);
    var that = this;
    var id = that.vueEl;
    var vue = new Vue({
        el: id,
        data: that.searchSelect(), //获取父组件数据
        computed: { //表单组件定义
            gmpTable() { //表格组件
                var template = strHtml.html;
                var props = ["tableData"]; //子组件参数名
                return {
                    template,
                    props,
                }
            }
        },
        methods: {
            //
        }
    });
    this.vueObj = vue;
};

//表格数据直接加载方法
gmpTableObj.prototype.reload = function(json) {
    if (json.queryUrl) {
        this.queryUrl = json.queryUrl;
    }
    if (json.queryParam) {
        this.queryParam = json.queryParam;
    }
    var that = this;
    if (this.queryUrl == null) {
        gmpPopup.throwMsg("未定义表格查询接口");
        return;
    }
    $.ajax({
        url: that.queryUrl,
        type: "get",
        dataType: "json",
        data: that.queryParam,
        success: function(res) {
            that.loadRecord(res.resp.content.data.result);
        },
        error: function() {
            gmpPopup.throwMsg("表格查询数据失败！")
        }
    })
};

// 表格由给定数据加载
gmpTableObj.prototype.loadRecord = function(data) {
    // 清空数据
    this.tableObjArr = [];
    // 重新注入方法
    this.searchSelect();
    for (var j = 0; j < data.length; j++) {
        this.tableObjArr.push(data[j]);
    }
    var parentComponentName = this.compId;
    this.vueObj[parentComponentName] = this.tableObjArr;
};

//获取选中行数据
gmpTableObj.prototype.getCheckedRows = function() {
    return this.rows;
};

//获取选中记录总数
gmpTableObj.prototype.getCheckedRowsCount = function() {
    return this.rows.length;
};

//设置表格参数
gmpTableObj.prototype.setOptions = function(json) {
    if (json.searchId) {
        this.searchId = json.searchId; //表格关联的查询块
    }
    if (json.queryUrl) {
        this.queryUrl = json.queryUrl //表格查询接口
    }
    if (json.postUrl) {
        this.postUrl = json.postUrl //获取后端数据接口
    }
    if (json.queryParam) {
        this.queryParam = json.queryParam //表格查询参数json
    }
    if (json.submitUrl) {
        this.submitUrl = json.submitUrl //表格提交接口
    }
    if (json.isCheckData) {
        this.isCheckData = json.isCheckData; //是否为提交表格选中的数据，默认为true
    }
    if (json.records) {
        this.records = json.records //表格初始数据
    }
    if (json.height) {
        this.height = json.height //表格高度，默认自适应
    }
    if (json.pagination) {
        this.pagination = json.pagination; //表格是否分页， 默认分页true
    }
    if (json.columns) {
        this.columns = json.columns //表格初始列信息
    }
    if (json.custom) {
        this.custom = json.custom //表格自定义列信息 ignore
    }
    if (json.singleSelect) {
        this.singleSelect = json.singleSelect //表格单选/多选 默认单选
    }
    if (json.pageSize) {
        this.pageSize = json.pageSize //每页显示多少条数据，当表格需要分页时有效
    }
    if (json.pageNo) {
        this.pageNo = json.pageNo; //当前第多少页，当表格需要分页时有效
    }
    if (json.total) {
        this.total = json.total; //共多少条数据，当表格需要分页时有效
    }
};

gmpTableObj.prototype.loadData = function() {
    var that = this;
    if (this.queryUrl == null) {
        gmpPopup.throwMsg("未定义表格查询接口");
        return;
    }
    $.ajax({
        url: that.queryUrl,
        type: "get",
        dataType: "json",
        data: that.queryParam,
        success: function(res) {
            var data = res.resp.content.data.result;
            if (data.length > 0) {
                GmpTable.tables.loadRecord(data);
            }
        },
        error: function() {
            gmpPopup.throwMsg("表格查询数据失败！");
        }
    })
};

//获取表格参数
gmpTableObj.prototype.getOptions = function() {
    var json = {};
    json.compId = this.compId; //父组件名字
    json.blockId = this.blockId; //功能块标识
    json.formBlockItems = this.formBlockItems; //表格块key值集合

    json.vueEl = this.vueEl; //vue el
    json.vueObj = this.vueObj; //vue对象实例
    json.tableObjArr = this.tableObjArr; //表格数组对象数据
    json.searchId = this.searchId; //表格关联的查询块
    json.queryUrl = this.queryUrl //表格查询接口
    json.postUrl = this.postUrl //获取后端数据接口
    json.queryParam = this.queryParam //表格查询参数json
    json.submitUrl = this.submitUrl //表格提交接口
    json.isCheckData = this.isCheckData; //是否为提交表格选中的数据，默认为true
    json.records = this.records //表格初始数据
    json.height = this.height //表格高度，默认自适应
    json.pagination = this.pagination; //表格是否分页， 默认分页true
    json.columns = this.columns //表格初始列信息
    json.custom = this.custom //表格自定义列信息 ignore
    json.singleSelect = this.singleSelect //表格单选/多选 默认单选
    json.pageSize = this.pageSize //每页显示多少条数据，当表格需要分页时有效
    json.pageNo = this.pageNo; //当前第多少页，当表格需要分页时有效
    json.total = this.total; //共多少条数据，当表格需要分页时有效

    json.rows = this.rows; //选中行数据
    return json;
};

// 提交表格数据
gmpTableObj.prototype.submit = function(json) {
    var that = this;
    $.ajax({
        url: that.submitUrl,
        type: "post",
        dataType: 'json',
        data: json.data,
        success: function(res) {
            json.callback(res);
        }
    })
};

//获取表格所有数据
gmpTableObj.prototype.getAllData = function() {
    var arr = [];
    for (var j = 0; j < this.tableObjArr.length; j++) {
        if (typeof this.tableObjArr[j] == 'object') {
            arr.push(this.tableObjArr[j]);
        }
    }
    return arr;
};

//获取表格所有数据总数
gmpTableObj.prototype.getAllDataCount = function() {
    var arr = [];
    for (var j = 0; j < this.tableObjArr.length; j++) {
        if (typeof this.tableObjArr[j] == 'object') {
            arr.push(this.tableObjArr[j]);
        }
    }
    return arr.length;
};

//添加表格数据(集合数组)
gmpTableObj.prototype.addRows = function(rowArr) {
    for (var j = 0; j < rowArr.length; j++) {
        this.tableObjArr.push(rowArr[j]);
    }
};

//添加表格数据,指定位置添加一条
gmpTableObj.prototype.addRow = function(json, index) {
    this.tableObjArr.splice(index, 0, json);
};

//删除表格指定索引数据
gmpTableObj.prototype.removeRowByIndex = function(index) {
    this.tableObjArr.splice(index, 1);
};

//删除指定列的指定值数据
gmpTableObj.prototype.removeRow = function(column, value) {
    console.log(column);
    console.log(value);
    var arr = [];
    for (var j = 0; j < this.tableObjArr.length; j++) {
        if (typeof this.tableObjArr[j] == "object") {
            arr.push(this.tableObjArr[j]);
        }
    }
    for (var j = 0; j < arr.length; j++) {
        if (arr[j][column] == value) {
            var index = arr[j];
            console.log(index);
            this.tableObjArr.splice(index, 1);
        }
    }
};

//清除表格数据
gmpTableObj.prototype.clear = function() {
    this.tableObjArr = []; //清空缓存数据
    var parentComponentName = this.compId;
    this.vueObj[parentComponentName] = [];
};

$.when(fun1, fun2)
    .done(function(){
        if(typeof gmp_onload == "function"){
            gmp_onload();
        }
    })
    .fail(function(){
        alert("页面加载失败");
    });


var fun1 = function(){
    var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

    //TO-DO
    dtd.resolve(); // 在最终返回处，改变Deferred对象的执行状态

    return dtd.promise(); // 返回promise对象
}

var fun2 = function(){
    var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

    //TO-DO
    dtd.resolve(); // 在最终返回处，改变Deferred对象的执行状态

    return dtd.promise(); // 返回promise对象
}