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
            queryData.getData(qurUrl,this.input,this,function(res){
                console.log(res);
                basLeft.currentChange(basLeft.tableData[0]);
            })
        },
        //分页不跳回第一页查询
        searchLeftTable() {
            pagingObj.Example(qurUrl,this.input,this.pageSize,this.pageNum,this,function(res){
                //console.log(res);
                //有数据选中第一行
                basLeft.currentChange(basLeft.tableData[0]);
            });
        },
        //每页 ${val} 条
        handleSizeChange(val) {
            this.pageSize=val;
            this.searchLeftTable();
        },
        //当前页: ${val}
        handleCurrentChange(val) {
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
                basRight.searchRight();
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
    data:  getData.dataObj({
        tableId:'objProp',
    }),
    methods: {
        //不跳转到第一页查询
        searchRightTable() {
            pagingObj.Examples(qurProUrl,basLeft.currentId,this.input,this.pageSize,this.pageNum,this,function(res){
                //有数据选中第一行
                if(res.data!=null){
                    //按钮禁掉
                    $.each(res.data.result, function(index, item) {
                        if(basLeft.currentVal.testDemo==true) {
                            item.testDemo = true;
                        }
                    });
                    //默认选中
                    basRight.currentRChange(basRight.tableData[0]);
                }
            })
        },
        //转到第一页查询
        searchRight(){
            queryData.getDatas(qurProUrl,basRight.input,basLeft.currentId,basRight,function(res){
                console.log(res);
                basRight.currentRChange(basRight.tableData[0]);
            })
        },
        editProp(){
            operateOPr = 2;
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑对象属性', '400px', '540px', function () {
                gmpAjax.showAjax(editObjUrl, {rowId: basRight.currentVal.rowId}, function (res) {
                //    //编辑拿到的数据
                    var data = res.data.data[0];
                    proEm.addProForm.codeProInput=data.propertyCode;   //代码
                    proEm.addProForm.nameProInput=data.propertyName;   //名称
                    if(data.wetherExpandPro=='true'){
                        proEm.addProForm.checked=true;
                        console.log(proEm.addProForm.checked)
                    }else{
                        proEm.addProForm.checked=false;
                    }
                    proEm.$refs.contablefield.conTableFieldInput=data.relateTableColumn ;  //关联表
                    //修改值类型和值类型来源下拉框  jms 2017/8/21
                    proEm.$refs.vtype.value=data.valueType   //值类型
                    proEm.$refs.vorigin.value=data.valueResourceType;   //值类型来源
                    proEm.addProForm.comContent=data.valueResourceContent;     //值来源内容

                })
            });
        },
        deleteProp(){
            deleteObj.del(function(){
                gmpAjax.showAjax(deleteProUrl,{rowId: basRight.rightVal},function(res){
                    showMsg.MsgOk(basRight,res);
                    //分页查询
                    basRight.searchRight();
                })
            });
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
            basRight.leftHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basRight.leftHeight = $(window).height() - 194;
        });
        var args={"objProp":{valueType:"valueType",valueResourceType:"valueTypeOrigin"}};
        TableKeyValueSet.init(args);
    },
    //页面一进入第一行高亮显示
    updated() {
        this.FindRFirstDate(this.tableData[0]);
    }
})