/**
 * Created by admin on 2017/10/19.
 */
var searchMore=serverPath + "/user/queryPage";
var allDate=new Vue({
    el:'#allDate',
    data:getData.dataObj({

    }),
    methods:{
        headSort(column){
            //列头排序
             pagingObj.headSort(qurUrl,this.resInput,this.pageSize,this.pageNum,column,this);

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
            this.pageSize=val;
        },
        handleCurrentChange(val){
            this.pageNum=val;
        },
        conformEvent(){
            var data={
                "url":serverPath + "/userGroup/addUserGroupUser",
                "jsonData":{
                    userRowIds:allDate.userId,
                    userGroupRowId:left.rowId,
                },
                "obj":allDate,
                "showMsg":true
            };
            gmpAjax.showAjax(data,function(res){
                console.log(res);
                //分页跳回到第一页
                //basRight.searchRight();
                ibcpLayer.Close(divIndex);
            })
        },
        cancel(){
            ibcpLayer.Close(divIndex);
        }
    },
    mounted(){
        querySearch.noParams(searchMore,this,function(res){
            //var data=res.resp.content.data;
            //if(data!=null){
                //默认选中行
                //this.currentChange(this.tableData[0]);
            //}
        })
    }
})