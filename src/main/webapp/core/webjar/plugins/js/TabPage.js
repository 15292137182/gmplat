var pagingObj = (function(){//分页不跳转回第一页调该方法
    /*tsj 17/08/28 修改后端返回结构*/
    var Example = function(url,args,pageSize,pageNum,obj,callback){
        $.ajax({
            url:url,
            type:"get",
            data:{
                args:args,
                pageSize:pageSize,
                pageNum:pageNum
            },
            dataType:"jsonp",
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    dataConversion.conversion(obj,res.resp.content.data.result);
                    obj.tableData = res.resp.content.data.result;//数据源
                    obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                    obj.pageNum = res.resp.content.data.pageNum;//当前页
                }else{
                    obj.tableData = [];
                }
                if(typeof callback =="function"){
                    callback(res);
                }
            },
            error:function(){
                obj.loading=false;
                alert("错误")
            }
        })
    }
    var Examples = function(url,rowId,args,pageSize,pageNum,obj,callback){//有依赖的分页查询
        $.ajax({
            url:url,
            type:"get",
            data:{
                rowId:rowId,
                args:args,
                pageSize:pageSize,
                pageNum:pageNum
            },
            dataType:"jsonp",
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    dataConversion.conversion(obj,res.resp.content.data.result);
                    obj.tableData = res.resp.content.data.result;//数据源
                    obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                    obj.pageNum = res.resp.content.data.pageNum;//定位到当前页
                }else{
                    obj.tableData = [];
                }
                if(typeof callback =="function"){
                    callback(res);
                }
            },
            error:function(){
                obj.loading=false;
                alert("错误")
            }
        })
    }
    var headSort = function(url,args,pageSize,pageNum,column,obj,callback){
        //列头排序
        //url:接口地址，args：table输入框，pageSize：每页多少条记录，pageNum：当前第几页
        //column：el函数当前列信息，obj:当前vue实例对象（this）,callback:成功后的回调函数
        var data = {};
        if(column.prop == null){
            //是否需要提示？？
            return;
        }
        if(column.order=="ascending"){
            data = {str:column.prop,num:1}
        }else{
            data = {str:column.prop,num:0}
        }
        var datas = JSON.stringify(data);
        $.ajax({
            url:url,
            type:"get",
            data:{
                args:args,
                pageSize:pageSize,
                pageNum:pageNum,
                order:datas
            },
            dataType:"jsonp",
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    dataConversion.conversion(obj,res.resp.content.data.result);
                    obj.tableData = res.resp.content.data.result;//数据源
                    obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                    obj.pageNum = 1;//定位到第一页
                }else{
                    obj.tableData = [];
                }
                if(typeof callback =="function"){
                    callback(res);
                }
            },
            error:function(){
                obj.loading=false;
                alert("错误111")
            }
        })
    }
    var headSorts1 = function(url,args,column,obj,callback){
        //有依赖的列头排序
        //url:接口地址，rowId:有依赖表的id，args：table输入框，column：el函数当前列信息，
        // obj:当前vue实例对象（this）,callback:成功后的回调函数
        var data = {};
        if(column.prop == null){
           //是否需要提示？？
            return;
        }
        if(column.order=="ascending"){
            data = {str:column.prop,num:1}
        }else{
            data = {str:column.prop,num:0}
        }
        var datas = JSON.stringify(data);
        $.ajax({
            url:url,
            type:"get",
            data:{
                args:args,
                pageSize:obj.pageSize,
                pageNum:1,
                order:datas
            },
            dataType:"jsonp",
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    dataConversion.conversion(obj,res.resp.content.data.result);
                    obj.tableData = res.resp.content.data.result;//数据源
                    obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                    obj.pageNum = 1;//定位到第一页
                }else{
                    obj.tableData = [];
                }
                if(typeof callback =="function"){
                    callback(res);
                }
            },
            error:function(){
                obj.loading=false;
                alert("错误")
            }
        })
    }
    var headSorts = function(url,rowId,args,column,obj,callback){
        //有依赖的列头排序
        //url:接口地址，rowId:有依赖表的id，args：table输入框，column：el函数当前列信息，
        // obj:当前vue实例对象（this）,callback:成功后的回调函数
        var data = {};
        if(column.prop == null){
            //是否需要提示？？
            return;
        }
        if(column.order=="ascending"){
            data = {str:column.prop,num:1}
        }else{
            data = {str:column.prop,num:0}
        }
        var datas = JSON.stringify(data);
        $.ajax({
            url:url,
            type:"get",
            data:{
                rowId:rowId,
                args:args,
                pageSize:obj.pageSize,
                pageNum:1,
                order:datas
            },
            dataType:"jsonp",
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    dataConversion.conversion(obj,res.resp.content.data.result);
                    obj.tableData = res.resp.content.data.result;//数据源
                    obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                    obj.pageNum = 1;//定位到第一页
                }else{
                    obj.tableData = [];
                }
                if(typeof callback =="function"){
                    callback(res);
                }
            },
            error:function(){
                obj.loading=false;
                alert("错误")
            }
        })
    }
    return {
        Example:Example,
        Examples:Examples,
        headSort:headSort,
        headSorts:headSorts,
        headSorts1:headSorts1
    }
})()

//表字段值替换
var dataConversion = (function(){
    //获取数据
    var getData = function(obj){//obj当前Vue实例对象
        var oId = obj.tableId;//表格ID
        //调用XX方法获取所有数据{'表格ID':{"Key":"Value"}};
        var data = TableKeyValueSet.getOptions();
        //var data = {"Tid":{"Fun":"code","Fun1":"code1"}};
        //   data.tableId = {"Key":"Value","key":"value"}
        var datas = data[oId];   //通过表格ID获取数据
        return datas;
    }
    //获取相同key
    var getKey = function(datas,arr){
        var keyArr = [];//保存datas与tableData中相同的key
        for(var i=0;i<1;i++){//获取tableData中的key
            for (var Key in arr[i]){//找出与datas相同的key
                for(var key in datas){
                    if(Key==key){
                        keyArr.push(key);
                    }
                }
            }
        }
        return keyArr;
    }
    //循环赋值
    var assignment = function(keyArr,arr,datas){
        for(var i=0;i<arr.length;i++){//遍历tableData（arr=Obj）
            var row = arr[i];
            for(var j=0;j<keyArr.length;j++){
                var setColumn = keyArr[j];
                //字段下面的具体代码（value）
                var val = datas[setColumn];
                //调用XXX方法获取所有代码对应的值{"val":{1:"文本",2:"日期"}}
                var data = TableKeyValueSet.getData()
                //var data = {"code":{1:"文本",2:"日期"}};
                //获取data下val的值
                var dataObj = data[val];
                var _newValue = dataObj[row[setColumn]];
                //重新赋值
                row[setColumn] = _newValue;
            }
        }
    }
    var conversion = function(obj,arr){
        var datas = getData(obj);
        var keyArr = getKey(datas,arr)
        assignment(keyArr,arr,datas);
    }
    return {
        conversion:conversion
    }
})()

var queryData = (function(){//刷新table跳转到第一页调改方法
    var getData = function(url,args,obj,callback){
        //url：分页查询接口，obj：当前vue实例对象（this），callback：成功后的回调函数
            $.ajax({
                url:url,
                type:"get",
                data:{
                    args:args,
                    pageSize:obj.pageSize,
                    pageNum:1
                },
                dataType:"jsonp",
                success:function(res){
                    obj.loading=false;
                    if(res.resp.respCode=="000"){
                        dataConversion.conversion(obj,res.resp.content.data.result);
                        obj.tableData = res.resp.content.data.result;//数据源
                        obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                        obj.pageNum = res.resp.content.data.pageNum;//当前页
                    }else{
                        obj.tableData = [];
                    }
                    if(typeof callback =="function"){
                        callback(res);
                    }
                },
                error:function(){
                    obj.loading=false;
                    alert("错误")
                }
            })
    }
    var getDatas = function(url,args,rowId,obj,callback){
        //url：分页查询接口，args：输入框，obj：当前vue实例对象（this），rowId：有依赖的ID，callback：成功后的回调函数
        $.ajax({
            url:url,
            type:"get",
            data:{
                rowId:rowId,
                args:args,
                pageSize:obj.pageSize,
                pageNum:1
            },
            dataType:"jsonp",
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    dataConversion.conversion(obj,res.resp.content.data.result);
                    obj.tableData = res.resp.content.data.result;//数据源
                    obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                    obj.pageNum = res.resp.content.data.pageNum;//当前页
                }else{
                    obj.tableData = [];
                }
                if(typeof callback =="function"){
                    callback(res);
                }
            },
            error:function(){
                obj.loading=false;
                alert("错误")
            }
        })
    }
    return {
        getData:getData,
        getDatas:getDatas
    }
})()

//删除确认框
var deleteObj = (function(){
    var del = function(callback){
        ibcpLayer.ShowConfirm("您确定删除吗?",function(){
            if(typeof callback =="function"){
                callback();
            }
        })
    }
    return {
        del:del
    }
})()

//新增确认框
var addObj = (function(){
    var addOk = function(callback){
        ibcpLayer.ShowConfirm("您确定新增吗?",function(){
            if(typeof callback =="function"){
                callback();
            }
        })
    }
    return {
        addOk:addOk
    }
})()

//编辑确认框
var editObj = (function(){
    var editOk = function(callback){
        ibcpLayer.ShowConfirm("您确定修改吗?",function(){
            if(typeof callback =="function"){
                callback();
            }
        })
    }
    return {
        editOk:editOk
    }
})()

var showMsg = (function(){
    var MsgOk = function(obj,data){
        //obj：需要弹出消息页面的Vue实例对象
        //msg：ajax成功后返回的res
        if(data.resp.respCode=="000"){//操作成功
            obj.$message({
                message: data.resp.content.msg,
                type: 'success'
            })
        }else{
            obj.$message.error(data.resp.content.msg);
        }
    }
    var MsgError = function(obj){
        //ajax相应失败
        obj.$message.error("请求失败！");
    }
    return {
        MsgOk:MsgOk,
        MsgError:MsgError
    }
})()


/*
* Vue实例抽出data，ajax
* */

var getData = (function(){
    var dataObj = function(json){
        var data = {};
        if(json){
            data = json;
            data.loading=true;
            data.input='';
            data.tableData=[];
            data.leftHeight='';
            data.pageSize=10;
            data.pageNum=1;
            data.allDate=0;
        }else{
             data = {
                loading:true,//加载提示效果
                input:'',//输入框
                tableData:[],//table数据
                leftHeight:'',//左边表格高度
                pageSize:10,//每页显示多少条
                pageNum:1,//第几页
                allDate:0//共多少条
            }
        }
        return data
    }
    return {
        dataObj:dataObj
    }
})()

var gmpAjax = (function(){
    var showAjax = function(url,jsonData,callback){
        $.ajax({
            url:url,
            type:"get",
            data:jsonData,
            dataType:"jsonp",
            success:function(res){
                if(typeof callback == "function"){
                    callback(res);
                }
            },
            error:function(){
                alert("请求失败")
            }
        })
    }
    return{
        showAjax:showAjax
    }
})()

/**
 * 下拉框选择组件
 * @type {{setOpt}}
 */
var SelectOptions = (function(){
    var setOpt = function(templateId,modelName,keysetCode,path){
        var _data={};
        var _modelName='value';
        if(undefined !=modelName && null !=modelName && "" !=modelName){
            _modelName=modelName;
        }
        _data[_modelName]='';
        _data["options"]=[];
        _data["list"]=[];
        var _templateId='tid';
        if(undefined !=templateId && null !=templateId && "" !=templateId){
            _templateId=templateId;
        }
        var selectComponet = {
            template: '#'+_templateId,
            data : function(){
                return _data;
            },
            methods: {
                flush() {
                    var _path = serverPath+"/keySet/queryNumber";
                    var param = {"args":keysetCode};
                    if(undefined != path && null != path && "" != path){
                        _path = path;
                        param = {};
                    }
                    this.$http.jsonp(_path, param, {
                        jsonp: 'callback'
                    }).then(function (res) {
                        this.list=res.data.resp.content.data;
                        this.options=this.list;
                    });
                }
                /*,
                 queryMethod(query){
                 if(query !=''){
                 this.options = this.list.filter(function(item) {
                 return item.confValue.indexOf(query) > -1 || item.confKey.indexOf(query)>-1;
                 });
                 }else{
                 this.options=this.list;
                 }
                 }*/
            },
            created(){
                this.flush();
            }
        }
        return selectComponet;
    };

    //关联下拉框查询
    var ConSetOpt = function(templateId,modelName,keysetCode,path){
        var _data={};
        _data[modelName]='';
        _data["options"]=[];
        _data["list"]=[];

        var selectComponet = {
            template: '#'+templateId,
            data : function(){
                return _data;
            },
            methods: {
                flush() {
                    var param = {"rowId":keysetCode};
                    this.$http.jsonp(path, param, {
                        jsonp: 'callback'
                    }).then(function (res) {
                        this.list=res.data.resp.content.data;
                        this.options=this.list;
                    });
                }
            },
            created(){
                this.flush();
            }
        }
        return selectComponet;
    };

    return {
        setOpt:setOpt,
        ConSetOpt:ConSetOpt
    }
})()


/**
 * 表格下拉框 值显示
 */

//var tabSelectShow={"dataSetConfig":
//                                  {"datasetType":"dataSetConfigType"}};
var TableKeyValueSet = (function(){
    var tableKeyValueSetIn='';
    var tableKeyValueSetOut='';
    var init = function(tableStr){
        if("" !=tableStr || undefined !=tableStr || null!=tableStr ){
            tableKeyValueSetIn=tableStr;
            var arr=new Array();
            for(var i in tableStr){
                for (var j in tableStr[i]){
                    var str='"'+tableStr[i][j]+'"';
                    arr.push(str);
                }
            }
            arr="["+arr+"]";
            $.ajax({
                url:serverPath+"/keySet/queryKeySet",
                type:"get",
                data:{
                    args:arr
                },
                dataType:"jsonp",
                success:function(res){
                    var param={};
                    // var jsonStr=JSON.parse(res.data);res.resp.content.data.result
                    /*tsj 17/08/28 修改后端返回结构*/
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






