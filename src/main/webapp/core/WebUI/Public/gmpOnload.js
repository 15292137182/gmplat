/**
 * Created by liyuanquan on 2017/9/19.
 */

//var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

// 全局变量
var GmpForm = {};
var GmpTable = {};
var GmpSearch = {};
var jsonDataConfig = null;
$(function() {
    fun1();
    if(typeof LoadingConfig =="function"){
        jsonDataConfig = LoadingConfig();
    }
    _function.push(fun2())
    $.when.apply($,_function)
        .done(function() {
            // alert("done");
            if(typeof gmp_onload == "function") {
                gmp_onload();
            }
        })
        .fail(function() {
            alert("页面加载失败");
        });
});

var _function = [];

var fun1 = function() {
    //TO-DO
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
                // creat.form(dtd, _json, param);
                _function.push(creat.form( _json, param));
            }
            // 表格模块
            if (item.attributes[":is"].value == "gmpTable") {
                param = item.attributes[":table-data"].value;
                // 渲染动态模板
                // creat.table(dtd, _json, param);
                _function.push(creat.table( _json, param));
            }
            // 查询模块
            if (item.attributes[":is"].value == "searchBlock") {
                param = item.attributes[":search-input"].value;
                // 渲染动态模板
                // creat.search(dtd, _json, param);
                _function.push(creat.search( _json, param));
            }
        });
    }

    // return dtd.promise(); // 返回promise对象
};

var fun2 = function(){
    var dtdObj = TableKeyValueSet.init();
    return dtdObj;
};

var TableKeyValueSet = (function(){
    var tableKeyValueSetIn='';
    var tableKeyValueSetOut='';

    var init = function(){
        var parameterStr='';
        if(typeof GlobalParameter =="function"){
            parameterStr=GlobalParameter();
            if("" !=parameterStr || undefined !=parameterStr || null!=parameterStr ){
                tableKeyValueSetIn=parameterStr;
                var arr=new Array();
                for(var i in parameterStr){
                    for (var j in parameterStr[i]){
                        for(var m in parameterStr[i][j]){
                            var str='"'+parameterStr[i][j][m]+'"';
                            arr.push(str);
                        }
                    }
                }
                arr="["+arr+"]";
                var dtd = $.Deferred();
                $.ajax({
                    url:serverPath+"/keySet/queryKeySet",
                    type:"get",
                    data:{
                        search:arr
                    },
                    dataType:"json",
                    xhrFields: {withCredentials: true},
                    success:function(res){
                        var param={};
                        // var jsonStr=JSON.parse(res.data);res.resp.content.data.result
                        /*tsj 17/08/28 修改后端返回结构*/
                        /**
                         * tsj 07/8/30 修改后端返回数据结构
                         **/
                        var jsonStr=JSON.parse(res.resp.content.data);
                        for(k in jsonStr){
                            var _param={};
                            for(m in jsonStr[k]){
                                var aa=jsonStr[k][m];
                                _param[aa.confKey]=aa.confValue;
                            }
                            param[k]=_param;
                        }
                        tableKeyValueSetOut=param;
                        dtd.resolve();
                    },
                    error:function(){
                        alert("未能获取键值集合")
                    }
                })

                return dtd.promise(); // 返回promise对象

            }else{
                alert("未传入参数");
            }
            return init;
        };
    }

    var getOptions=function () {
        return tableKeyValueSetIn;
    };

    var getData=function(){
        return tableKeyValueSetOut;
    };

    return {
        init:init,
        getOptions:getOptions,
        getData:getData
    }
})()

var creat = (function() {
    var form = function(data, param) {
        return dynamicObj.render(data, param);

        // return dtd.promise(); // 返回promise对象
    }

    var table = function(data, param) {
        return dynamicObj.render(data, param);

        // return dtd.promise(); // 返回promise对象
    };

    var search = function(data, param) {
        return dynamicObj.render(data, param);

        // return dtd.promise(); // 返回promise对象
    };

    return {
        form: form,
        table: table,
        search: search
    }
})();

var getHtml = (function() {
    var ajax_html = function(mainId, code, compId, params) {
        var codes = '['+'"'+code+'"'+']';
        var dtd = $.Deferred();
        $.ajax({
            url: serverPath + '/fronc/queryFuncCode',
            type: "get",
            dataType: "json",
            data: {funcCode: codes},
            success: function (res) {
                var arr = res.resp.content.data;
                if (arr != "") {
                    // 判断模板类型
                    if (arr[0].funcType == "form") {
                        var form = new gmpFormObj(compId, code, arr, mainId, "", "");
                        // 创建vue实例
                        form.bulidComponent(jsonDataConfig);
                        // 获取下拉框options
                        // console.log(form);
                        var _target = form.formObj;
                        var _obj = form.formBlockItems;
                        // 遍历当前功能块
                        $.each(_obj, function(index, item) {
                            // 若当前功能块属于下拉框并且之来源类型是keySet
                            if(item.displayWidget == "select-base" || item.valueResourceType == "keySet") {
                                var _key = item.ename;
                                var _rowId = item.valueResourceContent
                                if(_rowId != "") {
                                    // 获取option
                                    getOptions.option(_target, _key, _rowId);
                                }
                            }
                        });

                        // 缓存实例化对象
                        GmpForm[compId] = form;
                    }
                    if (arr[0].funcType == "grid") {
                        var _table = new gmpTableObj(jsonDataConfig,compId, code, arr, mainId, params.id, "", "", "", {
                            onClickRow: function (row) {},
                            onEditRow: function () {},
                            onDeleteRow: function () {},
                            onCellClick: function (row) {},
                            onDbClick: function (row) {},
                            onDbCellClick: function (row) {},
                        });
                        // 创建vue实例
                        _table.bulidComponent(_table);
                        // 缓存实例化对象
                        GmpTable[compId] = _table;
                        // alert("table done");
                    }
                    if (arr[0].funcType == "search") {
                        // alert("search start");
                        // console.log(_table);
                        var _search = new gmpsearchObj(compId, code, arr, mainId, [params.bind]);
                        // 创建vue实例
                        _search.bulidComponent(jsonDataConfig);
                        // 缓存实例化对象
                        GmpSearch[compId] = _search;
                    }
                }else {
                    // 提示错误信息
                    var _error = res.resp.content.msg;
                    gmpPopup.throwMsg(_error);
                }

                dtd.resolve(); // 改变Deferred对象的执行状态
            },
            error: function (res) {
                // 提示错误信息
                console.log(res);
            }
        });
        return dtd.promise();
    };

    return {
        html: ajax_html
    }
})();

var dynamicObj = (function() {
    // 用户配置
    function render(data, param) {
        // 获取配置参数
        var params = JSON.parse(data);
        // 创建对象
        return getHtml.html(params.main, params.code, param, params);
    };

    // 配置方法函数
    return {
        render: render
    }
})();

var getOptions = (function() {
    var option = function(target, key, _rowId) {
        // console.log(_rowId);
        // 调用接口传参
        var _param = {
            rowId: _rowId
        }
        // 调用接口
        $.ajax({
            url: serverPath + "/keySet/queryKeyCode",
            type: "get",
            data:  _param,
            dataType: "json",
            success:function(res) {
                if(res.resp.respCode == "000"){
                    if(res.resp.content.state == "1"){
                        var _jsonObj = res.resp.content.data;
                        // 循环配置value-label
                        for(var i = 0;i < _jsonObj.length;i++) {
                            _jsonObj[i].value = _jsonObj[i].confKey;
                            _jsonObj[i].label = _jsonObj[i].confValue;
                        }
                        // 赋值options
                        var _optionName = key + "Option";
                        target[_optionName] = _jsonObj;
                        // console.log(_jsonObj);
                    }
                }
            },
            error:function() {
                console.log("查询数据失败");
            }
        });
    };

    return {
        option: option
    }
})();