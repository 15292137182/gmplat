/**
 * Created by admin on 2017/8/1.
 */
//新增业务对象
    var addUrl=serverPath + "/businObj/add";
//编辑业务对象
    var editUrl=serverPath + "/businObj/modify";
//编辑业务对象查询
    var editObjUrl=serverPath + "/businObj/queryById";
//删除业务对象
    var deleteUrl=serverPath + "/businObj/delete";
//业务对象查询
    var qurUrl=serverPath + "/businObj/queryPage";
//新增业务对象属性
    var addProUrl=serverPath + "/businObj/add";
//删除业务对象属性
    var deleteProUrl=serverPath + "/businObjPro/delete";
//查询业务对象属性
    var qurProUrl=serverPath + "/businObj/queryProPage";
//生效
    var affectPropUrl=serverPath + "/businObj/takeEffect";
//变更
    var changeUrl=serverPath + "/businObj/changeOperat";
//关联表
    var conTable=serverPath + "/maintTable/query";
//关联表字段
    var conChildTable=serverPath + "/dbTableColumn/queryTabById";

var basTop = new Vue({
    el: '#basTop',
    data: {
        addOpe: false,
        addAttr: true,
        takeEffect: true,
        change: true,
    },
    methods: {
        //新增业务对象
        addEvent() {
            operate = 1;
            var htmlUrl = 'metadata-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '340px',function(){});
        },
        //新增业务对象属性
        addProp(){
            operateOPr=1;
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '400px', '540px', function () {});
        },
        //生效
        affectProp(){
            gmpAjax.showAjax(affectPropUrl,{rowId:basLeft.currentId},function(res){
                showMsg.MsgOk(basTop,res);
                basLeft.searchLeft();
            })
        },
        //变更
        changeProp(){
            gmpAjax.showAjax(changeUrl,{rowId:basLeft.currentId},function(res){
                showMsg.MsgOk(basTop,res);
                basLeft.searchLeft();
            })
        }
    }
});
var basLeft = new Vue({
    "el": "#basLeft",
    data:  getData.dataObj({
    }),
    methods: {
        //编辑业务对象
        editEvent() {
            operate = 2;
            var htmlUrl = 'metadata-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑业务对象', '400px', '340px', function () {
                console.log(basLeft.currentVal.rowId);
                gmpAjax.showAjax(editObjUrl,{rowId:basLeft.currentVal.rowId},function(res){
                    //编辑拿到的数据
                    var data=res.resp.content.data[0];
                    em.addForm.codeInput = data.objectCode;  //对象代码
                    em.addForm.nameInput =data.objectName;//对象名称
                    em.$refs.selectTab.tableInput =data.relateTableRowId;//关联表
                    em.addForm.versionInput =data.version;//版本
                })
            });
        },
        //删除业务对象
        deleteEvent() {
            deleteObj.del(function(){
                gmpAjax.showAjax(deleteUrl,{rowId:basLeft.currentVal.rowId},function(res){
                    showMsg.MsgOk(basLeft,res);
                    //分页跳回到第一页
                    basLeft.searchLeft();
                })
            })
        },
        //分页查询
        searchLeft(){
            queryData.getData(queryTemp,this.input,this,function(res){
                basLeft.currentChange(basLeft.tableData[0]);
            })




            queryData.getData(qurUrl,basLeft.leftInput,basLeft,function(res){
                if(res.data!=null){
                    //console.log(res.data.result);
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
        //不分页查询
        searchLeftTable() {
            pagingObj.Example(qurUrl,this.leftInput,this.pageSize,this.pageNum,this,function(res){
                //console.log(res)
                if(res.data!=null){
                    //console.log(res.data.result);
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
        searchRight(){
            queryData.getDatas(qurProUrl,basRight.rightInput,basLeft.currentId,basRight,function(res){});
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
                    basRight.searchRight();
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