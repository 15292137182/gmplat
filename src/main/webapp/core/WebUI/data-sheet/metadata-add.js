/**
 * Created by admin on 2017/8/30.
 */

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

            },
            //关联表下拉框数据
            table_1: {
                url: conTable,
                key:'{"label":"tableCname","value":"rowId"}',
                value: "",
                disabled: "false"
            },

            templateObj_1:{
                url: templateObjPageUrl,
                key:'{"label":"templateName","value":"rowId"}',
                value: "",
                disabled: "false"
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
        conformEvent() {
            //this.$refs.ruleForm.validate((valid) => {
            //    if (valid) {
            //        this.$message({
            //            message: "创建成功",
            //            showClose: true,
            //            duration: 5000,
            //            type: "success"
            //        })
            //    }
            //    else {
            //        this.$message({
            //            message: "请输入正确项目",
            //            showClose: true,
            //            duration: 5000,
            //            type: "warning"
            //        })
            //        return false;
            //    }
            //});
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
        },
        getTable_1(datas){
            this.table_1.value = datas.value;
        },
        getTemplateObj_1(datas){
            this.templateObj_1.value=datas.value

        },
        //所属模块change事件
        getBelongModule_1(datas){
           this.belongModule_1.value = datas.value;
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    },
})