/**
 * Created by jms on 2017/8/7.
 */
//定义各方法接口
var addUrl=serverPath+'/keySet/add';
var modifyUrl=serverPath+'/keySet/modify';
var checkCodeUrl=serverPath+'/keySet/checkCode';


var keyValueSetAdd = new Vue({
    el: '#keyValueSetAdd',
    data: {
        labelPosition: 'right',
        rowObj:'',
        keyForm:{
            keysetCodeInput: '',
            keysetNameInput: '',
          //  belongModuleInput:'',
         //   belongSystemInput:'',
            despInput: '',
            versionInput: '',
        },
        rules: {
            keysetCodeInput: [
                {required: true, message: '请输入编号'},
                { max:128, message: '长度最大为128字节', trigger: 'blur' }
            ],
            keysetNameInput: [
                {required: true, message: '请输入名称'},
                { max:128, message: '长度最大为128字节', trigger: 'blur' }
            ],
            despInput:[
                { max:512, message: '长度最大为512字节'}
            ]
        },
        //所属模块下拉框
        belongModule_1:{
            params:'belongModule',
            value:'',
            disabled:'false',
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

                    belongModule:this.belongModule_1.value,
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
                    belongModule:this.belongModule_1.value,
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

        //判断代码是否重复
        changeKeySetCode(){
            console.log(keyValueSetAdd.keyForm.keysetCodeInput);

            // $.ajax({
            //     url: checkCodeUrl,
            //     type: "get",
            //     data: {
            //         keysetCode: keyValueSetAdd.keyForm.keysetCodeInput
            //     },
            //     dataType: "json",
            //     xhrFields: {withCredentials: true},
            //     success: function (res) {
            //         console.log(res);
            //         if(res.resp.respCode=="000"){
            //             if(res.resp.content.state==1){
            //                 console.log(res.resp.content.msg);
            //                 console.log(keyValueSetAdd.rules.keysetCodeInput[0].message);
            //                  keyValueSetAdd.rules.keysetCodeInput[0].message=res.resp.content.msg;
            //                 console.log(keyValueSetAdd.rules.keysetCodeInput[0].message);
            //             }
            //         }
            //
            //     },
            //     error: function () {
            //         alert("错误")
            //     }
            // })
        },

        confirm(formName) {
            this.$refs[formName].validate(function (valid) {
                if(topButtonObj.isEdit == true){//编辑
                    if(valid){
                        editObj.editOk(function(){
                            keyValueSetAdd.editKeySet(leftKeyValueSet.rowId);
                        })
                    }else{
                        $('.el-form-item__error').css("display", "block");
                        keyValueSetAdd.judgeMent();
                        return false;
                    }
                }else{//新增
                    if(valid){
                        addObj.addOk(function(){
                            keyValueSetAdd.addKeySet();
                        })
                    }else{
                        $('.el-form-item__error').css("display", "block");
                        keyValueSetAdd.judgeMent();
                        return false;
                    }
                }
            })
        },
        cancel() {
            ibcpLayer.Close(topButtonObj.divIndex);
            ibcpLayer.Close(leftKeyValueSet.editdivIndex);
        },
        //模块下拉框
        getBelongModule_1(datas){
            this.belongModule_1.value=datas.value;
        },
        judgeMent(){
            setTimeout(function(){
                $('.el-form-item__error').css("display", "none");
            },1500)
        },
    }
})
