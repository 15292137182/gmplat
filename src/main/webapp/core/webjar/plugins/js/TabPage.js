var pagingObj = (function(){
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
                if(res.data!=null){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
                if(res.data!=null){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
                    obj.pageNum = 1;//定位到第一页
                    if(typeof callback =="function"){
                            callback(res);
                        }
                }else{
                    obj.tableData = [];
                }
                // if(typeof callback =="function"){
                //     callback(res);
                // }
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
        var datas = "["+JSON.stringify(data)+"]";
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
                console.log(res.data.result);
                obj.loading=false;
                if(res.data!=null){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
        var datas = "["+JSON.stringify(data)+"]";
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
                if(res.data!=null){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
        var datas = "["+JSON.stringify(data)+"]";
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
                console.log(res.data.result);
                obj.loading=false;
                if(res.data!=null){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
    var MsgOk = function(obj,msg){
        var type = '';
        //obj：需要弹出消息页面的Vue实例对象
        //msg：ajax成功后返回的res
        if(msg.data.state==1){//操作成功
            obj.$message({
                message: msg.data.message,
                type: 'success'
            })
        }else{
            obj.$message.error(msg.data.message);
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

/**
 * 下拉框选择组件
 * @type {{setOpt}}
 */
var SelectOptions = (function(){
    var setOpt = function(templateId,modelName,keysetCode,path){
        var _data={};
        _data[modelName]='';
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
                    var _path = serverPath+"/keySet/query";
                    var param = {"str":keysetCode};
                    if(undefined != path && null != path && "" != path){
                        _path = path;
                        param = {};
                    }
                    this.$http.jsonp(_path, param, {
                        jsonp: 'callback'
                    }).then(function (res) {
                        this.list=res.data.data;
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
    }

    var tableSetOpt=function(serUrl,args){
        var arr=new Array();
        for(var p in args){
            var str=args[p];
            arr.push(str);
        }

        $.ajax({
            url:serUrl,
            type:"get",
            data:{args:arr},
            dataType:"jsonp",
            success:function(res){
                console.log(res.data);
                if(typeof callback == "function"){
                    callback(res);
                }
            },
            error:function(){
                alert("error");
            }
        })
    }
    return {
        setOpt:setOpt,
        tableSetOpt:tableSetOpt
    }
})()



