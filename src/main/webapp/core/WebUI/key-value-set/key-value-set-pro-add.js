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
                { max:64, message: '长度最大为64字节'}
            ],
            confValueInput: [
                {required: true, message: '请输入值', trigger: 'blur'},
                { max:64, message: '长度最大为64字节'}
            ],
            despInput:[
                { max:512, message: '长度最大为512字节'}
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
                "obj":keyValueSetProAdd,
                "showMsg":true,
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
                    relateKeysetRowId:leftKeyValueSet.rowId,
                    desp:this.keyForm.despInput
                },
                "obj":keyValueSetProAdd,
                "showMsg":true,
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
                    }else{
                        $('.el-form-item__error').css("display", "block");
                        keyValueSetProAdd.judgeMent();
                        return false;
                    }
                }else{//新增
                    if(valid){
                        addObj.addOk(function(){
                            keyValueSetProAdd.addKeyValue();
                        })
                    }else{
                        $('.el-form-item__error').css("display", "block");
                        keyValueSetProAdd.judgeMent();
                        return false;
                    }
                }
            })
        },
        cancel() {
            ibcpLayer.Close(topButtonObj.divIndex);
            ibcpLayer.Close(rightKeyValueSet.editProDivIndex);
        },
        judgeMent(){
            setTimeout(function(){
                $('.el-form-item__error').css("display", "none");
            },1500)
        },
    }
})
