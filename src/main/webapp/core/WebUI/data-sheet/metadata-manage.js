/**
 * Created by admin on 2017/8/1.
 */
var addUrl=serverPath + "/businObj/add";
var editUrl=serverPath + "/businObj/modify";
var deleteUrl=serverPath + "/businObj/delete";
var qurUrl=serverPath + "/businObj/query";
var addProUrl=serverPath + "/businObj/add";
var deleteProUrl=serverPath + "/businObjPro/delete";
var qurProUrl=serverPath + "/businObjPro/querySlave";
var affectPropUrl=serverPath + "/businObj/takeEffect";//生效
var changeUrl=serverPath + "/businObj/changeOperat";//变更

var conTable=serverPath + "/maintTable/query";
var conChildTable=serverPath + "/dbTableColumn/query";//字表左侧查右侧




var basTop = new Vue({
    el: '#basTop',
    data: {
        addOpe: false,
        addAttr: true,
        takeEffect: true,
        change: true,
    },
    methods: {
        addEvent() {
            operate = 1;
            var htmlUrl = 'metadata-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '340px');
        },
        addProp(){
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '400px', '440px', function () {
                //键值类型(键值集合)
                proEm.$http.jsonp(serverPath + '/keySet/query', {
                    str: ''
                }, {
                    jsonp: 'callback'
                }).then(function (ref) {
                    proEm.optionLeft = ref.data.data;
                });
                //值来源类型(键值集合)
                proEm.$http.jsonp(serverPath + '/keySet/query', {
                    str: ''
                }, {
                    jsonp: 'callback'
                }).then(function (ref) {
                    proEm.optionRight = ref.data.data;
                });
            });

        },
        affectProp(){
            this.$http.jsonp(affectPropUrl, {
                rowId: basLeft.currentId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                ibcpLayer.ShowOK(ref.data.message);
                //刷新 不然状态不变化
                basLeft.searchLeftTable();
            });
        },
        changeProp(){
            this.$http.jsonp(changeUrl, {
                rowId: basLeft.currentId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                console.log(ref);
                ibcpLayer.ShowOK(ref.data.message);
                //刷新 不然状态不变化
                basLeft.searchLeftTable();
            });
        }
    }
});
var basLeft = new Vue({
    "el": "#basLeft",
    data: {
        leftInput: '',
        leftHeight: '',
        myLeftData: [],
        //currentPage4: 1
    },
    methods: {
        editEvent() {
            operate = 2;
            var htmlUrl = 'metadata-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑业务对象', '400px', '340px', function () {
                console.log(basLeft.currentVal)
                em.codeInput = basLeft.currentVal.objectCode;  //对象代码
                em.disabled = true;//对象代码不可点击
                em.firstIconEvent = true;//选择关联表的按钮不可用
                em.nameInput = basLeft.currentVal.objectName;//对象名称
                em.tableInput = basLeft.currentVal.associatTable;//关联表
                em.versionInput = basLeft.currentVal.version;//版本
                em.dataId = basLeft.currentVal.relateTableRowId;//关联表的ID
            });
        },
        deleteEvent() {
            var deleteId = basLeft.currentVal.rowId;  //左侧表的row的ID
            this.$http.jsonp(deleteUrl, {
                rowId: deleteId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                ibcpLayer.ShowOK(ref.data.message);
                basLeft.searchLeftTable();
                basRight.searchRightTable();
            });
        },
        searchLeftTable() {
            this.$http.jsonp(qurUrl, {
                str: this.leftInput
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                console.log(res);
                if (res.data.data !== null) {
                    this.myLeftData = res.data.data;
                    //默认选中第一行的数据
                    this.currentChange(this.myLeftData[0]);
                } else {
                    this.myLeftData = [];
                    basTop.addAttr = true,
                    basTop.takeEffect = true,
                    basTop.change = true
                }
            });
        },
        currentChange(row, event, column) {
           console.log(row)
            //判断是否生效
            if (row !== undefined) {
                if (row.status == 40) {
                    basTop.takeEffect = false;
                    basTop.change = true;
                } else if (row.status == 20) {
                    basTop.takeEffect = true;
                    basTop.change = false;
                }
                if(row.changeOperat==40){
                    basTop.change = false;
                    basTop.addAttr = true;
                }else if(row.changeOperat==20){
                    basTop.addAttr = false;
                    basTop.change = true;
                }
                this.currentVal = row;
                //关联表的数据
                this.relateTableRowId = row.relateTableRowId;
                //左边这一行的数据
                this.currentId = row.rowId;
                //查找右侧表的数据
                this.$http.jsonp(qurProUrl, {
                    str: '',
                    objRowId: this.currentId
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    // console.log(res)
                    if (res.data.data !== null) {
                        basRight.myRightData = res.data.data;
                        //右边有数据 默认点击第一行
                        basRight.currentRChange(basRight.myRightData[0])
                    } else {
                        basRight.myRightData = [];
                    }
                });
            }
        },
        FindLFirstDate(row){
            this.$refs.myLeftData.setCurrentRow(row);
        },
    },
    created() {
        this.searchLeftTable();
        $(document).ready(function () {
            basLeft.leftHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basLeft.leftHeight = $(window).height() - 194;
        })

    },
    updated() {
        this.FindLFirstDate(this.myLeftData[0]);
    }
});

var basRight = new Vue({
    "el": "#basRight",
    data: {
        rightInput: '',
        rightHeight: '',
        myRightData: [],
        //默认第一行
        currentPage: 1
    },
    methods: {
        searchRightTable() {
            this.$http.jsonp(qurProUrl, {
                str: this.rightInput,
                objRowId: basLeft.currentId
            }, {
                jsonp: 'callback'
            }).then(function (res) {
                if (res.data.data !== null) {
                    basRight.myRightData = res.data.data;
                    //右边有数据 默认点击第一行
                    basRight.currentRChange(basRight.myRightData[0])
                }
            })
        },
        deleteProp(){
            //拿到ID
            var deleteId = basRight.rightVal
            this.$http.jsonp(deleteProUrl, {
                rowId: deleteId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                ibcpLayer.ShowOK(ref.data.message);
                basLeft.searchLeftTable();
            });
        },
        currentRChange(row, event, column) {
            //console.log(row)
            //点击拿到这条数据的值
            if (row != undefined) {
                //console.log(row)
                this.rightVal = row.rowId;
                // console.log(this.rightVal)
            }
        },
        FindRFirstDate(row){
            this.$refs.myRightData.setCurrentRow(row);
        },
        handleSizeChange(val) {
            //每页多少条数变化时
            this.pageNum = 10;
            console.log(`每页 ${val} 条`);
            this.searchPage();
        },
        handleCurrentChange(val) {
            this.pageSize = val;
            console.log(`当前页: ${val}`);
            this.searchPage();
        }
    },
    created() {
        //this.searchRightTable();
        $(document).ready(function () {
            basRight.rightHeight = $(window).height() - 200;
        });
        $(window).resize(function () {
            basRight.rightHeight = $(window).height() - 200;
        })
    },
    updated() {
        if (this.myRightData != null) {
            this.FindRFirstDate(this.myRightData[0]);
        }
    }
})