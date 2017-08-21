/**
 * Created by andim on 2017/8/11.
 */

var myInlayerButton = new Vue({
    el:'#myInlayerButton',
    data:{
        Inlayer:{
            Cname:'',
            Ename:'',
            desp:''
        },
        isEdit:'',
        divIndex:''
    },
    methods:{
        addInlayerTableBase(){
            this.divIndex =  ibcpLayer.ShowDiv('add-inlayer-table.html','表字段','400px', '320px',function(){

            })
        },
    }
})

var DatabaseDetails = new Vue({
    el:'#right',
    data:{
        tableData:[],
        //rightHeight:'',
        Robj:'',
        url:serverPath+'/dbTableColumn/queryTabById',
        selUrl:serverPath+'/dbTableColumn/queryPageById',//分页查询
        delUrl:serverPath+'/dbTableColumn/delete',//删除
        rowObj:'',
        input:'',
        pageSize:10,//每页显示多少条
        pageNum:1,//第几页
        allDate:0//共多少条
    },
    methods:{
        FindData(id){
            this.$http.jsonp(DatabaseDetails.url,{
                "args":'',
                "rowId":id
            },{
                jsonp:'callback'
            }).then(function (res) {
                if(res.data.data!=null){
                    this.tableData=res.data.data;
                }
            })
        },
        selDatas(){
            pagingObj.Examples(DatabaseDetails.selUrl,DatabaseDetails.Robj.rowId,DatabaseDetails.input,DatabaseDetails.pageSize,DatabaseDetails.pageNum,DatabaseDetails)
        },
        clickTable(row){
            this.rowObj = row;
        },
        editInlayerTableBase(){
            myInlayerButton.divIndex =  ibcpLayer.ShowDiv('add-inlayer-table.html','表字段','400px', '320px',function(){
                myInlayerButton.isEdit = true;
                addInlayerData.Inlayer.Cname = DatabaseDetails.rowObj.columnCname;
                addInlayerData.Inlayer.Ename = DatabaseDetails.rowObj.columnEname;
                addInlayerData.Inlayer.desp = DatabaseDetails.rowObj.desp;
            })
        },
        delInlayerTableBase(){
            deleteObj.del(function(){
                DatabaseDetails.$http.jsonp(DatabaseDetails.delUrl,{
                    rowId:DatabaseDetails.rowObj.rowId
                },{
                    jsonp:'callback'
                }).then(function(res){
                    showMsg.MsgOk(dataBase,res);
                    //DatabaseDetails.FindData(DatabaseDetails.Robj.rowId);
                    queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.Robj.rowId,DatabaseDetails)
                },function(){
                    showMsg.MsgError(dataBase);
                })
            })
        },
        handleSizeChange(val){//每页显示多少条
            this.pageSize=val;
            DatabaseDetails.selDatas();
        },
        handleCurrentChange(val){//点击第几页
            this.pageNum=val;
            DatabaseDetails.selDatas();
        },
        conformEvent(){
            ibcpLayer.Close(dataBase.divIndex);
        }
    }
});