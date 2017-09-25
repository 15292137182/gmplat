/**
 * Created by admin on 2017/8/27.
 */
var count=true;
var addTemp = new Vue({
    "el": "#addEvent",
    data: function () {
            return {
                labelPosition: 'right',
                addTempObj: {
                    codeInput: '',//代码
                    nameInput: '',//名称
                    modules:'',//模块
                    comContent: '',//说明
                },
                //所属模块下拉框
                belongModule_1:{
                    params:'belongModule',
                    value:'',
                    disabled:'false',
                },
                //验证
                rules: {
                    nameInput: [
                        { required: true,message: '请输入名称'},
                        { max:128, message: '长度最大为128字节', trigger: 'blur' }
                    ],
                    modules: [
                        { required: true,trigger: 'blur', message: '请选择所属模块'},
                    ],
                    comContent: [
                        { required: true,message: '请输入说明'},
                        { max:512, message: '长度最大为512字节', trigger: 'blur' }
                    ]
                },
            }
    },
    methods: {
        //保存事件
        saveTemp(formName){
            this.$refs.addTempObj.validate((valid) => {
                if (valid) {
                    //新增
                    if(operate==1){
                        addObj.addOk(function(){
                            var data={
                                "url":addTempObj,
                                "jsonData":{templateName:addTemp.addTempObj.nameInput,desp:addTemp.addTempObj.comContent,belongModule:addTemp.belongModule_1.value,
                                    belongSystem:addTemp.addTempObj.system},
                                "obj":basLeft
                            };
                            gmpAjax.showAjax(data,function(res){
                                //关闭弹层
                                ibcpLayer.Close(divIndex);
                                //分页查询
                                queryData.getData(queryTemp,basLeft.input,basLeft,function(res){
                                    basLeft.currentChange(basLeft.tableData[0]);
                                })
                            })
                        })
                    }if(operate==2){
                        editObj.editOk(function(){
                            var data={
                                "url":editTempObj,
                                "jsonData":{templateCode:addTemp.addTempObj.codeInput,rowId:basLeft.currentId,templateName:addTemp.addTempObj.nameInput,desp:addTemp.addTempObj.comContent,
                                    belongModule:addTemp.belongModule_1.value, belongSystem:addTemp.addTempObj.system},
                                "obj":basLeft
                            };
                            gmpAjax.showAjax(data,function(res){
                                console.log(res)
                                ibcpLayer.Close(divIndex);
                                queryData.getData(queryTemp,basLeft.input,basLeft,function(res){
                                    basLeft.currentChange(basLeft.tableData[0]);
                                })
                            })
                        })
                    }
                }else {
                    $('.el-form-item__error').css("display", "block");
                    addTemp.judgeMent();
                    return false;
                }
            })

        },
        //取消事件
        cancelTemp(){
            ibcpLayer.Close(divIndex);
        },
        //模块下拉框
        getBelongModule_1(datas){
            this.belongModule_1.value=datas.value;

            this.addTempObj.modules = datas.value;
            var a=this.$refs.belongModule_1.$children[0].$children[0].$el;
            var b=$(a).children('input');
            if(this.addTempObj.modules.length==0) {
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