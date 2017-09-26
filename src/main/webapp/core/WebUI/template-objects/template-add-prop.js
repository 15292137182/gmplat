/**
 * Created by admin on 2017/8/27.
 */
var count=true;
var addTempProp = new Vue({
    "el": "#addEventProp",
    data: function () {
            return {
                labelPosition: 'right',
                addTempPropObj: {
                    codeInput: '',//代码
                    engNameInput: '',//英文名
                    chnNameInput: '',//中文名
                    default:'',//默认值,
                    typeValue:'',//值类型
                    comContent:''//说明
                },
                valueType_1:{
                    params:"valueType",
                    value:"",
                    disabled:"false"
                },
                //验证
                rules: {
                    engNameInput: [
                        { required: true,message: '请输入英文名'},
                        { max:64, message: '长度最大为64字节', trigger: 'blur' }
                    ],
                    chnNameInput: [
                        { required: true, message: '请输入中文名'},
                        { max:128, message: '长度最大为128字节', trigger: 'blur' }
                    ],
                    //default: [
                    //    { required: true,message: '请输入默认值'}
                    //],
                    typeValue: [
                        { required: true,trigger: 'blur', message: '请选择值类型'},
                    ],
                    comContent: [
                        //{ required: true,message: '请输入说明'},
                        { max:512, message: '长度最大为512字节', trigger: 'blur' }
                    ]
                },
            }
        },
    methods: {
        //保存事件
        saveTempProp(formName){
            this.$refs.addTempPropObj.validate((valid) => {
                if(valid){
                    if(operateOPr==1){
                        addObj.addOk(function(){
                            var data={
                                "url":addObjTemp,
                                "jsonData":{templateObjRowId:basLeft.currentId,
                                    ename:addTempProp.addTempPropObj.engNameInput,
                                    cname:addTempProp.addTempPropObj.chnNameInput,
                                    valueType:addTempProp.valueType_1.value,
                                    defaultValue:addTempProp.addTempPropObj.default,
                                    desp:addTempProp.addTempPropObj.comContent
                                },
                                "obj":basRight,
                                'showMsg':true
                            };
                            gmpAjax.showAjax(data,function(res){
                                //关闭弹层
                                ibcpLayer.Close(divIndex);
                                //分页查询
                                queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){})
                            })
                        })
                    }else if(operateOPr==2){
                        editObj.editOk(function(){
                            var data={
                                "url":editObjTemp,
                                "jsonData":{templateObjRowId:basLeft.currentId,
                                    proRowId:basRight.currentProId,
                                    ename:addTempProp.addTempPropObj.engNameInput,
                                    cname:addTempProp.addTempPropObj.chnNameInput,
                                    valueType:addTempProp.valueType_1.value,
                                    defaultValue:addTempProp.addTempPropObj.default,
                                    desp:addTempProp.addTempPropObj.comContent
                                },
                                "obj":basRight,
                                'showMsg':true
                            };
                            gmpAjax.showAjax(data,function(res){
                                //关闭弹层
                                ibcpLayer.Close(divIndex);
                                //分页查询
                                queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){
                                })
                            })
                        })
                    }
                }
               else {
                    $('.el-form-item__error').css("display", "block");
                    addTempProp.judgeMent();
                    return false;
                }
            })

        },
        //取消事件
        cancelTempProp(){
            ibcpLayer.Close(divIndex);
        },
        //值类型下拉框事件
        getValueType_1(datas){
            this.valueType_1.value = datas.value;

            this.addTempPropObj.typeValue = datas.value;
            var a=this.$refs.valueType_1.$children[0].$children[0].$el;
            var b=$(a).children('input');
            if(this.addTempPropObj.typeValue.length==0) {
                if(count!=true){
                    $(b).css('borderColor', '#ff4949');
                }
                count=false;
            }else{
                $(b).css('borderColor','#bfcbd9');
            }
        },
        judgeMent(){
            setTimeout(function(){
                $('.el-form-item__error').css("display", "none");
            },1500)
        },

    }
});