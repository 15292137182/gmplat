/**
 * Created by jms on 2017/8/7.
 */

var keyValueSetAdd = new Vue({
    el: '#keyValueSetAdd',
    data: {
        labelPosition: 'right',
        addUrl:serverPath+'/keySet/add',
        modifyUrl:serverPath+'/keySet/modify',
        rowObj:'',
        keyForm:{
            keysetCodeInput: '',
            numberInput:'',
            keysetNameInput: '',
            confKeyInput: '',
            confValueInput: '',
            despInput: '',
            versionInput: '',
            disabled:true
        },
        rules: {
            numberInput: [
                {required: true, message: '请输入编号', trigger: 'blur'},
            ],
            keysetNameInput: [
                {required: true, message: '请输入名称', trigger: 'blur'}
            ],
            confKeyInput: [
                {required: true, message: '请输入键', trigger: 'blur'}
            ],
            confValueInput: [
                {required: true, message: '请输入值', trigger: 'blur'}
            ],
            //despInput: [
            //     {required: true, message: '请输入说明', trigger: 'blur'}
            // ]
        }
    },
    methods: {
        //新增
        addKeySet(){
            gmpAjax.showAjax(keyValueSetAdd.addUrl,{
                number:this.keyForm.numberInput,
                keysetName: this.keyForm.keysetNameInput,
                confKey: this.keyForm.confKeyInput,
                confValue: this.keyForm.confValueInput,
                desp: this.keyForm.despInput,
            },function(res){
                showMsg.MsgOk(keyValueSet,res);
                queryData.getData(keyValueSet.url,keyValueSet.input,keyValueSet);
                ibcpLayer.Close(topButtonObj.divIndex);
            })
        },

        //编辑
        editKeySet(rowId){
            gmpAjax.showAjax(keyValueSetAdd.modifyUrl,{
                rowId:rowId,
                number:this.keyForm.numberInput,
                keysetName: this.keyForm.keysetNameInput,
                confKey: this.keyForm.confKeyInput,
                confValue: this.keyForm.confValueInput,
                desp: this.keyForm.despInput,
            },function(res){
                showMsg.MsgOk(keyValueSet,res);
                queryData.getData(keyValueSet.url,keyValueSet.input,keyValueSet);
                ibcpLayer.Close(keyValueSet.editdivIndex);
            })
        },

        confirm(formName) {
            this.$refs[formName].validate(function (valid) {
                if(topButtonObj.isEdit == true){//编辑
                    if(valid){
                        editObj.editOk(function(){
                            keyValueSetAdd.editKeySet(keyValueSetAdd.rowObj.rowId);
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
            ibcpLayer.Close(divIndex);
        }
    }
})
