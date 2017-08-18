/**
 * Created by andim on 2017/8/2.
 */
//左边table
var functionBlock = new Vue({
    el:"#app",
    data:{
        loading:true,
        input:'',
        tableData:[],
        leftHeight:'',
        //url:serverPath+'/fronc/query',//查询所有功能块信息
        Selurl:serverPath+'/fronc/queryPage',//查询所有功能块信息
        deleteId:'',
        rowId:'',
        editObj:'',
        divIndex:'',
        pageNum:1,//当前页号
        pageSize:10,//每页显示数据条数
        allDate:0//共多少条数据
    },
    methods:{
        //查询
        get(){
            pagingObj.Example(this.Selurl,this.input,this.pageSize,this.pageNum,this,function(){
                functionBlock.click(functionBlock.tableData[0]);
                functionBlock.deleteId = functionBlock.tableData[0].rowId;
                properties.getRight(functionBlock.tableData[0].rowId);
            });
        },
        //编辑
        editBlock(){
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','编辑功能块','400px', '420px',function(){
                em.isEdit = true;
                em.formTable.codeInput=functionBlock.editObj.funcCode;
                em.formTable.nameInput=functionBlock.editObj.funcName;
                em.formTable.typeInput=functionBlock.editObj.funcType;
                em.dataId=functionBlock.editObj.relateBusiObj;
                em.formTable.tableInput=functionBlock.editObj.objectName;
                em.formTable.desp=functionBlock.editObj.desp;
                em.rowId=functionBlock.editObj.rowId;
            });

        },
        //删除
        del(){
            deleteObj.del(function(){
                functionBlock.$http.jsonp(serverPath+"/fronc/delete",{
                    rowId:functionBlock.deleteId
                },{
                    jsonp:'callback'
                }).then(function(res){
                    ibcpLayer.ShowOK(res.data.message);
                    functionBlock.get();
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
    data:{
        loading:true,
        tableData:[],
        findRightDataUrl:serverPath+'/fronFuncPro/queryProPage',//查询指定ID功能块的属性信息
        rightHeight:'',
        rightInput:'',//右边表输入框
        rowId:'',//选中属性行ID
        funcId:'',//功能块ID
        divIndex:'',
        pageNum:1,//当前页号
        pageSize:10,//每页显示数据条数
        allDate:0//共多少条数据
    },
    methods:{
        handleClick(){//查看按钮
            this.divIndex = ibcpLayer.ShowDiv('attribute-details.html','属性明细','400px', '400px',function(){

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
                properties.$http.jsonp(topButtonObj.delUrl,{
                    rowId:properties.rowId
                },{
                    jsonp:'callback'
                }).then(function(res){
                    properties.getRight(properties.funcId);
                    ibcpLayer.ShowOK(res.data.message);
                },function(){
                    alert("删除失败")
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
            this.divIndex = ibcpLayer.ShowDiv('add-block.html','新增功能块','400px', '420px',function(){
                em.isEdit = false;
            });
        },
        editBlock(){
            this.divIndex = ibcpLayer.ShowDiv('demo.html','编辑功能块','400px', '400px',function(){
                em.isEdit = true;
                em.formTable.codeInput=functionBlock.editObj.funcCode;
                em.formTable.nameInput=functionBlock.editObj.funcName;
                em.formTable.typeInput=functionBlock.editObj.funcType;
                em.dataId=functionBlock.editObj.relateBusiObj;
                em.formTable.tableInput=functionBlock.editObj.objectName;
                em.formTable.desp=functionBlock.editObj.desp;
                em.rowId=functionBlock.editObj.rowId;
            });

        },
        del(){
            functionBlock.del();
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
                ibcpLayer.ShowOK(res.data.message);
            },function(){
                alert("删除失败")
            })
        }
    }
})