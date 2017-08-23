/**
 * Created by admin on 2017/8/1.
 */
var addUrl=serverPath + "/businObj/add";
var editUrl=serverPath + "/businObj/modify";
var deleteUrl=serverPath + "/businObj/delete";
var qurUrl=serverPath + "/businObj/queryPage";
var addProUrl=serverPath + "/businObj/add";
var deleteProUrl=serverPath + "/businObjPro/delete";
var qurProUrl=serverPath + "/businObj/queryProPage";
var affectPropUrl=serverPath + "/businObj/takeEffect";//生效
var changeUrl=serverPath + "/businObj/changeOperat";//变更
var conTable=serverPath + "/maintTable/query";
var conChildTable=serverPath + "/dbTableColumn/queryTabById";//字表左侧查右侧

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
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '340px',function(){
                //关联表字段
                basTop.$http.jsonp(conTable, {
                    str:''
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    em.reaOptTable = res.data.data;
                    console.log(em.reaOptTable)
                });
            });
        },
        addProp(){
            operateOPr=1;
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '400px', '540px', function () {
                basTop.getSelectValue();

            });
        },
        getSelectValue(){
            //关联表字段
            proEm.$http.jsonp(conChildTable, {
                args:'',
                rowId: basLeft.relateTableRowId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                //console.log(ref);
                proEm.optionValue = ref.data.data;
            });
            //键值类型(键值集合)
            proEm.$http.jsonp(serverPath + '/keySet/query', {
                str: ''
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                // console.log(ref);
                proEm.optionLeft = ref.data.data;
               // console.log(proEm.optionLeft)
            });
            //值来源类型(键值集合)
            proEm.$http.jsonp(serverPath + '/keySet/query', {
                str: ''
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                proEm.optionRight = ref.data.data;
            });
        },
        affectProp(){
            this.$http.jsonp(affectPropUrl, {
                rowId: basLeft.currentId
            }, {
                jsonp: 'callback'
            }).then(function (ref) {
                ibcpLayer.ShowOK(ref.data.message);
                //分页跳回到第一页
                queryData.getData(qurUrl,basLeft.leftInput,basLeft,function(res){});

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
                //分页跳回到第一页
                queryData.getData(qurUrl,basLeft.leftInput,basLeft,function(res){});
            });
        }
    }
});
var basLeft = new Vue({
    "el": "#basLeft",
    data: {
        leftInput: '',
        loading:true,
        leftHeight: '',
        tableData: [],
        pageNum:1,//当前为第一页
        pageSize:10,//每页显示条数
        //开始不能为空 否则会报错
        allDate:0,//总共有多少条

    },
    methods: {
        editEvent() {
            operate = 2;
            var htmlUrl = 'metadata-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑业务对象', '400px', '340px', function () {
                console.log(basLeft.currentVal.rowId);

                //关联表字段
                basLeft.$http.jsonp(serverPath+'/businObj/queryById', {
                    rowId:basLeft.currentVal.rowId
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    em.addForm.codeInput = res.data.data[0].objectCode;  //对象代码
                    em.addForm.nameInput = res.data.data[0].objectName;//对象名称
                    em.$refs.selectTab.tableInput = res.data.data[0].relateTableRowId;//关联表
                    em.addForm.versionInput = res.data.data[0].version;//版本

                });
            });
        },
        deleteEvent() {
            deleteObj.del(function(){
                var deleteId = basLeft.currentVal.rowId;  //左侧表的row的ID
                basLeft.$http.jsonp(deleteUrl, {
                    rowId: deleteId
                }, {
                    jsonp: 'callback'
                }).then(function (ref) {
                    showMsg.MsgOk(basTop,ref)
                    //分页跳回到第一页
                    queryData.getData(qurUrl,basLeft.leftInput,basLeft,function(res){});
                    //basRight.rightInput='';
                    //queryData.getDatas(qurProUrl,basRight.rightInput,basLeft.currentId,basRight,function(res){});
                },function(){
                    showMsg.MsgError(basTop)
                });
            })
        },
        searchLeftTable() {
            pagingObj.Example(qurUrl,this.leftInput,this.pageSize,this.pageNum,this,function(res){
                console.log(res)
                if(res.data!=null){
                    console.log(res.data.result);
                    $.each(res.data.result, function(index, item) {
                        if(item.status != "0") {
                            item.testDemo = true;
                        }
                    });
                    basLeft.currentChange(basLeft.tableData[0]);
                }else{
                    basRight.tableData=[];//没找到右边表的数据为空
                    basTop.addAttr = true,
                    basTop.takeEffect = true,
                    basTop.change = true
                }
            });
        },
        handleSizeChange(val) {
            //每页 ${val} 条
            this.pageSize=val;
            this.searchLeftTable();
        },
        handleCurrentChange(val) {
            //当前页: ${val}
            this.pageNum=val;
            this.searchLeftTable();
        },
        currentChange(row, event, column) {
          console.log(row)
            //判断是否生效
            if (row !== undefined) {
                //生效
                if (row.status == '0') {  //没啥变化
                    basTop.addAttr = false;
                    basTop.takeEffect = false;
                    basTop.change = true;
                } else if (row.status == '20') {  //生效了
                    basTop.addAttr = true;
                    basTop.takeEffect = true;
                    basTop.change = false;
                    //变更
                    if(row.changeOperat=='50'){
                        basTop.change = false;
                    }else if(row.changeOperat=='20'){  //变更了
                        basTop.change = true;
                    }
                } else if(row.status == '40'){
                    basTop.addAttr = true;
                    basTop.takeEffect = true;
                    basTop.change = true;
                }
                this.currentVal = row;
                //关联表的数据
                this.relateTableRowId = row.relateTableRowId;
                //左边这一行的数据
                this.currentId = row.rowId;
                //查找右侧表的数据
                basRight.rightInput='';
                basRight.searchRightTable(this.currentVal);
            }
        },
        FindLFirstDate(row){  //将选中行变颜色
            this.$refs.tableData.setCurrentRow(row);
        },
        headSort(column){//列头排序
            pagingObj.headSort(qurUrl,this.resInput,this.pageSize,this.pageNum,column,this);
        }
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
        this.FindLFirstDate(this.tableData[0]);
    }
});

var basRight = new Vue({
    "el": "#basRight",
    data: {
        tableId:'objProp',
        rightInput: '',
        loading:false,
        rightHeight: '',
        tableData: [],
        pageNum:1,//当前为第一页
        pageSize:10,//每页显示条数
        //开始不能为空 否则会报错
        allDate:0  //总共有多少条
    },
    methods: {
        searchRightTable() {
           // console.log(basLeft.currentVal);
            pagingObj.Examples(qurProUrl,basLeft.currentId,this.rightInput,this.pageSize,this.pageNum,this,function(res){
                if(res.data!=null){
                    //按钮禁掉
                    $.each(res.data.result, function(index, item) {
                        if(basLeft.currentVal.testDemo==true) {
                            item.testDemo = true;
                        }
                    });
                    //默认选中
                    basRight.currentRChange(basRight.tableData[0])
                }
            });

        },
        editProp(){
            operateOPr = 2;
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑对象属性', '400px', '540px', function () {
                basTop.getSelectValue();
                //console.log(basRight.currentVal);
                ////关联表字段
                basRight.$http.jsonp(serverPath+'/businObjPro/queryById', {
                    rowId:basRight.currentVal.rowId
                }, {
                    jsonp: 'callback'
                }).then(function (res) {
                    console.log(res.data.data[0])
                    proEm.addProForm.codeProInput=res.data.data[0].propertyCode;   //代码
                    proEm.addProForm.nameProInput=res.data.data[0].propertyName;   //名称
                    if(res.data.data[0].wetherExpandPro=='true'){
                        proEm.addProForm.checked=true;
                        console.log(proEm.addProForm.checked)
                    }else{
                        proEm.addProForm.checked=false;
                    }
                    proEm.$refs.contablefield.conTableFieldInput=res.data.data[0].relateTableColumn ;  //关联表

                    //修改值类型和值类型来源下拉框  jms 2017/8/21
                    proEm.$refs.vtype.value=res.data.data[0].valueType   //值类型
                    proEm.$refs.vorigin.value=res.data.data[0].valueResourceType;   //值类型来源
                    proEm.addProForm.comContent=res.data.data[0].valueResourceContent;     //值来源内容
                });
            });
        },
        deleteProp(){
            deleteObj.del(function(){
                //拿到ID
                var deleteId = basRight.rightVal
                basRight.$http.jsonp(deleteProUrl, {
                    rowId: deleteId
                }, {
                    jsonp: 'callback'
                }).then(function (ref) {
                    showMsg.MsgOk(basTop,ref);
                    //分页跳回到第一页
                    queryData.getDatas(qurProUrl,basRight.rightInput,basLeft.currentId,basRight,function(res){});
                },function(){
                    showMsg.MsgError(basTop)
                });
            })
        },
        currentRChange(row, event, column) {
            console.log(row)
            //点击拿到这条数据的值
            if (row != undefined) {
                this.currentVal = row;
                this.rightVal = row.rowId;
                // console.log(this.rightVal)
            }
        },
        extendOnly(row){
            if(row.wetherExpandPro =="true"){
                return "是"
            }else{
                return "否"
            }
        },
        FindRFirstDate(row){
           // console.log(row)
            this.$refs.tableData.setCurrentRow(row);
        },
        handleSizeChange(val) {
            //每页每页 ${val} 条
            this.pageSize=val;
            this.searchRightTable()
        },
        handleCurrentChange(val) {
            //当前页: ${val}
            this.pageNum=val;
            this.searchRightTable();
        },
        headSort(column){//列头排序
            pagingObj.headSorts(qurProUrl,basLeft.currentId,this.resInput,column,this);
        },
    },
    created() {
        $(document).ready(function () {
            basRight.rightHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basRight.rightHeight = $(window).height() - 194;
        });
        var args={"objProp":{valueType:"valueType",valueResourceType:"valueTypeOrigin"}};
        TableKeyValueSet.init(args);

    },
    updated() {
        //if (this.tableData != null) {
        //    this.FindRFirstDate(this.tableData[0]);
        //}
    }
})