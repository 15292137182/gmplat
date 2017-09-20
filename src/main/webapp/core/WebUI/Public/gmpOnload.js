/**
 * Created by liyuanquan on 2017/9/19.
 */

var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象

// 全局变量
var GmpForm = {};
var GmpTable = {};
var GmpSearch = {};

$(function() {
    $.when(fun1(dtd), fun2(dtd))
        .done(function() {
            if(typeof gmp_onload == "function") {
                gmp_onload();
            }
        })
        .fail(function() {
            alert("页面加载失败");
        });
});

var fun1 = function(dtd) {
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
                dynamicObj.render(dtd, _json, param);
            }
            // 表格模块
            if (item.attributes[":is"].value == "gmpTable") {
                param = item.attributes[":table-data"].value;
                // 渲染动态模板
                dynamicObj.render(dtd, _json, param);
            }
            // 查询模块
            if (item.attributes[":is"].value == "searchBlock") {
                param = item.attributes[":search-input"].value;
                // 渲染动态模板
                dynamicObj.render(dtd, _json, param);
            }
        });
    }

    return dtd.promise(); // 返回promise对象
};

var fun2 = function(dtd){
    TableKeyValueSet.init(dtd);



    return dtd.promise(); // 返回promise对象

};

var TableKeyValueSet = (function(){
    var tableKeyValueSetIn='';
    var tableKeyValueSetOut='';

    var init = function(dtd){
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

var getHtml = (function() {
    var ajax_html = function(dtd, mainId, code, compId) {
        var codes = '['+'"'+code+'"'+']';
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
                        form.bulidComponent();
                        // 缓存实例化对象
                        GmpForm[compId] = form;
                    }
                    if (arr[0].funcType == "grid") {
                        var _table = new gmpTableObj(compId, code, arr, mainId, "", "", "", {
                            onClickRow: function (row) {
                            },
                            onEditRow: function () {
                            },
                            onDeleteRow: function () {
                            },
                            onCellClick: function (row) {
                            },
                            onDbClick: function (row) {
                            },
                            onDbCellClick: function (row) {
                            },
                        });
                        // 创建vue实例
                        _table.bulidComponent();
                        // 获取下拉框options
                        // var _obj = _table.formObj;
                        // console.log(_table);
                        // for(var key in _obj) {
                        //     var _suffix = key.substr(-1, 6);
                        //     console.log(_suffix);
                        // }
                        // 缓存实例化对象
                        GmpTable[compId] = _table;
                        // alert("table done");
                    }
                    if (arr[0].funcType == "search") {
                        // alert("search start");
                        // var _search = new gmpsearchObj(compId, code, arr, mainId, [_table]);
                        // // 创建vue实例
                        // _search.bulidComponent();
                        // // 缓存实例化对象
                        // GmpSearch[compId] = _search;
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
    };

    return {
        html: ajax_html
    }
})();

var dynamicObj = (function() {
    // 用户配置
    function render(dtd, data, param) {
        // 获取配置参数
        var params = JSON.parse(data);
        // 创建对象
        create(dtd, params.main, params.code, param);
    };

    function create(dtd, mainId, code, compId) {
        getHtml.html(dtd, mainId, code, compId);
    };

    // 配置方法函数
    return {
        render: render
    }
})();