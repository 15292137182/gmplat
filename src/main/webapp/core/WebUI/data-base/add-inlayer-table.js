/**
 * Created by andim on 2017/8/21.
 */
var addInlayerData = new Vue({
    el:"#addInlayerData",
    data:{
        labelPosition:'right',
        Inlayer:{
            Cname:'',
            Ename:'',
            desp:''
        },
        addUrl:serverPath+'/dbTableColumn/add',//新增表字段
        editUrl:serverPath+'/dbTableColumn/modify'//编辑表字段
    },
    methods:{
        addTable(){//新增
            var data = {
                "url":addInlayerData.addUrl,
                "jsonData":{
                    relateTableRowId:DatabaseDetails.Robj.rowId,
                    columnCname:addInlayerData.Inlayer.Cname,
                    columnEname:addInlayerData.Inlayer.Ename,
                    desp:addInlayerData.Inlayer.desp,
                },
                "obj":addInlayerData
            }
            gmpAjax.showAjax(data,function(res){
                queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.input,DatabaseDetails.Robj.rowId,DatabaseDetails);
                addInlayerData.cancel();
            })
        },
        editTable(){//编辑
            var data = {
                "url":addInlayerData.editUrl,
                "jsonData":{
                    relateTableRowId:DatabaseDetails.Robj.rowId,
                    rowId:DatabaseDetails.rowObj.rowId,
                    columnCname:this.Inlayer.Cname,
                    columnEname:this.Inlayer.Ename,
                    desp:this.Inlayer.desp,
                },
                "obj":addInlayerData
            }
            gmpAjax.showAjax(data,function(res){
                queryData.getDatas(DatabaseDetails.selUrl,DatabaseDetails.input,DatabaseDetails.Robj.rowId,DatabaseDetails);
                addInlayerData.cancel();
            })
        },
        isNull(){//非空验证
            var data = [
                this.$refs.inlayerCname,
                this.$refs.inlayerEname
            ]
            console.log(data);
            for(var i=0;i<data.length;i++){
                if(data[i].value==''){
                    ibcpLayer.ShowMsg(data[i].placeholder);
                    return false
                }
            }
            return true;
        },
        conformEvent(){//确定按钮
            if(myInlayerButton.isEdit == true){//编辑
                if(this.isNull()){
                    editObj.editOk(function(){
                        addInlayerData.editTable();
                    })
                }
            }else{//新增
                if(this.isNull()){
                    addObj.addOk(function(){
                        addInlayerData.addTable();
                    })
                }
            }
        },
        cancel(){//取消按钮
            ibcpLayer.Close(myInlayerButton.divIndex);
        }
    }
})