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
            this.divIndex =  ibcpLayer.ShowDiv('add-inlayer-table.html','表字段','400px', '400px',function(){
                myInlayerButton.isEdit = false;
            })
        },
    }
})

var DatabaseDetails = new Vue({
    el:'#right',
    data:getData.dataObj({
            Robj:'',
            rowObj:'',
            url:serverPath+'/dbTableColumn/queryTabById',
            selUrl:serverPath+'/dbTableColumn/queryPageById',//分页查询
            delUrl:serverPath+'/dbTableColumn/delete',//删除
             }),
    methods:{
        selDatas(){
            pagingObj.Examples(DatabaseDetails.selUrl,DatabaseDetails.Robj.rowId,DatabaseDetails.input,DatabaseDetails.pageSize,DatabaseDetails.pageNum,DatabaseDetails)
        },
        selDataOne(){
            queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.input,DatabaseDetails.Robj.rowId,DatabaseDetails)
        },
        clickTable(row){
            this.rowObj = row;
        },
        editInlayerTableBase(){
            myInlayerButton.divIndex =  ibcpLayer.ShowDiv('add-inlayer-table.html','表字段','400px', '380px',function(){
                myInlayerButton.isEdit = true;
                if(DatabaseDetails.rowObj.isPk == "true"){
                    addInlayerData.Inlayer.primaryKey = true;
                }else{
                    addInlayerData.Inlayer.primaryKey = false;
                }
                addInlayerData.Inlayer.Cname = DatabaseDetails.rowObj.columnCname;
                addInlayerData.Inlayer.Ename = DatabaseDetails.rowObj.columnEname;
                addInlayerData.Inlayer.desp = DatabaseDetails.rowObj.desp;
            })
        },
        delInlayerTableBase(){
            deleteObj.del(function(){
                var data = {
                    "url":DatabaseDetails.delUrl,
                    "jsonData":{rowId:DatabaseDetails.rowObj.rowId},
                    "obj":DatabaseDetails,
                    "showMsg":true
                }
                gmpAjax.showAjax(data,function(res){
                    queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.input,DatabaseDetails.Robj.rowId,DatabaseDetails);
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