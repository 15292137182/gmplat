var pagingObj = (function(){
    var Example = function(url,args,pageSize,pageNum,callback){
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
                console.log(res);
                if(callback){
                    callback(res);
                }
            },
            error:function(){
                alert("错误")
            }
        })
    }
    return {
        Example:Example
    }
})()
