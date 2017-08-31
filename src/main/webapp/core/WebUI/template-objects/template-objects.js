/**
 * Created by admin on 2017/8/27.
 */

//模板对象查询接口
    var queryTemp=serverPath + "/templateObj/queryPage";
//模板对象新增接口
    var addTempObj=serverPath + "/templateObj/add";
//模板编辑查询接口
    var addTempObjQuery=serverPath + "/templateObj/queryById";
//模板对象编辑接口
    var editTempObj=serverPath + "/templateObj/modify";
//模板对象删除接口
    var deleteTempObj=serverPath + "/templateObj/delete";
//模板对象属性查询接口
    var queryObjTemp=serverPath + "/templateObj/queryProPage";

//模板对象属性新增接口
    var addObjTemp=serverPath + "/templateObjPro/add";
//模板对象属性编辑查询接口
    var editQueryObjTemp=serverPath + "/templateObjPro/queryById";
//模板对象属性编辑接口
    var editObjTemp=serverPath + "/templateObjPro/modify";
//模板对象属性删除接口
    var deleteObjTemp=serverPath + "/templateObjPro/delete";


var basTop = new Vue({
    el: '#basTop',
    data: {
        addTempAttr:false,//新增属性开始禁用
    },
    methods: {
        //新增模板对象
        addTemp(){
            operate = 1;
            var htmlUrl = 'template-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增模板对象', '400px', '420px',function(){});
        },
        //新增模板对象属性
        addTempProp(){
            operateOPr=1;
            var htmlUrl = 'template-add-prop.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '新增模板对象属性', '400px', '480px', function () {});
        },

    }
});


var basLeft = new Vue({
    "el": "#basLeft",
    data: getData.dataObj({
    }),
    methods: {
        //不分页查询
        searchLeft(){
            pagingObj.Example(queryTemp,this.input,this.pageSize,this.pageNum,this,function(res){
                //有数据选中第一行
                basLeft.currentChange(basLeft.tableData[0]);
            })
        },
        //分页查询
        searchLeftTable(){
            queryData.getData(queryTemp,this.input,this,function(res){
                basLeft.currentChange(basLeft.tableData[0]);
            })
        },
        //编辑事件
        editEvent(){
            operate = 2;
            var htmlUrl = 'template-add.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑模板对象', '400px', '420px',function(){
                gmpAjax.showAjax(addTempObjQuery,{rowId:basLeft.currentId},function(res){
                    //编辑拿到的数据
                    console.log(res);
                    var data=res.resp.content.data.result[0];
                    console.log(data);
                    addTemp.addTempObj.codeInput=data.templateCode;
                    addTemp.addTempObj.nameInput=data.templateName;
                    addTemp.addTempObj.comContent=data.desp;
                    addTemp.addTempObj.modules=data.belongModule;
                    addTemp.addTempObj.system=data.belongSystem;
                })
            });
        },
        //删除事件
        deleteEvent(){
            deleteObj.del(function(){
                gmpAjax.showAjax(deleteTempObj,{rowId:basLeft.currentId},function(res){
                    showMsg.MsgOk(basLeft,res);
                    queryData.getData(queryTemp,basLeft.input,basLeft,function(res){
                        basLeft.currentChange(basLeft.tableData[0]);
                    })
                })
            })
        },
        //表格点击事件
        currentChange(row){
            console.log(row);
            if(row!=undefined){
                //左边这一行的数据的ID
                this.currentId = row.rowId;
                //查找右边表的数据
                queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){
                    //console.log(res)
                    //选中右边第一行
                    var data=res.resp.content.data.result;
                    if(data!=null){
                        basRight.currentRChange(basRight.tableData[0]);
                    }
                })
            }
        },
        //将选中行变颜色
        FindLFirstDate(row){
            this.$refs.tableData.setCurrentRow(row);
        },
        //排序事件
        headSort(column){
            pagingObj.headSort(queryTemp,this.input,this.pageSize,this.pageNum,column,this);
        },
        //选择每页多少条数据
        handleSizeChange(val) {
            this.pageSize=val;
            //不分页查询
            this.searchLeft();
        },
        //选择当前页第几页
        handleCurrentChange(val) {
            this.pageNum=val;
            //不分页查询
            this.searchLeft();
        },
    },
    //表格高度
    created(){
        this.searchLeft();
        $(document).ready(function () {
            basLeft.leftHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basLeft.leftHeight = $(window).height() - 194;
        })
    },
    //页面一进入第一行高亮显示
    updated() {
        this.FindLFirstDate(this.tableData[0]);
    }
});



var basRight = new Vue({
    "el": "#basRight",
    data: getData.dataObj({
        tableId:'objTempProp',
        }),
    methods: {
        //不分页查询
        searchRight(){
            pagingObj.Examples(queryObjTemp,basLeft.currentId,this.input,this.pageSize,this.pageNum,this,function(res){
                //有数据选中第一行
                basRight.currentRChange(basRight.tableData[0]);
            })
        },
        //分页查询
        searchRightTable(){
            queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){
                basRight.currentRChange(basRight.tableData[0]);
            })
        },
        //右边点击事件
        currentRChange(row){
            console.log(row);
            this.currentId=row.rowId;
        },
        //编辑事件
        editProp(){
            operateOPr=2;
            var htmlUrl = 'template-add-prop.html';
            divIndex = ibcpLayer.ShowDiv(htmlUrl, '编辑模板对象属性', '400px', '480px',function() {
                gmpAjax.showAjax(editQueryObjTemp, {rowId: basRight.currentId}, function (res) {
                    //编辑拿到的数据
                    var data = res.resp.content.data[0];
                    console.log(data);
                    addTempProp.addTempPropObj.codeInput=data.code;
                    addTempProp.addTempPropObj.engNameInput=data.ename;
                    addTempProp.addTempPropObj.chnNameInput=data.cname;
                    addTempProp.$refs.vtype.value=data.valueType;
                    addTempProp.addTempPropObj.default=data.defaultValue;
                    addTempProp.addTempPropObj.comContent=data.desp;
                })
            })
        },
        //删除事件
        deleteProp(){
            deleteObj.del(function(){
                gmpAjax.showAjax(deleteObjTemp,{rowId: basRight.currentId},function(res){
                    showMsg.MsgOk(basRight,res);
                    //分页查询
                    queryData.getDatas(queryObjTemp,basRight.input,basLeft.currentId,basRight,function(res){})
                })
            })
        },
        //将选中行变颜色
        FindLFirstDate(row){
            this.$refs.tableData.setCurrentRow(row);
        },
        //排序事件
        headSort(column){
            pagingObj.headSorts(queryObjTemp,basLeft.currentId,this.input,column,this);
        },
        //选择每页多少条数据
        handleSizeChange(val) {
            this.pageSize=val;
            //不分页查询
            this.searchRight();
        },
        //选择当前页第几页
        handleCurrentChange(val) {
            this.pageNum=val;
            //不分页查询
            this.searchRight();
        },
    },
    //表格高度
    created(){
        $(document).ready(function () {
            basRight.leftHeight = $(window).height() - 194;
        });
        $(window).resize(function () {
            basRight.leftHeight = $(window).height() - 194;
        });
        var args={"objTempProp":{valueType:"valueType"}};
        TableKeyValueSet.init(args);
    },
    //页面一进入第一行高亮显示
    //updated() {
    //    this.FindLFirstDate(this.tableData[0]);
    //}
});