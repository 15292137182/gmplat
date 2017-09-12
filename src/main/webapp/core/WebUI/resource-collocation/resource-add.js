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

            }
        }

    },
    methods: {
        conformEvent() {
            if (operate == 1) { //新增的时候
                addObj.addOk(function() {
                    var data = {
                        "url": addUrl,
                        "jsonData": {
                            confKey: resAdd.colForm.codeInput,
                            confValue: resAdd.colForm.nameInput,
                            desp: resAdd.colForm.tableInput
                        },
                        "obj": resTop
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
                        "obj": resTop
                    };
                    gmpAjax.showAjax(data, function (res) {
                        resCol.searchTable();
                        ibcpLayer.Close(divIndex);
                    })
                })
            }
        },
        cancel() {
            ibcpLayer.Close(divIndex);
        }
    }
})