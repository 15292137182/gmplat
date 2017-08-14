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
    var Examples = function(url,rowId,args,pageSize,pageNum,obj,callback){
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
    return {
        Example:Example,
        Examples:Examples
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
