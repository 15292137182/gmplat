//分页不跳转回第一页调该方法
var pagingObj = (function(){
    /*tsj 17/08/28 修改后端返回结构*/
    var Example = function(url,search,pageSize,pageNum,obj,callback){
        $.ajax({
            url:url,
            type:"get",
            data:{
                search:search,
                pageSize:pageSize,
                pageNum:pageNum
            },
            dataType:"json",
            xhrFields: {withCredentials: true},
            success:function(res){
                console.log(res);
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    if(res.resp.content.state==-1){
                        obj.tableData = [];//数据源
                        obj.allDate = 0;//总共多少条数据
                        obj.pageNum = 1;//当前页
                        return;
                    }
                    //dataConversion.conversion(obj,res.resp.content.data.result);
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
    var Examples = function(url,rowId,search,pageSize,pageNum,obj,callback){//有依赖的分页查询
        $.ajax({
            url:url,
            type:"get",
            data:{
                rowId:rowId,
                search:search,
                pageSize:pageSize,
                pageNum:pageNum
            },
            dataType:"json",
            xhrFields: {withCredentials: true},
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    if(res.resp.content.state==-1){
                        obj.tableData = [];//数据源
                        obj.allDate = 0;//总共多少条数据
                        obj.pageNum = 1;//当前页
                        return;
                    }
                    //dataConversion.conversion(obj,res.resp.content.data.result);
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
    var headSort = function(url,search,pageSize,pageNum,column,obj,callback){
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
                search:search,
                pageSize:pageSize,
                pageNum:pageNum,
                order:datas
            },
            dataType:"json",
            xhrFields: {withCredentials: true},
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    if(res.resp.content.state==-1){
                        obj.tableData = [];//数据源
                        obj.allDate = 0;//总共多少条数据
                        obj.pageNum = 1;//当前页
                        return;
                    }
                    //dataConversion.conversion(obj,res.resp.content.data.result);
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
    var headSorts1 = function(url,search,column,obj,callback){
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
                search:search,
                pageSize:obj.pageSize,
                pageNum:1,
                order:datas
            },
            dataType:"json",
            xhrFields: {withCredentials: true},
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    if(res.resp.content.state==-1){
                        obj.tableData = [];//数据源
                        obj.allDate = 0;//总共多少条数据
                        obj.pageNum = 1;//当前页
                        return;
                    }
                    //dataConversion.conversion(obj,res.resp.content.data.result);
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
    var headSorts = function(url,rowId,search,column,obj,callback){
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
                search:search,
                pageSize:obj.pageSize,
                pageNum:1,
                order:datas
            },
            dataType:"json",
            xhrFields: {withCredentials: true},
            success:function(res){
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    if(res.resp.content.state==-1){
                        obj.tableData = [];//数据源
                        obj.allDate = 0;//总共多少条数据
                        obj.pageNum = 1;//当前页
                        return;
                    }
                    //dataConversion.conversion(obj,res.resp.content.data.result);
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

//刷新table跳转到第一页调该方法
var queryData = (function(){
    var getData = function(url,search,obj,callback){
        //url：分页查询接口，obj：当前vue实例对象（this），callback：成功后的回调函数
            $.ajax({
                url:url,
                type:"get",
                data:{
                    search:search,
                    pageSize:obj.pageSize,
                    pageNum:1
                },
                dataType:"json",
                xhrFields: {withCredentials: true},
                success:function(res){
                    obj.loading=false;
                    if(res.resp.respCode=="000"){
                        //dataConversion.conversion(obj,res.resp.content.data.result);
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
    var getDatas = function(url,search,rowId,obj,callback){
        //url：分页查询接口，args：输入框，obj：当前vue实例对象（this），rowId：有依赖的ID，callback：成功后的回调函数
        $.ajax({
            url:url,
            type:"get",
            data:{
                rowId:rowId,
                search:search,
                pageSize:obj.pageSize,
                pageNum:1
            },
            dataType:"json",
            xhrFields: {withCredentials: true},
            success:function(res){
                console.log(res);
                obj.loading=false;
                if(res.resp.respCode=="000"){
                    if(res.resp.content.data!=null){
                        //dataConversion.conversion(obj,res.resp.content.data.result);
                        obj.tableData = res.resp.content.data.result;//数据源
                        obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                        obj.pageNum = res.resp.content.data.pageNum;//当前页
                    }

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

//弹出消息
var showMsg = (function(){
    var MsgOk = function(obj,msg){
        //obj：需要弹出消息页面的Vue实例对象
        //msg：ajax成功后返回的res
            obj.$message({
                message: msg,
                type: 'success'
            })
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
        //需要自定义的data数据，格式json（"key":"value"） data:getData.dataObj(json)
        //不需要自定义不用传参数！！！data:getData.dataObj()
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
    /**
     * tsj 07/8030 ajax公共方法重构
     **/
    var showAjax = function(dataJson,callback){
        //dataJson:json格式的数据
        // (url：接口地址,jsonData:后端请求数据，obj：需要弹出消息层的vue实例),
        // callbakc:成功后的回调函数
        var data = dataJson;
        $.ajax({
            url:data.url,
            type:"post",
            data:data.jsonData,
            xhrFields: {withCredentials: true},
            dataType:"json",
            success:function(res){
                console.log(res);
                if(res.resp.content.state==1){
                    if (typeof callback == "function") {
                        console.log(res);
                        callback(res.resp.content);
                        showMsg.MsgOk(data.obj,res.resp.content.msg);
                    }
                }else{
                    callback(res.resp.content);
                    showMsg.MsgOk(data.obj,res.resp.content.msg);
                }
            },
            error:function(res){
                console.log(res);
                    data.obj.$message.error("操作失败");
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
    //templateId在public.js中小代码组件的id
    //modelName下拉框的值
    //keysetCode指键值集合中的某个编号
    //path  后端接口
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
                    var _path = serverPath+"/keySet/queryKeyCode";
                    var param = {"keyCode":keysetCode};
                    if(undefined != path && null != path && "" != path){
                        _path = path;
                        param = {};
                    }
                    this.$http.jsonp(_path, param, {
                        jsonp: 'callback'
                    }).then(function (res) {
                        /**
                         * tsj 07/8/30 修改后端返回数据结构
                         **/
                        this.list=res.data.resp.content.data.result;
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
                        /**
                         * tsj 07/8/30 修改后端返回数据结构
                         **/
                        this.list=res.data.resp.content.data.result;
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
                        console.log(res);
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

//测试代码
var index = 0;
var getSelectData = function(){
    if(index==0){
        var obj = {arr1:[{
            value: 'id1',
            label: '测试Value'
        },{
            value: 'id2',
            label: '测试Value1'
        }]}
    }else{
        var obj ={arr2:[{
            value: 'ids1',
            label: '测试V'
        },{
            value: 'ids2',
            label: '测试V1'
        }]}
    }
        index++;
        return obj;
}

//动态加载html代码片段
var DynamicStitching = (function(){
    var searchBlockArr = [];//查询块输入框key值集合
    var formBlockArr = [];//表单块key值集合
    var OperationColumn ='<el-table-column fixed="right" label="操作"width="100"><template scope="scope"><el-button type="text" size="small" icon="edit"></el-button><el-button type="text" size="small" icon="delete"></el-button></template></el-table-column>';
    var dataHtmlObj = null;//用来保存vue实例对象
    var formObj = {//form表单对象
        a:false
    };
    var tableObjArr = [];
    var searchObj = {//父组件属性
        select:'',
        click:function(){//父组件查询块按钮事件
            var obj = dataHtmlObj;
            console.log(obj.ruleForm1.select);
            for(var j=0;j<searchBlockArr.length;j++){
                console.log(obj.ruleForm1[searchBlockArr[j]]);
            }
        }
    }
    //查询块
    var searchBlock = function(obj){//动态拼接查询块html代码片段
        var str='';
        var width = obj.widthSetting;
        if(width==''){
            width="24";
        }
        // var keywordOne = obj.keywordOne;
        // var keywordTwo = obj.keywordTwo;
        // var keywordThree = obj.keywordThree;
        var keywordOne = "关键字1";
        var keywordTwo = "关键字2";
        var keywordThree = "关键字3";
        searchBlockArr.push(obj.ename);//存搜索框key值的数组
        searchObj[obj.ename]='';//创建搜索框对象属性，名字为key名
        var model = "searchInput." + obj.ename;//绑定v-model数据
        // str='<el-col :span='+width+'><el-input placeholder="请输入内容" v-model="searchInput.input5"><el-select v-model="searchInput.select" slot="prepend" placeholder="请选择" style="width:100px"><el-option label='+keywordOne+' value="1"></el-option><el-option label=keywordTwo value="2"></el-option><el-option label=keywordThree value="3"></el-option></el-select><el-button slot="append" @click="searchInput.click" icon="search"></el-button></el-input></el-col>'
        str='<el-col :span='+width+'><el-input placeholder="请输入内容" v-model="'+ model +'"><el-select v-model="searchInput.select" slot="prepend" placeholder="请选择" style="width:100px"><el-option label='+keywordOne+' value="1"></el-option><el-option label='+keywordTwo+' value="2"></el-option><el-option label='+keywordThree+' value="3"></el-option></el-select><el-button slot="append" @click="searchInput.click" icon="search"></el-button></el-input></el-col>'
        return str;
    }
    //表单块
    var formBlock = function(obj,caseName){
        if(obj.widthSetting){
            var width = obj.widthSetting;
        }else{
            var width = 24;
        }
        var laberName = obj.displayTitle;
        switch (caseName){
            case 'input'://单行文本框
                formBlockArr.push(obj.ename);//存单行文本框key值的数组
                formObj[obj.ename]='';//创建表单对象属性，名字为key名
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-input placeholder=请输入'+laberName+' v-model="'+model+'"></el-input></el-form-item></el-col></el-row>'
                break;
            case 'textarea'://多行文本框
                formBlockArr.push(obj.ename);//存单行文本框key值的数组
                formObj[obj.ename]='';//创建表单对象属性，名字为key名
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-input type="textarea" placeholder=请输入'+laberName+' v-model="'+model+'"></el-input></el-form-item></el-col></el-row>'
                break;
            case "select-base"://下拉框
                formBlockArr.push(obj.ename);//存下拉框key值的数组
                formObj[obj.ename]='';//创建表单对象属性，名字为key名
                var op =getSelectData();
                var keys
                for( keys in op){
                    formObj[keys]=op[keys];
                }
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var options ="item in childFormTable."+keys;
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-select v-model="'+model+'" placeholder="请选择" :disabled="childFormTable.a"><el-option v-for="'+options+'" :key="item.value" :label="item.label" :value="item.value"></el-option></el-select></el-form-item></el-col></el-row>';
                break;
            case "checkbox"://复选框
                formBlockArr.push(obj.ename);//存单行文本框key值的数组
                formObj[obj.ename]='';//创建表单对象属性，名字为key名
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var str='<el-row><el-col :span="20"><el-form-item style="margin-top: -15px" label='+laberName+'><el-checkbox data="'+obj.ename+'"  v-model="'+model+'">复选框</el-checkbox></el-form-item></el-col></el-row>';
                break;
            case "radio"://单选框
                formBlockArr.push(obj.ename);//存单行文本框key值的数组
                formObj[obj.ename]='';//创建表单对象属性，名字为key名
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var str='<el-row><el-col :span="20"><el-form-item style="margin-top: -15px" label='+laberName+'><el-radio v-model="'+model+'" class="radio">单选框</el-radio></el-form-item></el-col></el-row>';
                break;
            case "date"://日期框
                formBlockArr.push(obj.ename);//存单行文本框key值的数组
                formObj[obj.ename]='';//创建表单对象属性，名字为key名
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><div class="block"><el-date-picker v-model="'+model+'" placeholder="选择日期时间"></el-date-picker></div></el-form-item></el-col></el-row>'
                break;
        }
        return str;
    }
    //列表块
    var tableBlock = function(obj){
        tableObjArr.push(obj.ename);//存表格列key值的数组
        var title = obj.displayTitle;
        var data = obj.ename;
        var column ='<el-table-column prop="'+data+'" label='+title+'></el-table-column>'
        return column;
    }
    var Concatenation = function(arr){//判断是什么功能块，并获取html片段
        var htmlObj = {};
        var str = '';
        var tableColumn=null;//table列
        var i;
        for(i=0;i<arr.length;i++){
            var obj = arr[i];
            if(obj.funcType =='search'){
                htmlObj.search = searchBlock(obj);
            }
            if(obj.funcType =='form'){
                if(obj.displayWidget=='input'){
                    str +=formBlock(obj,'input');
                }
                if(obj.displayWidget=='textarea'){
                    str +=formBlock(obj,'textarea');
                }
                if(obj.displayWidget=="select-base"){
                    str +=formBlock(obj,"select-base");
                }
                if(obj.displayWidget=="checkbox"){
                    str +=formBlock(obj,"checkbox");
                }
                if(obj.displayWidget=="radio"){
                    str +=formBlock(obj,"radio");
                }
                if(obj.displayWidget=="date"){
                    str +=formBlock(obj,"date");
                }
            }
            if(obj.funcType =="grid"){
                tableColumn += tableBlock(obj);
            }
        }
        if(i == arr.length){//判断是否全部拼接完
            htmlObj.form ='<el-form label-width="100px" :model="childFormTable">'+str+'</el-form>';
            htmlObj.table='<el-table :data="childTable.tableData" border style="width: 100%">'+tableColumn+OperationColumn+'</el-table>';
        }
        return htmlObj;
    }
    var htmlComponent = function(code,callback){//得到后端接口数据
        var str='';
        for(var i =0;i<code.length;i++){
            str += '"'+code[i]+'"'+','
        }
        var newStr = str.substring(0,str.length-1);
        var arr = "["+newStr+"]";
        $.ajax({
            url:serverPath+'/fronc/queryFuncCode',
            type:'get',
            data:{funcCode:arr},
            dataType:"jsonp",
            success:function(res){
                var data = res.resp.content.data.result;
                var arr = [];
                for(var i=0;i<data.length;i++){
                    arr.push(data[i]);
                }
                callback(arr);
            }
        })
    }
    var searchSelect = function(){//父组件数据
        return {
            props: [],
            ruleForm1:searchObj,
            formTable:formObj,
            table:{
                tableData: [{l:"测试",test00:"demo"}]
            }
        }
    }
    var InstanceObject = function(obj){//获取当前实例对象
        dataHtmlObj = obj;
    }
    return {
        htmlComponent:htmlComponent,//
        InstanceObject:InstanceObject,//
        searchSelect:searchSelect,//
        Concatenation:Concatenation,//
        formBlockArr:formBlockArr,//表单块key值集合
        formObj:formObj//表单对象
    }
})()

//获取html内容
var htmlAjax = (function(){
    var keyValue = function(code,callback){
        var codes = '['+'"'+code+'"'+']';
        $.ajax({
            url:serverPath+'/fronc/queryFuncCode',
            type:"get",
            dataType:"json",
            data:{funcCode:codes},
            success:function(res){
                if(callback){
                    callback(res);
                }
            },
            error:function (res) {
                console.log(res);
            }
        })
    }
    return {
        keyValue:keyValue
    }
})();
//动态加载html代码片段
var DynamicStitchings = (function(){
    //查询块
    var searchBlock = function(obj){//动态拼接查询块html代码片段
        var str='';
        var width = obj.widthSetting;
        if(width==''){
            width="24";
        }
        var keywordOne = "关键字1";
        var keywordTwo = "关键字2";
        var keywordThree = "关键字3";
        var model = "searchInput." + obj.ename;//绑定v-model数据
        str='<el-col :span='+width+'><el-input placeholder="请输入内容" v-model="'+ model +'"><el-select v-model="searchInput.select" slot="prepend" placeholder="请选择" style="width:100px"><el-option label='+keywordOne+' value="1"></el-option><el-option label='+keywordTwo+' value="2"></el-option><el-option label='+keywordThree+' value="3"></el-option></el-select><el-button slot="append" @click="searchInput.click" icon="search"></el-button></el-input></el-col>'
        return str;
    }
    //表单块
    var formBlock = function(obj,caseName){
        if(obj.widthSetting){
            var width = obj.widthSetting;
        }else{
            var width = 24;
        }
        var laberName = obj.displayTitle;
        switch (caseName){
            case 'input'://单行文本框
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var disab = "childFormTable." + "disabled." + obj.ename + "Disabled";
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-input data="'+obj.ename+'" :disabled="'+disab+'" placeholder=请输入'+laberName+' v-model="'+model+'"></el-input></el-form-item></el-col></el-row>'
                break;
            case 'textarea'://多行文本框
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var disab = "childFormTable." + "disabled." + obj.ename + "Disabled";
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-input type="textarea" :disabled="'+disab+'" placeholder=请输入'+laberName+' v-model="'+model+'"></el-input></el-form-item></el-col></el-row>'
                break;
            case "select-base"://下拉框
                var disab = "childFormTable." + "disabled." + obj.ename + "Disabled";
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-select :disabled="'+disab+'" placeholder="请选择"><el-option></el-option></el-select></el-form-item></el-col></el-row>';
                break;
            case "checkbox"://复选框
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var disab = "childFormTable." + "disabled." + obj.ename + "Disabled";
                var str='<el-row><el-col :span="20"><el-form-item style="margin-top: -15px" label='+laberName+'><el-checkbox :disabled="'+disab+'" data="'+obj.ename+'"  v-model="'+model+'">复选框</el-checkbox></el-form-item></el-col></el-row>';
                break;
            case "radio"://单选框
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var disab = "childFormTable." + "disabled." + obj.ename + "Disabled";
                var str='<el-row><el-col :span="20"><el-form-item style="margin-top: -15px" label='+laberName+'><el-radio :disabled="'+disab+'" v-model="'+model+'" class="radio">单选框</el-radio></el-form-item></el-col></el-row>';
                break;
            case "date"://日期框
                var model = "childFormTable." + obj.ename;//绑定v-model数据
                var disab = "childFormTable." + "disabled." + obj.ename + "Disabled";
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><div class="block"><el-date-picker :disabled="'+disab+'" v-model="'+model+'" placeholder="选择日期时间"></el-date-picker></div></el-form-item></el-col></el-row>'
                break;
        }
        return str;
    }
    //列表块
    var tableBlock = function(obj){
        var title = obj.displayTitle;
        var data = obj.ename;
        var column ='<el-table-column prop="'+data+'" label='+title+'></el-table-column>'
        return column;
    }
    var Concatenation = function(arr){//判断是什么功能块，并获取html片段
        var htmlObj = {
            item : arr
        };
        var html = '';
        var str = '';
        var tableColumn='';//table列
        var OperationColumn ='<el-table-column fixed="right" label="操作"width="100"><template scope="scope"><el-button type="text" size="small" icon="edit" @click="tableData.editRow"></el-button><el-button type="text" size="small" icon="delete" @click="tableData.deleteRow"></el-button></template></el-table-column>';
        var i;
        for(i=0;i<arr.length;i++){
            var obj = arr[i];
            if(obj.funcType =='search'){
                htmlObj.type = obj.funcType;
                html = searchBlock(obj);
            }
            if(obj.funcType =='form'){
                htmlObj.type = obj.funcType;
                if(obj.displayWidget=='input'){
                    str +=formBlock(obj,'input');
                }
                if(obj.displayWidget=='textarea'){
                    str +=formBlock(obj,'textarea');
                }
                if(obj.displayWidget=="select-base"){
                    str +=formBlock(obj,"select-base");
                }
                if(obj.displayWidget=="checkbox"){
                    str +=formBlock(obj,"checkbox");
                }
                if(obj.displayWidget=="radio"){
                    str +=formBlock(obj,"radio");
                }
                if(obj.displayWidget=="date"){
                    str +=formBlock(obj,"date");
                }
            }
            if(obj.funcType =="grid"){
                htmlObj.type = obj.funcType;
                tableColumn += tableBlock(obj);
            }
        }
        if(i == arr.length){//判断是否全部拼接完
            if(obj.funcType =='form'){
                html ='<el-form label-width="100px" :model="childFormTable">'+str+'</el-form>';
            }
            if(obj.funcType =="grid"){
                html='<el-table :data="tableData" @row-click="tableData.clickRow" border style="width: 100%">'+tableColumn+OperationColumn+'</el-table>';
            }
        }
        htmlObj.html = html;
        return htmlObj;
    }
    return {
        Concatenation:Concatenation
    }
})()
//动态表单对象
function GmpForm1(compId, blockId, formBlockItems, vueEl,postUrl,postParam,formUrl){
    this.compId = compId;//父组件名字
    this.blockId = blockId; //功能块标识
    this.formBlockItems = formBlockItems;//表单块key值集合

    this.vueEl = vueEl;     //vue el
    this.vueObj = null;    //vue对象实例
    this.formObj = {
        disabled:{}
    }; //表单数据Key : value

    this.postUrl = postUrl //获取后端数据接口
    this.postParam = postParam //请求参数参数json
    this.formUrl = formUrl //提交接口
}
//获取vue实例对象
GmpForm1.prototype.getVueObj = function(){
    return this.vueObj;
}
//父组件数据
GmpForm1.prototype.searchSelect = function(){
    var compId = this.compId;
    var arr = this.formBlockItems;
    for(var j=0;j<arr.length;j++){
        var objs =arr[j].ename;
        var disa = arr[j].ename + "Disabled";
        this.formObj[objs] = '';
        this.formObj.disabled[disa] = false;
    }
    var obj = {
        props:[]
    }
    obj[compId] = this.formObj;
    return obj;
}
//构建表单组件
GmpForm1.prototype.bulidComponent = function(exeFunction){
    var strHtml = DynamicStitchings.Concatenation(this.formBlockItems);
    var that = this;
    var id = that.vueEl;
    var vue = new Vue({
        el: id,
        data:that.searchSelect(),//获取父组件数据
        computed:{//表单组件定义
            gmpForm(){//form输入框组件
                var template= strHtml.html;
                var props = ["childFormTable"];//子组件参数名
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
            demo:exeFunction
        }
    });
    this.vueObj = vue;
}

//获取表单数据
GmpForm1.prototype.getData = function(){
    var arr = this.formBlockItems;
    var formObj = this.formObj;
    var data = {};
    var dataConfig = {};
    for(var i=0;i<arr.length;i++){
        if(formObj[arr[i].ename] instanceof Date){
            var date = new Date(formObj[arr[i].ename]);
            var y = date.getFullYear();
            var m =date.getMonth()+1;
            var d = date.getDate();
            if( m < 10){
                m = "0" + m;
            }
            if( d < 10){
                d ="0" + d;
            }
            var newDate = y+"-"+m+"-"+d;
            formObj[arr[i].ename] = newDate;
        }
        data[arr[i].ename] = formObj[arr[i].ename];
        dataConfig[(arr[i].ename)+"Disabled"] = formObj.disabled[(arr[i].ename)+"Disabled"];
    }
    return {
        data:data,
        dataConfig:dataConfig
    };
}
//数据直接加载表单域
GmpForm1.prototype.loadData = function(json){
    var formObj = this.formObj;
    for(var key in json){
        formObj[key] = json[key];
    }
}
//获取表单域中控件的值
GmpForm1.prototype.getItemValue = function(key){
    return this.formObj[key];
}
//设置表单域中控制的值
GmpForm1.prototype.setItemValue = function(key,value){
    this.formObj[key] = value;
}
//获取表单域中控件的对象
GmpForm1.prototype.getItem = function(key){
    var selectDom = "[data=" +key+ "]";
    var dom = $(selectDom).children();
    return dom;
}
//获取 表单配置参数
GmpForm1.prototype.getOptions = function(){
   var data = {};
    data.compId = this.compId;//父组件名字
    data.blockId = this.blockId; //功能块标识
    data.formBlockItems = this.formBlockItems;//表单块key值集合

    data.vueEl = this.vueEl;     //vue el
    data.vueObj = this.vueObj;    //vue对象实例
    data.formObj = this.formObj; //表单数据Key : value

    data.postUrl = this.postUrl; //获取后端数据接口
    data.postParam = this.postParam; //请求参数参数json
    data.formUrl = this.formUrl;//提交地址
    return data;
}
//设置 表单ajax参数
GmpForm1.prototype.setOptions = function(json){
    this.postUrl = json.postUrl; //获取后端数据接口
    this.postParam = json.postParam; //请求参数参数json
}
//获取表单域数据
GmpForm1.prototype.request = function(callback){
    var data = this.postParam;
    console.log(this.postParam);
    var url = this.postUrl;
    $.ajax({
        url:url,
        type:"get",
        data:{data},
        success:function(res){
            if(callback){
                callback(res);
            }
        }
    })
}
//禁用 表单域控件
GmpForm1.prototype.lock = function(keyArr){
    for(var j=0;j<keyArr.length;j++){
        var disabled = keyArr[j] + "Disabled";
        this.formObj.disabled[disabled] = true;
    }
}
//启用 表单域控件
GmpForm1.prototype.unlock = function(keyArr){
    for(var j=0;j<keyArr.length;j++){
        var disabled = keyArr[j] + "Disabled";
        this.formObj[disabled] = false;
    }
}
//清空表单域数据
GmpForm1.prototype.clear = function(){
    var formObj = this.formObj;
    for(var key in formObj){
        if(key !="disabled"){
            formObj[key]= '';
        }
    }
}
//提交表单
GmpForm1.prototype.submit = function(json,callback){
    var that = this;
    if(json["formUrl"] == 'undefined'){
        if(that.formUrl ==''){
            alert("未定义提交接口");
            return
        }
        json["formUrl"] = that.formUrl;
    }
    if(json["data"] == 'undefined'){
        var datas = that.getData();
        json["data"] = datas.data;
    }
    if(json["isValidate"]){
        //验证方法
    }
    $.ajax({
        url:json["formUrl"],
        type:"post",
        data:json["data"],
        success:function(res){
            if(callback){
                callback(res);
            }
        }
    })
}


//动态表格对象
function GmpTableBlock(compId,blockId,formBlockItems,vueEl,postUrl,postParam,formUrl,jsonFunction){
    this.compId = compId;//父组件名字
    this.blockId = blockId; //功能块标识
    this.formBlockItems = formBlockItems;//表格块key值集合

    this.vueEl = vueEl;     //vue el
    this.vueObj = null;    //vue对象实例
    this.tableObjArr = [];//表格数组对象数据

    this.postUrl = postUrl //获取后端数据接口
    this.postParam = postParam //请求参数参数json
    this.formUrl = formUrl //提交接口

    this.onClickRow = jsonFunction.onClickRow;//单击行事件
    this.onEditRow = jsonFunction.onEditRow;//编辑行事件
    this.onDeleteRow = jsonFunction.onDeleteRow;//删除行事件
}
//父组件数据
GmpTableBlock.prototype.searchSelect = function(){
    var that = this;
    var compId = this.compId;
    //单击行事件
    this.tableObjArr["clickRow"] = function(row){
        that.onClickRow(row);
    }
    //编辑事件
    this.tableObjArr["editRow"] = function(){
        that.onEditRow();
    }
    //删除事件
    this.tableObjArr["deleteRow"] = function(){
        that.onDeleteRow();
    }
    var obj = {
        props:[],
    }
    obj[compId] = this.tableObjArr;
    return obj;
}
//构建表格组件
GmpTableBlock.prototype.bulidComponent = function(){
    var strHtml = DynamicStitchings.Concatenation(this.formBlockItems);
    var that = this;
    var id = that.vueEl;
    var vue = new Vue({
        el: id,
        data:that.searchSelect(),//获取父组件数据
        computed:{//表单组件定义
            gmpTable(){//表格组件
                var template= strHtml.html;
                var props = ["tableData"];//子组件参数名
                return {
                    template,
                    props,
                }
            }
        },
        methods: {

        }
    });
    this.vueObj = vue;
}
//表格点击
GmpTableBlock.prototype.clickTable = function(){
    console.log(this.tableObjArr.click());
}
//表格数据加载事件
GmpTableBlock.prototype.reload = function(){
    var arr = this.formBlockItems;
    var arrObj ={};
    //循环赋值
    for(var j=0;j<arr.length;j++){
        arrObj[arr[j]["ename"]] = '1';
    }
    this.tableObjArr.push(arrObj);
    var parentComponentName = this.compId;
    this.vueObj[parentComponentName] = this.tableObjArr;
    console.log(this.tableObjArr);
}
//表格由给定数据加载
GmpTableBlock.prototype.loadRecord = function(data){
    for(var j=0;j<data.length;j++){
        this.tableObjArr.push(data[j]);
    }
    var parentComponentName = this.compId;
    this.vueObj[parentComponentName] = this.tableObjArr;
    console.log(this.tableObjArr);
};