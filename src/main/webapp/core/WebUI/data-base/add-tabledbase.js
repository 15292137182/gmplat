/**
 * Created by andim on 2017/8/15.
 */
var tableBase = new Vue({
    el:"#addDataSet",
    data:{
        labelPosition:'right',
        formTable:{
            name:'',
            Ename:'',
            Cname:'',
            desp:''
        },
        url:serverPath+'/maintTable/add',
        editUrl:serverPath+'/maintTable/modify',
        rowObj:''
    },
    methods:{
        //新增
        addTableBase(){
            this.$http.jsonp(this.url,{
                tableSchema:this.formTable.name,
                tableEname:this.formTable.Ename,
                tableCname:this.formTable.Cname,
                desp:this.formTable.desp
            },{
                jsonp:'callback'
            }).then(function(res){
                showMsg.MsgOk(dataBase,res);
                dataBase.get();
                ibcpLayer.Close(topButtonObj.divIndex);
            },function(){
                showMsg.MsgError(dataBase);
            })
        },
        //编辑
        editTbleBase(rowId){
            this.$http.jsonp(this.editUrl,{
                rowId:rowId,
                tableSchema:this.formTable.name,
                tableEname:this.formTable.Ename,
                tableCname:this.formTable.Cname,
                desp:this.formTable.desp
            },{
                jsonp:'callback'
            }).then(function(res){
                showMsg.MsgOk(dataBase,res);
                dataBase.get();
                ibcpLayer.Close(dataBase.editdivIndex);
            },function(){
                showMsg.MsgError(dataBase);
            })
        },
        isNull(){
            var data = [
                this.$refs.name,
                this.$refs.Ename,
                this.$refs.Came
                ]
            for(var i=0;i<data.length;i++){
                if(data[i].value==''){
                    ibcpLayer.ShowMsg(data[i].placeholder);
                    return false
                }
            }
            return true;
        },
        conformEvent(){//确定按钮
            if(topButtonObj.isEdit == true){//编辑
                if(this.isNull()){
                    editObj.editOk(function(){
                        tableBase.editTbleBase(tableBase.rowObj.rowId);
                    })
                }
            }else{//新增
                if(this.isNull()){
                    addObj.addOk(function(){
                        tableBase.addTableBase();
                    })
                }
            }
        },
        cancel(){//取消按钮
            if(topButtonObj.isEdit){
                ibcpLayer.Close(dataBase.editdivIndex);
            }
            ibcpLayer.Close(topButtonObj.divIndex);
        }
    }
})