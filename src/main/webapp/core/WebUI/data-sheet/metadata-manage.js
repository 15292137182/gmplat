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
//编辑业务对象属性查询
var editQurProUrl=serverPath+'/businObjPro/queryById';
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
//查找模板对象属性
var tempObj=serverPath + "/businObj/queryTemplatePro";
//查找模板对象
var templateObj=serverPath + "/templateObj/query";

//模板对象全部数据
var templateObjPageUrl=serverPath + "/templateObj/queryPage";
//序列规则全部数据(供下拉框使用)
var sequenceRuleConfigPageUrl=serverPath+"/sequenceRule/queryPage";
//键值集合全部数据
var keySetPageUrl=serverPath+'/keySet/queryPage';
//数据集全部数据
var datasetConfigPageUrl=serverPath+'/dataSetConfig/queryPage'
var templateObjProPageUro=serverPath+"/templateObj/queryProPage"

//关联表

//serverPath+"/keySet/queryNumber";

//关联模板对象

//serverPath+"/keySet/queryKeySet",

//关联模板
// var belongModel=serverPath + "/templateObj/query";

//上方按钮事件
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
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增业务对象', '400px', '510px',function(){
            });
        },
        //新增业务对象属性
        addProp(){
            operateOPr=1;
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增对象属性', '800px', '500px', function () {});
        },
        //生效
        affectProp(){
            var data={
                "url":affectPropUrl,
                "jsonData":{rowId:basLeft.currentId},
                "obj":basTop
            };
            gmpAjax.showAjax(data,function(res){
                basLeft.searchLeft();
            })
        },
        //变更
        changeProp(){
            var data={
                "url":changeUrl,
                "jsonData":{rowId:basLeft.currentId},
                "obj":basTop
            };
            gmpAjax.showAjax(data,function(res){
                basLeft.searchLeft();
            })
        }
    }
});
//左侧表格
var basLeft = new Vue({
    "el": "#basLeft",
    data:  getData.dataObj({
        tableId:'obj',

    }),

    methods: {
        //编辑业务对象
        editEvent() {
            operate = 2;
            var htmlUrl = 'metadata-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑业务对象', '400px', '510px', function(){
                var data={
                    "url":editObjUrl,
                    "jsonData":{rowId:basLeft.currentVal.rowId},
                    "obj":basLeft
                };
                gmpAjax.showAjax(data,function(res){
                    //编辑拿到的数据
                    var data=res.data[0];
                    console.log(data);
                    em.ruleForm.codeInput = data.objectCode;  //对象代码
                    em.ruleForm.nameInput =data.objectName;//对象名称
                    em.$refs.table_1.setValue(data.relateTableRowId);
                    em.$refs.templateObj_1.setValue(data.relateTemplateObject);//关联模板对象
                    em.ruleForm.system=data.system;//所属系统
                    em.$refs.belongModule_1.setValue(data.belongModule);//所属模块
                    em.ruleForm.versionInput =data.version;//版本
                })
            });
        },
        //删除业务对象
        deleteEvent() {
            deleteObj.del(function(){
                var data={
                    "url":deleteUrl,
                    "jsonData":{rowId:basLeft.currentVal.rowId},
                    "obj":basLeft
                };
                gmpAjax.showAjax(data,function(res){
                    //分页跳回到第一页
                    basLeft.searchLeft();
                })
            })
        },
        //分页查询
        searchLeft(){
            queryData.getData(qurUrl,this.input,this,function(res){
                console.log(res);
                var data=res.resp.content.data.result
                if(data==null){
                    basRight.tableData=[];
                    basRightTop.tableData=[];
                }else{
                    basLeft.currentChange(basLeft.tableData[0]);
                    //basRight.currentRChange(basRightTop.tableData[0]);
                }

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
                basTop.addAttr=false;
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
                //关联模板对象rowId
                this.relateTemplateObject=row.relateTemplateObject;
                //查找右侧对象属性的数据
                basRight.searchRightTable();
                //查找右侧模板对象属性的数据
                basRightTop.searchRightTopEvent();

                //获取当前的关联表
                this.associatTable=row.associatTable;
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
        var args={"obj":{belongModule:"belongModule"},"objProp":{valueType:"valueType",wetherExpandPro:"proType"}};
        TableKeyValueSet.init(args);
        this.searchLeftTable();

        $(document).ready(function () {
            basLeft.leftHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basLeft.leftHeight = $(window).height() - 194;
        })


    },
    updated() {
        if(this.tableData!=null){
            this.FindLFirstDate(this.tableData[0]);
        }

    }
});

//对象的属性
var basRight=new Vue({
    "el": "#basRight",
    data: getData.dataObj({
         activeName:'first',
        tableId:'objProp',
    }),
    methods: {
        handleClick(tab, event) {

        },
        //不跳转到第一页查询
        searchRightTable() {
            pagingObj.Examples(qurProUrl,basLeft.currentId,this.input,this.pageSize,this.pageNum,this,function(res){
                //有数据选中第一行
                if(res.data!=null){
                    //按钮禁掉
                    $.each(res.data.result, function(index, item) {
                        if(basLeft.currentVal.disableButton==true) {
                            item.disableButton = true;
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
                //console.log(res);
                var data=res.resp.content.data;
                if(data!=null){
                    basRight.currentRChange(basRight.tableData[0]);
                }
            })
        },
        editProp(){
            operateOPr = 2;
            var htmlUrl = 'metadata-prop-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑对象属性', '800px', '500px', function () {
                var data={
                    "url":editQurProUrl,
                    "jsonData":{rowId: basRight.currentVal.rowId},
                    "obj":basRight
                };
                gmpAjax.showAjax(data, function (res) {
                    console.log(res);
                    ///编辑拿到的数据
                    var data = res.data[0];
                    proEm.addProForm.codeProInput=data.propertyCode;   //代码
                    proEm.addProForm.nameProInput=data.propertyName;   //名称
                    proEm.$refs.proType_1.setValue(data.wetherExpandPro);  //属性类型
                    proEm.$refs.tableField_1.setValue(data.relateTableColumn) ;  //关联表字段
                    //修改值类型和值类型来源下拉框  jms 2017/8/21
                    proEm.$refs.valueType_1.setValue(data.valueType);  //值类型
                    proEm.$refs.valueTypeOrigin_1.setValue(data.valueResourceType);   //值类型来源
                    proEm.$refs.valueOriginContent_1.setValue(data.valueResourceContent);     //值来源内容
                    proEm.addProForm.fieldAliasInput=data.fieldAlias; //字段别名
                })
            });
        },
        deleteProp(){
            deleteObj.del(function(){
                var data={
                    "url":deleteProUrl,
                    "jsonData":{rowId: basRight.rightVal},
                    "obj":basRight
                };
                gmpAjax.showAjax(data,function(res){
                    //分页查询
                    basRight.searchRightTable();
                })
            });
        },
        currentRChange(row, event, column) {
            // console.log(row)
            //点击拿到这条数据的值
            if (row != undefined) {
                this.currentVal = row;
                this.rightVal = row.rowId;

                //为了给下拉框塞值
                this.wetherExpandPro=row.wetherExpandPro;

                // console.log(this.rightVal)
            }
        },
        // extendOnly(row){
        //     if(row.wetherExpandPro =="true"){
        //         return "是"
        //     }else{
        //         return "否"
        //     }
        // },
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
            basRight.leftHeight = $(window).height() -260;
        });
        $(window).resize(function () {
            basRight.leftHeight = $(window).height() -260;
        });

    },
})
//模板的属性
var basRightTop = new Vue({
    "el": "#basRightTop",
    template:'#tempBlock',
    data:  getData.dataObj({

    }),
    methods:{
        //不分页查询
        searchRightTopEvent(){
            pagingObj.Examples(templateObjProPageUro,basLeft.relateTemplateObject,'','','',this,function(res){
                //console.log(res);
                //有数据选中第一行
                //var data=res.resp.content.data;
                //console.log(data);
                //if(data!=null){
                //    console.log(basRightTop.tableData)
                //    basRightTop.currentRChange(basRightTop.tableData[0]);
                //}
            })
        },
        headSort(column){//列头排序
            //pagingObj.headSorts(qurProUrl,basLeft.currentId,this.resInput,column,this);
        },
        currentChange(row, event, column) {
            console.log(row)
        },
    },
    created() {
        $(document).ready(function () {
            basRightTop.leftHeight = $(window).height() - 158;
        });
        $(window).resize(function () {
            basRightTop.leftHeight = $(window).height() - 158;
        });
        //var args={"objProp":{valueType:"valueType",valueResourceType:"valueTypeOrigin"}};
        //TableKeyValueSet.init(args);
    },
})