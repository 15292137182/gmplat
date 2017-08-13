/**
 * Created by admin on 2017/8/11.
 */
var pagingObj = (function(){
    var Example = function(){
        var author = {
            data:function(){
                return{

                }
            },
            props : ['url','pageSize','pageNum','args'],
            methods:{
                pagingObjAjax(callback){
                    this.$http.jsonp(this.url,{
                        args:this.args,
                        pageSize:this.pageSize,
                        pageNum:this.pageNum
                    },{
                        jsonp:'callback'
                    }).then(function(res){
                        //console.log(res.data.data);
                        if(callback){
                            callback(res);
                        }
                    },function(){
                        alert("发生错误！");
                    })
                }
            }
        };
        return author
    }
    return {
        Example:Example
    }
})()
