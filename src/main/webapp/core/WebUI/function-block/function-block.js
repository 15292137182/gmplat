/**
 * Created by andim on 2017/8/2.
 */
//左边table
var functionBlock = new Vue({
    el:"#app",
    /**
     * tsj 07/8/29 data代码重构
     **/
    data:getData.dataObj({
        tableId:'Block',
        Selurl:serverPath+'/fronc/queryPage',//查询所有功能块信息
        selUrlId:serverPath+'/fronc/queryById',//根据rowId查所有信息
        deleteId:'',
        rowId:'',
        editObj:'',
        divIndex:'',
    }),
    methods:{
        //查询
        get(){
            pagingObj.Example(this.Selurl,this.input,this.pageSize,this.pageNum,this,function(){
                functionBlock.click(functionBlock.tableData[0]);
                if(functionBlock.tableData.length>0){
                    functionBlock.deleteId = functionBlock.tableData[0].rowId;
                    properties.getRight(functionBlock.tableData[0].rowId);
                }else{
                    properties.loading = false;
                }
            });
        },
        //编辑
        editBlock(){
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','编辑功能块','400px', '540px',function(){
                /**
                 * tsj 07/8/29 编辑功能块ajax代码重构，增加所属模块，系统字段
                 **/
                var data = {
                    "url":functionBlock.selUrlId,
                    "jsonData":{
                        rowId:functionBlock.rowId
                    },
                    "obj":functionBlock
                }
                gmpAjax.showAjax(data,function(res){
                    var data = res.data;
                        em.isEdit = true;
                        em.formTable.codeInput=data[0].funcCode;
                        em.formTable.nameInput=data[0].funcName;
                        em.$refs.fbtype.value=data[0].funcType;
                        em.dataId=data[0].relateBusiObj;
                        em.$refs.conObj.connectObj=data[0].relateBusiObj;
                        em.formTable.Module=data[0].belongModule;
                        em.formTable.System=data[0].belongSystem;
                        em.formTable.desp=data[0].desp;
                        em.rowId=data[0].rowId;
                })
            });
        },
        //删除
        del(){
            /**
             * tsj 07/8/30删除功能块ajax代码重构
             **/
            var data = {
                "url":serverPath+"/fronc/delete",
                "jsonData":{
                    rowId:functionBlock.deleteId
                },
                "obj":functionBlock
            }
            deleteObj.del(function(){
                gmpAjax.showAjax(data,function(res){
                    queryData.getData(functionBlock.Selurl,functionBlock.input,functionBlock,function(res){
                                 properties.getRight(functionBlock.tableData[0].rowId);
                             })
                })
            })
        },
        //点击
        click(row, event, column){
            if(row){
                properties.rightInput = '';
                this.rowId = row.rowId;
                properties.getRight(row.rowId);
                this.deleteId = row.rowId;
                this.editObj = row;
            }
        },
        //选中
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        },
        //分页显示数据条数变化
        handleSizeChange(val){
            this.pageSize=val;
            this.get();
        },
        //分页页数变化
        handleCurrentChange(val){
            this.pageNum=val;
            this.get();
        },
        headSort(column){//列头排序
            console.log(column.prop);
            pagingObj.headSort(this.Selurl,this.input,this.pageSize,this.pageNum,column,this,function(res){
                properties.getRight(functionBlock.tableData[0].rowId);
            });
        }
    },
    created(){
        var args={"Block":{funcType:"functionBlockType"},"blockAttribute":{displayWidget:"showControl"}};
        TableKeyValueSet.init(args);
        this.get();
        $(document).ready(function(){
            functionBlock.leftHeight=$(window).height()-190;
        });
        $(window).resize(function(){
            functionBlock.leftHeight=$(window).height()-190;
        })
    },
    updated(){
        if(this.tableData.length>0){
            this.FindOk(this.tableData[0]);
        }
    }
});

//右边table
var properties = new Vue({
    el:'#right',
    /**
     * tsj 07/8/29 data代码重构
     **/
    data:getData.dataObj({
        tableId:'blockAttribute',
        findRightDataUrl:serverPath+'/fronFuncPro/queryProPage',//查询指定ID功能块的属性信息
        rightHeight:'',
        rightInput:'',//右边表输入框
        rowId:'',//选中属性行ID
        funcId:'',//功能块ID
        divIndex:'',
    }),
    methods:{
        handleClick(){//查看按钮
            this.divIndex = ibcpLayer.ShowDiv('attribute-details.html','属性明细','645px', '600px',function(){

            });
        },
        //点击右边table
        clickRightTable(row, event, column){
            if(row){
                this.rowId = row.rowId;
                this.funcId = row.funcRowId;
            }
        },
        //选中
        FindOk(row){
            this.$refs.myTable.setCurrentRow(row);
        },
        //查询
        getRight(id){
            pagingObj.Examples(this.findRightDataUrl,id,this.rightInput,this.pageSize,this.pageNum,this,function(){
                properties.clickRightTable(properties.tableData[0]);
            });
        },
        //分页查询
        getRightData(){
            pagingObj.Examples(this.findRightDataUrl,functionBlock.rowId,this.rightInput,this.pageSize,this.pageNum,this,function(){
                properties.clickRightTable(properties.tableData[0]);
            });
        },
        //分页显示数据条数变化
        handleSizeChange(val){
            this.pageSize=val;
            this.getRightData();
        },
        //分页页数变化
        handleCurrentChange(val){
            this.pageNum=val;
            this.getRightData();
        },
        headSort(column){//列头排序
            pagingObj.headSorts(this.findRightDataUrl,functionBlock.rowId,this.input,column,this);
        },
        //编辑属性
        editData(){
            topButtonObj.rowObjId = functionBlock.editObj.rowId;
            topButtonObj.objId = functionBlock.editObj.relateBusiObj;
            topButtonObj.isEdit = true;
            topButtonObj.divIndex = ibcpLayer.ShowIframe('add-data.html','编辑属性','500px', '550px')
        },
        //删除属性
        delData(){
            deleteObj.del(function(){
                /**
                 * tsj 07/8/29 ajax代码重构
                 **/
                var data = {
                    "url":topButtonObj.delUrl,
                    "jsonData":{
                        rowId:properties.rowId
                    },
                    "obj":topButtonObj
                }
                gmpAjax.showAjax(data,function(res){
                    queryData.getDatas(properties.findRightDataUrl,properties.rightInput,properties.funcId,properties);
                    //showMsg.MsgOk(properties,res);
                })
            })
        },
        wetherDisplay(row){//是否显示
            if(row.wetherDisplay =="true"){
                return "是"
            }else{
                return "否"
            }
        },
        wetherReadonly(row){//是否只读
            if(row.wetherReadonly =="true"){
                return "是"
            }else{
                return "否"
            }
        },
        allowEmpty(row){//是否为空
            if(row.allowEmpty =="true"){
                return "是"
            }else{
                return "否"
            }
        },
    },
    created(){
        $(document).ready(function(){
            properties.rightHeight=$(window).height()-190;
        });
        $(window).resize(function(){
            properties.rightHeight=$(window).height()-190;
        })
    },
    updated(){
        if(this.tableData.length>0){
            this.FindOk(this.tableData[0]);
        }
    }
});

//按钮
var topButtonObj = new Vue({
    el:'#myButton',
    data:{
        divIndex:'',
        rowObjId:'',//功能块ID
        objId:'',//关联业务对象ID
        isEdit:'',//是否编辑
        delUrl:serverPath+'/fronFuncPro/delete'//删除属性接口
    },
    methods: {
        //功能块
        addBlock(){
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','新增功能块','400px', '540px',function(){
                em.isEdit = false;
            });
        },
        //功能块属性
        addData(){
            topButtonObj.rowObjId = functionBlock.editObj.rowId;
            topButtonObj.objId = functionBlock.editObj.relateBusiObj;
            this.isEdit = false;
            topButtonObj.divIndex = ibcpLayer.ShowIframe('add-data.html','新增属性','500px', '550px')
        },
        editData(){
            topButtonObj.rowObjId = functionBlock.editObj.rowId;
            topButtonObj.objId = functionBlock.editObj.relateBusiObj;
            this.isEdit = true;
            topButtonObj.divIndex = ibcpLayer.ShowIframe('add-data.html','编辑属性','500px', '550px')
        },
        delData(){
            this.$http.jsonp(this.delUrl,{
                rowId:properties.rowId
            },{
                jsonp:'callback'
            }).then(function(res){
                properties.getRight(properties.funcId);
                showMsg.MsgOk(functionBlock,res);
            },function(){
                showMsg.MsgError(functionBlock);
            })
        }
    }
})