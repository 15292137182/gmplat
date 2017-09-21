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
                    }else if(res.resp.content.data==null){
                        obj.tableData=[];

                    }else{
                        dataConversion.conversion(obj,res.resp.content.data.result);
                        obj.tableData = res.resp.content.data.result;//数据源
                        obj.allDate = Number(res.resp.content.data.total);//总共多少条数据
                        obj.pageNum = res.resp.content.data.pageNum;//定位到当前页
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
        if(data !=''){
            if(data.hasOwnProperty("tableKeySet")){
                if(data.tableKeySet.hasOwnProperty(oId)){
                    return data.tableKeySet[oId];   //通过表格ID获取数据
                }
            }
        }
        return [];
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
                if(res.resp.respCode=='000'){
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
                }else{
                    data.obj.$message.error(res.resp.respMsg);
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
    var keyValue = function(code,async,callback){
        var codes = '['+'"'+code+'"'+']';
        $.ajax({
            url:serverPath+'/fronc/queryFuncCode',
            type:"get",
            dataType:"json",
            async: async,   // 同步加载
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
        var model = "searchInput.sel" //绑定v-model数据
        var options = "item in searchInput.options";
        str='<el-col :span='+width+'><el-input placeholder="请输入内容" v-model="'+ model +'"><el-select v-model="searchInput.key" slot="prepend" clearable placeholder="请选择" style="width:100px"><el-option v-for="'+options+'" :key="item.value" :label="item.label" :value="item.value"></el-option></el-select><el-button slot="append" @click="searchInput.clickSearch" icon="search"></el-button></el-input></el-col>'
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
                var optionName = obj.ename + "Option";
                var options = "item in childFormTable." + optionName;
                var keyName = obj.ename + "Key";
                // var key = "childFormTable." + keyName;
                var key = "childFormTable." + obj.ename;
                var str = '<el-row><el-col :span="20"><el-form-item label='+laberName+'><el-select v-model="' + key + '" :disabled="'+disab+'" placeholder="请选择"><el-option v-for="'+options+'" :key="item.value" :label="item.label" :value="item.value" ></el-option></el-select></el-form-item></el-col></el-row>';
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
        var OperationColumn ='<el-table-column fixed="right" label="操作"width="100"><template scope="scope"><el-button type="text" size="small" icon="edit" @click="tableData.editRow(scope.$index,scope.row)"></el-button><el-button type="text" size="small" icon="delete" @click="tableData.deleteRow(scope.$index,scope.row)"></el-button></template></el-table-column>';
        var selection = '<el-table-column data="checkTable" type="selection" width="55"></el-table-column>'
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
                html='<el-table :data="tableData" @row-click="tableData.clickRow" @cell-click="tableData.cellClick" @select="tableData.select" @select-all="tableData.selectAll" @selection-change="tableData.getCheckedRows" border style="width: 100%">'+selection+tableColumn+OperationColumn+'</el-table>';
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
        disabled:{},
        options:[{value:'',label:''}],
        key:'',
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
    if(json.postUrl){
        this.postUrl = json.postUrl; //获取后端数据接口
    }
    if(json.postParam){
        this.postParam = json.postParam; //请求参数参数json
    }
}
//获取表单域数据
GmpForm1.prototype.request = function(callback){
    var that = this;
    var data = this.postParam;
    var url = this.postUrl;
    if(url==undefined){
        alert("未定义接口地址");
        return ;
    }
    $.ajax({
        url:url,
        type:"post",
        data:data,
        dataType:"json",
        success:function(res){
            var arr = res.resp.content.data;
            for(var j=0;j<arr.length;j++){
                var obj = arr[j];
                for(var k in obj){
                    that.formObj[k] = obj[k];
                }
            }
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
function GmpTableBlock(compId,blockId,formBlockItems,vueEl,postUrl,queryParam,submitUrl,jsonFunction){
    this.compId = compId;//父组件名字
    this.blockId = blockId; //功能块标识
    this.formBlockItems = formBlockItems;//表格块key值集合

    this.vueEl = vueEl;     //vue el
    this.vueObj = null;    //vue对象实例
    this.tableObjArr = [];//表格数组对象数据
    this.searchId = [];	//表格关联的查询块
    this.queryUrl = null;//表格查询接口
    this.postUrl = postUrl //获取后端数据接口
    this.queryParam = queryParam //表格查询参数json
    this.submitUrl = submitUrl //表格提交接口
    this.isCheckData = true;  //是否为提交表格选中的数据，默认为true
    this.records = [] 	//表格初始数据
    this.height = ''//表格高度，默认自适应
    this.pagination = true;	//表格是否分页， 默认分页true
    this.columns = []	//表格初始列信息
    this.custom = []	//表格自定义列信息 ignore
    this.singleSelect = false 	//表格单选/多选 默认单选
    this.pageSize = 10//每页显示多少条数据，当表格需要分页时有效
    this.pageNo = 1;//当前第多少页，当表格需要分页时有效
    this.total = 0;//共多少条数据，当表格需要分页时有效

    this.rows = [];//选中行数据

    this.onClickRow = jsonFunction.onClickRow;//单击行事件
    this.onEditRow = jsonFunction.onEditRow;//编辑行事件
    this.onDeleteRow = jsonFunction.onDeleteRow;//删除行事件
    this.onDbClick = jsonFunction.onDbClick;//双击行事件
    this.onCellClick = jsonFunction.onCellClick;//单元格点击事件
    this.onDbCellClick = jsonFunction.onDbCellClick;//单元格双击事件
    this.onCheck = jsonFunction.onCheck;//表格选中事件
    this.onUnCheck = jsonFunction.onUnCheck;//表格取消选中事件
    this.onCheckAll = jsonFunction.onCheckAll;//表格全选事件
    this.onUnCheckAll = jsonFunction.onUnCheckAll;//表格移除全选事件
}
//父组件数据
GmpTableBlock.prototype.searchSelect = function(){
    var that = this;
    var compId = this.compId;
    var clickRowTime = null;
    var cellClickTime = null;
    //单击行事件
    this.tableObjArr["clickRow"] = function(row){
        that.onClickRow(row);
    }
    //双击行事件
    this.tableObjArr["dblclick"] = function(row,event){
        that.onDbClick(row);
    }
    //单元格单击事件
    this.tableObjArr["cellClick"] = function(row, column, cell){
        //that.onCellClick(row,null,null,row[column.property]);
    }
    //单元格双击事件
    this.tableObjArr["DbClickCell"] = function(row, column, cell){
        that.onDbCellClick(row,null,null,row[column.property])
    }
    // //行选中事件
    // this.tableObjArr["select"] = function(selection, row){
    //     that.onCheck(row);
    //     console.log(that.getCheckedRowsCount());
    // }
    // //行全选事件
    // this.tableObjArr["selectAll"] = function(selection){
    //     if(selection.length>0){
    //         // console.log("全选")
    //         // that.onCheckAll(selection);
    //     }else{
    //         // console.log("取消全选")
    //         // that.onUnCheckAll(selection);
    //     }
    // }
    //获取选中记录
    this.tableObjArr["getCheckedRows"] = function(val){
        that.rows = val;
    }
    this.tableObjArr["editRow"] = function(index,row){
        that.onEditRow(index,row);
    }
    //删除按钮事件
    this.tableObjArr["deleteRow"] = function(index,row){
        that.onDeleteRow(index,row);
    }
    var obj = {
        props:[],
        tableId:"dataSetConfig"
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
//表格数据直接加载方法
GmpTableBlock.prototype.reload = function(json){
    if(json.queryUrl){
        this.queryUrl = json.queryUrl;
    }
    if(json.queryParam){
        this.queryParam = json.queryParam;
    }
    var that = this;
    if(this.queryUrl == null){
        alert("未定义表格查询接口");
        return ;
    }
    $.ajax({
        url:that.queryUrl,
        type:"get",
        dataType:"json",
        data:that.queryParam,
        success:function(res){
            that.loadRecord(res.resp.content.data.result);
        },
        error:function(){
            alert("表格查询数据失败！")
        }
    })
}
//表格由给定数据加载
GmpTableBlock.prototype.loadRecord = function(data){
    var that = this;
    this.tableObjArr = [];
    this.searchSelect();
    dataConversion.conversion(that.vueObj,data);
    for(var j=0;j<data.length;j++){
        this.tableObjArr.push(data[j]);
    }
    var parentComponentName = this.compId;
    this.vueObj[parentComponentName] = this.tableObjArr;
};
//获取选中行数据
GmpTableBlock.prototype.getCheckedRows = function(){
    return this.rows;
}
//获取选中记录总数
GmpTableBlock.prototype.getCheckedRowsCount = function(){
    return this.rows.length;
}
//设置表格参数
GmpTableBlock.prototype.setOptions = function(json){
    if(json.searchId){
        this.searchId =json.searchId;	//表格关联的查询块
    }
    if(json.queryUrl){
        this.queryUrl = json.queryUrl//表格查询接口
    }
    if(json.queryParam){
        this.queryParam = json.queryParam//表格查询参数json
    }
    if(json.submitUrl){
        this.submitUrl = json.submitUrl//表格提交接口
    }
    if(json.isCheckData){
        this.isCheckData = json.isCheckData;  //是否为提交表格选中的数据，默认为true
    }
    if(json.records){
        this.records = json.records	//表格初始数据
    }
    if(json.height){
        this.height = json.height//表格高度，默认自适应
    }
    if(json.pagination){
        this.pagination = json.pagination;	//表格是否分页， 默认分页true
    }
    if(json.columns){
        this.columns = json.columns//表格初始列信息
    }
    if(json.custom){
        this.custom = json.custom//表格自定义列信息 ignore
    }
    if(json.singleSelect){
        this.singleSelect = json.singleSelect	//表格单选/多选 默认单选
    }
    if(json.pageSize){
        this.pageSize = json.pageSize//每页显示多少条数据，当表格需要分页时有效
    }
    if(json.pageNo){
        this.pageNo = json.pageNo;//当前第多少页，当表格需要分页时有效
    }
    if(json.total){
        this.total = json.total;//共多少条数据，当表格需要分页时有效
    }
};
//获取表格参数
GmpTableBlock.prototype.getOptions = function(){
    var json = {};
    json.compId = this.compId;//父组件名字
    json.blockId = this.blockId; //功能块标识
    json.formBlockItems = this.formBlockItems;//表格块key值集合

    json.vueEl = this.vueEl;     //vue el
    json.vueObj = this.vueObj;    //vue对象实例
    json.tableObjArr = this.tableObjArr;//表格数组对象数据
    json.searchId = this.searchId;	//表格关联的查询块
    json.queryUrl = this.queryUrl//表格查询接口
    json.postUrl = this.postUrl //获取后端数据接口
    json.queryParam = this.queryParam//表格查询参数json
    json.submitUrl = this.submitUrl//表格提交接口
    json.isCheckData = this.isCheckData;  //是否为提交表格选中的数据，默认为true
    json.records = this.records	//表格初始数据
    json.height = this.height//表格高度，默认自适应
    json.pagination = this.pagination;	//表格是否分页， 默认分页true
    json.columns = this.columns//表格初始列信息
    json.custom = this.custom//表格自定义列信息 ignore
    json.singleSelect = this.singleSelect	//表格单选/多选 默认单选
    json.pageSize = this.pageSize//每页显示多少条数据，当表格需要分页时有效
    json.pageNo = this.pageNo;//当前第多少页，当表格需要分页时有效
    json.total = this.total;//共多少条数据，当表格需要分页时有效

    json.rows = this.rows;//选中行数据
    return json;
};
//提交表格数据
GmpTableBlock.prototype.submit = function(json){
    var that = this;
    $.ajax({
        url:that.submitUrl,
        type:"post",
        dataType:'json',
        data:json.data,
        success:function(res){
            json.callback(res);
        }
    })
}
//获取表格所有数据
GmpTableBlock.prototype.getAllData = function(){
    var arr = [];
    for(var j=0;j<this.tableObjArr.length;j++){
        if(typeof this.tableObjArr[j] =='object'){
            arr.push(this.tableObjArr[j]);
        }
    }
    return arr;
}
//获取表格所有数据总数
GmpTableBlock.prototype.getAllDataCount = function(){
    var arr = [];
    for(var j=0;j<this.tableObjArr.length;j++){
        if(typeof this.tableObjArr[j] =='object'){
            arr.push(this.tableObjArr[j]);
        }
    }
    return arr.length;
}
//添加表格数据(集合数组)
GmpTableBlock.prototype.addRows =function(rowArr){
    for(var j=0;j<rowArr.length;j++){
        this.tableObjArr.push(rowArr[j]);
    }
}
//添加表格数据,指定位置添加一条
GmpTableBlock.prototype.addRow =function(json,index){
    this.tableObjArr.splice(index,0,json);
}
//删除表格指定索引数据
GmpTableBlock.prototype.removeRowByIndex = function(index){
    this.tableObjArr.splice(index,1);
}
//删除指定列的指定值数据
GmpTableBlock.prototype.removeRow = function(column,value){
    var arr = [];
    for(var j=0;j<this.tableObjArr.length;j++){
        if(typeof this.tableObjArr[j] =="object"){
            arr.push(this.tableObjArr[j]);
        }
    }
    for(var j=0;j<arr.length;j++){
        if(arr[j][column] ==value[j]){
            var index = arr[j];
            this.tableObjArr.splice(index,1);
        }
    }
}
//清除表格数据
GmpTableBlock.prototype.clear = function(){
    this.tableObjArr = [];//清空缓存数据
    var parentComponentName = this.compId;
    this.vueObj[parentComponentName] = [];
}
//获取分页信息
GmpTableBlock.prototype.getPageInfo = function(){
    var json = {};
    json.pageSize = this.pageSize;
    json.pageNo = this.pageNo;
    json.total = this.total;
    return json
}
//设置分页信息
GmpTableBlock.prototype.setPageInfo = function(json){
    this.pageSize = json.pageSize;
    this.pageNo = json.pageNo;
    this.total = json.total;
}

//动态查询块对象
function GmpSearchBlock(compId,blockId,searchBlockItems,vueEl,grids,custom,filterData,selUrl,postParam,formUrl){
    this.compId = compId;//父组件名字
    this.blockId = blockId; //功能块标识
    this.searchBlockItems = searchBlockItems;//查询块key值集合
    this.vueEl = vueEl;     //vue el
    this.vueObj = null;    //vue对象实例
    this.searchObj = {        //表单数据Key : value
        sel:'',
        options:[],
        key:''
    };
    this.filter = searchBlockItems;//配置的过滤字段
    this.custom = custom	//自定义的过滤字段
    this.filterData = filterData	//过滤的数据
    this.grids = grids 	//查询返回的响应数据加载的GmpGrid.id
    this.selUrl = selUrl //获取后端数据接口
    this.postParam = postParam //请求参数参数json
    this.formUrl = formUrl //提交接口
}
//父组件数据
GmpSearchBlock.prototype.searchSelect = function(){
    var that = this;
    var compId = this.compId;
    var arr = this.searchBlockItems;
    for(var j=0;j<arr.length;j++){
        var objs =arr[j].ename;
        var label = arr[j].displayTitle;
        var arrObj = {value:objs,label:label};
        this.searchObj.options.push(arrObj);
    }
    this.searchObj["clickSearch"] = function(){
        that.search();
    }
    var obj = {
        props:[]
    }
    obj[compId] = this.searchObj;
    return obj;
}
//构建查询块组件
GmpSearchBlock.prototype.bulidComponent = function(){
    var strHtml = DynamicStitchings.Concatenation(this.searchBlockItems);
    var that = this;
    var id = that.vueEl;
    var vue = new Vue({
        el: id,
        data:that.searchSelect(),//获取父组件数据
        computed:{//表单组件定义
            searchBlock(){//查询块输入框组件
                var template= strHtml.html;
                var props = ["searchInput"];//子组件参数名
                return {
                    template,
                    props
                }
            }
        },
        methods: {

        }
    });
    this.vueObj = vue;
}
//获取配置项信息
GmpSearchBlock.prototype.getOptions = function(){
    var json = {};
    json.compId = this.compId//父组件名字
    json.blockId = this.blockId; //功能块标识
    json.searchBlockItems = this.searchBlockItems;//查询块key值集合
    json.vueEl = this.vueEl;     //vue el
    json.vueObj = this.vueObj;    //vue对象实例
    json.searchObj = this.searchObj;       //表单数据Key : value;
    json.filter = this.filter;//配置的过滤字段
    json.custom = this.custom//自定义的过滤字段
    json.filterData = this.filterData	//过滤的数据
    json.grids = this.grids	//查询返回的响应数据加载的GmpGrid.id
    json.selUrl = this.selUrl//获取后端数据接口
    json.postParam = this.postParam //请求参数参数json
    json.formUrl = this.formUrl//提交接口
    return json;
}
//设置配置项信息
GmpSearchBlock.prototype.getOptions = function(json){
    if(json.filter){
        this.filter = json.filter;//配置的过滤字段
    }
    if(json.custom){
        this.custom = json.custom//自定义的过滤字段
    }
    if(json.filterData){
        this.filterData = json.filterData	//过滤的数据
    }
    if(json.grids){
        this.grids = json.grids	//查询返回的响应数据加载的GmpGrid.id
    }
    if(json.selUrl){
        this.selUrl = json.selUrl //获取后端数据接口
    }
    if(json.postParam){
        this.postParam = json.postParam  //请求参数参数json
    }
    if(json.formUrl){
        this.formUrl = json.formUrl//提交接口
    }
}
//获取过滤数据
GmpSearchBlock.prototype.getFilterData = function(){
    return this.filterData	//过滤的数据
}
//设置过滤数据
GmpSearchBlock.prototype.setFilterData = function(data){
    this.filterData = data;
}
//执行搜索操作
GmpSearchBlock.prototype.search = function(json,callback){
    var that = this;
    var url = that.selUrl;
    var dataJson = null;
    var pageConfig = {};
    if(that.grids != undefined){
        pageConfig = that.grids[0].getPageInfo();
    }else{
        pageConfig.pageSize = 10;
        pageConfig.pageNo = 1;
    }
    var search = that.searchObj.sel;
    if(that.searchObj.key == ""){
        dataJson = {
            parameter:{},
            search:search,
            pageSize:pageConfig.pageSize,
            pageNum:pageConfig.pageNo
        }
    }else{
        var parameter = {};
        parameter[that.searchObj.key] = that.searchObj.sel;
        dataJson = {
            parameter:parameter,
            search:search,
            pageSize:pageConfig.pageSize,
            pageNum:pageConfig.pageNo
        }
    }
    if(json){
        if(json.url){
            url = json.url;
            dataJson = json.data;
        }
    }
    $.ajax({
        url:url,
        type:"get",
        dataType:"json",
        data:dataJson,
        success:function(res){
            console.log(res);
            var dataName = that.grids[0];
            var load = "GmpTable."+dataName+".reload(res.resp.content.data.result)";
            eval(load);
            if(callback){
                callback(res);
            }
        },
        error:function(){
            alert("查询块请求失败！");
        }
    })
}

// var deferred = (function(){
//     var done = function(json){
//         $.when(json.fun)
//             .done(function(){
//                 if(typeof json.callback == "function"){
//                     json.callback();
//                     // gmp_onload();
//                 }
//             })
//             .fail(function(){
//                 alert("页面加载失败");
//             });
//     }
//     return {
//         done:done
//     }
// })()

