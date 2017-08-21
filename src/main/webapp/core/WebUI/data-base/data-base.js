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
        rightData:[],
        //rightHeight:'',
        Robj:'',
        url:serverPath+'/dbTableColumn/queryTabById',
        delUrl:serverPath+'/dbTableColumn/delete',//删除
        rowObj:'',
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
                    this.rightData=res.data.data;
                }
            })
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
                    DatabaseDetails.FindData(DatabaseDetails.Robj.rowId);
                },function(){
                    showMsg.MsgError(dataBase);
                })
            })
        },
        conformEvent(){
            ibcpLayer.Close(dataBase.divIndex);
        }
    }
});