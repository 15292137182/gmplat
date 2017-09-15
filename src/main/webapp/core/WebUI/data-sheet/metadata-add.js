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
            initial_1: {
                url: conTable,
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
                            relateTableRowId: em.initial_1.value,//关联表
                            relateTemplateObject: em.value_2,//关联模板对象
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
                            relateTableRowId: em.initial_1.value,//关联表
                            relateTemplateObject: em.value_2,//关联模板对象
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
        getChildData_1(datas){
            this.initial_1.value = datas.value;
           // this.label_1 = datas.label;
            //console.log( this.label_1);
            console.log( this.value_1);

        },
        multipleDatas_1(datas){
            this.value_2=JSON.stringify(datas);
           console.log(this.value_2);
        },
        //所属模块change事件
        belongModule_1(datas){
            console.log(datas);
           this.belongModule_1.value = datas.value;
           console.log( this.belongModule_1.value);

        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    },
    created(){
        this.initial_1.value = basLeft.relateTableRowId;
    }
})