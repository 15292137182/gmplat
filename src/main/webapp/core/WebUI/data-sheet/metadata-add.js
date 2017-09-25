/**
 * Created by admin on 2017/8/30.
 */
var count=true;
var em = new Vue({
    el: '#addEvent',
    data: function () {
        return {
            ruleForm:{
                codeInput: '',//代码
                nameInput: '',//名称
                reaTable:'',//关联表
                reaTableObj:'',//关联模板对象
                versionInput: '',//版本
                system:''
            },
            disabledBool:true,
            value_1: "",
            value_2: [],

            rules: {
                nameInput: [
                    { required: true, message: '请输入对象名称'},
                ],
                reaTable: [
                    { required: true,trigger: 'blur',message: '请选择关联表'}
                ],
            },
            //关联表下拉框数据
            table_1: {
                url: conTable,
                key:'{"label":"tableCname","value":"rowId"}',
                value: "",
                disabled: "false"
            },

            templateObj_1:{
                url: serverPath + "/templateObj/queryPage",
                key:'{"label":"templateName","value":"rowId"}',
                value: "",
                disabled: "false",
                multiple: "true"
            },

            //所属模块下拉框
            belongModule_1:{
                params: "belongModule",
                value: "",
                disabled: "false"
            }
        }
    },
    methods: {
        conformEvent(formName) {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (operate == 1) {    //新增业务对象
                        addObj.addOk(function(){
                            var data={
                                "url":addUrl,
                                "jsonData":{objectName:em.ruleForm.nameInput,//名称
                                    relateTableRowId: em.table_1.value,//关联表
                                    relateTemplateObject: em.templateObj_1.value,//关联模板对象
                                    belongModule: em.belongModule_1.value,//所属模块
                                },
                                "obj":basTop
                            };
                            gmpAjax.showAjax(data,function(res){
                                console.log(res);
                                //分页跳回到第一页
                                basLeft.searchLeft();
                                ibcpLayer.Close(divIndex);
                            })
                        })
                    }
                    if (operate == 2) {  //新增业务对象
                        editObj.editOk(function(){
                            console.log(em.templateObj_1.value)
                            var data={
                                "url":editUrl,
                                "jsonData":{
                                    rowId: basLeft.currentVal.rowId,//ID
                                    objectName:em.ruleForm.nameInput,//名称
                                    relateTableRowId: em.table_1.value,//关联表
                                    relateTemplateObject: em.templateObj_1.value,//关联模板对象
                                    belongModule: em.belongModule_1.value,//所属模块
                                },
                                "obj":basTop
                            };
                            gmpAjax.showAjax(data,function(res){
                                //分页跳回到第一页
                                basLeft.searchLeft();
                                ibcpLayer.Close(divIndex);
                            })
                        })
                    }
                }
                else {
                    $('.el-form-item__error').css("display", "block");
                    em.judgeMent();
                    return false;
                }
            });

        },
        getTable_1(datas){
            this.table_1.value = datas.value;
            this.ruleForm.reaTable = datas.value;

            var a=this.$refs.table_1.$children[0].$children[0].$el;
            var b=$(a).children('input');
            if(this.ruleForm.reaTable.length==0) {
                if(count!=true){
                    $(b).css('borderColor', '#ff4949');
                 }
                count=false;
            }else{
                $(b).css('borderColor','#bfcbd9');
            }
        },
        getTemplateObj_1(datas){
            if(datas.length==0){
                this.templateObj_1.value='';
            }else{
                this.templateObj_1.value=JSON.stringify(datas);
                //this.templateObj_1.value=datas;

                console.log(this.templateObj_1.value);
            }


        },
        //所属模块change事件
        getBelongModule_1(datas){
           this.belongModule_1.value = datas.value;
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        },
        judgeMent(){
            setTimeout(function(){
                $('.el-form-item__error').css("display", "none");
            },1500)
        },
    },
})