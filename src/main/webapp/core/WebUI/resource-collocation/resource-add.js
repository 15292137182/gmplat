/**
 * Created by admin on 2017/9/8.
 */
var resAdd = new Vue({
    el: '#addEvent',
    data: function () {
        return {
            labelPosition: 'right',
            colForm: {
                codeInput: '',  //键
                nameInput: '',  //值
                tableInput: '', //描述
                disabled: false  //开始键的内容可以写
            },
            rules: {
                codeInput: [
                    { required: true,message: '请输入键'},
                    { max:64, message: '长度最大为64字节'}
                ],
                nameInput: [
                    { required: true,message: '请选择值'},
                    { max:64, message: '长度最大为64字节'}
                ],
                tableInput: [
                    { max:512, message: '长度最大为512字节'}
                ]
            }
        }

    },
    methods: {
        conformEvent(formName) {
            this.$refs.colForm.validate((valid) => {
                if(valid){
                    if (operate == 1) { //新增的时候
                        addObj.addOk(function() {
                            var data = {
                                "url": addUrl,
                                "jsonData": {
                                    confKey: resAdd.colForm.codeInput,
                                    confValue: resAdd.colForm.nameInput,
                                    desp: resAdd.colForm.tableInput
                                },
                                "obj": resTop,
                                "showMsg":true
                            };
                            gmpAjax.showAjax(data, function (res) {
                                resCol.searchTable();
                                ibcpLayer.Close(divIndex);
                            })
                        })
                    }
                    if (operate == 2) {
                        editObj.editOk(function() {
                            var data = {
                                "url": editUrl,
                                "jsonData": {
                                    rowId: resCol.currentValue.rowId,
                                    confKey: resAdd.colForm.codeInput,
                                    confValue: resAdd.colForm.nameInput,
                                    desp: resAdd.colForm.tableInput
                                },
                                "obj": resTop,
                                "showMsg":true
                            };
                            gmpAjax.showAjax(data, function (res) {
                                resCol.searchTable();
                                ibcpLayer.Close(divIndex);
                            })
                        })
                    }
                }else {
                    $('.el-form-item__error').css("display", "block");
                    resAdd.judgeMent();
                    return false;
                }
            })

        },
        cancel() {
            ibcpLayer.Close(divIndex);
        },
        judgeMent(){
            setTimeout(function(){
                $('.el-form-item__error').css("display", "none");
            },1500)
        },
    }
})