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
                tableInput:'',//所属模块
                versionInput: '',//版本
            },
            disabledBool:true,
            value_1: "",
            value_2: [],
            value_3:"",
            rules: {

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
                    //var data={
                    //    "url":addUrl,
                    //    "jsonData":{objectName:em.ruleForm.nameInput,//名称
                    //        relateTableRowId: em.,//关联表
                    //        relateTemplateObject: em.,//关联模板对象
                    //        belongModule: em.,//所属模块
                    //    },
                    //    "obj":basTop
                    //};
                    //gmpAjax.showAjax(data,function(res){
                    //    //分页跳回到第一页
                    //    basLeft.searchLeft();
                    //    ibcpLayer.Close(divIndex);
                    //})
                })
            }
            if (operate == 2) {  //新增业务对象
                editObj.editOk(function(){
                    //var data={
                    //    "url":addUrl,
                    //    "jsonData":{
                    //        rowId: basLeft.currentVal.rowId,//ID
                    //        objectName:em.ruleForm.nameInput,//名称
                    //        relateTableRowId: em.,//关联表
                    //        relateTemplateObject: em.,//关联模板对象
                    //        belongModule: em.,//所属模块
                    //    },
                    //    "obj":basTop
                    //};
                    //gmpAjax.showAjax(data,function(res){
                    //    //分页跳回到第一页
                    //     basLeft.searchLeft();
                    //        ibcpLayer.Close(divIndex);
                    //})
                })
            }
        },
        getChildData_1(datas){
            this.value_1 = datas.value;
            //console.log( this.value_1)

        },
        multipleDatas_1(datas){
            this.value_2 = datas.value;
            //console.log( this.value_2)


        },
        getChildData_2(datas){
            this.value_3 = datas.value;
            //console.log( this.value_3)

        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    }
})