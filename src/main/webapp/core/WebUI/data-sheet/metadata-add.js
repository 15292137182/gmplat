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
            filterBool3: false,
            disabledBool2: false,//是否禁用
            multipleBool2: false,//是否多选
            filterBool2: true,
            disabledBool1: false,//是否禁用
            multipleBool1: false,//是否多选
            filterBool1: false,

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
            var _DOM3;
            this.multipleBool3 == true ? _DOM3 = this.selectDatas3.el.children[1].lastElementChild : _DOM3 = this.selectDatas3.el.children[0].lastElementChild;
            var _DOM2;
            this.multipleBool2 == true ? _DOM2 = this.selectDatas2.el.children[1].lastElementChild : _DOM2 = this.selectDatas2.el.children[0].lastElementChild;
            var _DOM1;
            this.multipleBool1 == true ? _DOM1 = this.selectDatas1.el.children[1].lastElementChild : _DOM1 = this.selectDatas1.el.children[0].lastElementChild;

            if(this.selectDatas3.values == "") {
                // 捕获到具体input节点
                _DOM3.style.borderColor = "red";
                this.$message({
                    message: "请选择类型3",
                    customClass: "messageBox",	// 给消息弹出框添加类名
                    showClose: true,
                    duration: 5000,
                    type: "warning"
                });
                return
            }
            if(this.selectDatas2.values == "") {
                // 捕获到具体input节点
                _DOM2.style.borderColor = "red";
                this.$message({
                    message: "请选择类型2",
                    customClass: "messageBox",	// 给消息弹出框添加类名
                    showClose: true,
                    duration: 5000,
                    type: "warning"
                });
                return
            }
            if(this.selectDatas1.values == "") {
                // 捕获到具体input节点
                _DOM1.style.borderColor = "red";
                this.$message({
                    message: "请选择类型1",
                    customClass: "messageBox",	// 给消息弹出框添加类名
                    showClose: true,
                    duration: 5000,
                    type: "warning"
                });
                return
            }
            if (operate == 1) {
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
            var _DOM1;

            this.multipleBool1 == true ? _DOM1 = this.selectDatas1.el.children[1].lastElementChild : _DOM1 = this.selectDatas1.el.children[0].lastElementChild;

            if(datas != "") {
                _DOM1.style.borderColor = "";
            }

            this.selectDatas1.values = datas;
            console.log(this.selectDatas1.values)
        },
        afterClear2(datas) {
            var _DOM2;

            this.multipleBool2 == true ? _DOM2 = this.selectDatas2.el.children[1].lastElementChild : _DOM2 = this.selectDatas2.el.children[0].lastElementChild;

            if(datas != "") {
                _DOM2.style.borderColor = "";
            }

            this.selectDatas2.values = datas;
            console.log(this.selectDatas2.values)
        },
        afterClear3(datas) {
            var _DOM3;

            this.multipleBool3 == true ? _DOM3 = this.selectDatas3.el.children[1].lastElementChild : _DOM3 = this.selectDatas3.el.children[0].lastElementChild;

            if(datas != "") {
                _DOM3.style.borderColor = "";
            }

            this.selectDatas3.values = datas;
            console.log(this.selectDatas3.values)
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    }
})