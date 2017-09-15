/**
 * Created by jms on 2017/8/31.
 */
//定义各方法接口
var addProUrl=serverPath+'/keySetPro/add';
var modifyProUrl=serverPath+'/keySetPro/modify';

var keyValueSetProAdd = new Vue({
    el: '#keyValueSetProAdd',
    data: {
        labelPosition: 'right',
        rowObj:'',
        keyForm:{
            confKeyInput: '',
            confValueInput: '',
            despInput: '',
        },
        rules: {
            confKeyInput: [
                {required: true, message: '请输入键', trigger: 'blur'},
            ],
            confValueInput: [
                {required: true, message: '请输入值', trigger: 'blur'}
            ],
        }
    },
    methods: {
        //新增
        addKeyValue(){
            var data={
                "url":addProUrl,
                "jsonData":{
                    relateKeysetRowId:leftKeyValueSet.rowId,
                    confKey:this.keyForm.confKeyInput,
                    confValue:this.keyForm.confValueInput,
                    desp:this.keyForm.despInput
                },
                "obj":keyValueSetProAdd
            }
            gmpAjax.showAjax(data,function(res){
                ibcpLayer.Close(topButtonObj.divIndex);
                queryData.getDatas(queryProUrl,rightKeyValueSet.input,leftKeyValueSet.rowId,rightKeyValueSet,function(res){});
            })
        },

        //编辑
        editKeyValue(rowId){
            var data={
                "url":modifyProUrl,
                "jsonData":{
                    rowId:rowId,
                    confKey:this.keyForm.confKeyInput,
                    confValue:this.keyForm.confValueInput,
                    desp:this.keyForm.despInput
                },
                "obj":keyValueSetProAdd
            }
            gmpAjax.showAjax(data,function(res){
                ibcpLayer.Close(rightKeyValueSet.editProDivIndex);
                queryData.getDatas(queryProUrl,rightKeyValueSet.input,leftKeyValueSet.rowId,rightKeyValueSet,function(res){});
            })
        },

        confirm(formName) {
            this.$refs[formName].validate(function (valid) {
                if(topButtonObj.isEdit == true){//编辑
                    if(valid){
                        editObj.editOk(function(){
                            keyValueSetProAdd.editKeyValue(rightKeyValueSet.rowId);
                        })
                    }
                }else{//新增
                    if(valid){
                        addObj.addOk(function(){
                            keyValueSetProAdd.addKeyValue();
                        })
                    }
                }
            })
        },
        cancel() {
            ibcpLayer.Close(topButtonObj.divIndex);
            ibcpLayer.Close(rightKeyValueSet.editProDivIndex);
        }
    }
})
