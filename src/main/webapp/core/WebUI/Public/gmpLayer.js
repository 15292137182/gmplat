/**
 * Created by liyuanquan on 2017/9/15.
 */

/**
 * @description:
 * @author:liyuanquan
 */
var gmpPopup = (function() {
    // 自定义索引
    var gmpPopIndex = 29891014;

    // 弹出div
    var dynamicDiv = function (divHtmlUrl, title, width, height, callback) {
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
            success: function (layero, index) {
                // 找出当前layer弹出层的div对象 作为 容器
                var div = layero.find(".layui-layer-content");
                div.attr("layerIndex", index);
                div.css("overflow", 'hidden');
                div.load(divHtmlUrl, function () {
                    // 取消父页面的焦点
                    var aaa = layero.find(
                        ".layui-layer-ico,.layui-layer-close,.layui-layer-close1");
                    aaa.focus();    //先设置弹出页的焦点
                    aaa.blur();     //再取消焦点

                    var components = $(this).find("components");

                    $.each(components, function(index, item) {
                        var _json,
                            param;
                        if(item.attributes[":is"].value == "gmpForm") {
                            _json = item.attributes["data"].value;
                            param = item.attributes[":child-form-table"].value;

                            GmpFormObj.render(_json, param);
                        }

                        if (callback) {
                            callback();
                        }
                    });
                });
            }
        });

        return divIndex;
    }

    return {
        dynamicDiv: dynamicDiv
    }
})();

/**
 * @description:创建gmp动态模块
 * @author:liyuanquan
 */

// 表单对象
var GmpFormObj = (function() {
    // 用户配置
    function render(data, param) {
        // 获取配置参数
        var params = JSON.parse(data);
        // 创建对象
        create(params.main, params.code, param);
    };

    function create(mainId, code, compId) {
        htmlAjax.keyValue(code, function(res) {
            var arr = res.resp.content.data;
            // 判断模板类型
            if(arr[0].funcType == "form") {
                var form = new GmpForm1(compId, code, arr, mainId, "", "");
                // 创建vue实例
                form.bulidComponent();
                done(form);
            }
            if(arr[0].funcType == "table") {}
            if(arr[0].funcType == "search") {}
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
function GmpForm1(compId, blockId, formBlockItems, vueEl, postUrl, postParam, formUrl) {
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
GmpForm1.prototype.getVueObj = function() {
    return this.vueObj;
};

// 父组件数据
GmpForm1.prototype.searchSelect = function() {
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
GmpForm1.prototype.bulidComponent = function(exeFunction) {
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
GmpForm1.prototype.getData = function() {
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
GmpForm1.prototype.loadData = function(json) {
    var formObj = this.formObj;
    for (var key in json) {
        formObj[key] = json[key];
    }
};

// 获取表单域中控件的值
GmpForm1.prototype.getItemValue = function(key) {
    return this.formObj[key];
};

// 设置表单域中控制的值
GmpForm1.prototype.setItemValue = function(key, value) {
    this.formObj[key] = value;
};

// 获取表单域中控件的对象
GmpForm1.prototype.getItem = function(key) {
    var selectDom = "[data=" + key + "]";
    var dom = $(selectDom).children();
    return dom;
};

// 获取 表单配置参数
GmpForm1.prototype.getOptions = function() {
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
GmpForm1.prototype.setOptions = function(json) {
    this.postUrl = json.postUrl; //获取后端数据接口
    this.postParam = json.postParam; //请求参数参数json
};

// 获取表单域数据
GmpForm1.prototype.request = function(callback) {
    var data = this.postParam;
    console.log(this.postParam);
    var url = this.postUrl;
    $.ajax({
        url: url,
        type: "get",
        data: { data },
        success: function(res) {
            if (callback) {
                callback(res);
            }
        }
    })
};

// 禁用 表单域控件
GmpForm1.prototype.lock = function(keyArr) {
    for (var j = 0; j < keyArr.length; j++) {
        var disabled = keyArr[j] + "Disabled";
        this.formObj.disabled[disabled] = true;
    }
};

// 启用 表单域控件
GmpForm1.prototype.unlock = function(keyArr) {
    for (var j = 0; j < keyArr.length; j++) {
        var disabled = keyArr[j] + "Disabled";
        this.formObj[disabled] = false;
    }
}
//清空表单域数据
GmpForm1.prototype.clear = function() {
    var formObj = this.formObj;
    for (var key in formObj) {
        if (key != "disabled") {
            formObj[key] = '';
        }
    }
};

// 提交表单
GmpForm1.prototype.submit = function(json, callback) {
    var that = this;
    if (json["formUrl"] == 'undefined') {
        if (that.formUrl == '') {
            alert("未定义提交接口");
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
