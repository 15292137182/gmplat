/**
 * Created by admin on 2017/10/19.
 */
var searchMore=serverPath + "/userGroup/queryUserInfo";
var allDate=new Vue({
    el:'#allDate',
    data:getData.dataObj({

    }),
    methods:{
        headSort(column){
            //列头排序
            var headDate={
                pageNum:this.pageNum,
                pageSize:this.pageSize,
                order:'',
            }
            querySearch.headSorts(searchMore,headDate,column,this);

        },
        currentChange(){

        },
        selectRow(selection){
            console.log(selection)
            allDate.userId=[];//多选的用户ID组
            $.each(selection,function(i,item){
                allDate.userId.push(item.rowId)

            })
            console.log( allDate.userId)
        },
        selectAllRow(selection){
            allDate.userId=[];//多选的用户ID组
            $.each(selection,function(i,item){
                allDate.userId.push(item.rowId)
            })
            console.log(allDate.userId)
        },
        handleSizeChange(val){
            //每页几条
            this.pageSize=val;
            //加载跳回第一页
            this.searchMore();
        },
        handleCurrentChange(val){
            this.pageNum=val;
            //加载 不跳回第一页
            this.searchMore();
        },
        conformEvent(){
            addObj.addOk(function() {
                var data = {
                    "url": serverPath + "/userGroup/addUserGroupUser",
                    "jsonData": {
                        userRowIds: allDate.userId,
                        userGroupRowId: left.rowId,
                    },
                    "obj": allDate,
                    "showMsg": true
                };
                gmpAjax.showAjax(data, function (res) {
                    //刷新表
                    rightBottom.searchMoreFirst();
                    ibcpLayer.Close(divIndex);
                })
            })
        },
        cancel(){
            ibcpLayer.Close(divIndex);
        },
        searchMoreFirst(){
            //加载 跳回第一页
            var headDate={
                userGroupRowId: left.rowId,
                pageNum:1,
                pageSize:this.pageSize,
            }
            querySearch.searchResourceFirst(searchMore,headDate,this,function(res){
                console.log(res)
            })
        },
        //不跳回第一页
        searchMore(){
            //加载 跳回第一页
            var headDate={
                userGroupRowId: left.rowId,
                pageNum:this.pageNum,
                pageSize:this.pageSize,
            }
            querySearch.searchResource(searchMore,headDate,this,function(res){})
        }
    },
    created(){
        console.log(111)
       this.searchMoreFirst();
    }
})