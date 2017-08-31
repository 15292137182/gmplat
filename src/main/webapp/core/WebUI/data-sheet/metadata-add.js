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
            options1: [{
                value: '选项1',
                label: '黄金糕'
            }, {
                value: '选项2',
                label: '黄金'
            }],
            options2: [{
                value: '选项1',
                label: '黄金糕'
            }, {
                value: '选项2',
                label: '黄金'
            }],
            options3: [{
                value: '选项1',
                label: '黄金糕'
            }, {
                value: '选项2',
                label: '黄金'
            }],
            disabledBool3: false,//是否禁用
            multipleBool3: true,//是否多选
            filterBool3: true,
            disabledBool2: false,//是否禁用
            multipleBool2: true,//是否多选
            filterBool2: true,
            disabledBool1: false,//是否禁用
            multipleBool1: true,//是否多选
            filterBool1: true,

            selectDatas3: {
                values: []//下拉框为空
            },
            selectDatas2: {
                values: []//下拉框为空
            },
            selectDatas1: {
                values: []//下拉框为空
            },
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
            var _DOM;

            this.multipleBool3 == true ? _DOM = this.selectDatas3.el.children[1].lastElementChild : _DOM = this.selectDatas3.el.children[0].lastElementChild;

            if(this.selectDatas.values == "") {
                // 捕获到具体input节点
                _DOM.style.borderColor = "red";
                this.$message({
                    message: "请选择类型",
                    customClass: "messageBox",	// 给消息弹出框添加类名
                    showClose: true,
                    duration: 5000,
                    type: "warning"
                });
                return
            }
            if (operate == 1) {

                console.log(em.ruleForm.reaTableObj)
                console.log(em.ruleForm.tableInput)
                //addObj.addOk(function(){
                //    em.$http.jsonp(addUrl, {
                //        objectName: em.addForm.nameInput,
                //        relateTableRowId: em.$refs.selectTab.tableInput,
                //    }, {
                //        jsonp: 'callback'
                //    }).then(function (res) {
                //        showMsg.MsgOk(basTop,res);
                //        basLeft.leftInput='';
                //        //分页跳回到第一页
                //        basLeft.searchLeft();
                //        ibcpLayer.Close(divIndex);
                //    })
                //},function(){
                //    showMsg.MsgError(basTop)
                //})
            }
            if (operate == 2) {
                //editObj.editOk(function(){
                //    em.$http.jsonp(editUrl, {
                //        //拿到这条数据的ID
                //        rowId: basLeft.currentVal.rowId,
                //        objectName: em.addForm.nameInput,
                //        relateTableRowId: em.$refs.selectTab.tableInput,
                //    }, {
                //        jsonp: 'callback'
                //    }).then(function (res) {
                //        showMsg.MsgOk(basTop,res);
                //        basLeft.leftInput='';
                //        basLeft.searchLeft();
                //        ibcpLayer.Close(divIndex);
                //    });
                //},function(){
                //    showMsg.MsgError(basTop)
                //})
            }
        },
        afterClear1(datas) {
            var _DOM;

            this.multipleBool1 == true ? _DOM = this.selectDatas1.el.children[1].lastElementChild : _DOM = this.selectDatas1.el.children[0].lastElementChild;

            if(datas != "") {
                _DOM.style.borderColor = "";
            }

            this.selectDatas.values = datas;
        },
        afterClear2(datas) {
            var _DOM;

            this.multipleBool2 == true ? _DOM = this.selectDatas2.el.children[1].lastElementChild : _DOM = this.selectDatas2.el.children[0].lastElementChild;

            if(datas != "") {
                _DOM.style.borderColor = "";
            }

            this.selectDatas.values = datas;
        },
        afterClear3(datas) {
            var _DOM;

            this.multipleBool3 == true ? _DOM = this.selectDatas3.el.children[1].lastElementChild : _DOM = this.selectDatas3.el.children[0].lastElementChild;

            if(datas != "") {
                _DOM.style.borderColor = "";
            }

            this.selectDatas.values = datas;
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    }
})