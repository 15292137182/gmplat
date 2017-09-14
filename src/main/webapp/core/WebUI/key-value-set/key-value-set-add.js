/**
 * Created by jms on 2017/8/7.
 */
//定义各方法接口
var addUrl=serverPath+'/keySet/add';
var modifyUrl=serverPath+'/keySet/modify';

var keyValueSetAdd = new Vue({
    el: '#keyValueSetAdd',
    data: {
        labelPosition: 'right',
        rowObj:'',
        keyForm:{
            keysetCodeInput: '',
            keysetNameInput: '',
            belongModuleInput:'',
         //   belongSystemInput:'',
            despInput: '',
            versionInput: '',
        },
        rules: {
            numberInput: [
                {required: true, message: '请输入编号', trigger: 'blur'},
            ],
            keysetNameInput: [
                {required: true, message: '请输入名称', trigger: 'blur'}
            ],
        }
    },
    methods: {
        //新增
        addKeySet(){
            var data={
                "url":addUrl,
                "jsonData":{
                    keysetCode:this.keyForm.keysetCodeInput,
                    keysetName:this.keyForm.keysetNameInput,

                    belongModule:this.keyForm.belongModuleInput,
           //         belongSystem:this.keyForm.belongSystemInput,
                    desp:this.keyForm.despInput
                },
                "obj":keyValueSetAdd
            }
            gmpAjax.showAjax(data,function(res){
                queryData.getData(url,leftKeyValueSet.input,leftKeyValueSet,function(res){
                    leftKeyValueSet.leftCurrentChange(leftKeyValueSet.tableData[0])});
                ibcpLayer.Close(topButtonObj.divIndex);
            })
        },

        //编辑
        editKeySet(rowId){
            var data={
                "url":modifyUrl,
                "jsonData":{
                    rowId:rowId,
                    keysetCode:this.keyForm.keysetCodeInput,
                    keysetName:this.keyForm.keysetNameInput,
                    belongModule:this.keyForm.belongModuleInput,
               //     belongSystem:this.keyForm.belongSystemInput,
                    desp:this.keyForm.despInput,
                    version:"1.0"
                },
                "obj":keyValueSetAdd
            }
            gmpAjax.showAjax(data,function(res){
                queryData.getData(url,leftKeyValueSet.input,leftKeyValueSet);
                ibcpLayer.Close(leftKeyValueSet.editdivIndex);
            })
        },

        confirm(formName) {
            this.$refs[formName].validate(function (valid) {
                if(topButtonObj.isEdit == true){//编辑
                    if(valid){
                        editObj.editOk(function(){
                            keyValueSetAdd.editKeySet(leftKeyValueSet.rowId);
                        })
                    }
                }else{//新增
                    if(valid){
                        addObj.addOk(function(){
                            keyValueSetAdd.addKeySet();
                        })
                    }
                }
            })
        },
        cancel() {
            ibcpLayer.Close(topButtonObj.divIndex);
            ibcpLayer.Close(leftKeyValueSet.editdivIndex);
        }
    }
})
