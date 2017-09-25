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
        rules:{
            name:[{ required: true, message: '请输入表schema', trigger: 'blur' }],
            Ename:[{ required: true, message: '请输入表英文名', trigger: 'blur' }],
            Cname:[{ required: true, message: '请输入表中文名', trigger: 'blur' }]
        },
        url:serverPath+'/maintTable/add',
        editUrl:serverPath+'/maintTable/modify',
        rowObj:''
    },
    methods:{
        //新增
        addTableBase(){
            var data = {"url":tableBase.url,"jsonData":{
                    tableSchema:this.formTable.name,
                    tableEname:this.formTable.Ename,
                    tableCname:this.formTable.Cname,
                    desp:this.formTable.desp
                },
                "obj":tableBase,
                "showMsg":true
            }
            gmpAjax.showAjax(data,function(res){
                //showMsg.MsgOk(dataBase,msg);
                queryData.getData(dataBase.url,dataBase.input,dataBase);
                console.log(topButtonObj.divIndex);
                ibcpLayer.Close(topButtonObj.divIndex);
            })
        },
        //编辑
        editTbleBase(rowId){
            var data = {"url":tableBase.editUrl,"jsonData":{
                    rowId:rowId,
                    tableSchema:this.formTable.name,
                    tableEname:this.formTable.Ename,
                    tableCname:this.formTable.Cname,
                    desp:this.formTable.desp
                },
                "obj":tableBase,
                "showMsg":true
            }
            gmpAjax.showAjax(data,function(res){
                //showMsg.MsgOk(dataBase,msg);
                queryData.getData(dataBase.url,dataBase.input,dataBase);
                ibcpLayer.Close(dataBase.editdivIndex);
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
        conformEvent(formName){//确定按钮
            if(topButtonObj.isEdit == true){//编辑
                // if(this.isNull()){
                //     editObj.editOk(function(){
                //         tableBase.editTbleBase(tableBase.rowObj.rowId);
                //     })
                // }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        editObj.editOk(function(){
                            tableBase.editTbleBase(tableBase.rowObj.rowId);
                        })
                    } else {
                        return false;
                    }
                })
            }else{//新增
                // if(this.isNull()){
                //     addObj.addOk(function(){
                //         tableBase.addTableBase();
                //     })
                // }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        addObj.addOk(function(){
                                    tableBase.addTableBase();
                                })
                    } else {
                        return false;
                    }
                });
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