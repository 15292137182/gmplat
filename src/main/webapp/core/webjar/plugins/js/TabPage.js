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
                if(res.data.result.length!=0){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
                if(res.data.result.length!=0){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
                if(res.data.result.length!=0){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
    var headSorts1 = function(url,args,column,obj,callback){
        //有依赖的列头排序
        //url:接口地址，rowId:有依赖表的id，args：table输入框，column：el函数当前列信息，
        // obj:当前vue实例对象（this）,callback:成功后的回调函数
        var data = {};
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
                console.log(res.data.result);
                obj.loading=false;
                if(res.data.result.length!=0){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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
                if(res.data.result.length!=0){
                    obj.tableData = res.data.result;//数据源
                    obj.allDate = Number(res.data.total);//总共多少条数据
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

